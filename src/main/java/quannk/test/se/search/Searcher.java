package quannk.test.se.search;

import org.javatuples.Pair;
import quannk.test.se.index.Document;

import java.util.List;

public interface Searcher {
	List<Pair<Score, Document>> search(String query, int limit);
}
