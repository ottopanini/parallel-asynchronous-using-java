package com.learnjava.completablefuture;

import com.learnjava.domain.Product;
import com.learnjava.service.ProductInfoService;
import com.learnjava.service.ReviewService;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ProductServiceUsingCompletableFutureTest {
    private ProductInfoService productInfoService = new ProductInfoService();
    private ReviewService reviewService = new ReviewService();
    private ProductServiceUsingCompletableFuture productServiceUsingCompletableFuture =
            new ProductServiceUsingCompletableFuture(productInfoService, reviewService);

    @Test
    void shouldRetrieveProductDetails() {
        String productId = "ABC123";
        Product product = productServiceUsingCompletableFuture.retrieveProductDetails(productId);

        assertNotNull(product);
        assertTrue(product.getProductInfo().getProductOptions().size() > 0);
        assertNotNull(product.getReview());
    }
}