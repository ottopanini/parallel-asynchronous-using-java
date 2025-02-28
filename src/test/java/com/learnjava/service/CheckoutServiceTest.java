package com.learnjava.service;

import com.learnjava.domain.checkout.Cart;
import com.learnjava.domain.checkout.CheckoutResponse;
import com.learnjava.domain.checkout.CheckoutStatus;
import com.learnjava.util.DataSet;
import org.junit.jupiter.api.Test;

import java.util.concurrent.ForkJoinPool;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class CheckoutServiceTest {
    PriceValidatorService priceValidatorService = new PriceValidatorService();
    CheckoutService checkoutService = new CheckoutService(priceValidatorService);

    @Test
    void shouldCheckout() {
        Cart cart = DataSet.createCart(6);
        CheckoutResponse checkout = checkoutService.checkout(cart);

        assertEquals(CheckoutStatus.SUCCESS, checkout.getCheckoutStatus());
        assertTrue(checkout.getFinalRate() > 0);
    }

    @Test
    void noOfCores() {
        System.out.println("number of cores: " + Runtime.getRuntime().availableProcessors());
    }

    @Test
    void parallelism() {
        System.out.println("parallelism: " + ForkJoinPool.getCommonPoolParallelism());
    }

    @Test
    void shouldExseed500milliesAndFailCheckout() {
        Cart cart = DataSet.createCart(Runtime.getRuntime().availableProcessors() + 1);
        CheckoutResponse checkout = checkoutService.checkout(cart);

        assertEquals(CheckoutStatus.FAILURE, checkout.getCheckoutStatus());
        // total time taken has doubled
    }

    @Test
    void shouldExseed500milliesWith25elems() {
        Cart cart = DataSet.createCart(25);
        CheckoutResponse checkout = checkoutService.checkout(cart);

        assertEquals(CheckoutStatus.FAILURE, checkout.getCheckoutStatus());
    }

    @Test
    void shouldUseDefaultParallelism() {
        System.setProperty("java.util.concurrent.ForkJoinPool.common.parallelism", "100");
        Cart cart = DataSet.createCart(100);
        CheckoutResponse checkout = checkoutService.checkout(cart);

        assertEquals(CheckoutStatus.FAILURE, checkout.getCheckoutStatus());
    }//5104ms

    @Test
    void shouldModifyParallelism() {
        System.setProperty("java.util.concurrent.ForkJoinPool.common.parallelism", "100");
        Cart cart = DataSet.createCart(100);
        CheckoutResponse checkout = checkoutService.checkout(cart);

        assertEquals(CheckoutStatus.FAILURE, checkout.getCheckoutStatus());
    }//714 ms
}