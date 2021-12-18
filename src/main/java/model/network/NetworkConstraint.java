package model.network;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class NetworkConstraint {
    private Integer leadTimeDays;
    private Integer freightCost;
}
