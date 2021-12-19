package sample;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import javax.inject.Inject;

import org.apache.commons.lang3.StringUtils;

public class SampleAttributeListGenerator {

    @Inject
    public SampleAttributeListGenerator() {}

    public List<String> generate(String prefix, Integer size) {
        return IntStream.rangeClosed(1, size)
                .mapToObj(i -> StringUtils.join(prefix, String.valueOf(i)))
                .collect(Collectors.toList());
    }
}
