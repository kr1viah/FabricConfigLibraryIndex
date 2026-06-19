package kr1v.index.libs;

import java.util.List;

import kr1v.index.util.*;

public class AxolotlClientConfig extends ConfigLibrary {
	public AxolotlClientConfig() {
		id = "axolotlclientconfig";
		name = "AxolotlClientConfig";
		side = Side.CLIENT;
		modrinthSlug = "axolotlclient";
		type = Type.BOTH;
		dependencies = List.of(Dependency.FABRIC_API);
		extraConfigTypes = List.of(ConfigType.COLOR);
		extraFeatures = List.of(Feature.AUTO_UPGRADING, Feature.CUSTOM_CONFIG_TYPES, Feature.MOD_MENU_INTEGRATION, Feature.SLIDER, Feature.SECTIONS);
		configFormats = List.of(ConfigFormat.JSON);
		manualInitialization = InitMode.AT_MOD_INIT;
		configMethod = ConfigMethod.of(ConfigMethod.TypeOfClass.NONE, ConfigMethod.MemberType.EITHER, ConfigMethod.Waaa.special("IntegerOption", "StringArrayOption", "EnumOption", "BooleanOption"));
		uiMethod = UiMethod.AUTOMATIC;
		notes = List.of("""
				Not all available Minecraft versions are actively maintained.
				Custom config formats are possible.
				Custom UI implementations are supported (partial & full).
				Only the GUI depends on Minecraft, the common module is independent.
				Supports graphical options (when used with Minecraft).
				Annotation-based configs are possible with an add-on.
				""".split("\n"));
		source = "https://codeberg.org/AxolotlClient/AxolotlClient-config";
		exampleConfigClass = """
				public class ExampleConfig {
					public final BooleanOption exampleBoolean = new BooleanOption("exampleBoolean", false);
					public ExampleConfig() {
						var configPath = FabricLoader.getInstance().getConfigDir().resolve("example-config.json");
						// create a root category. For automatic modmenu integration this must be named the same as your mod's modid.
						var exampleCategory = OptionCategory.of("example-category");
						exampleCategory.add(exampleBoolean);

						// versioned
						var currentVersion = 1;
						var configManager = new VersionedJsonConfigManager(configPath, exampleCategory, currentVersion,
							(oldVersion, newVersion, config, json) -> {
							// this lambda can be used to convert old config values into new ones
							});
						// unversioned
						//var configManager = new JsonConfigManager(configPath, exampleCategory);

						// register the config
						AxolotlClientConfig.register(configManager);
						// The config will not load automatically.
						// Similarly, you can also manually save the config using .save() but the GUI will do that
						// for you after an option is changed as well.
						configManager.load();
					}
				}
				""";
	}
}
