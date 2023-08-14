package com.stay.cache;

import com.stay.domain.model.CacheConfigModel;
import org.apache.commons.collections.MapIterator;
import org.apache.commons.collections.map.LRUMap;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

// https://crunchify.com/how-to-create-a-simple-in-memory-cache-in-java-lightweight-cache/
public class BaseCacheImpl<K, T> implements BaseCache<K, T> {

    private final long timeToLive;

    // LRUMap: A Map implementation with a fixed maximum size which
    // removes the least recently used entry if an entry is added when full.
    // The least recently used algorithm works on the get and put operations only.
    // Iteration of any kind, including setting the value by iteration, does not change the order.
    // Queries such as containsKey and containsValue or access via views also do not change the order.
    private final LRUMap cacheMap;

    protected class CacheObject {

        // currentTimeMillis(): Returns the current time in milliseconds.
        // Note that while the unit of time of the return value is a millisecond,
        // the granularity of the value depends on the underlying operating system and may be larger.
        // For example, many operating systems measure time in units of tens of milliseconds.
        public long lastAccessed;

        public T value;

        protected CacheObject(T value) {
            this.value = value;
            this.lastAccessed = System.currentTimeMillis();
        }
    }


    public BaseCacheImpl(CacheConfigModel config) {
        this.timeToLive = config.getTimeToLive() * 1000;
        cacheMap = new LRUMap(config.getMaxItems());
        if (timeToLive > 0 && config.getTimerInterval() > 0) {
            Thread t = new Thread(() -> { // Runnable Class
                while (true) {
                    try {
                        Thread.sleep(config.getTimerInterval() * 1000);
                    } catch (InterruptedException ex) {
                        ex.printStackTrace();
                    }
                    cleanup();
                }
            });

            // JVM exits when the only threads running are all daemon threads.
            // This method must be invoked before the thread is started.
            t.setDaemon(true);
            t.start();
        }
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<T> getAll() {
        return (List<T>) cacheMap.values()
                .stream()
                .map(cache -> ((CacheObject)cache).value)
                .collect(Collectors.toList());
    }

    @Override
    public T get(K key) {
        synchronized (cacheMap) {
            CacheObject c = (CacheObject) cacheMap.get(key);
            if (c == null)
                return null;
            else {
                c.lastAccessed = System.currentTimeMillis();
                return c.value;
            }
        }
    }

    @Override
    public void put(K key, T value) {
        synchronized (cacheMap) {
            cacheMap.put(key, new CacheObject(value));
        }
    }

    @Override
    public void remove(K key) {
        synchronized (cacheMap) {
            cacheMap.remove(key);
        }
    }

    @Override
    public int size() {
        synchronized (cacheMap) {
            return cacheMap.size();
        }
    }

    @Override
    public void cleanup() {
        // System: The System class contains several useful class fields and methods.
        // It cannot be instantiated. Among the facilities provided by the System class are standard input, standard output,
        // and error output streams; access to externally defined properties and environment variables;
        // a means of loading files and libraries; and a utility method for quickly copying a portion of an array.
        long now = System.currentTimeMillis();
        ArrayList<K> deleteKey = null;
        synchronized (cacheMap) {
            MapIterator itr = cacheMap.mapIterator();
            // ArrayList: Constructs an empty list with the specified initial capacity.
            // size(): Gets the size of the map.
            deleteKey = new ArrayList<K>((cacheMap.size() / 2) + 1);
            K key = null;
            CacheObject c = null;
            while (itr.hasNext()) {
                key = (K) itr.next();
                c = (CacheObject) itr.getValue();
                if (c != null && (now > (timeToLive + c.lastAccessed))) {
                    // deleteKey.add(key);
                    cacheMap.remove(key);
                }
            }
        }

        /*for (K key : deleteKey) {
            synchronized (cacheMap) {
                // remove(): Removes the specified mapping from this map.
                cacheMap.remove(key);
            }
            // yield(): A hint to the scheduler that the current thread is willing to
            // yield its current use of a processor.
            // The scheduler is free to ignore this hint.
            Thread.yield();
        }*/
    }
}
