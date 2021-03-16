import java.util.Iterator;
import java.util.LinkedHashMap;

public class LRUCache<T> {

	private final int capacity;
	private final LinkedHashMap<Long, T> map;

	public LRUCache(int capacity) {
		this.capacity = capacity;
		this.map = new LinkedHashMap<>(capacity);
	}

	public T get(long key) {
		return map.get(key);
	}

	public void put(long key, T value) {
		if (map.containsKey(key) && map.size() == capacity) {
			Iterator<Long> iter = map.keySet().iterator();
			iter.next();
			iter.remove();
		}
		map.put(key, value);
	}

	public void clear() {
		map.clear();
	}

}
