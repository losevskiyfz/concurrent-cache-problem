package com.github.losevskiyfz.impl;

import com.github.losevskiyfz.api.Cache;
import com.github.losevskiyfz.api.Updater;

import java.util.Arrays;

public class ConcurrentCache implements Cache {
    private volatile long[] currentCacheState = new long[] {
            1L, 2L, 3L, 4L, 5L, 6L, 7L, 8L, 9L, 10L
    };

    @Override
    public void bulkUpdate(Updater updater) {
        long[] newCacheState = Arrays.copyOf(currentCacheState, currentCacheState.length);
        updater.updateCurrentState(newCacheState);
        currentCacheState = newCacheState;
    }

    @Override
    public long[] bulkRead(int[] indices) {
        long[] snapshot = currentCacheState;
        int counter = 0;
        long[] res =  new long[indices.length];
        for (int i = 0; i< indices.length; i++){
            res[counter++]  = snapshot[indices[i]];
        }
        return res;
    }
}
