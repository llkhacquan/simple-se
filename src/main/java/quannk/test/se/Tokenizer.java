package quannk.test.se;

import org.jetbrains.annotations.NotNull;
import quannk.test.se.index.Term;

import java.text.Normalizer;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class Tokenizer {

	/**
	 * normalize, convert rawContent to list of term
	 */
	public @NotNull List<Term> tokenize(@NotNull String rawContent) {
		String normalized = normalizerRawContent(rawContent);
		if (normalized.length() == 0) {
			return Collections.emptyList();
		}
		final String[] terms = normalized.split(" ");
		if (terms.length == 0) {
			return Collections.emptyList();
		} else {
			return Arrays.stream(terms).filter(s -> s.length() > 0).map(Term::new).collect(Collectors.toList());
		}
	}

	/**
	 * - lower case
	 * - remove accents (Et ça sera sa moitié => Et ca sera sa moitie)
	 * - normalize whitespace: trimming; convert multiple spaces to a single space
	 */
	private @NotNull String normalizerRawContent(@NotNull String rawContent) {

		// lowser case + trimming
		String s = rawContent.toLowerCase().trim();

		// remove accents (https://stackoverflow.com/questions/3322152/is-there-a-way-to-get-rid-of-accents-and-convert-a-whole-string-to-regular-lette)
		s = Normalizer.normalize(s, Normalizer.Form.NFD);
		s = s.replaceAll("[\\p{InCombiningDiacriticalMarks}]", "");
		s = s.replaceAll("\\p{M}", "");

		s = removeNonAlphabetAndDigitOnly(s);
		// normalize spaces
		s = s.replaceAll("\\s+", " ");

		return s;
	}

	/**
	 * replace all non-digit and non-alphabet to space
	 */
	private @NotNull String removeNonAlphabetAndDigitOnly(@NotNull String s) {
		return s.replaceAll("[^a-z0-9 ]", " ");
	}
}
