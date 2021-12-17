package model;

import java.util.Map;

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
public class Demand {

    @Parsed(field = FileHeaders.LOCATION)
    @NonNull
    @Trim
    private String location;

    @Parsed(field = FileHeaders.DAY)
    @NonNull
    @Trim
    private String day;

    @Parsed(field = FileHeaders.DEMAND)
    @NonNull
    @Trim
    private Integer demand;
}
