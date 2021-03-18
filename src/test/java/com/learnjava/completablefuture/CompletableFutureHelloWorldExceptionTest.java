package com.learnjava.completablefuture;

import com.learnjava.service.HelloWorldService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CompletableFutureHelloWorldExceptionTest {

    @Mock
    private HelloWorldService helloWorldService = Mockito.mock(HelloWorldService.class);

    @InjectMocks
    CompletableFutureHelloWorldException completableFutureHelloWorldException;

    @Test
    void shouldHandleExceptionByHello() {
        when(helloWorldService.hello()).thenThrow(new RuntimeException("Exception occured"));
        when(helloWorldService.world()).thenCallRealMethod();

        String result = completableFutureHelloWorldException.helloWorldCombined3AsyncTasksHandle();

        assertEquals(" WORLD!HI COMPLETABLEFUTURE", result);
    }

    @Test
    void shouldHandleExceptionByHelloAndWorld() {
        when(helloWorldService.hello()).thenThrow(new RuntimeException("Exception occured"));
        when(helloWorldService.world()).thenThrow(new RuntimeException("Exception occured"));

        String result = completableFutureHelloWorldException.helloWorldCombined3AsyncTasksHandle();

        assertEquals("HI COMPLETABLEFUTURE", result);
    }

    @Test
    void shouldHandleNoException() {
        when(helloWorldService.hello()).thenCallRealMethod();
        when(helloWorldService.world()).thenCallRealMethod();

        String result = completableFutureHelloWorldException.helloWorldCombined3AsyncTasksHandle();

        assertEquals("HI COMPLETABLEFUTURE", result);
    }


    @Test
    void shouldHandleExceptionalNoException() {
        when(helloWorldService.hello()).thenCallRealMethod();
        when(helloWorldService.world()).thenCallRealMethod();

        String result = completableFutureHelloWorldException.helloWorldCombined3AsyncTasksExceptionally();

        assertEquals("HELLO WORLD!HI COMPLETABLEFUTURE", result);
    }

    @Test
    void shouldHandleExceptionalEveryWhere() {
        when(helloWorldService.hello()).thenThrow(new RuntimeException("Exception occured"));
        when(helloWorldService.world()).thenThrow(new RuntimeException("Exception occured"));

        String result = completableFutureHelloWorldException.helloWorldCombined3AsyncTasksExceptionally();

        assertEquals("HI COMPLETABLEFUTURE", result);
    }

}