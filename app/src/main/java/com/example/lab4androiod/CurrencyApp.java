package com.example.lab4androiod;

import android.app.Application;


import java.util.Collection;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class CurrencyApp extends Application {
    static ExecutorService executorService = Executors.newFixedThreadPool(4);
    NetworkingService networkingService = new NetworkingService();
    DatabaseManager databaseManager = new DatabaseManager();


}
