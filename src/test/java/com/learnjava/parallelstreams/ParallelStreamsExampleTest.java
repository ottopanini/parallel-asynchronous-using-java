package com.learnjava.parallelstreams;

import com.learnjava.util.CommonUtil;
import com.learnjava.util.DataSet;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ParallelStreamsExampleTest {

    ParallelStreamsExample parallelStreamsExample = new ParallelStreamsExample();

    @Test
    void stringTransform() {
        List<String> inputs = DataSet.namesList();

        CommonUtil.startTimer();
        List<String> results = parallelStreamsExample.stringTransform(inputs);
        CommonUtil.timeTaken();

        assertEquals(4, results.size());
        results.forEach(x -> assertTrue(x.contains("-")));
    }

    @ParameterizedTest
    @ValueSource(booleans = {false, true})
    void stringTransformWithIsParallel(boolean isParallel) {
        List<String> inputs = DataSet.namesList();

        CommonUtil.startTimer();
        List<String> results = parallelStreamsExample.stringTransform(inputs, isParallel);
        CommonUtil.timeTaken();
        CommonUtil.stopWatchReset();

        assertEquals(4, results.size());
        results.forEach(x -> assertTrue(x.contains("-")));
    }
}