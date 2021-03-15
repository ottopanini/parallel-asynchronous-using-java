package com.learnjava.completablefuture;

import com.learnjava.service.HelloWorldService;
import org.junit.jupiter.api.Test;

import java.util.concurrent.CompletableFuture;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CompletableFutureHelloWorldTest {

    private HelloWorldService helloWorldService = new HelloWorldService();
    private CompletableFutureHelloWorld completableFutureHelloWorld = new CompletableFutureHelloWorld(helloWorldService);

    @Test
    void shouldHelloWorld() {
        CompletableFuture<String> helloFuture = completableFutureHelloWorld.helloWorld();

        helloFuture
                .thenAccept(s -> assertEquals("HELLO WORLD", s))
                .join();
    }

    @Test
    void shouldHelloWorldWithSize() {
        CompletableFuture<String> helloWorldWithSize = completableFutureHelloWorld.helloWorld_withSize();

        helloWorldWithSize
                .thenAccept(s -> assertEquals("11 - HELLO WORLD", s))
                .join();
    }

}