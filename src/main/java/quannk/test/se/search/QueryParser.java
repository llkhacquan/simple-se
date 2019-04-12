package quannk.test.se.search;

import org.jetbrains.annotations.NotNull;
import quannk.test.se.TextHelper;
import quannk.test.se.Tokenizer;
import quannk.test.se.index.Dictionary;
import quannk.test.se.index.TermId;

import java.util.List;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 *
 */
public class QueryParser implements Function<String, Query> {
	private final Tokenizer tokenizer;
	private final Dictionary dictionary;

	QueryParser(Tokenizer tokenizer, Dictionary dictionary) {
		this.tokenizer = tokenizer;
		this.dictionary = dictionary;
	}

	@Override
	@NotNull
	public Query apply(@NotNull String rawQuery) {
		final String normalizedQuery = TextHelper.normalizerString(rawQuery);
		final List<TermId> termIds = tokenizer.tokenize(normalizedQuery, false).stream().map(dictionary::getTermId).filter(Objects::nonNull).collect(Collectors.toList());
		return new Query(rawQuery, normalizedQuery, termIds);
	}
}
