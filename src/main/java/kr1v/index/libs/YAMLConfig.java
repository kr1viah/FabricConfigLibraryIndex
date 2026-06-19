package kr1v.index.libs;

import kr1v.index.util.*;

import java.util.List;

public class YAMLConfig extends ConfigLibrary {
	public YAMLConfig() {
		id = "yaml-config";
		name = "YAML Config";
		side = Side.BOTH;
		modrinthSlug = "yaml-config";
		type = Type.BOTH;
		dependencies = List.of(Dependency.UI_LIB);
		extraConfigTypes = List.of(ConfigType.IDENTIFIER, ConfigType.REGISTRY_ENTRY, ConfigType.DATE_TIME);
		extraFeatures = List.of(Feature.CUSTOM_CONFIG_TYPES, Feature.CONSTRAINT);
		configFormats = List.of(ConfigFormat.YAML, ConfigFormat.JSON5, ConfigFormat.TOML, ConfigFormat.HOCON);
		manualInitialization = InitMode.YES;
		configMethod = ConfigMethod.of(ConfigMethod.TypeOfClass.NORMAL, ConfigMethod.MemberType.STATIC, ConfigMethod.Waaa.builder("builder.defineString()", "builder.defineBoolean()", "builder.push()"));
		uiMethod = UiMethod.AUTOMATIC;
		notes = List.of();
		source = "https://github.com/DAQEM/YamlConfig";

		// TODO: example config for this
	}
}
