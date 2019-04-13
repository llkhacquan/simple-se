package quannk.test.se.search;

import org.jetbrains.annotations.NotNull;
import quannk.test.se.index.Document;
import quannk.test.se.index.DocumentStorage;
import quannk.test.se.index.InvertedIndexer;
import quannk.test.se.index.TermId;

/**
 *
 */
public class BM25Ranker implements Ranker<DoubleScore> {

	private final DocumentStorage documentStorage;
	private final InvertedIndexer invertedIndexer;
	private final double k1;
	private final double b;
	private final double averageLengthOfDocument;

	BM25Ranker(DocumentStorage documentStorage, InvertedIndexer invertedIndexer) {
		this(documentStorage, invertedIndexer, 1.2, 0.75);
	}

	BM25Ranker(DocumentStorage documentStorage, InvertedIndexer invertedIndexer, double k1, double b) {
		this.documentStorage = documentStorage;
		this.invertedIndexer = invertedIndexer;
		this.k1 = k1;
		this.b = b;
		final long[] totalLength = {0};
		documentStorage.iterator().forEachRemaining(document -> {
			totalLength[0] += document.getTermIds().size();
		});
		averageLengthOfDocument = (double) totalLength[0] / documentStorage.size();
	}

	/**
	 * compute idf of a given term base on
	 * return log( (N-n+0.5) / (n+0.5))
	 * where:
	 * <li>N = nDoc: number of all documents</li>
	 * <li>n = nDocContainingTerm: number of documents contains the term</li>
	 */
	private static double idf(int nDoc, int nDocContainingTerm) {
		return Math.log((nDoc - nDocContainingTerm + 0.5) / (nDocContainingTerm + 0.5));
	}

	private double idf(@NotNull TermId id) {
		return idf(documentStorage.size(), invertedIndexer.getNumberOfDocumentsContainingTerm(id));
	}

	/**
	 * return term frequency (number of appearance) of the given term in the given document
	 */
	private double f(TermId termId, Document document) {
		return document.getTermIds().stream().filter(termId1 -> termId1.equals(termId)).count();
	}

	@Override
	public DoubleScore score(Document document, Query query) {
		final double sum = query.getTerms().stream().mapToDouble(termId -> {
			final double f = f(termId, document);
			return idf(termId) * (f * (k1 + 1) / (f + k1 * (1 - b + b * documentStorage.size() / averageLengthOfDocument)));
		}).sum();
		return new DoubleScore(sum);
	}
}
