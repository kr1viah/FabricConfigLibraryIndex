const activeFilters = new Map();
let libraryData = {};
let searchQuery = '';

fetch('https://kr1v.net/libs/libs.json').then(r => r.json()).then(data => { libraryData = data; });

function toggleFilter(category, value) {
    const span = event.currentTarget;
    if (!activeFilters.has(category)) activeFilters.set(category, new Set());
    const set = activeFilters.get(category);

    if (set.has(value)) {
        set.delete(value);
        span.classList.remove('active');
    } else {
        set.add(value);
        span.classList.add('active');
    }

    if (set.size === 0) activeFilters.delete(category);
    filterPanels();
    updateFilterBadge(span, category, set);
}

function updateFilterBadge(span, category, set) {
    const existing = span.querySelector('.filter-badge');
    if (set && set.size > 0) {
        if (!existing) {
            const badge = document.createElement('span');
            badge.className = 'filter-badge';
            badge.textContent = set.size;
            span.appendChild(badge);
        } else {
            existing.textContent = set.size;
        }
    } else if (existing) {
        existing.remove();
    }
}

function resetFilters() {
    document.querySelectorAll('span.tag.active').forEach(t => {
        t.classList.remove('active');
        const badge = t.querySelector('.filter-badge');
        if (badge) badge.remove();
    });
    activeFilters.clear();
    filterPanels();
}

function panelMatches(lib, category, values) {
    switch (category) {
        case 'versions':
            return [...values].every(v => lib.versions?.includes(v));

        case 'side':
            return [...values].every(v => lib.side === v || lib.side === 'BOTH');

        case 'extraConfigTypes':
            return [...values].every(v => lib.extraConfigTypes?.includes(v));

        case 'extraFeatures':
            return [...values].every(v => lib.extraFeatures?.includes(v));

        case 'configFormats':
            return [...values].some(v => lib.configFormats?.includes(v));

        case 'manualInitialization':
            return values.has(lib.manualInitialization);

        case 'configMethod.instance':
            if (!lib.configMethod) return false;
            const memberType = String(lib.configMethod.memberType);
            if (!memberType) return false;
            if (memberType === "EITHER") return true;
            if (values.has("true")) return memberType === "INSTANCE";
            return memberType === "STATIC";

        case 'configMethod.waaas': {
            if (!Array.isArray(lib.configMethod?.waaas)) return false;
            return [...values].some(v => lib.configMethod?.waaas.some(s => s.name === v));
        }
        case 'uiMethod':
            return [...values].some(v => lib.uiMethod === v)

        default:
            return true;
    }
}

function searchLibraries(query) {
    searchQuery = query.toLowerCase().trim();
    filterPanels();
}

function filterPanels() {
    document.querySelectorAll('.library-grid .library-card').forEach(panel => {
        const lib = libraryData[panel.id];
        if (!lib) return;
        const matchesSearch = !searchQuery ||
            lib.name?.toLowerCase().includes(searchQuery) ||
            lib.id?.toLowerCase().includes(searchQuery);
        if (!matchesSearch) {
            panel.style.display = 'none';
            return;
        }
        const visible = [...activeFilters.entries()]
            .every(([cat, vals]) => panelMatches(lib, cat, vals));
        panel.style.display = visible ? '' : 'none';
    });
}

// Smooth scroll for anchor links
document.addEventListener('click', e => {
    const target = e.target.closest('a[href^="#"]');
    if (target) {
        e.preventDefault();
        const id = target.getAttribute('href').slice(1);
        const el = document.getElementById(id);
        if (el) {
            el.scrollIntoView({ behavior: 'smooth', block: 'start' });
        }
    }
});
