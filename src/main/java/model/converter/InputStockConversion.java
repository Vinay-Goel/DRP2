package model.converter;

import org.apache.commons.lang3.StringUtils;

import com.univocity.parsers.conversions.Conversion;

public class InputStockConversion implements Conversion<String, String> {

    private final String INFINITE_STOCK_STRING = "Infinite production capacity";
    private final Integer INFINITE_STOCK_BUFFER = 100000;
    private final Integer INFINITE_STOCK_VALUE = Integer.MAX_VALUE-INFINITE_STOCK_BUFFER;

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
