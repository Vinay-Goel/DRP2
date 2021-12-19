package scripts;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Instant;

import executor.DRPExecutor;

public class DRPScript {

    public static void main(String[] args) {
        String currentDir = System.getProperty("user.dir");
        System.out.println(String.format("Picking input from current working directory - [%s]", currentDir));

        Path outputDir = Paths.get(currentDir, "execution", Instant.now().toString());
        System.out.println(String.format("Execution i/o can be found in directory - [%s]", outputDir));

        Path billOfDistributionFilePath = Paths.get(currentDir, "bill_of_distribution.csv");
        Path onHandInventoryFilePath = Paths.get(currentDir, "on_hand_inventory.csv");
        Path customerDemandFilePath = Paths.get(currentDir, "customer_demand.csv");

        final DRPExecutor drpExecutor = new DRPExecutor();
        drpExecutor.execute(billOfDistributionFilePath, onHandInventoryFilePath, customerDemandFilePath, outputDir);
    }
}
