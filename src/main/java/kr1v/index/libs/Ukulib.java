package kr1v.index.libs;

import kr1v.index.util.*;

import java.util.List;

public class Ukulib extends ConfigLibrary {
	public Ukulib() {
		id = "ukulib";
		name = "ukulib";
		side = Side.CLIENT;
		modrinthSlug = "ukulib";
		type = Type.BOTH;
		dependencies = List.of();
		extraConfigTypes = List.of(ConfigType.COLOR, ConfigType.BUTTON);
		extraFeatures = List.of(Feature.SLIDER);
		configFormats = List.of(ConfigFormat.JSON, ConfigFormat.TOML);
		manualInitialization = InitMode.YES;
		configMethod = ConfigMethod.of(ConfigMethod.TypeOfClass.EXTENDING, ConfigMethod.MemberType.INSTANCE, ConfigMethod.Waaa.PRIMITIVE);
		uiMethod = UiMethod.BUILDER;
		notes = List.of();
		source = "https://github.com/uku3lig/ukulib";

		exampleConfigClass = """
public class ConfigClass implements Serializable {
	public boolean exampleBoolean = false;
}
""";
	}
}
