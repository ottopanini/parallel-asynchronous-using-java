package com.learnjava.completablefuture;

import com.learnjava.domain.*;
import com.learnjava.service.InventoryService;
import com.learnjava.service.ProductInfoService;
import com.learnjava.service.ReviewService;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

import static com.learnjava.util.CommonUtil.stopWatch;
import static com.learnjava.util.LoggerUtil.log;

public class ProductServiceUsingCompletableFuture {
    private ProductInfoService productInfoService;
    private ReviewService reviewService;
    private final InventoryService inventoryService;

    public ProductServiceUsingCompletableFuture(ProductInfoService productInfoService, ReviewService reviewService, InventoryService inventoryService) {
        this.productInfoService = productInfoService;
        this.reviewService = reviewService;
        this.inventoryService = inventoryService;
    }

    public Product retrieveProductDetails(String productId) {
        stopWatch.start();
        CompletableFuture<ProductInfo> productInfoFuture = CompletableFuture
                .supplyAsync(() -> productInfoService.retrieveProductInfo(productId));
        CompletableFuture<Review> reviewFuture = CompletableFuture
                .supplyAsync(() -> reviewService.retrieveReviews(productId));
        Product product = productInfoFuture
                .thenCombine(reviewFuture, (productInfo, review) -> new Product(productId, productInfo, review))
                .join(); //block the thread
        stopWatch.stop();

        log("Total Time Taken : "+ stopWatch.getTime());
        return product;
    }

    // no blocking op in here
    public CompletableFuture<Product> retrieveProductDetailsFuture(String productId) {
        CompletableFuture<ProductInfo> productInfoFuture = CompletableFuture
                .supplyAsync(() -> productInfoService.retrieveProductInfo(productId));
        CompletableFuture<Review> reviewFuture = CompletableFuture
                .supplyAsync(() -> reviewService.retrieveReviews(productId));
        CompletableFuture<Product> productCompletableFuture = productInfoFuture
                .thenCombine(reviewFuture, (productInfo, review) -> new Product(productId, productInfo, review));

        return productCompletableFuture;
    }

    public Product retrieveProductDetailsWithInventory(String productId) {
        stopWatch.start();
        CompletableFuture<ProductInfo> productInfoFuture = CompletableFuture
                .supplyAsync(() -> productInfoService.retrieveProductInfo(productId))
                .thenApply(productInfo -> {
                    productInfo.setProductOptions(updateInventory(productInfo));
                    return productInfo;
                });

        CompletableFuture<Review> reviewFuture = CompletableFuture
                .supplyAsync(() -> reviewService.retrieveReviews(productId));
        Product product = productInfoFuture
                .thenCombine(reviewFuture, (productInfo, review) -> new Product(productId, productInfo, review))
                .join(); //block the thread
        stopWatch.stop();

        log("Total Time Taken : "+ stopWatch.getTime());
        return product;
    }

    public Product retrieveProductDetailsWithInventory_approach2(String productId) {
        stopWatch.start();
        CompletableFuture<ProductInfo> productInfoFuture = CompletableFuture
                .supplyAsync(() -> productInfoService.retrieveProductInfo(productId))
                .thenApply(productInfo -> {
                    productInfo.setProductOptions(updateInventoryParallelCallsBlockingOnCollectResult(productInfo));
                    return productInfo;
                });

        CompletableFuture<Review> reviewFuture = CompletableFuture
                .supplyAsync(() -> reviewService.retrieveReviews(productId))
                .exceptionally(e -> {
                    log("Handled the exception in review service: " + e.getMessage());
                    return Review.builder()
                            .noOfReviews(0)
                            .overallRating(0)
                            .build();
                });

        Product product = productInfoFuture
                .thenCombine(reviewFuture, (productInfo, review) -> new Product(productId, productInfo, review))
                .join(); //block the thread
        stopWatch.stop();

        log("Total Time Taken : "+ stopWatch.getTime());
        return product;
    }

    private List<ProductOption> updateInventory(ProductInfo productInfo) {
        return productInfo.getProductOptions().stream()
                .map(option -> {
                    Inventory inventory = inventoryService.addInventory(option);
                    option.setInventory(inventory);

                    return option;
                })
                .collect(Collectors.toList());
    }

    private List<ProductOption> updateInventoryParallelCallsBlockingOnCollectResult(ProductInfo productInfo) {
        List<CompletableFuture<ProductOption>> options = productInfo.getProductOptions().stream()
                .map(option -> {
                    return CompletableFuture.supplyAsync(() -> inventoryService.addInventory(option))
                            .thenApply(inventory -> {
                                option.setInventory(inventory);
                                return option;
                            });
                })
                .collect(Collectors.toList());

        return options.stream().map(CompletableFuture::join).collect(Collectors.toList());
    }

    public static void main(String[] args) throws InterruptedException {

        ProductInfoService productInfoService = new ProductInfoService();
        ReviewService reviewService = new ReviewService();
        ProductServiceUsingCompletableFuture productService = new ProductServiceUsingCompletableFuture(productInfoService, reviewService, new InventoryService());
        String productId = "ABC123";
        Product product = productService.retrieveProductDetails(productId);
        log("Product is " + product);

    }
}
