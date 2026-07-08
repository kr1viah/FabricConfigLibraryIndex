const activeFilters = new Map();
let libraryData = {};

fetch('https://kr1v.net/libs/libs.json').then(r => r.json()).then(data => { libraryData = data; });
//fetch('libs.json').then(r => r.json()).then(data => { libraryData = data; });

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
}

function resetFilters() {
	document.querySelectorAll('span.tag.active').forEach(t => t.classList.remove('active'));
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

function filterPanels() {
	document.querySelectorAll('.library_grid .panel').forEach(panel => {
		const lib = libraryData[panel.id];
		if (!lib) return;
		const visible = [...activeFilters.entries()]
			.every(([cat, vals]) => panelMatches(lib, cat, vals));
		panel.style.display = visible ? '' : 'none';
	});
}

const meowBegin = ['m']
const meowContinuation = ['e', 'o', 'a', 'r', 'p', 'w']
const meowEnding = ['w', 'r']

const meowFaceyEyes = [
	['>', '<'],
	['^', '^'],
	['U', 'U'],
	['O', 'O'],
	['@', '@']
]
const meowFaceyMouth = ['~', '-', 'w', 'u', '..']

const emojies = [':3', ':D', 'x3', 'c:']

function randarr(array) {
	return array[Math.floor(Math.random() * array.length)]
}

function getMoewEmoji() {
	const eyes = randarr(meowFaceyEyes)
	return eyes[0] + randarr(meowFaceyMouth) + eyes[1]
}

function getMeow() {
	let meow = randarr(meowBegin).repeat(1 + Math.floor(Math.random() * 3))
	let len = 3 + Math.floor(Math.random() * 6)
	for (let i = 0; i < len; ++i) {
		meow += randarr(meowContinuation)
	}

	len = 1 + Math.floor(Math.random() * 3)
	for (let i = 0; i < len; ++i) {
		meow += randarr(meowEnding)
	}
	return meow
}

function getThing() {
	const i = Math.random()
	if (i > 0.4) {
		return getMeow()
	}
	if (i > 0.35) {
		return "pu" + "r".repeat(2 + Math.floor(Math.random() * 4))
	}
	if (i > 0.3) {
		return "ny" + "a".repeat(1 + Math.floor(Math.random() * 7))
	}
	if (i > 0.15) {
		return randarr(emojies)
	}
	return getMoewEmoji()
}

function onLoad() {
	const meowPanel = document.getElementById("meow-panel");
	let meow = ""
	for (let i = 0; i < 100; ++i) {
		meow += getThing() + ' '
	}
	meowPanel.innerHTML = meow;
}

