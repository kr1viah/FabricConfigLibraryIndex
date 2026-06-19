package kr1v.index.libs;

import kr1v.index.util.*;

import java.util.List;

public class BetterConfig extends ConfigLibrary {
	public BetterConfig() {
		id = "betterconfig";
		name = "BetterConfig";
		side = Side.BOTH;
		modrinthSlug = "betterconfig";
		type = Type.LOADER;
		dependencies = List.of();
		extraConfigTypes = List.of();
		extraFeatures = List.of(Feature.CUSTOM_CONFIG_TYPES);
		configFormats = List.of();
		manualInitialization = InitMode.YES;
		configMethod = ConfigMethod.of(ConfigMethod.TypeOfClass.NORMAL, ConfigMethod.MemberType.STATIC, ConfigMethod.Waaa.ANNOTATED_PRIMITIVE);
		uiMethod = UiMethod.COMMANDS;
		notes = List.of();
		source = "https://github.com/xpple/BetterConfig";

		exampleConfigClass = """
public class ConfigClass {
	@Config
	public static boolean exampleBoolean = false;
}
""";
	}
}
