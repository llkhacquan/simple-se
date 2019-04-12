package quannk.test.se.index;

import org.jetbrains.annotations.NotNull;
import quannk.test.se.Tokenizer;

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
		Map<TermId, Set<DocId>> term2documentsMap = new HashMap<>();

		try (BufferedReader reader = new BufferedReader(new FileReader(data))) {
			String line;
			int count = 0;
			while ((line = reader.readLine()) != null) {
				final List<Term> terms = tokenizer.tokenize(line);
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
		return new InvertedIndexer(term2documentsMap);
	}

	public Iterable<DocId> getDocumentsContainingTerm(TermId termId) {
		return term2documentsMap.get(termId);
	}
}
