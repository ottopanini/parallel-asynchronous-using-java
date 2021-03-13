package com.learnjava.parallelstreams;

import com.learnjava.util.DataSet;
import org.junit.jupiter.api.RepeatedTest;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ArrayListSplitaratorExampleTest {
    ArrayListSplitaratorExample arrayListSplitaratorExample = new ArrayListSplitaratorExample();

    @RepeatedTest(5)
    void shouldMultiply() {
        int size = 1_000_000;
        ArrayList<Integer> integers = DataSet.generateArrayList(size);
        List<Integer> results = arrayListSplitaratorExample.multiplyEachValue(integers, 2);

        assertEquals(size, results.size());
    }
}