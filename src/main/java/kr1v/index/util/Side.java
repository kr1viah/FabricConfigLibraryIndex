package kr1v.index.util;

public enum Side {
	CLIENT(true, false),
	SERVER(false, true),
	BOTH(true, true),
	UNKNOWN(false, false);

	public final boolean client;
	public final boolean server;

	Side(boolean client, boolean server) {
		this.client = client;
		this.server = server;
	}
}
