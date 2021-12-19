package network;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import model.BillOfDistribution;
import model.Demand;
import model.OnHandInventory;
import model.plan.NextInPath;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;

import lombok.Getter;

@Getter
public class Network {
    private Multimap<String, NextInPath> graph;
    private Map<String, Integer> inventory;
    private InventoryStore inventoryStore;
    private ShortestPathStore shortestPathStore;

    /**
    Network will hold the details of current network.
        * Graph - This will map DESTINATION to SOURCE with the given LEAD TIME & FREIGHT COST.
                This way we can run shortest path algorithms from customer to factory slightly improving run time.
        * Inventory - This will map LOCATION to ON HAND INVENTORY
        * Location Day Inventory Used - This will map LOCATION + DAY to INVENTORY USED.
                Any ON HAND INVENTORY used by a previous CUSTOMER will not subtracted during shortest path computation.
                (Check method - getInventoryForDay(location, day))
     This class will also execute the shortest path to build Result and update the LocationDayInventoryUsed map accordingly.
     **/
    public Network(List<BillOfDistribution> distributionBills, List<OnHandInventory> onHandInventories) {
        graph = ArrayListMultimap.create();
        for (BillOfDistribution distributionBill: distributionBills)
            addEdge(distributionBill);
        inventory = new HashMap<>();
        for(OnHandInventory onHandInventory: onHandInventories) {
            inventory.put(onHandInventory.getLocation(), onHandInventory.getStock());
        }
        inventoryStore = new InventoryStore();
        shortestPathStore = new ShortestPathStore();
    }

    private void addEdge(BillOfDistribution distributionBill) {
        NextInPath path = NextInPath.builder()
                .source(distributionBill.getSource())
                .freightCost(distributionBill.getFreightCost())
                .leadTime(distributionBill.getLeadTimeDays())
                .inventoryCollected(0)
                .build();
        graph.put(distributionBill.getDestination(), path);
    }

    public Collection<NextInPath> get(String location) {
        return graph.get(location);
    }

    public int getInventoryForDay(String location, Integer day) {
        int inv = inventory.getOrDefault(location, 0);
        int usedInventory = inventoryStore.getLocationInventoryUsedForDay(location, day);
        return Integer.max(inv - usedInventory, 0);
    }

    public void executePath(Demand demand, NextInPath top) {
        for (NextInPath path = top; null != path.getFromEdge(); path = path.getFromEdge()) {
            NextInPath to = path.getFromEdge();
            Integer inventoryDispatched = path.getInventoryCollected() - to.getInventoryCollected();
            inventoryStore.useInventory(path.getSource(), to.getSource(), path.getLeadTime(), inventoryDispatched);
            shortestPathStore.addEdgeToShortestPath(demand, path);
        }
    }
}
