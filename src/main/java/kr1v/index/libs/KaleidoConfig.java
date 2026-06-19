package kr1v.index.libs;

import kr1v.index.util.*;

import java.util.List;

public class KaleidoConfig extends ConfigLibrary {
	public KaleidoConfig() {
		id = "kaleido-config";
		name = "Kaleido config";
		side = Side.BOTH;
		versions = Versions.ALL_LIST;
		type = Type.LOADER;
		dependencies = List.of();
		extraConfigTypes = List.of();
		extraFeatures = List.of();
		configFormats = List.of(ConfigFormat.TOML);
		manualInitialization = InitMode.YES;
		configMethod = ConfigMethod.of(ConfigMethod.TypeOfClass.EXTENDING, ConfigMethod.MemberType.INSTANCE, ConfigMethod.Waaa.ANNOTATED_PRIMITIVE);
		uiMethod = UiMethod.NONE;
		notes = List.of("Not made with minecraft in mind");
		source = "https://github.com/sisby-folk/kaleido-config";

		exampleConfigClass = """
public class ExampleModConfig extends ReflectiveConfig {
	public final TrackedValue<Boolean> exampleBoolean = this.value("Example boolean");
}""";
	}
}
