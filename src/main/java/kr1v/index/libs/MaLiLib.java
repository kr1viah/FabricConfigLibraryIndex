package kr1v.index.libs;

import kr1v.index.util.*;

import java.util.List;

public class MaLiLib extends ConfigLibrary {
	public MaLiLib() {
		id = "malilib";
		name = "MaLiLib";
		side = Side.CLIENT;
		modrinthSlug = "malilib";
		type = Type.BOTH;
		dependencies = List.of();
		extraConfigTypes = List.of(ConfigType.COLOR, ConfigType.HOTKEY);
		extraFeatures = List.of(Feature.SLIDER);
		configFormats = List.of(ConfigFormat.JSON);
		manualInitialization = InitMode.AT_MOD_INIT;
		configMethod = ConfigMethod.of(ConfigMethod.TypeOfClass.EXTENDING, ConfigMethod.MemberType.STATIC, ConfigMethod.Waaa.special("ConfigInteger", "ConfigOptionList", "ConfigDouble"));
		uiMethod = UiMethod.CUSTOM_SCREEN_CLASS;
		notes = List.of("Hard to set up");
		source = "https://github.com/sakura-ryoko/malilib";

		exampleConfigClass = """
public class Configs implements IConfigHandler {
	private static final Path CONFIG_FILE = FabricLoader.getInstance().getConfigDir(CONFIG_FILE_NAME);
	public class ConfigClass {
		public static final ConfigBoolean EXAMPLE_BOOLEAN = new ConfigBoolean("Example boolean", false);
		public static final ImmutableList<IConfigBase> OPTIONS = ImmutableList.of(
				EXAMPLE_BOOLEAN
		);
	}

	@Override
	public void load() {
		if (Files.exists(CONFIG_FILE) && Files.isReadable(CONFIG_FILE)) {
			JsonElement element = JsonUtils.parseJsonFile(CONFIG_FILE);

			if (element != null && element.isJsonObject()) {
				JsonObject root = element.getAsJsonObject();

				ConfigUtils.readConfigBase(root, "main", Configs.ConfigClass.OPTIONS);
			}
		} else {
			System.err.println("load(): Failed to load config file " + CONFIG_FILE.toAbsolutePath());
		}
	}

	@Override
	public void save() {
		JsonObject root = new JsonObject();

		ConfigUtils.writeConfigBase(root, "main", Configs.ConfigClass.OPTIONS);

		JsonUtils.writeJsonToFile(root, CONFIG_FILE);
	}
}
""";
	}
}
