package com.learnjava.service;

import com.learnjava.domain.checkout.Cart;
import com.learnjava.domain.checkout.CheckoutResponse;
import com.learnjava.domain.checkout.CheckoutStatus;
import com.learnjava.util.DataSet;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CheckoutServiceTest {
    PriceValidatorService priceValidatorService = new PriceValidatorService();
    CheckoutService checkoutService = new CheckoutService(priceValidatorService);

    @Test
    void shouldCheckout() {
        Cart cart = DataSet.createCart(6);
        CheckoutResponse checkout = checkoutService.checkout(cart);

        assertEquals(CheckoutStatus.SUCCESS, checkout.getCheckoutStatus());
    }
}