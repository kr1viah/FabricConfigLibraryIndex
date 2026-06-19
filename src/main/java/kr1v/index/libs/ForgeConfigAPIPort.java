package kr1v.index.libs;

import kr1v.index.util.*;

import java.util.List;

public class ForgeConfigAPIPort extends ConfigLibrary {
	public ForgeConfigAPIPort() {
		id = "forge-config-api-port";
		name = "Forge Config API Port";
		side = Side.BOTH;
		modrinthSlug = "forge-config-api-port";
		type = Type.LOADER;
		dependencies = List.of();
		extraConfigTypes = ConfigType.UNKNOWN;
		extraFeatures = Feature.UNKNOWN;
		configFormats = ConfigFormat.UNKNOWN;
		manualInitialization = InitMode.YES;
		configMethod = ConfigMethod.UNKNOWN;
		uiMethod = UiMethod.NONE;
		notes = List.of();
		source = "https://github.com/Fuzss/forgeconfigapiport";

		// TODO: I tried for ~15 minutes, I can't find a coherent example, maybe later
	}
}
