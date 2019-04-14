package quannk.test.se.search;

import org.jetbrains.annotations.NotNull;
import quannk.test.se.TextHelper;
import quannk.test.se.Tokenizer;
import quannk.test.se.index.Dictionary;
import quannk.test.se.index.Term;
import quannk.test.se.index.TermId;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

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
		final List<TermId> termIds = new ArrayList<>();
		for (Term term : tokenizer.tokenize(normalizedQuery, false)) {
			TermId termId = dictionary.getTermId(term);
			if (termId == null) {
				termId = dictionary.getTermId(new Term(TextHelper.removeAccents(term.getRaw())));
			}
			if (termId != null) {
				termIds.add(termId);
			}
		}
		return new Query(rawQuery, normalizedQuery, termIds);
	}
}
