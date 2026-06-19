package kr1v.index.util;

public enum Type {
	LOADER(true, false),
	UI(false, true),
	BOTH(true, true),
	UNKNOWN(false, false);

	public final boolean loader;
	public final boolean ui;

	Type(boolean loader, boolean ui) {
		this.loader = loader;
		this.ui = ui;
	}
}
