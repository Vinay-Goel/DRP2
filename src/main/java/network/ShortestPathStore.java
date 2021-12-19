package network;

import java.io.PrintWriter;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;

import model.Demand;
import model.plan.NextInPath;
import writer.WriterProvider;

public class ShortestPathStore {
    private Map<Demand, NextInPath> shortestPath;

    public ShortestPathStore() {
        shortestPath = new HashMap<>();
    }

    public void addEdgeToShortestPath(Demand demand, NextInPath edge) {
        shortestPath.put(demand, edge);
    }

    public void write(Path outputFilePath) {
        PrintWriter writer = WriterProvider.getPrintWriter(outputFilePath);
        for (Demand demand: shortestPath.keySet()) {
            writer.println(String.format("Demand: [%s], ShortestPath: [%s]", demand, shortestPath.get(demand)));
        }
        writer.close();
    }
}
