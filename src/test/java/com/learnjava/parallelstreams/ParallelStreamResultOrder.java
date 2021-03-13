package com.learnjava.parallelstreams;

import com.learnjava.util.LoggerUtil;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class ParallelStreamResultOrder {
    public static List<Integer> listOrder(List<Integer> inputs) {
        return inputs.parallelStream()
                .map(x -> x * 2)
                .collect(Collectors.toList());
    }

    public static Set<Integer> setOrder(Set<Integer> inputs) {
        return inputs.parallelStream()
                .map(x -> x * 2)
                .collect(Collectors.toSet());
    }

    public static void main(String[] args) {
        List<Integer> integersList = List.of(1, 2, 3, 4, 5, 6, 7, 8);
        List<Integer> resultsList = listOrder(integersList);

        LoggerUtil.log("inputlist: " + integersList);
        LoggerUtil.log("results: " + resultsList);

        Set<Integer> integersSet = Set.of(1, 2, 3, 4, 5, 6, 7, 8);
        Set<Integer> resultsSet = setOrder(integersSet);

        LoggerUtil.log("inputlist: " + integersSet);
        LoggerUtil.log("results: " + resultsSet);
    }
}
