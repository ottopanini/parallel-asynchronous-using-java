package com.learnjava.completablefuture;

import com.learnjava.service.HelloWorldService;

import java.util.concurrent.CompletableFuture;

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

    public static void main(String[] args) {
        CompletableFutureHelloWorld completableFutureHelloWorld = new CompletableFutureHelloWorld(new HelloWorldService());
        completableFutureHelloWorld.helloWorld()
                .thenAccept(x -> log("Result: " + x))
                .join();

        log("done!");
    }
}
