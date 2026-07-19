@file:Suppress("FunctionName")

package kr1v.index

import com.google.gson.GsonBuilder
import com.google.gson.JsonObject
import kotlinx.html.*
import kotlinx.html.stream.appendHTML
import kotlinx.html.title
import kr1v.index.libs.Libraries
import kr1v.index.util.*
import kr1v.index.util.ConfigMethod.TypeOfClass
import kr1v.index.util.ConfigMethod.Waaa
import java.nio.file.Files
import java.nio.file.Path
import java.time.format.DateTimeFormatter
import java.util.Base64
import java.util.Locale
import kotlin.math.abs
import kotlin.random.Random
import kotlin.text.ifEmpty

fun readResource(path: String): String {
	return object {}.javaClass
		.getResourceAsStream(path)
		?.bufferedReader()
		?.use { it.readText() }
		?: error("Resource not found: $path")
}

val css = readResource("/main.css")
val js = readResource("/main.js")

fun ConfigLibrary.tags(): List<Pair<String, String>> {
	val tags = arrayListOf<Pair<String, String>>()

	if (side.client) tags.add(Pair("Client"))
	if (side.server) tags.add(Pair("Server"))
	if (type.ui) tags.add(Pair("Ui"))
	if (type.loader) tags.add(Pair("Loader"))

	extraFeatures.forEach { tags.add(Pair(it.name, it.description)) }
	configFormats.forEach { tags.add(Pair(it.name)) }

	return tags
}

fun Pair(first: String): Pair<String, String> {
	return Pair(first, "")
}

fun FlowContent.tag(titleStr: String, tooltip: String = "", onClickStr: String = "", spanStyle: String = "", h6Style: String = "") {
	span("tag") {
		if (spanStyle.isNotEmpty()) {
			style = spanStyle
		}
		if (onClickStr.isNotEmpty()) {
			onClick = onClickStr
		}
		h6 {
			if (tooltip.isNotEmpty()) {
				classes = setOf("hoverable")
				title = tooltip
			}
			style = h6Style.ifEmpty {
				"margin: 2px;"
			}
			+titleStr
		}
	}
}

