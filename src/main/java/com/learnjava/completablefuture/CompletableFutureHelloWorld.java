package com.learnjava.completablefuture;

import com.learnjava.service.HelloWorldService;
import com.learnjava.util.CommonUtil;

import java.util.concurrent.CompletableFuture;

import static com.learnjava.util.LoggerUtil.log;

public class CompletableFutureHelloWorld {
    public static void main(String[] args) {
        HelloWorldService helloWorldService = new HelloWorldService();
        CompletableFuture.supplyAsync(helloWorldService::helloWorld)
                .thenAccept(x -> log("Result: " + x));
        log("done!");

        // we have to wait - without only 'done!' is printed to the console
        CommonUtil.delay(2000);
    }
}
