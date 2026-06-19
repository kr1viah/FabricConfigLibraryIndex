package kr1v.index.libs;

import kr1v.index.util.*;

import java.util.List;

public class CodecConfigApi extends ConfigLibrary {
	public CodecConfigApi() {
		id = "codec-config-api";
		name = "Codec config API";
		side = Side.BOTH;
		versions = Versions.versions("1.19", "1.19.1", "1.19.2", "1.19.3", "1.19.4", "1.20", "1.20.1", "1.20.2", "1.20.3", "1.20.4", "1.20.5", "1.20.6", "1.21", "1.21.1", "1.21.2", "1.21.3", "1.21.4", "1.21.5", "1.21.6", "1.21.7", "1.21.8", "1.21.9", "1.21.10", "1.21.11", "26.1", "26.1.1", "26.1.2");
		type = Type.LOADER;
		dependencies = List.of();
		extraConfigTypes = ConfigType.UNKNOWN;
		extraFeatures = List.of(Feature.CODEC_BASED_CONFIGS, Feature.AUTO_UPGRADING);
		configFormats = List.of(ConfigFormat.JSON);
		manualInitialization = InitMode.YES;
		configMethod = ConfigMethod.of(ConfigMethod.TypeOfClass.EXTENDING, ConfigMethod.MemberType.INSTANCE, ConfigMethod.Waaa.PRIMITIVE);
		uiMethod = UiMethod.NONE;
		notes = List.of();
		source = "https://code.mschae23.de/mschae23/codec-config-api";

		exampleConfigClass = """
public record ExampleConfig(boolean exampleBoolean) implements ModConfig<ExampleConfig> {
	public static final MapCodec<ExampleConfig> TYPE_CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(
		Codec.BOOL.fieldOf("example_boolean").forGetter(ExampleConfig::exampleBoolean)
	).apply(instance, instance.stable(ExampleConfig::new)));

	public static final ModConfig.Type<ExampleConfig, ExampleConfig> TYPE = new ModConfig.Type<>(1, TYPE_CODEC);
	@SuppressWarnings("unchecked")
	public static final ModConfig.Type<ExampleConfig, ? extends ModConfig<ExampleConfig>>[] VERSIONS = new ModConfig.Type[] { TYPE, };
	public static final Codec<ModConfig<ExampleConfig>> CODEC = ModConfig.createCodec(TYPE.version(), version ->
		ModConfig.getConfigType(VERSIONS, version));

	public static final ExampleConfig DEFAULT = new ExampleConfig(true);

	@Override
	public Type<ExampleConfig, ?> type() {
		return TYPE;
	}

	@Override
	public ExampleConfig latest() {
		return this;
	}

	@Override
	public boolean shouldUpdate() {
		return true;
	}
}
""";
	}
}
