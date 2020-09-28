package com.learnjava.completablefuture;

import com.learnjava.service.HelloWorldService;

import java.util.concurrent.CompletableFuture;

import static com.learnjava.util.CommonUtil.*;
import static com.learnjava.util.LoggerUtil.log;

public class CompletableFutureHelloWorld {

    private HelloWorldService helloWorldService;

    public CompletableFutureHelloWorld(HelloWorldService helloWorldService) {
        this.helloWorldService = helloWorldService;
    }

    public CompletableFuture<String> helloWorld() {

        return CompletableFuture.supplyAsync(() -> helloWorldService.helloWorld())//  runs this in a common fork-join pool
                .thenApply(String::toUpperCase);
    }

    public CompletableFuture<String> helloWorld_withSize() {

        return CompletableFuture.supplyAsync(() -> helloWorldService.helloWorld())//  runs this in a common fork-join pool
                .thenApply(String::toUpperCase)
                .thenApply((s) -> s.length() + " - " + s);
    }

    public String helloWorld_multiple_async_calls() {
        startTimer();
        CompletableFuture<String> hello = CompletableFuture.supplyAsync(() -> this.helloWorldService.hello());
        CompletableFuture<String> world = CompletableFuture.supplyAsync(() -> this.helloWorldService.world());

        String hw = hello
                .thenCombine(world, (h, w) -> h + w) // (first,second)
                .thenApply(String::toUpperCase)
                .join();

        timeTaken();

        return hw;
    }


    public String helloWorld_multiple_async_calls_1() {
        startTimer();
        CompletableFuture<String> hello = CompletableFuture.supplyAsync(() -> this.helloWorldService.hello());
        CompletableFuture<String> world = CompletableFuture.supplyAsync(() -> this.helloWorldService.world());
        CompletableFuture<String> hiCompletableFuture = CompletableFuture.supplyAsync(() -> {
            delay(1000);
            return " HI CompletableFuture!";
        });

        String hw = hello
                .thenCombine(world, (h, w) -> h + w) // (first,second)
                .thenCombine(hiCompletableFuture, (previous, current) -> previous + current)
                .thenApply(String::toUpperCase)
                .join();

        timeTaken();

        return hw;
    }

    public CompletableFuture<String> helloWorld_thenCompose() {

        CompletableFuture<String> helloWorldFuture=    CompletableFuture.supplyAsync(() -> this.helloWorldService.hello())
                 .thenCompose(previous -> helloWorldService.worldFuture(previous))
                 .thenApply(String::toUpperCase);

        return helloWorldFuture;

    }

    public String helloWorld_1() {

        return CompletableFuture.supplyAsync(() -> helloWorldService.helloWorld())//  runs this in a common fork-join pool
                .thenApply(String::toUpperCase)
                .join();

    }

    public static void main(String[] args) {

        HelloWorldService helloWorldService = new HelloWorldService();
        CompletableFuture.supplyAsync(() -> helloWorldService.helloWorld()) //  runs this in a common fork-join pool
                .thenApply(String::toUpperCase)
                .thenAccept((result) -> {
                    log("result " + result);
                })
                .join();

        log("Done!");
        delay(2000);
    }
}
