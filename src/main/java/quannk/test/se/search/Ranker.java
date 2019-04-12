package quannk.test.se.search;

import quannk.test.se.index.Document;

public interface Ranker<T extends Score> {

	/**
	 * rank the relevant of the document with the given query; normally the bigger score, the more relevant the document
	 */
	T score(Document document, Query query);
}
