package quannk.test.se.search;

import org.jetbrains.annotations.NotNull;

/**
 * a simple implementation of {@link Score} backed by a double
 */
public class DoubleScore implements Score {
	private final double score;

	public DoubleScore(double score) {
		this.score = score;
	}

	@Override
	public int compareTo(@NotNull Score o) {
		if (o instanceof DoubleScore) {
			return Double.compare(score, ((DoubleScore) o).score);
		} else {
			throw new IllegalArgumentException("Not supported score " + o);
		}
	}

	@Override
	public String toString() {
		return String.valueOf(score);
	}
}
