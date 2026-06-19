package kr1v.index.libs;

import kr1v.index.util.*;

import java.util.List;

public class McQoy extends ConfigLibrary {
	public McQoy() {
		id = "mcqoy";
		name = "McQoy";
		side = Side.CLIENT;
		modrinthSlug = "mcqoy";
		type = Type.UI;
		dependencies = List.of(Dependency.MOD_MENU, Dependency.YET_ANOTHER_CONFIG_LIB);
		extraConfigTypes = List.of();
		extraFeatures = List.of(Feature.MOD_MENU_INTEGRATION);
		configFormats = List.of();
		manualInitialization = InitMode.NOT_AVAILABLE;
		configMethod = ConfigMethod.NOT_AVAILABLE;
		uiMethod = UiMethod.AUTOMATIC;
		notes = List.of("Makes a YACL config screen for kaleido configs");
		source = "https://github.com/sisby-folk/mcqoy";

		exampleConfigClass = "";
	}
}
