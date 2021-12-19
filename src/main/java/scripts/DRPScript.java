package scripts;

import java.nio.file.Path;
import java.nio.file.Paths;

import executor.DRPCollectInputExecutor;

public class DRPScript {

    public static void main(String[] args) {

        String currentDir = System.getProperty("user.dir");
        System.out.println(String.format("Picking input from current working directory - [%s]", currentDir));
        System.out.println(String.format("Execution i/o can be found in directory - [%s]", currentDir));

        Path billOfDistributionFilePath = Paths.get(currentDir, "bill_of_distribution.csv");
        Path onHandInventoryFilePath = Paths.get(currentDir, "on_hand_inventory.csv");
        Path customerDemandFilePath = Paths.get(currentDir, "customer_demand.csv");

        final DRPCollectInputExecutor drpCollectInputExecutor = new DRPCollectInputExecutor();
        drpCollectInputExecutor.execute(billOfDistributionFilePath, onHandInventoryFilePath, customerDemandFilePath, Paths.get(currentDir));
    }
}
