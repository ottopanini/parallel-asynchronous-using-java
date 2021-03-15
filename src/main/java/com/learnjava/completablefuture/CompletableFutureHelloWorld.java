package com.learnjava.completablefuture;

import com.learnjava.service.HelloWorldService;
import com.learnjava.util.CommonUtil;

import java.util.concurrent.CompletableFuture;

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
        CommonUtil.startTimer();
        CompletableFuture<String> helloFuture = CompletableFuture.supplyAsync(helloWorldService::hello);
        CompletableFuture<String> worldFuture = CompletableFuture.supplyAsync(helloWorldService::world);

        String join = helloFuture.thenCombine(worldFuture, (h, w) -> h + w)
                .thenApply(String::toUpperCase)
                .join();
        CommonUtil.timeTaken();

        return join;
    }

    public String helloWorldCombined3AsyncTasks() {
        CommonUtil.startTimer();
        CompletableFuture<String> helloFuture = CompletableFuture.supplyAsync(helloWorldService::hello);
        CompletableFuture<String> worldFuture = CompletableFuture.supplyAsync(helloWorldService::world);
        CompletableFuture<String> hiFuture = CompletableFuture.supplyAsync(() -> {
            CommonUtil.delay(1000);
            return "Hi Completablefuture";
        });

        String join = helloFuture.thenCombine(worldFuture, (h, w) -> h + w)
                .thenCombine(hiFuture, (previous, current) -> previous + current)
                .thenApply(String::toUpperCase)
                .join();
        CommonUtil.timeTaken();

        return join;
    }

    public static void main(String[] args) {
        CompletableFutureHelloWorld completableFutureHelloWorld = new CompletableFutureHelloWorld(new HelloWorldService());
        completableFutureHelloWorld.helloWorld()
                .thenAccept(x -> log("Result: " + x))
                .join();

        log("done!");
    }
}
