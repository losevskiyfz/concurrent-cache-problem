package com.github.losevskiyfz;

import com.github.losevskiyfz.api.Cache;
import com.github.losevskiyfz.api.Updater;
import com.github.losevskiyfz.impl.ConcurrentCache;
import com.github.losevskiyfz.impl.RandomValuesUpdater;

import java.util.Arrays;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Main {
    public static void main(String[] args) {

        Cache cache = new ConcurrentCache();
        Updater updater = new RandomValuesUpdater();

        ExecutorService readers = Executors.newFixedThreadPool(10);
        ExecutorService updaters = Executors.newSingleThreadExecutor();

        updaters.execute(() -> {
            while (true) {
                cache.bulkUpdate(updater);
                System.out.println("Updater thread updated cache");
                sleep(200);
            }
        });

        for (int i = 0; i < 10; i++) {
            final int threadNumber = i + 1;
            readers.execute(() -> {
                // offset перед стартом чтения
                sleep(threadNumber * 200); // 200 мс между стартами потоков
                while (true) {
                    System.out.println(Thread.currentThread().getName() + " read: " +
                            Arrays.toString(cache.bulkRead(new int[]{0, 1, 2, 3, 4, 5, 6, 7, 8, 9})));
                    sleep(500);
                }
            });
        }
    }

    private static void sleep(long millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}