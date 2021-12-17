package model;

import constants.FileHeaders;

import com.univocity.parsers.annotations.Parsed;
import com.univocity.parsers.annotations.Trim;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BillOfDistribution {

    @Parsed(field = FileHeaders.SOURCE)
    @NonNull
    @Trim
    private String source;

    @Parsed(field = FileHeaders.DESTINATION)
    @NonNull
    @Trim
    private String destination;

    @Parsed(field = FileHeaders.LEAD_TIME)
    @NonNull
    private Integer leadTimeDays;

    @Parsed(field = FileHeaders.FREIGHT_COST)
    @NonNull
    private Integer freightCost;
}
