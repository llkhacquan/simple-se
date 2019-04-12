package quannk.test.se.search;

import com.google.common.base.Preconditions;
import org.jetbrains.annotations.NotNull;
import quannk.test.se.index.TermId;

import java.util.Collections;
import java.util.List;

public class Query {
	private final @NotNull String rawQuery;
	private final @NotNull String normalizedQuery;
	private final @NotNull List<TermId> terms;

	public Query(@NotNull String rawQuery, @NotNull String normalizedQuery, @NotNull List<TermId> terms) {
		this.rawQuery = rawQuery;
		this.normalizedQuery = normalizedQuery;
		this.terms = Collections.unmodifiableList(terms);
		for (TermId term : terms) {
			Preconditions.checkNotNull(term);
		}
	}

	@NotNull
	public String getRawQuery() {
		return rawQuery;
	}

	@NotNull
	public String getNormalizedQuery() {
		return normalizedQuery;
	}

	@NotNull
	public List<TermId> getTerms() {
		return terms;
	}

}
