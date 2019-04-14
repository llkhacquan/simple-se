package quannk.test.se;

import org.jetbrains.annotations.NotNull;

import java.text.Normalizer;

public final class TextHelper {
	/**
	 * - lower case
	 * - normalize whitespace: trimming; convert multiple spaces to a single space
	 */
	public static @NotNull String normalizerString(@NotNull String rawContent) {

		// lowser case + trimming
		String s = rawContent.toLowerCase().trim();

		s = removeNonAlphabetAndDigitOnly(s);
		// normalize spaces
		s = s.replaceAll("\\s+", " ");

		return s;
	}

	/**
	 *
	 * - remove accents (Et ça sera sa moitié => Et ca sera sa moitie)
	 */
	public static @NotNull String removeAccents(@NotNull String s){
		// remove accents (https://stackoverflow.com/questions/3322152/is-there-a-way-to-get-rid-of-accents-and-convert-a-whole-string-to-regular-lette)
		s = Normalizer.normalize(s, Normalizer.Form.NFD);
		s = s.replaceAll("[\\p{InCombiningDiacriticalMarks}]", "");
		s = s.replaceAll("\\p{M}", "");
		return s;
	}

	/**
	 * replace all non-digit and non-alphabet to space
	 */
	private static @NotNull String removeNonAlphabetAndDigitOnly(@NotNull String s) {
		return s.replaceAll("[^a-z0-9 ]", " ");
	}
}
