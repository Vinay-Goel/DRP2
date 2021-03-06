package planner;

import java.util.List;

import model.BillOfDistribution;
import model.Demand;
import model.OnHandInventory;
import network.Network;
import model.plan.NextInPath;

import lombok.extern.log4j.Log4j2;

@Log4j2
public abstract class ResourcePlanner {

    /*
    Resource Planner will output the best possible routes prioritizing following constraints in given order:
    1. Lowest Freight Cost
    2. Minimum Lead Time
     */
    public Network plan(List<BillOfDistribution> distributionBills, List<OnHandInventory> inventories, List<Demand> customerDemand) {
        Network network = new Network(distributionBills, inventories);

        for (Demand demand: customerDemand) {
            try {
                log.info("Planning for demand: [{}]", demand);
                NextInPath path = getShortestPath(demand, network);
                network.executePath(demand, path);
            } catch (Exception e) {
                log.error(e);
            }
        }
        return network;
    }

    abstract NextInPath getShortestPath(Demand demand, Network network);
}
