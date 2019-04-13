package quannk.test.se.search;

import org.javatuples.Pair;
import quannk.test.se.Tokenizer;
import quannk.test.se.index.Dictionary;
import quannk.test.se.index.Document;
import quannk.test.se.index.DocumentStorage;
import quannk.test.se.index.InvertedIndexer;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

public class Runner {
	private final Searcher searcher;

	public Runner(File file) throws IOException {
		Dictionary dictionary = new Dictionary();
		DocumentStorage documentsStorage = new DocumentStorage();
		final Tokenizer tokenizer = new Tokenizer();
		InvertedIndexer indexer = InvertedIndexer.createFromFile(file, tokenizer, dictionary, documentsStorage);
		Ranker<DoubleScore> ranker = new SimpleRanker();
		QueryParser queryParser = new QueryParser(tokenizer, dictionary);
		Ranker<DoubleScore> bm25Ranker = new BM25Ranker(documentsStorage, indexer);
		searcher = new InvertedIndexSearcher(indexer, documentsStorage, bm25Ranker, queryParser);
	}

	public static void main(String[] args) throws IOException {
		Runner runner = new Runner(new File("product_names.txt"));
		File queryFile = new File("100_query.txt");
		try (BufferedReader reader = new BufferedReader(new FileReader(queryFile))) {
			String line;
			while ((line = reader.readLine()) != null) {
				final List<Pair<Score, Document>> searchResult = runner.searcher.search(line, 3);
				if (searchResult.size() == 0) {
					System.out.printf("%s\t%s\t%s\n", line, 0, "NONE");
				} else {
					for (Pair<Score, Document> pair : searchResult) {
						System.out.printf("%s\t%s\t%s\n", line, pair.getValue0(), pair.getValue1());
					}
				}

			}
		}
	}
}
