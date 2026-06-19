package kr1v.index.util;

public enum UiMethod {
	BUILDER("Builder", "Build a config screen using a builder class"),
	AUTOMATIC("Automatic", "You don't need to think about the config screen; it is done automatically"),
	CUSTOM_SCREEN_CLASS("Manual", "You build the entire config screen, with helpers"),
	COMMANDS("Commands", "Commands are used to edit the config values"),
	NONE("None", "(G)uis are not officially supported");

	public final String name;
	public final String description;

	UiMethod(String name, String description) {
		this.name = name;
		this.description = description;
	}
}
