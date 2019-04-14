package quannk.test.se.index;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Iterator;
import java.util.Map;

/**
 * simple interface of Bidirectional Map which map objects between 2 sets 1 by 1
 */
public interface TwoWayMap<T1, T2> {

	int size();

	/**
	 * remove all element in this bi-map
	 */
	void clear();

	/**
	 * @return true if the map is empty
	 */
	default boolean isEmpty() {
		return size() == 0;
	}

	/**
	 * add a pair of (t1,t2) to the map
	 *
	 * @throws IllegalArgumentException if t1 or t2 exists in the map
	 */
	default void addSafe(T1 t1, T2 t2) throws IllegalArgumentException {
		if (!add(t1, t2)) {
			throw new IllegalArgumentException("t1 and t2 must not exist in the map");
		}
	}

	/**
	 * if both t1 and t2 do not exist in the map, add a pair of (t1,t2) to the map; and return true
	 * else return false; this is no-throw version of {@link TwoWayMap#addSafe(Object, Object)}
	 */
	boolean add(T1 t1, T2 t2);

	/**
	 * return the corresponding T1 of the given t2
	 */
	@Nullable T1 get1(@NotNull T2 t2);

	/**
	 * return the corresponding T2 of the given t1
	 */
	@Nullable T2 get2(@NotNull T1 t1);

	/**
	 * if T1 t1 exists in the map; delete the object T1 t1 and its corresponding T2 t2; return the t2
	 * else return null;
	 */
	@Nullable T2 delete1(@NotNull T1 t);

	/**
	 * if T2 t2 exists in the map; delete the object T2 t2 and its corresponding T1 t1; return the t1
	 * else return null;
	 */
	@Nullable T1 delete2(@NotNull T2 t);

	Iterator<Map.Entry<T1, T2>> iterator();

}