fun FlowContent.ConfigLibraryPanel(library: ConfigLibrary) {
	div {
		classes = setOf("panel")

		attributes["id"] = library.id

		div {
			style = "height: fit-content; display: flex; justify-content: space-between;"
			// title
			div {
				h1 {
					style = "display: inline-block; margin: 0;"
					+library.name
					+" ("
				}
				h2 {
					style = "display: inline-block; margin: 0;"
					code {
						span {
							style = "padding: 3px;"
							+library.id
						}
					}
				}
				h1 {
					style = "display: inline-block; margin: 0;"
					+")"
				}

				// dependencies
				if (library.dependencies.isNotEmpty()) {
					h4 {
						var isFirst = true
						+"Dependencies: "
						for (dep in library.dependencies) {
							if (!isFirst) {
								+", "
							}
							a {
								href = dep.url
								+dep.name
							}
							isFirst = false
						}
					}
				}

				// init mode
				h4 {
					+"Manual initialization: "
					+library.manualInitialization.name
				}

				// config format
				if (library.configMethod != ConfigMethod.NOT_AVAILABLE) {
					h4 {
						+"Config method: "
						if (library.configMethod == ConfigMethod.UNKNOWN) {
							+"Unknown"
						} else {
							val method = library.configMethod
							if (method.typeOfClass != TypeOfClass.NONE) {
								+"a"
								if (method.typeOfClass == TypeOfClass.EXTENDING || method.typeOfClass == TypeOfClass.ANNOTATED) +"n"
								+" ${method.typeOfClass.name.lowercase(Locale.ROOT)} class with "
							}
							if (method.waaas.size != 1) {
								+"either "
							}
							for (waaa in method.waaas) {
								val isPrimitive = waaa == Waaa.ANNOTATED_PRIMITIVE
								if (isPrimitive) {
									+"annotated "
								}
								+method.memberType.description
								if (!isPrimitive) {
									+", "
									+waaa.methodDescription
								}

								if (method.waaas.indexOf(waaa) < method.waaas.size-1) {
									+", or "
								}
							}

							br()

							+"Examples: "

							val random = Random(library.name.hashCode())
							val length = method.waaas.size

							val examples = HashSet<String>()
							while (examples.size < 3) {
								val exampless = method.waaas[abs(random.nextInt() % length)].examples
								examples.add(exampless[abs(random.nextInt() % exampless.size)])
							}
							for (example in examples) {
								span {
									style = "display: inline-block; margin: 1px;"
									code {
										span {
											style = "margin: 5px;"
											+example
										}
									}
								}
							}
						}
					}
				}

				// config types
				if (library.extraConfigTypes.isNotEmpty()) {
					h4 {
						var isFirst = true
						+"Extra config type(s) supported: "
						for (type in library.extraConfigTypes) {
							if (!isFirst) {
								+", "
							}
							span {
								if (type.description.isNotEmpty()) {
									classes = setOf("hoverable")
									title = type.description
								}
								+type.name
							}
							isFirst = false
						}
					}
				}

				if (library.type.ui) {
					h4 {
						span {
							if (library.uiMethod.description.isNotEmpty()) {
								classes = setOf("hoverable")
								title = library.uiMethod.description
							}
							+"Ui method: "
							+library.uiMethod.name
						}
					}
				}

				div { style = "height: 5px" }

				// versions
				h4 {
					var isFirst = true
					+"Available for versions: "

					var versions: List<String> = ArrayList(library.versions)

					versions = Versions.condensVersions(versions)

					for (version in versions) {
						if (!isFirst) {
							+", "
						}
						+version
						isFirst = false
					}
				}

				div { style = "height: 5px" }

				if (library.notes.isNotEmpty()) {
					h4 {
						+"Notes:"
					}
					h5 {
						style = "margin: 0;"
						if (library.notes.isNotEmpty()) {
							ul {
								library.notes.forEach {
									li {
										+it
									}
								}
							}
						}
					}
				}
			}

			// screenshot
			div {
				val stream = object {}.javaClass.getResourceAsStream("/screenshots/${library.modrinthSlug}.png")
				if (stream != null) {
					val bytes = stream.readBytes()
					val base64 = Base64.getEncoder().encodeToString(bytes)
					img {
						src = "data:image/png;base64,$base64"
						width = (1920 / 4).toString()
						height = (1080 / 4).toString()
					}
				}
			}
		}

		if (library.type.loader) {
			div {
				h4 {
					style = "display: inline; margin: 0;"
					+"Example config"
				}

				tag(
					"Show/hide",
					onClickStr = """
						const el = document.getElementById("example-config-${library.id}");
						el.style.display = el.style.display === "none" ? "block" : "none";
					""".trimIndent(),
					spanStyle = "display: inline-block; margin-left: 0px; cursor: pointer;"
				)

				span {
					style = "display: inline; margin-left: 4px;"
					+":"
				}

				pre("codeSamp") {
					style = "display: none;"
					id = "example-config-${library.id}"
					samp {
						if (library.exampleConfigClass == null) {
							+"None yet! Contribute by providing an example "
							a {
								val link = "https://github.com/kr1viah/FabricConfigLibraryIndex/blob/master/src/main/java/kr1v/index/libs/" + library.javaClass.simpleName + ".java"
								href = link
								+"here"
							}
							+"."
						} else {
							+library.exampleConfigClass.replace("\t", "	")
						}
					}
				}
			}
		}

		div { style = "height: 5px" }

		div {
			style = "display: flex; justify-content: space-between;"
			div {
				style = "margin-bottom: 2px;"
				// tags
				library.tags().forEach {
					tag(it.first, it.second)
				}
			}
			// links
			div {
				style = "margin-right: 2px"
				if (library.modrinthSlug != null) {
					a {
						href = "https://modrinth.com/mod/${library.modrinthSlug}"
						+"Modrinth"
					}
					+" "
				}
				a {
					href = "https://github.com/kr1viah/FabricConfigLibraryIndex/blob/master/src/main/java/kr1v/index/libs/" + library.javaClass.simpleName + ".java"
					+"View entry"
				}
				+" "
				a {
					href = library.source
					+"Source code"
				}
			}
		}
	}
}

