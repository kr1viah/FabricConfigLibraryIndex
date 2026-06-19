package kr1v.index.libs;

import kr1v.index.util.*;

import java.util.List;

public class YetAnotherConfigLib extends ConfigLibrary {
	public YetAnotherConfigLib() {
		id = "yacl";
		name = "YetAnotherConfigLib";
		side = Side.BOTH;
		modrinthSlug = "yacl";
		type = Type.BOTH;
		dependencies = List.of(Dependency.FABRIC_API);
		extraConfigTypes = List.of(ConfigType.BUTTON, ConfigType.COLOR, ConfigType.DROPDOWN);
		extraFeatures = List.of();
		configFormats = List.of(ConfigFormat.JSON, ConfigFormat.JSON5);
		manualInitialization = InitMode.YES;
		configMethod = ConfigMethod.of(ConfigMethod.TypeOfClass.NORMAL, ConfigMethod.MemberType.INSTANCE, ConfigMethod.Waaa.ANNOTATED_PRIMITIVE);
		uiMethod = UiMethod.BUILDER;
		notes = List.of();
		source = "https://github.com/isXander/YetAnotherConfigLib";

		exampleConfigClass = """
public class ConfigClass {
	public static final ConfigClassHandler<ConfigClass> GSON = ConfigClassHandler.createBuilder(ConfigClass.class)
			.serializer(config -> GsonConfigSerializerBuilder.create(config)
					.setPath(YACLPlatform.getConfigDir().resolve("yacl-example.json"))
					.build())
			.build();

	@SerialEntry
	public boolean exampleBoolean = false;
}
""";
	}
}
