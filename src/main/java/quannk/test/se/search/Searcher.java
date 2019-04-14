package quannk.test.se.search;

import quannk.test.se.index.*;

import java.util.Map;

public class Searcher {
	Map<DocId, Document> documentsStorage;
	TwoWayMap<TermId, Term> dictionary;
	InvertedIndexer invertedIndexer;
}
