package quannk.test.se.index;

import org.jetbrains.annotations.NotNull;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * class holds information of documents in index; in this problem, it's a product
 */
public class Document {
	private final @NotNull DocId id;
	private final @NotNull String rawContent;
	private final @NotNull Set<TermId> termIds;

	public Document(@NotNull DocId id, @NotNull String rawContent, @NotNull List<TermId> termIds) {
		this.id = id;
		this.rawContent = rawContent;
		this.termIds = Collections.unmodifiableSet(new HashSet<>(termIds));
	}

	public @NotNull DocId getId() {
		return id;
	}

	public @NotNull Set<TermId> getTermIds() {
		return termIds;
	}

	public @NotNull String getRawContent() {
		return rawContent;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}

		Document document = (Document) o;

		return id.equals(document.id);

	}

	@Override
	public int hashCode() {
		return id.hashCode();
	}
}
