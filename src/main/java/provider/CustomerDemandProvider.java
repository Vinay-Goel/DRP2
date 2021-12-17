package provider;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.inject.Inject;

import model.Demand;
import org.apache.commons.lang3.StringUtils;

import com.univocity.parsers.common.processor.RowListProcessor;
import com.univocity.parsers.csv.CsvParser;
import com.univocity.parsers.csv.CsvParserSettings;

import lombok.NonNull;
import lombok.extern.log4j.Log4j2;

@Log4j2
public class CustomerDemandProvider {

    @NonNull
    private final CsvParser customerDemandParser;

    @NonNull
    private final RowListProcessor rowListProcessor;

    @Inject
    public CustomerDemandProvider() {
        this.rowListProcessor = new RowListProcessor();
        CsvParserSettings settings = new CsvParserSettings();
        settings.setProcessor(rowListProcessor);
        settings.setNullValue(StringUtils.EMPTY);
        settings.setSkipEmptyLines(true);
        settings.setHeaderExtractionEnabled(true);
        this.customerDemandParser = new CsvParser(settings);
    }

    public List<Demand> provide(File customerDemandInputFile) {
        log.info("Fetching customer demand from path: [{}]", customerDemandInputFile.getPath());
        customerDemandParser.parse(customerDemandInputFile);
        String[] customerDemandHeader = rowListProcessor.getHeaders();
        List<String[]> customerDemandRows = rowListProcessor.getRows();
        return customerDemandRows.stream()
                .map(customerDemandRow -> convertCustomerDemandRow(customerDemandRow, customerDemandHeader))
                .flatMap(List::stream)
                .collect(Collectors.toList());
    }

    public List<Demand> convertCustomerDemandRow(String[] customerDemandRow, String[] headers) {
        List<Demand> customerDemand = new ArrayList<>();
        for (int i = 1; i < customerDemandRow.length; i++) {
            if (StringUtils.isNotBlank(customerDemandRow[i])) {
                Integer demandValue = Integer.parseInt(customerDemandRow[i]);
                Demand demand = new Demand(customerDemandRow[0], headers[i], demandValue);
                customerDemand.add(demand);
            }
        }
        return customerDemand;
    }
}
