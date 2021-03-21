package com.learnjava.apiclient;

import com.learnjava.domain.github.GitHubPosition;
import com.learnjava.util.CommonUtil;
import com.learnjava.util.LoggerUtil;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;

class GitHubJobsClientTest {
    WebClient webClient = WebClient.create("https://jobs.github.com/");
    GitHubJobsClient gitHubJobsClient = new GitHubJobsClient(webClient);

    @Test
    void invokeGitHubJobsAPIWithPageNumber() {
        //given
        int pageNum = 1;
        String description = "JavaScript";

        //when
        List<GitHubPosition> gitHubPositions = gitHubJobsClient.invokeGitHubJobsAPIWithPageNumber(pageNum, description);
        LoggerUtil.log("gitHubPositions " + gitHubPositions);

        //then
        assertTrue(gitHubPositions.size() > 0);
        gitHubPositions.forEach(Assertions::assertNotNull);
    }

    @Test
    void invokeGitHubJobsAPIWithPageNumbers() {
        //given
        List<Integer> pageNums = List.of(1, 2, 3);
        String description = "Java";

        CommonUtil.startTimer();
        //when
        List<GitHubPosition> gitHubPositions = gitHubJobsClient.invokeGitHubJobsAPIWithPageNumbers(pageNums, description);
        CommonUtil.timeTaken();

        LoggerUtil.log("gitHubPositions " + gitHubPositions);

        //then
        assertTrue(gitHubPositions.size() > 0);
        gitHubPositions.forEach(Assertions::assertNotNull);
    }

    @Test
    void invokeGitHubJobsAPIWithPageNumbersCompletableFuture() {
        //given
        List<Integer> pageNums = List.of(1, 2, 3);
        String description = "Java";

        CommonUtil.startTimer();
        //when
        List<GitHubPosition> gitHubPositions = gitHubJobsClient.invokeGitHubJobsAPIWithPageNumbersCompletableFuture(pageNums, description);
        CommonUtil.timeTaken();

        LoggerUtil.log("gitHubPositions " + gitHubPositions);

        //then
        assertTrue(gitHubPositions.size() > 0);
        gitHubPositions.forEach(Assertions::assertNotNull);
    }

    @Test
    void invokeGitHubJobsAPIWithPageNumbersCompletableFutureAllOf() {
        //given
        List<Integer> pageNums = List.of(1, 2, 3);
        String description = "Java";

        CommonUtil.startTimer();
        //when
        List<GitHubPosition> gitHubPositions = gitHubJobsClient.invokeGitHubJobsAPIWithPageNumbersCompletableFutureAllOf(pageNums, description);
        CommonUtil.timeTaken();

        LoggerUtil.log("gitHubPositions " + gitHubPositions);

        //then
        assertTrue(gitHubPositions.size() > 0);
        gitHubPositions.forEach(Assertions::assertNotNull);
    }
}