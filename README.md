# Multithreading,Parallel & Asynchronous Coding in Modern Java
This repo has the code for parallel and asynchronous programming in Java.

# What is parallelism (vs. Concurrency) 
![](parallel-1.png)

![](parallel-2.png)

```java
public class ParallelismExample {
    public static void main(String[] args) {
        List<String> namesList = List.of("Bob", "Jamie", "Jill", "Rick");
        System.out.println("namesList : " + namesList);
        List<String> namesListUpperCase = namesList
                .parallelStream()
                .map(String::toUpperCase)
                .collect(Collectors.toList());

        System.out.println("namesListUpperCase : " + namesListUpperCase);
    }
}
```
# Threads, Future, ForkJoin and its Limitations
![](product-service-1.png)

![](product-service-2.png)

![](product-service-3.png)

## Threads and its Limitations - Hands On

We need a lot of boilerplate code to achive parallelism here. We have to always implement the `Runnable` interface and pass its instances each into a seperate `Thread`.
```java
...

    public Product retrieveProductDetails(String productId) throws InterruptedException {
        stopWatch.start();

        ProductInfoRunnable productInfoRunnable = new ProductInfoRunnable(productId);
        Thread productInfoThread = new Thread(productInfoRunnable);

        ReviewRunnable reviewRunnable = new ReviewRunnable(productId);
        Thread reviewThread = new Thread(reviewRunnable);

        productInfoThread.start();
        reviewThread.start();

        productInfoThread.join();
        reviewThread.join();

        ProductInfo productInfo = productInfoRunnable.getProductInfo();
        Review review = reviewRunnable.getReview();

        stopWatch.stop();
        log("Total Time Taken : "+ stopWatch.getTime());
        return new Product(productId, productInfo, review);
    }

...

    private class ProductInfoRunnable implements Runnable {
        private ProductInfo productInfo;
        private String productId;

        public ProductInfoRunnable(String productId) {
            this.productId = productId;
        }

        @Override
        public void run() {
            productInfo = productInfoService.retrieveProductInfo(productId);
        }

        public ProductInfo getProductInfo() {
            return productInfo;
        }
    }

    private class ReviewRunnable implements Runnable {
        private String productId;
        private Review review;

        public ReviewRunnable(String productId) {
            this.productId = productId;
        }

        @Override
        public void run() {
            review = reviewService.retrieveReviews(productId);
        }

        public Review getReview() {
            return review;
        }
    }

...
```
Then it's needed to manually:
- create the threads
- start the threads
- join the threads

Next thing is, threads are expensive
- threads have their own *runtime-stack*, *memory*, *registers* and ...

the answer...



