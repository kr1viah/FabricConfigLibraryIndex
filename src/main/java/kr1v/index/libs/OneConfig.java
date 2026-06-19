package kr1v.index.libs;

import kr1v.index.util.*;

import java.util.List;

public class OneConfig extends ConfigLibrary {
	public OneConfig() {
		id = "oneconfig";
		name = "OneConfig";
		side = Side.CLIENT;
		modrinthSlug = "oneconfig";
		type = Type.BOTH;
		dependencies = List.of(Dependency.FABRIC_LANGUAGE_KOTLIN);
		extraConfigTypes = List.of(ConfigType.BUTTON, ConfigType.HOTKEY, ConfigType.COLOR);
		extraFeatures = List.of(Feature.SLIDER, Feature.CUSTOM_CONFIG_TYPES, Feature.SECTIONS);
		configFormats = List.of(ConfigFormat.JSON, ConfigFormat.YAML, ConfigFormat.TOML);
		manualInitialization = InitMode.AT_MOD_INIT;
		configMethod = ConfigMethod.of(ConfigMethod.TypeOfClass.EXTENDING, ConfigMethod.MemberType.STATIC, ConfigMethod.Waaa.ANNOTATED_PRIMITIVE);
		uiMethod = UiMethod.AUTOMATIC;
		notes = List.of("Currently in beta");
		source = "https://github.com/Polyfrost/OneConfig";

		exampleConfigClass = """
public class TestConfig extends Config {
	public static TestConfig INSTANCE = new TestConfig();

	@Checkbox(title = "checkbox", description = "I do checkboxes", category = "bob")
	public static boolean checkbox = false;
	@Slider(title = "Slide", min = 10f, max = 110f, icon = "assets/oneconfig/ico/paintbrush.svg", description = "I do sliding", category = "bob")
	public static float p = 50f;
	@Text(title = "Text")
	public static String text = "Hello world!";

	public TestConfig() {
		super("test_mod.json", "Test Mod", Category.QOL);
	}
}
""";
	}
}
