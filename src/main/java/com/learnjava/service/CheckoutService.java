package com.learnjava.service;

import com.learnjava.domain.checkout.Cart;
import com.learnjava.domain.checkout.CartItem;
import com.learnjava.domain.checkout.CheckoutResponse;
import com.learnjava.domain.checkout.CheckoutStatus;
import com.learnjava.util.CommonUtil;
import com.learnjava.util.LoggerUtil;

import java.util.List;
import java.util.stream.Collectors;

public class CheckoutService {

    private final PriceValidatorService priceValidatorService;

    public CheckoutService(PriceValidatorService priceValidatorService) {
        this.priceValidatorService = priceValidatorService;
    }

    public CheckoutResponse checkout(Cart cart) {
        CommonUtil.startTimer();
        List<CartItem> invalidCartItems = cart.getCartItemList()
                .parallelStream()
                .peek(cartItem -> {
                    boolean valid = priceValidatorService.isCartItemInvalid(cartItem);
                    cartItem.setExpired(valid);
                })
                .filter(CartItem::isExpired)
                .collect(Collectors.toList());
        CommonUtil.timeTaken();

        if (!invalidCartItems.isEmpty()) {
            return new CheckoutResponse(CheckoutStatus.FAILURE, invalidCartItems);
        }

        //double finalPrice = calculateFinalPrice(cart);
        double finalPrice = calculateFinalPriceReduce(cart);
        LoggerUtil.log("Final price: " + finalPrice);
        return new CheckoutResponse(CheckoutStatus.SUCCESS, finalPrice);
    }

    private double calculateFinalPrice(Cart cart) {
        return cart.getCartItemList().parallelStream()
                .map(cartItem -> cartItem.getQuantity() * cartItem.getRate())
                .mapToDouble(Double::doubleValue)
                .sum();
    }

    private double calculateFinalPriceReduce(Cart cart) {
        return cart.getCartItemList().parallelStream()
                .map(cartItem -> cartItem.getQuantity() * cartItem.getRate())
                .reduce(0.0, (x, y) -> x + y); // don't use IntelliJ recommendation here - it slows down
    }
}
