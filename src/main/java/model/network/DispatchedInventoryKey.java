package model.network;

import org.apache.commons.lang3.StringUtils;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class DispatchedInventoryKey {
    String source;
    String destination;
    Integer day;

    public boolean equals(DispatchedInventoryKey key) {
        return StringUtils.equalsIgnoreCase(source, key.getSource()) &&
                StringUtils.equalsIgnoreCase(destination, key.getDestination()) &&
                day.equals(key.getDay());
    }
}
