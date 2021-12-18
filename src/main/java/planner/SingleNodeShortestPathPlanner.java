package planner;

import java.util.Collection;
import java.util.TreeSet;

import javax.inject.Inject;

import model.Demand;
import model.network.Network;
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
        Collection<NextInPath> edges = network.get(demand.getLocation());

        //Add all edges from to the queue
        for (NextInPath edge: edges) {
            int inv = network.getInventoryForDay(edge.getLocation(), demand.getDay()+edge.getLeadTime());
            priorityQueue.add(nextPath(edge, demand.getDay()+edge.getLeadTime(), inv, null));
        }

        while (!priorityQueue.isEmpty()) {
            NextInPath top = priorityQueue.pollFirst();
            if (top.getInventoryCollected() >= demand.getDemand()) {
                return top;
            }
            edges = network.get(top.getLocation());
            for (NextInPath edge: edges) {
                int inv = top.getInventoryCollected() + network.getInventoryForDay(top.getLocation(), top.getCurrentDay()+edge.getLeadTime());
                priorityQueue.add(nextPath(edge, top.getCurrentDay()+edge.getLeadTime(), inv, top));
            }
        }
        String errorMessage =
                String.format("No path found to satisfy location: [%s] demand: [%s] for day: [%s]",
                        demand.getLocation(), demand.getDemand(), demand.getDay());
        throw new RuntimeException(errorMessage);
    }

    public NextInPath nextPath(NextInPath edge, Integer day, Integer invUsed, NextInPath fromEdge) {
        return NextInPath.builder()
                .freightCost(edge.getFreightCost())
                .leadTime(edge.getLeadTime())
                .location(edge.getLocation())
                .currentDay(day)
                .inventoryCollected(invUsed)
                .fromEdge(fromEdge)
                .build();
    }
}
