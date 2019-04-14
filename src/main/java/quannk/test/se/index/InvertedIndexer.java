package quannk.test.se.index;

import com.google.common.base.Preconditions;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import quannk.test.se.TextHelper;
import quannk.test.se.Tokenizer;
import quannk.test.se.search.Runner;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

/**
 * In general, the indexer holds two abstract things: all documents and inverted-map from term => documents which contains term
 */
public class InvertedIndexer {
	private final static Logger LOG = LoggerFactory.getLogger(Runner.class);
	/**
	 * hold the inverted index: term => documents
	 */
	private final Map<TermId, Set<DocId>> term2documentsMap;

	private InvertedIndexer(Map<TermId, Set<DocId>> term2documentsMap) {
		this.term2documentsMap = Collections.unmodifiableMap(term2documentsMap);
	}

	/**
	 * read data from file and build the {@link Dictionary}
	 */
	public static @NotNull InvertedIndexer createFromFile(@NotNull File data, @NotNull Tokenizer tokenizer, @NotNull Dictionary dictionary, @NotNull DocumentStorage documentStorage) throws IOException {
		long start = System.currentTimeMillis();
		Map<TermId, Set<DocId>> term2documentsMap = new HashMap<>();

		try (BufferedReader reader = new BufferedReader(new FileReader(data))) {
			String line;
			int count = 0;
			while ((line = reader.readLine()) != null) {
				final String normalized = TextHelper.normalizerString(line);
				final List<Term> termsWithNoAccents = tokenizer.tokenize(TextHelper.removeAccents(normalized), false);
				final List<Term> termsWithAccents = tokenizer.tokenize(normalized, false);
				Preconditions.checkArgument(termsWithAccents.size() == termsWithNoAccents.size());
				final List<Term> terms = new ArrayList<>();
				for (int i = 0; i < termsWithAccents.size(); i++) {
					Preconditions.checkArgument(termsWithNoAccents.get(i).getRaw().equals(TextHelper.removeAccents(termsWithAccents.get(i).getRaw())));
					terms.add(termsWithAccents.get(i));
					if (!termsWithAccents.get(i).equals(termsWithNoAccents.get(i))) {
						terms.add(termsWithNoAccents.get(i));
					}
				}
				final List<TermId> termIds = terms.stream().map(dictionary::addTerm).collect(Collectors.toList());
				DocId newDocId = new DocId(count);
				documentStorage.add(new Document(newDocId, line, termIds));
				count++;
				// for each term, put the new document to its docIds set
				for (TermId termId : termIds) {
					term2documentsMap.compute(termId, (termId1, docIds) -> {
						if (docIds == null) {
							docIds = new HashSet<>();
						}
						docIds.add(newDocId);
						return docIds;
					});

				}
			}
		}
		LOG.info("Loaded data from {} in {}ms", data, System.currentTimeMillis() - start);
		return new InvertedIndexer(term2documentsMap);
	}

	public Iterable<DocId> getDocumentsContainingTerm(TermId termId) {
		return term2documentsMap.get(termId);
	}

	public int getNumberOfDocumentsContainingTerm(TermId termId) {
		return term2documentsMap.get(termId).size();
	}
}
