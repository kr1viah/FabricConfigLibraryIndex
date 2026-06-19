package kr1v.index.libs;

import kr1v.index.util.*;

import java.util.List;

public class Configuration extends ConfigLibrary {
	public Configuration() {
		id = "configuration";
		name = "Configuration";
		side = Side.BOTH;
		modrinthSlug = "configuration";
		type = Type.BOTH;
		dependencies = List.of();
		extraConfigTypes = List.of();
		extraFeatures = List.of(Feature.CUSTOM_CONFIG_TYPES, Feature.CONSTRAINT, Feature.MOD_MENU_INTEGRATION, Feature.SECTIONS);
		configFormats = List.of(ConfigFormat.JSON, ConfigFormat.YAML, ConfigFormat.PROPERTIES, ConfigFormat.INI);
		manualInitialization = InitMode.YES;
		configMethod = ConfigMethod.of(ConfigMethod.TypeOfClass.ANNOTATED, ConfigMethod.MemberType.INSTANCE, ConfigMethod.Waaa.ANNOTATED_PRIMITIVE);
		uiMethod = UiMethod.AUTOMATIC;
		notes = List.of();
		source = "https://github.com/Toma1O6/Configuration";

		exampleConfigClass = """
@Config(id = "configuration-example")
public class ConfigClass {
	@Configurable
	public boolean exampleBoolean = false;
}
""";
	}
}
