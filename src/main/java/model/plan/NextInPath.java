package model.plan;

import lombok.Builder;
import lombok.Data;
import lombok.NonNull;

@Data
@Builder
public class NextInPath {
    @NonNull
    private String source;
    private Integer freightCost;
    private Integer leadTime;
    private Integer inventoryCollected;

    //Linked List from Source to Customer
    private NextInPath fromEdge;

    public int compare(NextInPath compareWith) {
        if (freightCost.equals(compareWith.getFreightCost())) {
            if (leadTime.equals(compareWith.getLeadTime())) {
                if (inventoryCollected.equals(compareWith.getInventoryCollected()))
                    return source.compareTo(compareWith.getSource());
                return compareWith.getInventoryCollected().compareTo(inventoryCollected);
            }
            return leadTime.compareTo(compareWith.getLeadTime());
        }
        return freightCost.compareTo(compareWith.getFreightCost());
    }
}
