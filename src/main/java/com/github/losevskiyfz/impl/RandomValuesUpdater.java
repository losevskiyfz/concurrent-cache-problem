package com.github.losevskiyfz.impl;

import com.github.losevskiyfz.api.Updater;

import java.util.Random;

public class RandomValuesUpdater implements Updater {
    private Random random = new Random();

    @Override
    public void updateCurrentState(long[] currentCacheState) {
        for (int i = 0; i < currentCacheState.length; i++) {
            currentCacheState[i] = random.nextLong() % 1000;
        }
    }
}
