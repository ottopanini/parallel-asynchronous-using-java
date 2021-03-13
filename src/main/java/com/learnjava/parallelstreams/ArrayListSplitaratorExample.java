package com.learnjava.parallelstreams;

import com.learnjava.util.CommonUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ArrayListSplitaratorExample {
    public List<Integer> multiplyEachValue(ArrayList<Integer> inputs, int multiplier, boolean isParallel) {
        CommonUtil.stopWatchReset();
        CommonUtil.startTimer();
        Stream<Integer> integerStream = inputs.stream(); // sequential

        if (isParallel) integerStream = integerStream.parallel();

        List<Integer> results = integerStream.map(integer -> integer * multiplier)
                .collect(Collectors.toList());
        CommonUtil.timeTaken();

        return results;
    }
}
