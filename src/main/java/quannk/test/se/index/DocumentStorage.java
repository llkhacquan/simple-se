package quannk.test.se.index;

import com.google.common.base.Preconditions;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * a simple document storage backed by an array list
 */
public class DocumentStorage {
	private List<Document> documents = new ArrayList<>();

	public void add(@NotNull Document document) {
		Preconditions.checkState(document.getId().getId() == documents.size());
		documents.add(document);
	}

	public Document getDoc(DocId docId) {
		return documents.get(docId.getId());
	}

	public int size() {
		return documents.size();
	}

	public Iterator<Document> iterator() {
		return documents.iterator();
	}
}
