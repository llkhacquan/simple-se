package quannk.test.se.index.impl;

import com.google.common.collect.HashBiMap;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import quannk.test.se.index.TwoWayMap;

import java.util.Iterator;
import java.util.Map;

/**
 * simple implementation of {@link TwoWayMap} which is backed by {@link com.google.common.collect.BiMap}
 * <p>
 * In the future (which means never), an advanced implementation might support threat-safe or very big data;
 */
public class Simple2WayMap<K, V> implements TwoWayMap<K, V> {
	private final HashBiMap<K, V> map = HashBiMap.create();

	@Override
	public int size() {
		return map.size();
	}

	@Override
	public void clear() {
		map.clear();
	}

	@Override
	public boolean add(K k, V v) {
		if (map.containsKey(k) || map.containsValue(v)) {
			return false;
		} else {
			map.put(k, v);
			return true;
		}
	}

	@Nullable
	@Override
	public K get1(@NotNull V v) {
		return map.inverse().get(v);
	}

	@Nullable
	@Override
	public V get2(@NotNull K k) {
		return map.get(k);
	}

	@Nullable
	@Override
	public V delete1(@NotNull K t) {
		return map.remove(t);
	}

	@Nullable
	@Override
	public K delete2(@NotNull V t) {
		return map.inverse().remove(t);
	}

	@Override
	public Iterator<Map.Entry<K, V>> iterator() {
		return map.entrySet().iterator();
	}
}
