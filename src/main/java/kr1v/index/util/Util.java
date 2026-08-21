package kr1v.index.util;

import java.lang.reflect.Field;

public class Util {
	public static <T> T get(Field f) {
		return get(null, f);
	}

	@SuppressWarnings("unchecked")
	public static <T> T get(Object instance, Field f) {
		try {
			return (T) f.get(instance);
		} catch (IllegalAccessException e) {
			throw rethrow(e);
		}
	}

	@SuppressWarnings("unchecked")
	public static <T extends Throwable> T rethrow(Throwable t) throws T {
		throw (T) t;
	}
}
