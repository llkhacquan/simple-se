package quannk.test.se.index;

import quannk.test.se.index.impl.Simple2WayMap;

import java.util.Iterator;
import java.util.Map;

/**
 * hold all {@link Term}s
 */
public class Dictionary {
	private TwoWayMap<TermId, Term> map = new Simple2WayMap<>();

	public int size() {
		return map.size();
	}

	/**
	 * put a term to the map; if the term is already in the dictionary, we return its id; else return the id of inserted term
	 */
	public TermId addTerm(Term term) {
		TermId id = map.get1(term);
		if (id == null) {
			id = new TermId(map.size());
			map.add(id, term);
		}
		return id;
	}

	/**
	 * @return term with the given id, null if the dictionary does not contains the term
	 */
	public Term getTerm(TermId id) {
		return map.get2(id);
	}

	/**
	 * @return id of the given term; null if not found
	 */
	public TermId getTermId(Term term) {
		return map.get1(term);
	}

	/**
	 * @return true if the dictionary contains the given {@link Term}
	 */
	public boolean containTerm(Term term) {
		return map.get1(term) != null;
	}

	public Iterator<Map.Entry<TermId, Term>> terms() {
		return map.iterator();
	}
}
