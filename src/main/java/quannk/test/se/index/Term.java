package quannk.test.se.index;

import com.google.common.base.Preconditions;
import org.jetbrains.annotations.NotNull;

/**
 * {@link Term} is the smallest unit of a document. A document contains many terms, many times with order
 * <p>
 * In this simple search-engine; term is a non-empty-lower-case string contains only a-z, 0-9
 */
public class Term {

	private final @NotNull String raw;

	public Term(@NotNull String raw) {
		Preconditions.checkArgument(raw.matches("[a-z0-9]+"), "[" + raw + "] is not a valid term");
		this.raw = raw;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}

		Term term = (Term) o;

		return raw.equals(term.raw);

	}

	@Override
	public int hashCode() {
		return raw.hashCode();
	}

	public @NotNull String getRaw() {
		return raw;
	}
}
