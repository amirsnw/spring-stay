package com.stay.domain.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class CacheConfigModel {

    long timeToLive;
    long timerInterval;
    int maxItems;

    public String getRawCacheConfig() {
        return timeToLive + "-" + timerInterval + "-" + maxItems;
    }
}
