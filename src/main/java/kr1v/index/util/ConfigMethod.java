package kr1v.index.util;

import java.util.*;

public record ConfigMethod(TypeOfClass typeOfClass, MemberType memberType, List<Waaa> waaas) {
	public static final ConfigMethod NOT_AVAILABLE = new ConfigMethod(null, null, null);
	public static final ConfigMethod UNKNOWN = new ConfigMethod(null, null, null);

	public static ConfigMethod of(TypeOfClass typeOfClass, MemberType instance, List<Waaa> waaas) {
		return new ConfigMethod(typeOfClass, instance, waaas);
	}

	public static ConfigMethod of(TypeOfClass typeOfClass, MemberType instance, Waaa waaa) {
		return new ConfigMethod(typeOfClass, instance, List.of(waaa));
	}

	public enum MemberType {
		INSTANCE("instance members"),
		STATIC("static members"),
		EITHER("static or instance members"),
		NONE("");

		public final String description;
		MemberType(String description) {
			this.description = description;
		}

	}
	public static class Waaa {
		public static final Map<String, Set<String>> ENTRIES = new HashMap<>();
		public static final Waaa PRIMITIVE = new Waaa("Primitive", "of primitive types", "int", "List<String>", "File");
		public static final Waaa ANNOTATED_PRIMITIVE = new Waaa("Annotated primitive", "of primitive types", "@Config int", "@Comment(\"This is a string list\") List<String>", "@Config File");
		public static final Waaa WRAPPER = new Waaa("Wrapper", "Config<Integer>", "Config<List<String>>", "Config<File>");


		public static Waaa special(String... examples) {
			return new Waaa("Special", "typed with special classes", examples);
		}

		public static Waaa builder(String... examples) {
			return new Waaa("Builder", "typed using builder methods", examples);
		}
		public final String name;
		public final String methodDescription;
		public final String[] examples;

		private Waaa(String name, String methodDescription, String... examples) {
			this.name = name;
			this.methodDescription = methodDescription;
			this.examples = examples;
			ENTRIES.computeIfAbsent(name, _ -> new TreeSet<>()).addAll(Arrays.asList(examples));
		}
	}

	public enum TypeOfClass {
		NORMAL("Normal"),
		EXTENDING("Extending"),
		ANNOTATED("Annotated"),
		RECORD("Record"),
		NONE("None"),
		;

		public final String name;

		TypeOfClass(String name) {
			this.name = name;
		}
	}
}
