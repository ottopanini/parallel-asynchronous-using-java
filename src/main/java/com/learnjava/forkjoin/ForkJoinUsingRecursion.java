package com.learnjava.forkjoin;

import com.learnjava.util.DataSet;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.RecursiveTask;

import static com.learnjava.util.CommonUtil.delay;
import static com.learnjava.util.CommonUtil.stopWatch;
import static com.learnjava.util.LoggerUtil.log;

public class ForkJoinUsingRecursion extends RecursiveTask<List<String>> {
    private List<String> inputList;

    public ForkJoinUsingRecursion(List<String> inputList) {
        this.inputList = inputList;
    }

    public static void main(String[] args) {
        stopWatch.start();

        List<String> names = DataSet.namesList();
        ForkJoinPool forkJoinPool = new ForkJoinPool();
        ForkJoinUsingRecursion forkJoinUsingRecursion = new ForkJoinUsingRecursion(names);
        List<String> resultList = forkJoinPool.invoke(forkJoinUsingRecursion);

        stopWatch.stop();
        log("Final Result : "+ resultList);
        log("Total Time Taken : "+ stopWatch.getTime());
    }


    private static String addNameLengthTransform(String name) {
        delay(500);
        return name.length()+" - "+name ;
    }

    @Override
    protected List<String> compute() {
        // processing
        if (inputList.size() <= 1) {
            List<String> resultList = new ArrayList<>();
            inputList.forEach(name -> resultList.add(addNameLengthTransform(name)));

            return resultList;
        }

        // forking
        int midpoint = inputList.size() / 2;
        ForkJoinTask<List<String>> leftInputList = new ForkJoinUsingRecursion(inputList.subList(0, midpoint)).fork();
        inputList = inputList.subList(midpoint, inputList.size());
        List<String> rightResult = compute();// recursion

        //join
        List<String> leftResult = leftInputList.join();
        leftResult.addAll(rightResult);

        return leftResult;
    }
}
