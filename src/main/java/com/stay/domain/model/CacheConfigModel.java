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

    public CacheConfigModel(String timeToLive, String timerInterval, String maxItems) {
        this.timeToLive = Long.parseLong(timeToLive);
        this.timerInterval = Long.parseLong(timerInterval);
        this.maxItems = Integer.parseInt(maxItems);
    }

    public String getRawCacheConfig() {
        return timeToLive + "-" + timerInterval + "-" + maxItems;
    }
}
