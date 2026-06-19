package kr1v.index.libs;

import kr1v.index.util.*;

import java.util.List;

public class Configurable extends ConfigLibrary {
	public Configurable() {
		id = "configurable";
		name = "Configurable";
		side = Side.BOTH;
		versions = Versions.versions("1.20.1", "1.21", "1.21.1", "1.21.2", "1.21.3", "1.21.4", "1.21.5", "1.21.6", "1.21.7", "1.21.8", "1.21.9", "1.21.10", "1.21.11");
		type = Type.LOADER;
		dependencies = List.of(Dependency.FABRIC_API);
		extraConfigTypes = List.of();
		extraFeatures = List.of(Feature.CONSTRAINT, Feature.CUSTOM_CONFIG_TYPES);
		configFormats = List.of(ConfigFormat.JSON, ConfigFormat.TOML);
		manualInitialization = InitMode.NO;
		configMethod = ConfigMethod.of(ConfigMethod.TypeOfClass.NONE, ConfigMethod.MemberType.STATIC, ConfigMethod.Waaa.ANNOTATED_PRIMITIVE);
		uiMethod = UiMethod.NONE;
		notes = List.of();
		source = "https://github.com/Bawnorton/Configurable";

		exampleConfigClass = "/** Example comment */ @Configurable boolean exampleBoolean = false;";
	}
}
