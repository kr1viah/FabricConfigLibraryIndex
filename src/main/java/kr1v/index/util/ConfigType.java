package kr1v.index.util;

import java.util.ArrayList;
import java.util.List;

public enum ConfigType {
	COLOR("Color"),
	HOTKEY("Hotkey"),
	DROPDOWN("Dropdown"),
	BUTTON("Button", "A button in the list of configs that does something when pressed"),
	FILE("File"),
	PAIR("Pair", "A pair of configs next to each other"),
	REGISTRY_ENTRY("Registry entry"),
	IDENTIFIER("Identifier"),
	DATE_TIME("Date Time");

	public static final List<ConfigType> UNKNOWN = new ArrayList<>();
	public final String name;
	public final String description;

	ConfigType(String name) {
		this(name, "");
	}

	ConfigType(String name, String description) {
		this.name = name;
		this.description = description;
	}
}
