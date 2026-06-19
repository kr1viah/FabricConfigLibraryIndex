package kr1v.index.libs;

import kr1v.index.util.*;

import java.util.List;

public class MaLiLibAPI extends ConfigLibrary {
	public MaLiLibAPI() {
		id = "malilib-api";
		name = "MaLiLib API";
		side = Side.CLIENT;
		modrinthSlug = "malilib-api";
		type = Type.BOTH;
		dependencies = List.of(Dependency.MALILIB);
		extraConfigTypes = List.of(ConfigType.COLOR, ConfigType.HOTKEY, ConfigType.PAIR, ConfigType.BUTTON);
		extraFeatures = List.of(Feature.SLIDER, Feature.CUSTOM_CONFIG_TYPES, Feature.MOD_MENU_INTEGRATION, Feature.SECTIONS);
		configFormats = List.of(ConfigFormat.JSON);
		manualInitialization = InitMode.OPTIONAL;
		configMethod = ConfigMethod.of(ConfigMethod.TypeOfClass.ANNOTATED, ConfigMethod.MemberType.STATIC, ConfigMethod.Waaa.special("ConfigInteger", "ConfigList<ConfigString>", "ConfigCycle"));
		uiMethod = UiMethod.AUTOMATIC;
		notes = List.of();
		source = "https://github.com/kr1viah/malilib-api";

		exampleConfigClass = """
@Config("malilib-api-example")
public class ConfigClass {
	public static final ConfigBooleanPlus EXAMPLE_BOOLEAN = new ConfigBooleanHotkeyedPlus("Example boolean");
}
""";
	}
}
