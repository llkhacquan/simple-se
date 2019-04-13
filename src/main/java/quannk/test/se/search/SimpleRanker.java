package quannk.test.se.search;

import quannk.test.se.index.Document;
import quannk.test.se.index.TermId;

import java.util.HashSet;
import java.util.Set;

/**
 * a simple ranker by Jaccard similarity
 * <p>
 * see more: https://en.wikipedia.org/wiki/Jaccard_index
 */
public class SimpleRanker implements Ranker<DoubleScore> {

	@Override
	public DoubleScore score(Document document, Query query) {
		final Set<TermId> term1 = new HashSet<>(document.getTermIds());
		final Set<TermId> term2 = new HashSet<>(query.getTerms());

		final Set<TermId> allTerms = new HashSet<>(term1);
		allTerms.addAll(term2);
		int numberOfTermInBoth = (int) term1.stream().filter(term2::contains).count();
		return new DoubleScore((double) numberOfTermInBoth / allTerms.size());
	}
}
