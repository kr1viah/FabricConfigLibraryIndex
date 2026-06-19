package kr1v.index.libs;

import kr1v.index.util.*;

import java.util.List;

public class Gson extends ConfigLibrary {
	public Gson() {
		id = "gson";
		name = "Gson";
		side = Side.BOTH;
		versions = Versions.ALL_LIST;
		type = Type.LOADER;
		dependencies = List.of();
		extraConfigTypes = List.of();
		extraFeatures = List.of();
		configFormats = List.of(ConfigFormat.JSON);
		manualInitialization = InitMode.YES;
		configMethod = ConfigMethod.of(ConfigMethod.TypeOfClass.NORMAL, ConfigMethod.MemberType.INSTANCE, ConfigMethod.Waaa.PRIMITIVE);
		uiMethod = UiMethod.NONE;
		notes = List.of("Not really a config library, but still useful for some");
		source = "https://github.com/google/gson";

		exampleConfigClass = """
public class ConfigClass {
	public boolean exampleBoolean = false;

	// could be anywhere
	public static ConfigClass config = new ConfigClass();
	public static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
	public static final Path PATH = FabricLoader.getInstance().getConfigDir().resolve("gson-example");
	public static save() {
		try {
			Files.writeString(PATH, GSON.toJson(CONFIG));
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
	public static load() {
		try {
			CONFIG = GSON.fromJson(Files.readString(PATH));
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
}""";
	}
}
