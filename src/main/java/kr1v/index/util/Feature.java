package kr1v.index.util;

import java.util.List;

public enum Feature {
	SLIDER("Slider"),
	CONSTRAINT("Constraint", "Constraints for configs. For example, only allowing string configs that matches a certain regex"),
	MOD_MENU_INTEGRATION("Mod menu integration"),
	CUSTOM_CONFIG_TYPES("Custom config types", "Allows you to add your own config types"),
	CODEC_BASED_CONFIGS("Codec based configs"),
	SECTIONS("Config section", "Sections of configs. May also be referred to as a 'config object'"),
	AUTO_UPGRADING("Automatic upgrading", "Configs are versioned and can be upgraded automatically");

	public static final List<Feature> UNKNOWN = List.of();
	public final String name;
	public final String description;

	Feature(String name) {
		this(name, "");
	}

	Feature(String name, String description) {
		this.name = name;
		this.description = description;
	}
}
