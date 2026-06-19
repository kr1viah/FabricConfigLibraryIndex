package kr1v.index

import com.google.gson.GsonBuilder
import kr1v.index.libs.Libraries
import kr1v.index.util.*
import kr1v.index.util.ConfigMethod.{TypeOfClass, Waaa}
import scalatags.Text.all.*
import scalatags.Text.tags2.time
import java.nio.file.{Files, Path}
import java.time.format.DateTimeFormatter
import java.util.Base64
import scala.collection.mutable
import scala.jdk.CollectionConverters.*
import scala.math.abs
import scala.util.Random

object Main:

  private def readResource(path: String): String =
    val stream = getClass.getResourceAsStream(path)
    if stream == null then throw RuntimeException(s"Resource not found: $path")
    try new java.util.Scanner(stream).useDelimiter("\\A").next()
    finally stream.close()

  private val css: String = readResource("/main.css")
  private val js: String  = readResource("/main.js")

  // ──────────────────────────────────────────────
  //  Helpers
  // ──────────────────────────────────────────────

  private def libraryTags(lib: ConfigLibrary): Seq[(String, String)] =
    val buf = mutable.ArrayBuffer.empty[(String, String)]
    if lib.side.client  then buf += "Client" -> ""
    if lib.side.server  then buf += "Server" -> ""
    if lib.`type`.ui    then buf += "Ui"     -> ""
    if lib.`type`.loader then buf += "Loader" -> ""
    lib.extraFeatures.forEach(f => buf += f.name -> f.description)
    lib.configFormats.forEach(f  => buf += f.name -> "")
    buf.toSeq

  private def tagEl(
      titleStr: String,
      tooltip: String      = "",
      click: String        = "",
  ): Tag =
    span(cls := "tag",
      if click.nonEmpty then onclick := click else (),
      h6(
        if tooltip.nonEmpty then cls := "hoverable" else (),
        if tooltip.nonEmpty then attr("title") := tooltip else (),
        titleStr,
      ),
    )

  // ──────────────────────────────────────────────
  //  Card component
  // ──────────────────────────────────────────────

  private def card(lib: ConfigLibrary): Tag =
    div(cls := "library-card", id := lib.id,

      // image header
      div(cls := "card-image",
        Option(getClass.getResourceAsStream(s"/screenshots/${lib.modrinthSlug}.png")).map { s =>
          try
            val b64 = Base64.getEncoder.encodeToString(s.readAllBytes())
            img(src := s"data:image/png;base64,$b64", alt := s"${lib.name} screenshot")
          finally s.close()
        }.getOrElse(span()),
        div(cls := "card-image-overlay"),
        span(cls := "card-badge", "Config Library"),
        if lib.`type`.loader then span(cls := "card-type-icon", "Loader")
        else if lib.`type`.ui then span(cls := "card-type-icon", "UI")
        else (),
      ),

      // body
      div(cls := "card-body",
        h1(lib.name),
        h2("(", code(lib.id), ")"),

        // tags
        div(cls := "card-tags",
          libraryTags(lib).map { (n, d) => tagEl(n, d) },
        ),

        // details
        div(cls := "card-details",

          // init
          div(cls := "card-detail",
            span(cls := "detail-label", "Init"),
            span(cls := "detail-value", lib.manualInitialization.name),
          ),

          // config method
          configDetailBlock(lib),

          // deps
          if !lib.dependencies.isEmpty then
            div(cls := "card-detail",
              span(cls := "detail-label", "Deps"),
              span(cls := "detail-value",
                raw(lib.dependencies.asScala.map(d =>
                  s"""<a href="${d.url}">${d.name}</a>"""
                ).mkString(" · ")),
              ),
            )
          else (),

          // extra config types
          if !lib.extraConfigTypes.isEmpty then
            div(cls := "card-detail",
              span(cls := "detail-label", "Types"),
              span(cls := "detail-value",
                raw(lib.extraConfigTypes.asScala.map { t =>
                  if t.description.nonEmpty then
                    s"""<span class="hoverable" title="${t.description}">${t.name}</span>"""
                  else t.name
                }.mkString(", ")),
              ),
            )
          else (),

          // UI method
          if lib.`type`.ui then
            div(cls := "card-detail",
              span(cls := "detail-label", "UI"),
              span(cls := "detail-value",
                if lib.uiMethod.description.nonEmpty then
                  span(cls := "hoverable", attr("title") := lib.uiMethod.description, lib.uiMethod.name)
                else span(lib.uiMethod.name),
              ),
            )
          else (),
        ),

        // versions
        versionChips(lib),

        // notes
        if !lib.notes.isEmpty then
          div(cls := "card-notes",
            ul(lib.notes.asScala.toSeq.map(n => li(n))),
          )
        else (),

        // example config (loader only)
        exampleBlock(lib),

        // footer
        div(cls := "card-footer",
          if lib.modrinthSlug != null then
            a(href := s"https://modrinth.com/mod/${lib.modrinthSlug}", "Modrinth")
          else (),
          a(href := s"https://github.com/kr1viah/FabricConfigLibraryIndex/blob/master/src/main/java/kr1v/index/libs/${lib.getClass.getSimpleName}.java", "Entry"),
          a(href := lib.source, "Source"),
        ),
      ),
    )

  // ── config method + examples ──

  private def configDetailBlock(lib: ConfigLibrary): Tag =
    if lib.configMethod == ConfigMethod.NOT_AVAILABLE then span(style := "display: none;")
    else if lib.configMethod == ConfigMethod.UNKNOWN then
      div(cls := "card-detail",
        span(cls := "detail-label", "Config"),
        span(cls := "detail-value", "Unknown"),
      )
    else
      val m = lib.configMethod
      div(
        div(cls := "card-detail",
          span(cls := "detail-label", "Config"),
          span(cls := "detail-value",
            if m.typeOfClass != TypeOfClass.NONE then
              val pre = if m.typeOfClass == TypeOfClass.EXTENDING || m.typeOfClass == TypeOfClass.ANNOTATED then "an" else "a"
              s"$pre ${m.typeOfClass.name.toLowerCase} class "
            else "",
            if m.waaas.size != 1 then " (either " else " (",
            m.waaas.asScala.toSeq.zipWithIndex.map { (w, i) =>
              val prim = w == Waaa.ANNOTATED_PRIMITIVE
              val sep  = if i < m.waaas.size - 1 then ", or " else ""
              val sb   = StringBuilder()
              if prim then sb.append("annotated ")
              sb.append(m.memberType.description.toLowerCase)
              if !prim then sb.append(" ").append(w.methodDescription)
              sb.append(sep)
              sb.toString
            }.mkString,
            ")",
          ),
        ),
        {
          val rng  = Random(lib.name.hashCode)
          val n    = m.waaas.size
          val exs  = mutable.LinkedHashSet.empty[String]
          while exs.size < 3 do
            val pool = m.waaas.get(abs(rng.nextInt() % n)).examples
            exs += pool(abs(rng.nextInt() % pool.length))
          div(cls := "card-detail",
            span(cls := "detail-label", "Exps"),
            span(cls := "detail-value",
              exs.toSeq.map(e => code(e)),
            ),
          )
        },
      )

  // ── version chips ──

  private def versionChips(lib: ConfigLibrary): Tag =
    val raw: java.util.List[String] = lib.versions
    val vv = Versions.condensVersions(raw).asScala
    div(cls := "card-versions",
      vv.toSeq.map(v => span(cls := "version-chip", v)),
    )

  // ── example config block ──

  private def exampleBlock(lib: ConfigLibrary): Tag =
    if !lib.`type`.loader then span(style := "display: none;")
    else
      val toggleJs = raw"""
        const el = document.getElementById("example-config-${lib.id}");
        el.style.display = el.style.display === "none" ? "block" : "none";
      """.toString.trim
      div(
        span(cls := "example-toggle", onclick := toggleJs, "▼ show example config"),
        pre(cls := "codeSamp", id := s"example-config-${lib.id}", style := "display: none;",
          tag("samp")(
            if lib.exampleConfigClass == null then
              raw("""None yet! Contribute by providing an example <a href="https://github.com/kr1viah/FabricConfigLibraryIndex/blob/master/src/main/java/kr1v/index/libs/""" +
                lib.getClass.getSimpleName + """.java">here</a>.""")
            else raw(lib.exampleConfigClass.replace("\t", "    ")),
          ),
        ),
      )

  // ──────────────────────────────────────────────
  //  Page:  Index
  // ──────────────────────────────────────────────

  private def renderIndex(): String =
    val libs = Libraries.CONFIG_LIBRARIES().asScala.toSeq
    val rng  = Random(libs.hashCode())

    html(lang := "en",
      head(
        meta(charset := "UTF-8"),
        meta(name := "viewport", content := "width=device-width, initial-scale=1.0"),
        tag("style")(raw(css)),
        tag("title")("Fabric Config Library Index"),
      ),
      body(

        // ── hero ──
        header(cls := "hero",
          h1("Fabric Config Library Index"),
          p(cls := "subtitle",
            "Browse · Search · Discover — ",
            span("The Ultimate Config Library Compendium"),
          ),
          div(cls := "hero-stats",
            heroStat(libs.size.toString, "Libraries"),
            heroStat(libs.count(l => l.`type`.ui).toString, "UI Libraries"),
            heroStat(libs.count(l => l.`type`.loader).toString, "Loaders"),
            heroStat(libs.flatMap(l => l.configFormats.asScala).distinct.size.toString, "Formats"),
          ),
        ),

        // ── main layout ──
        div(cls := "main-layout",

          // sidebar
          div(cls := "sidebar",
            div(cls := "panel",
              input(`type` := "text", placeholder := "Search libraries...",
                onkeyup := "searchLibraries(this.value)"),
              div(cls := "filter-row",
                h4("Versions"),
                span(cls := "tag reset-btn", onclick := "resetFilters()", h6("Reset")),
              ),
              Versions.ALL_SET.asScala.toSeq.reverse.map { vs =>
                frag(
                  h5(vs.get(0)),
                  vs.asScala.toSeq.map(v => tagEl(v, click = s"toggleFilter('versions', '$v')")),
                )
              },
              h4("Side"),
              tagEl("Client", click = "toggleFilter('side', 'CLIENT')"),
              tagEl("Server", click = "toggleFilter('side', 'SERVER')"),
              h4("Config types"),
              ConfigType.values().map(t => tagEl(t.name, t.description, s"toggleFilter('extraConfigTypes', '$t')")),
              h4("Features"),
              Feature.values().map(f => tagEl(f.name, f.description, s"toggleFilter('extraFeatures', '$f')")),
              h4("Config formats"),
              ConfigFormat.values().filter(_ != ConfigFormat.NOT_AVAILABLE)
                .map(c => tagEl(c.name, click = s"toggleFilter('configFormats', '$c')")),
              h4("Init mode"),
              InitMode.values().filter(m => m != InitMode.NOT_AVAILABLE && m != InitMode.UNKNOWN)
                .map(m => tagEl(m.name, click = s"toggleFilter('manualInitialization', '$m')")),
              h4("Config method"),
              h5("Field kind"),
              tagEl("instance", click = "toggleFilter('configMethod.instance', 'true')"),
              tagEl("static",   click = "toggleFilter('configMethod.instance', 'false')"),
              h5("Type of fields"),
              Waaa.ENTRIES.asScala.toSeq.map { (key, exs) =>
                tagEl(key, s"Examples: ${exs.asScala.mkString(", ")}", s"toggleFilter('configMethod.waaas', '$key')")
              },
              h4("UI Method"),
              UiMethod.values().map(u => tagEl(u.name, u.description, s"toggleFilter('uiMethod', '$u')")),
            ),
            div(cls := "panel sidebar-footer",
              div(
                a(href := "https://kr1v.net/libs/libs.json", "JSON"),
                " · ",
                a(href := "https://kr1v.net/libs/facts", "Facts"),
              ),
              div(cls := "lib-count", s"${libs.size} libs"),
            ),
          ),

          // grid
          div(cls := "library-grid",
            Random.shuffle(libs).map(card),
          ),
        ),

        script(raw(js)),
      ),
    ).render

  private def heroStat(num: String, label: String): Tag =
    div(cls := "hero-stat",
      span(cls := "stat-number", num),
      span(cls := "stat-label", label),
    )

  // ──────────────────────────────────────────────
  //  Page:  Facts list
  // ──────────────────────────────────────────────

  private def renderFacts(): String =
    val facts = Facts.facts().asScala.toSeq
    val df    = DateTimeFormatter.ofPattern("dd MMM uuuu")

    html(lang := "en",
      head(
        meta(charset := "UTF-8"),
        meta(name := "viewport", content := "width=device-width, initial-scale=1.0"),
        tag("style")(raw(css)),
        tag("title")("Fabric Config Library Facts"),
        meta(attr("property") := "og:title",        content := "Fabric Config Library Facts"),
        meta(attr("property") := "og:description",  content := "\"Daily\" config library facts"),
        meta(attr("property") := "og:url",           content := "https://kr1v.net/libs/facts"),
        meta(attr("property") := "og:type",          content := "article"),
      ),
      body(cls := "facts-page",
        div(cls := "panel",
          h4(a(href := "https://kr1v.net/libs", "Go to index")),
          h4("Have a suggestion? Send me a dm on discord: ", code(".kr1v")),
        ),
        facts.reverse.map { fact =>
          val day = facts.indexOf(fact) + 1
          div(cls := "panel", id := s"day-$day",
            div(style := "opacity: 0.75; margin-bottom: 6px;",
              h3(style := "display: inline-block; margin: 0;",
                s"Day $day: ${fact.title()} (",
                time(attr("datetime") := fact.date().toString, fact.date().format(df)),
                ")",
              ),
              h5(style := "user-select: none; margin: 0; cursor: pointer; text-decoration: underline; display: inline-block;",
                onclick := s"navigator.clipboard.writeText('https://kr1v.net/libs/facts/$day')",
                "(Copy permalink)",
              ),
              h5(style := "margin: 0; display: inline-block;",
                a(href := s"https://kr1v.net/libs/facts/$day", "(Open permalink)"),
              ),
            ),
            div(style := "overflow-wrap: anywhere; word-break: break-word;",
              raw(fact.fact()),
            ),
          )
        },
      ),
    ).render

  // ──────────────────────────────────────────────
  //  Page:  Single fact
  // ──────────────────────────────────────────────

  private def renderFactPage(day: Int, fact: Facts.Fact): String =
    val df = DateTimeFormatter.ofPattern("dd MMM uuuu")

    def ogDesc: String =
      fact.fact()
        .replace("<br>\n", "\n").replace("<br>", "\n")
        .replace("<code>", "`").replace("</code>", "`")
        .replace("<samp><pre class='codeSamp'>", "```").replace("</pre></samp>", "```")
        .replace("<ul>", "").replace("</ul>", "")
        .replace("<li>", " - ").replace("</li>", "")
        .replace("<p>", "").replace("</p>", "\n\n")

    html(lang := "en",
      head(
        meta(charset := "UTF-8"),
        meta(name := "viewport", content := "width=device-width, initial-scale=1.0"),
        tag("style")(raw(css)),
        tag("title")(s"Fabric Config Library Fact Day $day: ${fact.title()}"),
        meta(attr("property") := "og:title",        content := s"Fabric Config Library Fact Day $day: ${fact.title()}"),
        meta(attr("property") := "og:description",  content := ogDesc),
        meta(attr("property") := "og:url",           content := s"https://kr1v.net/libs/facts/$day"),
        meta(attr("property") := "og:type",          content := "article"),
      ),
      body(cls := "facts-page",
        div(cls := "panel",
          h4(a(href := "https://kr1v.net/libs/facts",   "Go to all facts")),
          h4(a(href := "https://kr1v.net/libs",         "Go to index")),
        ),
        div(cls := "panel", id := s"day-$day",
          div(style := "opacity: 0.75; margin-bottom: 6px;",
            h3(style := "margin: 0;",
              s"Day $day: ${fact.title()} (",
              time(attr("datetime") := fact.date().toString, fact.date().format(df)),
              ")",
            ),
          ),
          div(style := "overflow-wrap: anywhere; word-break: break-word;",
            raw(fact.fact()),
          ),
        ),
      ),
    ).render

  // ──────────────────────────────────────────────
  //  Entry point
  // ──────────────────────────────────────────────

  def main(args: Array[String]): Unit =
    val gson = GsonBuilder().setPrettyPrinting().serializeNulls().create
    val dir  = Path.of("generated", "json")

    Files.createDirectories(dir)
    Files.createDirectories(Path.of("generated", "facts"))

    val obj = new com.google.gson.JsonObject()

    Libraries.CONFIG_LIBRARIES().asScala.foreach { lib =>
      val fileName = lib.id + ".json"
      obj.add(lib.id, gson.toJsonTree(lib))
      Files.writeString(dir.resolve(fileName), gson.toJson(lib))
    }

    Files.writeString(Path.of("generated", "libs.json"), gson.toJson(obj))
    Files.writeString(Path.of("generated", "index.html"),  renderIndex())
    Files.writeString(Path.of("generated", "facts.html"),  renderFacts())

    Facts.facts().asScala.foreach { fact =>
      val day = Facts.facts().indexOf(fact) + 1
      Files.writeString(
        Path.of("generated", "facts", s"$day.html"),
        renderFactPage(day, fact),
      )
    }
