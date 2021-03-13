package com.learnjava.parallelstreams;

import com.learnjava.util.DataSet;
import org.junit.jupiter.api.RepeatedTest;

import java.util.LinkedList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class LinkedListSplitaratorExampleTest {
    LinkedListSplitaratorExample linkedListSplitaratorExample = new LinkedListSplitaratorExample();

    @RepeatedTest(5)
    void shouldMultiplySequential() {
        int size = 1_000_000;
        LinkedList<Integer> integers = DataSet.generateIntegerLinkedList(size);
        List<Integer> results = linkedListSplitaratorExample.multiplyEachValue(integers, 2, false);

        assertEquals(size, results.size());
    }
    @RepeatedTest(5)
    void shouldMultiplyParallel() {
        int size = 1_000_000;
        LinkedList<Integer> integers = DataSet.generateIntegerLinkedList(size);
        List<Integer> results = linkedListSplitaratorExample.multiplyEachValue(integers, 2, true);

        assertEquals(size, results.size());
    }
}