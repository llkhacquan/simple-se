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
	 * hold
	 */
	private final Map<TermId, Set<DocId>> term2documentsMap;
	Set<DocId> docIdSet;

	private InvertedIndexer(Map<TermId, Set<DocId>> term2documentsMap) {
		this.term2documentsMap = term2documentsMap;
	}

	/**
	 * read data from file and build the {@link Dictionary}
	 */
	public static @NotNull InvertedIndexer createFromFile(@NotNull File data, @NotNull Dictionary dictionary) throws IOException {
		Tokenizer tokenizer = new Tokenizer();
		List<Document> documents = new ArrayList<>();
		Map<TermId, Set<DocId>> term2documentsMap = new HashMap<>();

		try (BufferedReader reader = new BufferedReader(new FileReader(data))) {
			String line;
			int count = 0;
			while ((line = reader.readLine()) != null) {
				final List<Term> terms = tokenizer.tokenize(line);
				final List<TermId> termIds = terms.stream().map(dictionary::addTerm).collect(Collectors.toList());
				DocId newDocId = new DocId(count);
				documents.add(new Document(newDocId, line, termIds));
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
		return null;
	}

	public Iterable<DocId> getDocumentsContainingTerm(TermId termId) {
		return term2documentsMap.get(termId);
	}
}
