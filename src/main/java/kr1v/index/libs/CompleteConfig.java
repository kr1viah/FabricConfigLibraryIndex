package kr1v.index.libs;

import kr1v.index.util.*;

import java.util.List;

public class CompleteConfig extends ConfigLibrary {
	public CompleteConfig() {
		id = "complete-config";
		name = "CompleteConfig";
		side = Side.BOTH;
		versions = Versions.versions("1.17", "1.17.1", "1.18", "1.18.1", "1.18.2", "1.19", "1.19.1", "1.19.2", "1.19.3", "1.19.4", "1.20", "1.20.1", "1.20.2", "1.20.3", "1.20.4", "1.20.6");
		type = Type.BOTH;
		dependencies = List.of();
		extraConfigTypes = List.of(); // TODO: unsure. it says things like UUID and File and Path and Color on its wiki, but it also says "Note: Not all types are supported for the config screen."
		extraFeatures = List.of(Feature.CUSTOM_CONFIG_TYPES, Feature.SECTIONS);
		configFormats = List.of(ConfigFormat.JSON);
		manualInitialization = InitMode.YES;
		configMethod = ConfigMethod.of(ConfigMethod.TypeOfClass.EXTENDING, ConfigMethod.MemberType.INSTANCE, ConfigMethod.Waaa.ANNOTATED_PRIMITIVE);
		uiMethod = UiMethod.BUILDER;
		notes = List.of();
		source = "https://github.com/Lortseam/completeconfig";

		exampleConfigClass = """
@ConfigEntries(includeAll = true)
public class ConfigClass extends Config {
	public ConfigClass() {
		super(ConfigOptions
				.mod("complete-config-example")
				.fileHeader("This is an example config")
		);
	}

	private boolean exampleBoolean;
}
""";
	}
}
