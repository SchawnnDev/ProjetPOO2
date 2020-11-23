package fr.poo.threads;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ThreadManager {

    private static final ExecutorService service = Executors.newFixedThreadPool(10);

    public static ExecutorService getService() {
        return service;
    }
}
