package com.learnjava.parallelstreams;

import com.learnjava.util.DataSet;
import com.learnjava.util.LoggerUtil;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.learnjava.util.CommonUtil.*;

public class ParallelStreamsExample {
    public static void main(String[] args) {
        List<String> names = DataSet.namesList();
        ParallelStreamsExample parallelStreamsExample = new ParallelStreamsExample();

        startTimer();
        List<String> results = parallelStreamsExample.stringTransform(names);
        timeTaken();

        LoggerUtil.log("results: " + results);

    }

    List<String> stringTransform(List<String> inputs) {
        return inputs.parallelStream()
                .map(ParallelStreamsExample::addNameLengthTransform)
                .sequential()
                .collect(Collectors.toList());
    }

    List<String> stringTransform(List<String> inputs, boolean isParallel) {
        Stream<String> inputStream = inputs.stream();

        if (isParallel)
            inputStream = inputStream.parallel();

        return inputStream
                .map(ParallelStreamsExample::addNameLengthTransform)
                .collect(Collectors.toList());
    }

    private static String addNameLengthTransform(String name) {
        delay(500);
        return name.length()+" - "+name ;
    }

}
