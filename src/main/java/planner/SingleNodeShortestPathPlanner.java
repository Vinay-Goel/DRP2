package planner;

import java.util.TreeSet;

import javax.inject.Inject;

import model.Demand;
import network.Network;
import model.plan.NextInPath;

import lombok.extern.log4j.Log4j2;

@Log4j2
public class SingleNodeShortestPathPlanner extends ResourcePlanner {

    @Inject
    public SingleNodeShortestPathPlanner() {}

    /*
    Single node shortest path planner implements dijkstra's algorithm. It internally uses priority queue based implementation to execute
    algorithm in O(V+E*log(V))
     */
    NextInPath getShortestPath(Demand demand, Network network) {
        TreeSet<NextInPath> priorityQueue = new TreeSet<>(NextInPath::compare);

        //Add all edges from to the queue
        for (NextInPath edge: network.get(demand.getLocation()))
            priorityQueue.add(nextEdgeFromTop(network, demand, null, edge));

        while (!priorityQueue.isEmpty()) {
            NextInPath top = priorityQueue.pollFirst();
            log.info("Top: [{}]", top);
            if (top.getInventoryCollected().equals(demand.getDemand()))
                return top;

            for (NextInPath edge: network.get(top.getSource()))
                priorityQueue.add(nextEdgeFromTop(network, demand, top, edge));
        }
        String errorMessage =
                String.format("No path found to satisfy location: [%s] demand: [%s] for day: [%s]",
                        demand.getLocation(), demand.getDemand(), demand.getDay());
        throw new RuntimeException(errorMessage);
    }

    public NextInPath nextEdgeFromTop(Network network, Demand demand, NextInPath top, NextInPath edge) {
        int totalLeadTime = edge.getLeadTime() + (null == top ? 0 : top.getLeadTime());
        int totalFreightCost = edge.getFreightCost() + (null == top ? 0 : top.getFreightCost());

        int dispatchDay = demand.getDay() - totalLeadTime;
        int availableInventoryOnDispatchDay = network.getInventoryForDay(edge.getSource(), dispatchDay);

        int totalInventoryCollected = availableInventoryOnDispatchDay + (null == top ? 0 : top.getInventoryCollected());
        totalInventoryCollected = Integer.min(totalInventoryCollected, demand.getDemand());

        return NextInPath.builder()
                .freightCost(totalFreightCost)
                .leadTime(totalLeadTime)
                .inventoryCollected(totalInventoryCollected)
                .source(edge.getSource())
                .fromEdge(top)
                .build();
    }
}
