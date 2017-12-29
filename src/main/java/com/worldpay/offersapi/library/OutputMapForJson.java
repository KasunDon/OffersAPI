package com.worldpay.offersapi.library;

import java.util.HashMap;
import java.util.Map;

public class OutputMapForJson {

    public static <K, V> Map<K, V> output(
        K key,
        V value
    ) {

        Map<K, V> output = new HashMap<>();
        output.put(key, value);

        return output;
    }
}
