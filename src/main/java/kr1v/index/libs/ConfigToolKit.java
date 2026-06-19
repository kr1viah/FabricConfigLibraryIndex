package kr1v.index.libs;

import kr1v.index.util.*;

import java.util.List;

public class ConfigToolKit extends ConfigLibrary {
	public ConfigToolKit() {
		id = "configtoolkit";
		name = "ConfigToolKit";
		side = Side.BOTH;
		versions = Versions.versions("1.20.6", "1.21", "1.21.1", "1.21.2", "1.21.3", "1.21.4", "1.21.5", "1.21.6", "1.21.7", "1.21.8", "1.21.9", "1.21.10", "1.21.11");
		type = Type.LOADER;
		dependencies = List.of(Dependency.FABRIC_API);
		extraConfigTypes = ConfigType.UNKNOWN;
		extraFeatures = List.of(Feature.CODEC_BASED_CONFIGS);
		configFormats = List.of(ConfigFormat.JSON);
		manualInitialization = InitMode.AT_MOD_INIT;
		configMethod = ConfigMethod.of(ConfigMethod.TypeOfClass.RECORD, ConfigMethod.MemberType.INSTANCE, ConfigMethod.Waaa.PRIMITIVE);
		uiMethod = UiMethod.NONE;
		notes = List.of();
		source = "https://github.com/MattiDragon/ConfigToolkit";

		exampleConfigClass = """
@GenerateMutable(useFancyMethodNames = true)
public record ConfigClass(boolean exampleBoolean) implements MutableConfigClass.Source {
	public static final ConfigClass DEFAULT = new ConfigClass(false);
	public static final Codec<ConfigClass> CODEC = RecordCodecBuilder.create(instance -> instance.group(
			AlwaysSerializedOptionalFieldCodec.create(Codec.BOOL, "exampleBoolean", BarrelConfig.DEFAULT.exampleBoolean).forGetter(ConfigClass::exampleBoolean)
	).apply(instance, ConfigClass::new));
}""";
	}
}
