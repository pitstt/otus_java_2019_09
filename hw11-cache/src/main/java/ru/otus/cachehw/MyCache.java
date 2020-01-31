package ru.otus.cachehw;


import java.lang.ref.SoftReference;
import java.util.*;

public class MyCache<K, V> implements HwCache<K, V> {

    private final WeakHashMap <K, SoftReference<V>> elements = new WeakHashMap <>();
    private List<HwListener> listeners = new ArrayList<>();

    @Override
    public void put(K key, V value) {
        notifyListener(key, value, "put");
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
        if(elements.get(key)==null){
            return null;
        }
        V result = elements.get(key).get();
        notifyListener(key, result, "get");
        return result;
    }

    @Override
    public void addListener(HwListener listener) {
        listeners.add(listener);
    }

    @Override
    public void removeListener(HwListener listener) {
        listeners.remove(listener);
    }


    private void notifyListener(K key, V value, String action) {
        listeners.forEach(listener -> {
            try {
                listener.notify(key, value, action);
            } catch (Exception e) {
                System.out.println(e);
            }
        });
    }

}
