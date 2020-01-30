package ru.otus.cachehw;


import java.lang.ref.SoftReference;
import java.util.*;

public class MyCache<K, V> implements HwCache<K, V> {

    private final Map<K, SoftReference<V>> elements = new HashMap<>();
    private List<HwListener> listeners = new ArrayList<>();

    @Override
    public void put(K key, V value) {
        elements.put(key, new SoftReference<>(value));
    }

    @Override
    public void remove(K key) {
        SoftReference<V> ref = elements.get(key);
        if (ref != null) {
            elements.remove(key);
        }
    }

    @Override
    public V get(K key) {
        Optional<SoftReference<V>> optional = Optional.ofNullable(elements.get(key));
        return optional.map(SoftReference::get).orElse(null);
    }

    @Override
    public void addListener(HwListener listener) {
        listeners.add(listener);
    }

    @Override
    public void removeListener(HwListener listener) {
        listeners.remove(listener);
    }

}
