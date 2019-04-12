package quannk.test.se;

import org.jetbrains.annotations.NotNull;
import quannk.test.se.index.Term;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class Tokenizer {

	/**
	 * normalize, convert rawContent to list of term
	 */
	public @NotNull List<Term> tokenize(@NotNull String rawContent) {
		return tokenize(rawContent, true);
	}

	/**
	 * convert rawContent to list of term
	 */
	public @NotNull List<Term> tokenize(@NotNull String rawContent, boolean needNormalize) {
		String normalized = needNormalize ? TextHelper.normalizerString(rawContent) : rawContent;
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
}
