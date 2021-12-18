package model;

import constants.FileHeaders;

import com.univocity.parsers.annotations.Parsed;
import com.univocity.parsers.annotations.Trim;

import lombok.Builder;
import lombok.Data;
import lombok.NonNull;

@Data
@Builder
public class Demand {

    @Parsed(field = FileHeaders.LOCATION)
    @NonNull
    @Trim
    private String location;

    @Parsed(field = FileHeaders.DAY)
    @NonNull
    @Trim
    private Integer day;

    @Parsed(field = FileHeaders.DEMAND)
    @NonNull
    @Trim
    private Integer demand;
}
