package provider;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.inject.Inject;

import constants.PlannerConstants;
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

    private List<Demand> convertCustomerDemandRow(String[] customerDemandRow, String[] headers) {
        List<Demand> customerDemand = new ArrayList<>();
        for (int i = 1; i < customerDemandRow.length; i++) {
            if (StringUtils.isNotBlank(customerDemandRow[i])) {
                Integer demandValue = Integer.parseInt(customerDemandRow[i]);
                Integer dayValue = getDayValue(headers[i]);
                Demand demand = Demand.builder()
                        .location(customerDemandRow[0])
                        .day(dayValue)
                        .demand(demandValue)
                        .build();
                customerDemand.add(demand);
            }
        }
        return customerDemand;
    }

    private Integer getDayValue(String day) {
        String dayValue = StringUtils.removeStartIgnoreCase(day, PlannerConstants.DEMAND_DAY_PREFIX).trim();
        if (StringUtils.isNumeric(dayValue)) {
            return Integer.valueOf(dayValue);
        } else {
            String errorMessage = String.format("Invalid Day: [%s] in Customer Demand", day);
            throw new RuntimeException(errorMessage);
        }
    }
}
