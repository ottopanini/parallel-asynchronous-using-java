package com.learnjava.completablefuture;

import com.learnjava.service.HelloWorldService;
import com.learnjava.util.LoggerUtil;

import java.util.concurrent.CompletableFuture;

import static com.learnjava.util.CommonUtil.*;

public class CompletableFutureHelloWorldException {
    private final HelloWorldService helloWorldService;

    public CompletableFutureHelloWorldException(HelloWorldService helloWorldService) {
        this.helloWorldService = helloWorldService;
    }

    public String helloWorldCombined3AsyncTasksHandle() {
        startTimer();
        CompletableFuture<String> helloFuture = CompletableFuture.supplyAsync(helloWorldService::hello);
        CompletableFuture<String> worldFuture = CompletableFuture.supplyAsync(helloWorldService::world);
        CompletableFuture<String> hiFuture = CompletableFuture.supplyAsync(() -> {
            delay(1000);
            return "Hi Completablefuture";
        });

        String join = helloFuture
                .handle((res, e) -> {
                    LoggerUtil.log("Exception is: " + e.getMessage());
                    return "";
                })
                .thenCombine(worldFuture, (h, w) -> h + w)
                .thenCombine(hiFuture, (previous, current) -> previous + current)
                .thenApply(String::toUpperCase)
                .join();
        timeTaken();

        return join;
    }

}
