package kr1v.index.libs;

import kr1v.index.util.*;

import java.util.List;

public class MidnightLib extends ConfigLibrary {
	public MidnightLib() {
		id = "midnight-lib";
		name = "MidnightLib";
		side = Side.BOTH;
		modrinthSlug = "midnightlib";
		type = Type.BOTH;
		dependencies = List.of(Dependency.FABRIC_API);
		extraConfigTypes = List.of(ConfigType.FILE);
		extraFeatures = List.of(Feature.SLIDER, Feature.MOD_MENU_INTEGRATION);
		configFormats = ConfigFormat.UNKNOWN;
		manualInitialization = InitMode.YES;
		configMethod = ConfigMethod.of(ConfigMethod.TypeOfClass.EXTENDING, ConfigMethod.MemberType.STATIC, ConfigMethod.Waaa.ANNOTATED_PRIMITIVE);
		uiMethod = UiMethod.AUTOMATIC;
		notes = List.of();
		source = "https://github.com/TeamMidnightDust/MidnightLib";

		exampleConfigClass = """
public class ConfigClass extends MidnightConfig {
	@Entry(category = "Example category") public static boolean exampleBoolean = false;
}
""";
	}
}

