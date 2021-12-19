package model.network;

import org.apache.commons.lang3.StringUtils;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class LocationDayInventoryUsedKey {
    private String location;
    private Integer day;

    public boolean equals(LocationDayInventoryUsedKey key) {
        return StringUtils.equalsIgnoreCase(location, key.getLocation()) && day.equals(key.getDay());
    }
}
