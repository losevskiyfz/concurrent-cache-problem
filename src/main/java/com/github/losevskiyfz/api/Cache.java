package com.github.losevskiyfz.api;

public interface Cache {
    void bulkUpdate(Updater updater);
    long[] bulkRead(int[] indices);
}
