package com.learnjava.completablefuture;

import com.learnjava.service.HelloWorldService;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static com.learnjava.util.CommonUtil.*;
import static com.learnjava.util.LoggerUtil.log;

public class CompletableFutureHelloWorld {

    private final HelloWorldService helloWorldService;

    public CompletableFutureHelloWorld(HelloWorldService helloWorldService) {
        this.helloWorldService = helloWorldService;
    }

    public CompletableFuture<String> helloWorld_withSize() {
        return CompletableFuture.supplyAsync(helloWorldService::helloWorld)
                .thenApply(String::toUpperCase)
                .thenApply(x -> x.length() + " - " + x);
    }

    public CompletableFuture<String> helloWorld() {
        return CompletableFuture.supplyAsync(helloWorldService::helloWorld)
                .thenApply(String::toUpperCase);
    }

    public String helloWorldCombinedAsyncTasks() {
        startTimer();
        CompletableFuture<String> helloFuture = CompletableFuture.supplyAsync(helloWorldService::hello);
        CompletableFuture<String> worldFuture = CompletableFuture.supplyAsync(helloWorldService::world);

        String join = helloFuture.thenCombine(worldFuture, (h, w) -> h + w)
                .thenApply(String::toUpperCase)
                .join();
        timeTaken();

        return join;
    }

    public String helloWorldCombined3AsyncTasks() {
        startTimer();
        CompletableFuture<String> helloFuture = CompletableFuture.supplyAsync(helloWorldService::hello);
        CompletableFuture<String> worldFuture = CompletableFuture.supplyAsync(helloWorldService::world);
        CompletableFuture<String> hiFuture = CompletableFuture.supplyAsync(() -> {
            delay(1000);
            return "Hi Completablefuture";
        });

        String join = helloFuture
                .thenCombine(worldFuture, (h, w) -> h + w)
                .thenCombine(hiFuture, (previous, current) -> previous + current)
                .thenApply(String::toUpperCase)
                .join();
        timeTaken();

        return join;
    }

    public String helloWorldCombined3AsyncTasksWithLog() {
        startTimer();
        CompletableFuture<String> helloFuture = CompletableFuture.supplyAsync(helloWorldService::hello);
        CompletableFuture<String> worldFuture = CompletableFuture.supplyAsync(helloWorldService::world);
        CompletableFuture<String> hiFuture = CompletableFuture.supplyAsync(() -> {
            delay(1000);
            return "Hi Completablefuture";
        });

        String join = helloFuture
                .thenCombine(worldFuture, (h, w) -> {
                    log("thenCombine h/w");
                    return h + w;
                })
                .thenCombine(hiFuture, (previous, current) -> {
                    log("thenCombine previous/current");
                    return previous + current;
                })
                .thenApply(s -> {
                    log("thenApply");
                    return s.toUpperCase();
                })
                .join();
        timeTaken();

        return join;
    }

    public String helloWorldCombined3AsyncTasksWithCustomThreadPool() {
        startTimer();

        ExecutorService executorService = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());

        CompletableFuture<String> helloFuture = CompletableFuture.supplyAsync(helloWorldService::hello, executorService);
        CompletableFuture<String> worldFuture = CompletableFuture.supplyAsync(helloWorldService::world, executorService);
        CompletableFuture<String> hiFuture = CompletableFuture.supplyAsync(() -> {
            delay(1000);
            return "Hi Completablefuture";
        }, executorService);

        String join = helloFuture
                .thenCombine(worldFuture, (h, w) -> {
                    log("thenCombine h/w");
                    return h + w;
                })
                .thenCombine(hiFuture, (previous, current) -> {
                    log("thenCombine previous/current");
                    return previous + current;
                })
                .thenApply(s -> {
                    log("thenApply");
                    return s.toUpperCase();
                })
                .join();
        timeTaken();

