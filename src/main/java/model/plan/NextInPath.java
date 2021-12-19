package model.plan;

import lombok.Builder;
import lombok.Data;
import lombok.NonNull;

@Data
@Builder
public class NextInPath {
    @NonNull
    private String location;
    private Integer freightCost;
    private Integer leadTime;
    private Integer inventoryCollected;
    private Integer currentDay;

    //Linked List from Source to Customer
    private NextInPath fromEdge;

    public int compare(NextInPath compareWith) {
        if (freightCost.equals(compareWith.getFreightCost())) {
            if (leadTime.equals(compareWith.getLeadTime())) {
                return location.compareTo(compareWith.getLocation());
            }
            return leadTime.compareTo(compareWith.getLeadTime());
        }
        return freightCost.compareTo(compareWith.getFreightCost());
    }
}
