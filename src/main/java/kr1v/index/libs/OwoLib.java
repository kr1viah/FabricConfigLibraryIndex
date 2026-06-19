package kr1v.index.libs;

import kr1v.index.util.*;

import java.util.List;

public class OwoLib extends ConfigLibrary {
	public OwoLib() {
		id = "owo-lib";
		name = "oωo";
		side = Side.BOTH;
		modrinthSlug = "owo-lib";
		type = Type.BOTH;
		dependencies = List.of(Dependency.FABRIC_API);
		extraConfigTypes = List.of();
		extraFeatures = List.of(Feature.CONSTRAINT);
		configFormats = ConfigFormat.UNKNOWN;
		manualInitialization = InitMode.YES;
		configMethod = ConfigMethod.of(ConfigMethod.TypeOfClass.ANNOTATED, ConfigMethod.MemberType.INSTANCE, ConfigMethod.Waaa.PRIMITIVE);
		uiMethod = UiMethod.AUTOMATIC;
		notes = List.of("Comes with a lot of additional, sometimes problematic, features.");
		source = "https://github.com/wisp-forest/owo-lib";

		exampleConfigClass = """
@Config(name = "owo-lib-example", wrapperName = "ConfigClass")
public class ConfigClassModel {
	public boolean exampleBoolean = false;
}
""";
	}
}
