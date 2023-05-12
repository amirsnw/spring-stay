package com.stay.resource.cache;

import org.springframework.stereotype.Repository;

import java.util.List;

public interface BaseCache<K, T> {

    List<T> getAll();

    T get(K key);

    void put(K key, T value);

    void remove(K key);

    int size();

    void cleanup();
}
