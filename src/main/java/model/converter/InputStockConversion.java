package model.converter;

import static constants.PlannerConstants.INFINITE_STOCK_VALUE;

import org.apache.commons.lang3.StringUtils;

import com.univocity.parsers.conversions.Conversion;

public class InputStockConversion implements Conversion<String, String> {

    private final String INFINITE_STOCK_STRING = "Infinite production capacity";

    public InputStockConversion(String... args) {}

    @Override public String execute(final String stockString) {
        if (StringUtils.equals(INFINITE_STOCK_STRING, stockString)) {
            return INFINITE_STOCK_VALUE.toString();
        } else {
            return stockString;
        }
    }

    @Override public String revert(final String stockString) {
        return stockString;
    }
}
