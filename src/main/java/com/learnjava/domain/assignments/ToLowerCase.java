package com.learnjava.domain.assignments;

import java.util.List;
import java.util.stream.Collectors;

public class ToLowerCase {
    public List<String> string_toLowerCase(List<String> inputs) {
        return inputs.parallelStream()
                .map(String::toLowerCase)
                .collect(Collectors.toList());
    }
}
