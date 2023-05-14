package com.stay.util;

import org.springframework.stereotype.Component;

@Component
public class Statistic {
    private static int visited = 0;

    public synchronized int getNextValue() {
        return ++visited;
    }
}
