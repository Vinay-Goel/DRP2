package model;

import constants.FileHeaders;
import org.apache.commons.lang3.StringUtils;

import com.univocity.parsers.annotations.Parsed;
import com.univocity.parsers.annotations.Trim;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NonNull;

@Data
@Builder
@AllArgsConstructor
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

    public boolean equals(Demand compareWith) {
        return StringUtils.equalsIgnoreCase(location, compareWith.getLocation()) &&
                day.equals(compareWith.getDay()) &&
                demand.equals(compareWith.getDemand());
    }
}
