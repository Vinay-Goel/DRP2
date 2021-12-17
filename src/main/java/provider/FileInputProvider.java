package provider;

import java.io.File;
import java.util.List;

import javax.inject.Inject;

import org.apache.commons.lang3.StringUtils;

import com.univocity.parsers.common.processor.BeanListProcessor;
import com.univocity.parsers.csv.CsvParser;
import com.univocity.parsers.csv.CsvParserSettings;

import lombok.NonNull;
import lombok.extern.log4j.Log4j2;

@Log4j2
public class FileInputProvider<T> {

    @NonNull
    private final CsvParser inputFileParser;
    @NonNull
    private final BeanListProcessor<T> inputProcessor;

    @Inject
    public FileInputProvider(final BeanListProcessor<T> inputProcessor) {
        this.inputProcessor = inputProcessor;

        CsvParserSettings settings = new CsvParserSettings();
        settings.setProcessor(inputProcessor);
        settings.setNullValue(StringUtils.EMPTY);
        settings.setSkipEmptyLines(true);

        this.inputFileParser = new CsvParser(settings);
    }

    public List<T> provide(File inputFile) {
        log.info("Fetching input from path: [{}]", inputFile.getPath());
        inputFileParser.parse(inputFile);
        return inputProcessor.getBeans();
    }
}
