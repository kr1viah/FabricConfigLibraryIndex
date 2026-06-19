package kr1v.index.libs;

import kr1v.index.util.*;

import java.util.List;

public class OffsetUtils538 extends ConfigLibrary {
	public OffsetUtils538() {
		id = "offsetutils538";
		name = "OffsetUtils538";
		side = Side.BOTH;
		versions = Versions.ALL_LIST;
		type = Type.LOADER;
		dependencies = List.of();
		extraConfigTypes = List.of();
		extraFeatures = List.of(Feature.CUSTOM_CONFIG_TYPES);
		configFormats = List.of(ConfigFormat.JSON);
		manualInitialization = InitMode.AT_MOD_INIT;
		configMethod = ConfigMethod.of(ConfigMethod.TypeOfClass.EXTENDING, ConfigMethod.MemberType.INSTANCE, ConfigMethod.Waaa.ANNOTATED_PRIMITIVE);
		uiMethod = UiMethod.NONE;
		notes = List.of();
		source = "https://codeberg.org/OffsetMods538/OffsetUtils538";

		exampleConfigClass = """
public final class ConfigClass implements Config {
	public boolean exampleBoolean = false;

	@Override
	public Path getConfigDirPath() {
		return FabricLoader.getInstance().getConfigDir().resolve("offsetutils538-example");
	}

	@Override
	public String getId() {
		return "offsetutils538-example/main";
	}
}
""";
	}
}