fun main() {
	val gson = GsonBuilder().setPrettyPrinting().serializeNulls().create()
	val dir = Path.of("generated", "json")

	Files.createDirectories(dir)
	Files.createDirectories(Path.of("generated", "facts"))

	val obj = JsonObject()

	for (library in Libraries.CONFIG_LIBRARIES()) {
		val fileName = library.id + ".json"

		obj.add(library.id, gson.toJsonTree(library))

		val path = dir.resolve(fileName)
		Files.writeString(path, gson.toJson(library))
	}

	Files.writeString(Path.of("generated", "libs.json"), gson.toJson(obj))

	val libsHtml = buildString {
		appendHTML().html {
			lang = "en"
			head {
				meta(charset = "UTF-8")
				style {
					unsafe {
						+css
					}
				}
				title {
					+"Fabric Config Library Index"
				}
                link {
                    rel = "icon"
                    type = "image/x-icon"
                    href = "https://fabricmc.net/assets/logo.png"
                }
			}
			body {
				style = "font-size: 13px; color: #cdd6f4; margin: 0px; display: flex; height: 100%"

				div("sidebar") {
					style = "flex: 1; display: flex; flex-direction: column; padding: 10px"

					div("panel") {
						style = "overflow-y: auto;"

						div {
							style = "display: flex; justify-content: space-between; align-items: center"
							h4 {
								+"Versions"
							}
							span("tag") {
								onClick = "resetFilters()"
								h6 {
									style = "margin: 2px;"
									+"Reset filters"
								}
							}
						}
						for (versionSet in Versions.ALL_SET.reversed()) {
							h5 {
								style = "margin-left: 2px; margin: 0"
								+versionSet.first()
							}
							for (version in versionSet) {
								tag(version, onClickStr = "toggleFilter('versions', '$version')", h6Style = "margin-left: 4px; margin: 2px;")
							}
						}

						h4 {
							+"Side"
						}
						tag("Client", onClickStr = "toggleFilter('side', 'CLIENT')")
						tag("Server", onClickStr = "toggleFilter('side', 'SERVER')")

						h4 {
							+"Config types"
						}
						for (type in ConfigType.entries) {
							tag(type.name, type.description, "toggleFilter('extraConfigTypes', '$type')")
						}

						h4 {
							+"Features"
						}
						for (feature in Feature.entries) {
							tag(feature.name, feature.description, "toggleFilter('extraFeatures', '$feature')")
						}

						h4 {
							+"Config formats"
						}
						for (configFormat in ConfigFormat.entries) {
							if (configFormat == ConfigFormat.NOT_AVAILABLE) continue
							tag(configFormat.name, onClickStr = "toggleFilter('configFormats', '$configFormat')")
						}

						h4 {
							+"Init mode"
						}
						for (mode in InitMode.entries) {
							if (mode == InitMode.NOT_AVAILABLE || mode == InitMode.UNKNOWN) continue
							tag(mode.name, onClickStr = "toggleFilter('manualInitialization', '$mode')")
						}

						h4 {
							+"Config method"
						}
						h5 {
							style = "margin: 0;"
							+"Field kind"
							tag("instance", onClickStr = "toggleFilter('configMethod.instance', 'true')")
							tag("static", onClickStr = "toggleFilter('configMethod.instance', 'false')")
						}
						h5 {
							style = "margin: 0;"
							+"Type of fields"
							for (waaa in Waaa.ENTRIES) {
								tag(waaa.key, "Examples: ${waaa.value.joinToString(", ")}", "toggleFilter('configMethod.waaas', '${waaa.key}')")
							}
						}

						h4 {
							+"UI Method"
						}
						for (uiMethod in UiMethod.entries) {
							tag(uiMethod.name, uiMethod.description, "toggleFilter('uiMethod', '$uiMethod')")
						}
					}

					div("panel") {
						style = "height: fit-content; display: flex; gap: 1em; justify-content: space-between;"
						div {
							a {
								href = "https://kr1v.net/libs/libs.json"
								+"View json"
							}
							br
							a {
								href = "https://kr1v.net/libs/facts"
								+"View facts"
							}
							br
							a {
								href = "https://github.com/kr1viah/FabricConfigLibraryIndex"
								+"Github repo"
							}
						}

						div {
							style = "height: 100%; flex: 1; display: flex; flex-direction: column; justify-content: space-between; align-items: end;"
							p {
								style = "margin: 0;"
								+("Total libraries: " + Libraries.CONFIG_LIBRARIES().size)
                                br()
                                +"The content on this website is available under the CC0 1.0 Universal license. "
                                a {
                                    href = "https://github.com/kr1viah/FabricConfigLibraryIndex/blob/master/README.md"
                                    +"More info"
                                }
							}
							tag(
								"Meow!",
								onClickStr = "initMeow();",
								spanStyle = "display: inline-block; cursor: pointer;"
							)
						}
					}

					div("panel meow-panel") {
						style = "display: none"
						attributes["id"] = "meow-panel"
					}
				}

				div("library_grid") {
					style = "flex: 2; overflow-y: auto; padding: 10px;"
					Libraries.CONFIG_LIBRARIES().shuffled(Random(Libraries.CONFIG_LIBRARIES().hashCode())).forEach {
						ConfigLibraryPanel(it)
					}
				}

				script {
					unsafe {
						+js
					}
				}
			}
		}
	}

	Files.writeString(Path.of("generated", "index.html"), libsHtml)

	val dateFormatter = DateTimeFormatter.ofPattern("dd MMM uuuu")

	val factsHtml = buildString {
		appendHTML().html {
			lang = "en"
			head {
				meta(charset = "UTF-8")
				style {
					unsafe {
						+css
					}
				}
				title {
					+"Fabric Config Library Facts"
				}

				meta {
					attributes["property"] = "og:title"
					content = "Fabric Config Library Facts"
				}
				meta {
					attributes["property"] = "og:description"
					content = "\"Daily\" config library facts"
				}
				meta {
					attributes["property"] = "og:url"
					content = "https://kr1v.net/libs/facts"
				}
				meta {
					attributes["property"] = "og:type"
					content = "article"
				}
			}
			body {
				style = "font-size: 13px; color: #cdd6f4; margin: 0px"

				div("panel") {
					style = "width: 66%; align: middle; margin-left: auto; margin-right: auto;"
					h4 {
						a {
							href = "https://kr1v.net/libs"
							+"Go to index"
						}
					}
					h4 {
						+"Have a suggestion? Send me a dm on discord: "
						code {
							+".kr1v"
						}
					}
				}

				for (fact in Facts.facts().reversed()) {
					val day = (Facts.facts().indexOf(fact)+1)
					div("panel") {
						id = "day-$day"
						style = "width: 66%; align: middle; margin-left: auto; margin-right: auto;"
						div {
							style = "opacity: 0.75; margin-bottom: 6px;"
							h3 {
								style = "display: inline-block; margin: 0;"
								+"Day $day: ${fact.title} ("
								time {
									dateTime = fact.date().toString()
									+fact.date().format(dateFormatter)
								}
								+")"
							}
							h5 {
								style = "user-select: none; margin: 0; cursor: pointer; text-decoration: underline; display: inline-block;"
								onClick = "navigator.clipboard.writeText('https://kr1v.net/libs/facts/$day')"
								+"(Copy permalink)"
							}
							h5 {
								style = "margin: 0; display: inline-block;"
								a {
									href = "https://kr1v.net/libs/facts/$day"
									+"(Open permalink)"
								}
							}
						}
						div {
							style = """
								font: helvetica;
								overflow-wrap: anywhere;
								word-break: break-word;
							""".trimIndent()
							unsafe {
								+fact.fact()
							}
						}
					}
				}
			}
		}
	}

	Files.writeString(Path.of("generated", "facts.html"), factsHtml)

	for (fact in Facts.facts()) {
		val day  = Facts.facts().indexOf(fact)+1

		val factOfTheDayHtml = buildString {
			appendHTML().html {
				lang = "en"
				head {
					meta(charset = "UTF-8")
					style {
						unsafe {
							+css
						}
					}
					title {
						+"Fabric Config Library Fact Day $day: ${fact.title}"
					}

					meta {
						attributes["property"] = "og:title"
						content = "Fabric Config Library Fact Day $day: ${fact.title}"
					}
					meta {
						attributes["property"] = "og:description"
						content = fact.fact()
							.replace("<br>\n", "\n")
							.replace("<br>", "\n")

							.replace("<code>", "`")
							.replace("</code>", "`")

							.replace("<samp><pre class='codeSamp'>", "```")
							.replace("</pre></samp>", "```")

							.replace("<ul>", "")
							.replace("</ul>", "")

							.replace("<li>", " - ")
							.replace("</li>", "")

							.replace("<p>", "")
							.replace("</p>", "\n\n")
					}
					meta {
						attributes["property"] = "og:url"
						content = "https://kr1v.net/libs/facts/$day"
					}
					meta {
						attributes["property"] = "og:type"
						content = "article"
					}
				}

				body {
					style = "font-size: 13px; color: #cdd6f4; margin: 0px"

					div("panel") {
						style = "width: 66%; align: middle; margin-left: auto; margin-right: auto;"
						h4 {
							a {
								href = "https://kr1v.net/libs/facts"
								+"Go to all facts"
							}
						}
						h4 {
							a {
								href = "https://kr1v.net/libs"
								+"Go to index"
							}
						}
					}

					div("panel") {
						id = "day-$day"
						style = "width: 66%; align: middle; margin-left: auto; margin-right: auto;"
						div {
							style = "opacity: 0.75; margin-bottom: 6px;"
							h3 {
								style = "margin: 0;"
								+"Day $day: ${fact.title} ("
								time {
									dateTime = fact.date().toString()
									+fact.date().format(dateFormatter)
								}
								+")"
							}
						}
						div {
							style = """
								font: helvetica;
								overflow-wrap: anywhere;
								word-break: break-word;
							""".trimIndent()
							unsafe {
								+fact.fact()
							}
						}
					}
				}
			}
		}

		Files.writeString(Path.of("generated", "facts", "$day.html"), factOfTheDayHtml)
	}
}
