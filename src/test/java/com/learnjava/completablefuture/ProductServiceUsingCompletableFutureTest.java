package com.learnjava.completablefuture;

import com.learnjava.domain.Product;
import com.learnjava.service.InventoryService;
import com.learnjava.service.ProductInfoService;
import com.learnjava.service.ReviewService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.concurrent.CompletableFuture;

import static com.learnjava.util.CommonUtil.startTimer;
import static com.learnjava.util.CommonUtil.timeTaken;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ProductServiceUsingCompletableFutureTest {
    private ProductInfoService productInfoService = new ProductInfoService();
    private ReviewService reviewService = new ReviewService();
    private InventoryService inventoryService = new InventoryService();
    private ProductServiceUsingCompletableFuture productServiceUsingCompletableFuture =
            new ProductServiceUsingCompletableFuture(productInfoService, reviewService, inventoryService);

    @Test
    void shouldRetrieveProductDetails() {
        String productId = "ABC123";
        Product product = productServiceUsingCompletableFuture.retrieveProductDetails(productId);

        assertNotNull(product);
        assertTrue(product.getProductInfo().getProductOptions().size() > 0);
        assertNotNull(product.getReview());
    }

    @Test
    void shouldRetrieveProductDetailsFuture() {
        startTimer();
        String productId = "ABC123";
        CompletableFuture<Product> productFuture = productServiceUsingCompletableFuture.retrieveProductDetailsFuture(productId);

        productFuture.thenAccept(product -> {
                    assertNotNull(product);
                    assertTrue(product.getProductInfo().getProductOptions().size() > 0);
                    assertNotNull(product.getReview());
                    timeTaken();
                })
                .join();
    }

    @Test
    void shouldRetrieveProductDetailsWithInventory() {
        String productId = "ABC123";
        Product product = productServiceUsingCompletableFuture.retrieveProductDetailsWithInventory(productId);

        assertNotNull(product);
        assertTrue(product.getProductInfo().getProductOptions().size() > 0);
        product.getProductInfo().getProductOptions().forEach(Assertions::assertNotNull);
        assertNotNull(product.getReview());
    }

    @Test
    void shouldRetrieveProductDetailsWithInventory_approach2() {
        String productId = "ABC123";
        Product product = productServiceUsingCompletableFuture.retrieveProductDetailsWithInventory_approach2(productId);

        assertNotNull(product);
        assertTrue(product.getProductInfo().getProductOptions().size() > 0);
        product.getProductInfo().getProductOptions().forEach(Assertions::assertNotNull);
        assertNotNull(product.getReview());
    }

}