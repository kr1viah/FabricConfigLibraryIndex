package kr1v.index.util;

import org.intellij.lang.annotations.Language;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class ConfigLibrary {
	public String id;
	public String name;

	public Side side;

	public List<String> versions;
	public Type type;
	public List<Dependency> dependencies;
	public List<ConfigType> extraConfigTypes;
	public List<Feature> extraFeatures;
	public List<ConfigFormat> configFormats;
	public InitMode manualInitialization;
	public ConfigMethod configMethod;
	public UiMethod uiMethod;
	public List<String> notes;
	public String source;
	@Nullable
	public String modrinthSlug;

	@Language("java")
	@Nullable
	public String exampleConfigClass;
}
