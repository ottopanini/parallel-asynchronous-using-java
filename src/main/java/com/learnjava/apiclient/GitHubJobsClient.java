package com.learnjava.apiclient;

import com.learnjava.domain.github.GitHubPosition;
import com.learnjava.util.LoggerUtil;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Collection;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

public class GitHubJobsClient {
    private final WebClient webClient;

    public GitHubJobsClient(WebClient webClient) {
        this.webClient = webClient;
    }

    public List<GitHubPosition> invokeGitHubJobsAPIWithPageNumber(int pageNumber, String description) {
        String uriString = UriComponentsBuilder.fromUriString("/positions.json")
                .queryParam("description", description)
                .queryParam("page", pageNumber)
                .buildAndExpand()
                .toUriString();

        LoggerUtil.log("uri: " + uriString);
        List<GitHubPosition> gitHubPositions = webClient.get().uri(uriString)
                .retrieve()
                .bodyToFlux(GitHubPosition.class)
                .collectList()
                .block();

        return gitHubPositions;
    }

    public List<GitHubPosition> invokeGitHubJobsAPIWithPageNumbers(List<Integer> pageNumber, String description) {
        List<GitHubPosition> results = pageNumber.stream()
                .map(page -> invokeGitHubJobsAPIWithPageNumber(page, description))
                .flatMap(gitHubPositions -> gitHubPositions.stream())
                .collect(Collectors.toList());

        return results;
    }

    public List<GitHubPosition> invokeGitHubJobsAPIWithPageNumbersCompletableFuture(List<Integer> pageNumber, String description) {
        List<CompletableFuture<List<GitHubPosition>>> resultFutures = pageNumber.stream()
                .map(page -> CompletableFuture.supplyAsync(() -> invokeGitHubJobsAPIWithPageNumber(page, description)))
                .collect(Collectors.toList());

        List<GitHubPosition> results = resultFutures.stream()
                .map(CompletableFuture::join)
                .flatMap(Collection::stream)
                .collect(Collectors.toList());

        return results;
    }

    public List<GitHubPosition> invokeGitHubJobsAPIWithPageNumbersCompletableFutureAllOf(List<Integer> pageNumber,
                                                                                         String description) {
        List<CompletableFuture<List<GitHubPosition>>> resultFutures = pageNumber.stream()
                .map(page -> CompletableFuture.supplyAsync(() -> invokeGitHubJobsAPIWithPageNumber(page, description)))
                .collect(Collectors.toList());

        CompletableFuture<Void> allOf = CompletableFuture.allOf(
                resultFutures.toArray(new CompletableFuture[resultFutures.size()]));

        // the completable futures are joined when allOf are completed
        List<GitHubPosition> results =  allOf.thenApply(voidly -> resultFutures.stream()
                .map(CompletableFuture::join)
                .flatMap(Collection::stream)
                .collect(Collectors.toList()))
                .join();

        return results;
    }
}
