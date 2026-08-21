package kr1v.index.libs;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;
import kr1v.index.util.ConfigLibrary;
import kr1v.index.util.Util;
import kr1v.index.util.Versions;
import org.jetbrains.annotations.Nullable;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

@SuppressWarnings("unused")
public class Libraries {
	public static final ConfigLibrary AxolotlClientConfig = new AxolotlClientConfig();
	public static final ConfigLibrary BetterConfig = new BetterConfig();
	public static final ConfigLibrary CarbonConfig = new CarbonConfig();
	public static final ConfigLibrary ClothConfig = new ClothConfig();
	public static final ConfigLibrary CodecConfigApi = new CodecConfigApi();
	public static final ConfigLibrary CompleteConfig = new CompleteConfig();
	public static final ConfigLibrary ConfigToolKit = new ConfigToolKit();
	public static final ConfigLibrary Configurable = new Configurable();
	public static final ConfigLibrary Configuration = new Configuration();
	public static final ConfigLibrary CryonicConfig = new CryonicConfig();
	public static final ConfigLibrary ForgeConfigAPIPort = new ForgeConfigAPIPort();
	public static final ConfigLibrary FzzyConfig = new FzzyConfig();
	public static final ConfigLibrary Gson = new Gson();
	public static final ConfigLibrary KaleidoConfig = new KaleidoConfig();
	public static final ConfigLibrary MaLiLib = new MaLiLib();
	public static final ConfigLibrary MaLiLibAPI = new MaLiLibAPI();
	public static final ConfigLibrary McQoy = new McQoy();
	public static final ConfigLibrary MidnightLib = new MidnightLib();
	public static final ConfigLibrary MonkeyLib538 = new MonkeyLib538();
	public static final ConfigLibrary OffsetUtils538 = new OffsetUtils538();
	public static final ConfigLibrary OneConfig = new OneConfig();
	public static final ConfigLibrary OwoLib = new OwoLib();
	public static final ConfigLibrary QoMC = new QoMC();
	public static final ConfigLibrary Ukulib = new Ukulib();
	public static final ConfigLibrary YAMLConfig = new YAMLConfig();
	public static final ConfigLibrary YetAnotherConfigLib = new YetAnotherConfigLib();

	static {
		for (ConfigLibrary library : CONFIG_LIBRARIES()) {
			if (library.modrinthSlug != null) {
				String versionsUrl = "https://api.modrinth.com/v2/project/" + library.modrinthSlug + "/version";
				com.google.gson.Gson gson = new com.google.gson.Gson();

				URI url = URI.create(versionsUrl);
				JsonArray json;

				try (InputStreamReader reader = new InputStreamReader(
						url.toURL().openStream(), StandardCharsets.UTF_8)) {

					json = gson.fromJson(reader, JsonArray.class);
				} catch (IOException e) {
					throw new RuntimeException(e);
				}

				library.versions = json.asList().stream()
						.map(JsonElement::getAsJsonObject)
						.filter(version -> version.get("loaders").getAsJsonArray().contains(new JsonPrimitive("fabric")))
						.map(version -> version.get("game_versions"))
						.map(JsonElement::getAsJsonArray)
						.map(JsonArray::asList)
						.map(List::reversed)
						.flatMap(List::stream)
						.map(JsonElement::getAsString)
						.filter(Versions.ALL_LIST::contains)
						.distinct()
						.sorted(Comparator.comparingInt(Versions.ALL_LIST::indexOf))
						.toList();
			}
		}

		for (var lib : CONFIG_LIBRARIES()) {
			for (var f : lib.getClass().getFields()) {
				if (Util.get(lib, f) == null && f.getAnnotation(Nullable.class) != null) {
					throw new IllegalStateException("Field " + f.getName() + " for library " + lib.name + " is null, but it cant be");
				}
			}
		}
	}

	public static List<ConfigLibrary> CONFIG_LIBRARIES() {
		return Arrays.stream(Libraries.class.getFields()).filter(f -> f.getType().isAssignableFrom(ConfigLibrary.class)).map(Util::<ConfigLibrary>get).toList();
	}
}
