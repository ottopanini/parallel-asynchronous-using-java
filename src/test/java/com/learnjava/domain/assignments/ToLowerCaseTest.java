package com.learnjava.domain.assignments;

import com.learnjava.util.CommonUtil;
import com.learnjava.util.DataSet;
import com.learnjava.util.LoggerUtil;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ToLowerCaseTest {

    @Test
    void shouldTransformToLowerCase() {
        ToLowerCase toLowerCase = new ToLowerCase();

        CommonUtil.startTimer();
        List<String> results = toLowerCase.string_toLowerCase(DataSet.namesList());
        CommonUtil.timeTaken();
        LoggerUtil.log("results: " + results);

        assertEquals(4, results.size());
    }
}