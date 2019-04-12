package quannk.test.se.search;

import org.javatuples.Pair;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import quannk.test.se.index.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class InvertedIndexSearcher implements Searcher {
	private static final Logger LOG = LoggerFactory.getLogger(InvertedIndexSearcher.class);
	private final @NotNull DocumentStorage documentsStorage;
	private final @NotNull InvertedIndexer invertedIndexer;
	private final @NotNull Ranker ranker;
	private final @NotNull QueryParser queryParser;

	public InvertedIndexSearcher(@NotNull InvertedIndexer indexer,
								 @NotNull DocumentStorage documentsStorage,
								 @NotNull Ranker ranker,
								 @NotNull QueryParser queryParser
	) {
		this.documentsStorage = documentsStorage;
		this.invertedIndexer = indexer;
		this.ranker = ranker;
		this.queryParser = queryParser;
	}

	@Override
	public List<Pair<Score, Document>> search(String rawQuery, int limit) {
		long start = System.currentTimeMillis();
		try {
			Query query = queryParser.apply(rawQuery);
			if (query.getTerms().size() == 0) {
				return Collections.emptyList();
			}
			List<Pair<Score, Document>> result = new ArrayList<>();
			for (TermId termId : query.getTerms()) {
				for (DocId docId : invertedIndexer.getDocumentsContainingTerm(termId)) {
					Document document = documentsStorage.getDoc(docId);
					final Score score = ranker.score(document, query);
					result.add(Pair.with(score, document));
				}
			}
			// compare by score, the bigger come first
			result.sort((o1, o2) -> o2.getValue0().compareTo(o1.getValue0()));
			return result.stream().limit(limit).collect(Collectors.toList());
		} finally {
			LOG.info("Processed query [{}] in {}ms", rawQuery, System.currentTimeMillis() - start);
		}
	}
}
