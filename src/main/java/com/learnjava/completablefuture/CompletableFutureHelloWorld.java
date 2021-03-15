package com.learnjava.completablefuture;

import com.learnjava.service.HelloWorldService;

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


    public static void main(String[] args) {
        CompletableFutureHelloWorld completableFutureHelloWorld = new CompletableFutureHelloWorld(new HelloWorldService());
        completableFutureHelloWorld.helloWorld()
                .thenAccept(x -> log("Result: " + x))
                .join();

        log("done!");
    }
}