        return join;
    }

    public String helloWorldCombined3AsyncTasksWithLogAndAsyncStage() {
        startTimer();
        CompletableFuture<String> helloFuture = CompletableFuture.supplyAsync(helloWorldService::hello);
        CompletableFuture<String> worldFuture = CompletableFuture.supplyAsync(helloWorldService::world);
        CompletableFuture<String> hiFuture = CompletableFuture.supplyAsync(() -> {
            delay(1000);
            return "Hi Completablefuture";
        });

        String join = helloFuture
                .thenCombineAsync(worldFuture, (h, w) -> {
                    log("thenCombine h/w");
                    return h + w;
                })
                .thenCombineAsync(hiFuture, (previous, current) -> {
                    log("thenCombine previous/current");
                    return previous + current;
                })
                .thenApplyAsync(s -> {
                    log("thenApply");
                    return s.toUpperCase();
                })
                .join();
        timeTaken();

        return join;
    }

    public String helloWorldCombined3AsyncTasksWithCustomThreadPoolAndAsyncStage() {
        startTimer();

        ExecutorService executorService = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());

        CompletableFuture<String> helloFuture = CompletableFuture.supplyAsync(helloWorldService::hello, executorService);
        CompletableFuture<String> worldFuture = CompletableFuture.supplyAsync(helloWorldService::world, executorService);
        CompletableFuture<String> hiFuture = CompletableFuture.supplyAsync(() -> {
            delay(1000);
            return "Hi Completablefuture";
        }, executorService);

        String join = helloFuture
                .thenCombineAsync(worldFuture, (h, w) -> {
                    log("thenCombine h/w");
                    return h + w;
                }, executorService)
                .thenCombineAsync(hiFuture, (previous, current) -> {
                    log("thenCombine previous/current");
                    return previous + current;
                }, executorService)
                .thenApplyAsync(s -> {
                    log("thenApply");
                    return s.toUpperCase();
                }, executorService)
                .join();
        timeTaken();

        return join;
    }

    //assignment 4
    public String helloWorld_4_async_calls() {
        startTimer();
        CompletableFuture<String> helloFuture = CompletableFuture.supplyAsync(helloWorldService::hello);
        CompletableFuture<String> worlFutured = CompletableFuture.supplyAsync(helloWorldService::world);
        CompletableFuture<String> hiFuture = CompletableFuture.supplyAsync(() -> {
            delay(1000);
            return " HI CompletableFuture!";
        });
        CompletableFuture<String> byeFuture = CompletableFuture.supplyAsync(() -> {
            delay(1000);
            return " Bye!";
        });

        String hw = helloFuture
                .thenCombine(worlFutured, (h, w) -> h + w) // (first,second)
                .thenCombine(hiFuture, (previous, current) -> previous + current)
                .thenCombine(byeFuture, (previous, current) -> previous + current)
                .thenApply(String::toUpperCase)
                .join();

        timeTaken();

        return hw;
    }

    public CompletableFuture<String> composeHelloWorld() {
        return CompletableFuture.supplyAsync(helloWorldService::hello)
                .thenCompose((previous) -> helloWorldService.worldFuture(previous))
                .thenApply(String::toUpperCase);
    }

    public String anyOf() {
        //db
        CompletableFuture<String> response_from_db = CompletableFuture.supplyAsync(() -> {
            delay(1000);
            log("response from db");
            return "hello world";
        });
        //restCall
        CompletableFuture<String> response_from_restApi = CompletableFuture.supplyAsync(() -> {
            delay(2000);
            log("response from restCall");
            return "hello world";
        });
        //soapCall
        CompletableFuture<String> response_from_soapApi = CompletableFuture.supplyAsync(() -> {
            delay(3000);
            log("response from soapCall");
            return "hello world";
        });

        // take the result of the fastest
        CompletableFuture<Object> fastest = CompletableFuture.anyOf(response_from_db, response_from_restApi, response_from_soapApi);
        String result = (String) fastest.thenApply(s -> {
            if (s instanceof String) {
                return s;
            }

            return null;
        })
                .join();

        return result;
    }

    public static void main(String[] args) {
        CompletableFutureHelloWorld completableFutureHelloWorld = new CompletableFutureHelloWorld(new HelloWorldService());
        completableFutureHelloWorld.helloWorld()
                .thenAccept(x -> log("Result: " + x))
                .join();

        log("done!");
    }
}
