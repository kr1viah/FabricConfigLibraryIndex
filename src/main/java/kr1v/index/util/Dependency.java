package kr1v.index.util;

public enum Dependency {
	FABRIC_API("Fabric API", "https://github.com/FabricMC/fabric-api"),
	FABRIC_LANGUAGE_KOTLIN("Fabric Language Kotlin", "https://github.com/FabricMC/fabric-language-kotlin"),
	MALILIB("MaLiLib", "https://github.com/sakura-ryoko/malilib"),
	UI_LIB("UI Lib", "https://github.com/DAQEM/UILib"),
	MOD_MENU("Mod Menu", "https://github.com/TerraformersMC/ModMenu"),
	YET_ANOTHER_CONFIG_LIB("Yacl", "https://github.com/isXander/YetAnotherConfigLib"),
	PLACEHOLDER_API("Text Placeholder API", "https://github.com/Patbox/TextPlaceholderAPI");

	public final String name;
	public final String url;

	Dependency(String name, String url) {
		this.name = name;
		this.url = url;
	}
}
