package model;

import constants.FileHeaders;
import model.converter.InputStockConversion;

import com.univocity.parsers.annotations.Convert;
import com.univocity.parsers.annotations.Parsed;
import com.univocity.parsers.annotations.Trim;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OnHandInventory {

    @Parsed(field = FileHeaders.LOCATION)
    @NonNull
    @Trim
    private String location;

    @Parsed(field = FileHeaders.STOCK)
    @Convert(conversionClass = InputStockConversion.class)
    private Integer stock;
}
