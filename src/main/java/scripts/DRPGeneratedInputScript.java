package scripts;

import java.nio.file.Paths;
import java.util.Arrays;

import executor.DRPGeneratedInputExecutor;

public class DRPGeneratedInputScript {
    public static void main(String[] args) {
        final DRPGeneratedInputExecutor executor = new DRPGeneratedInputExecutor();

        String currentDir = System.getProperty("user.dir");
        System.out.println(String.format("Picking input from current working directory - [%s]", currentDir));
        System.out.println(String.format("Execution i/o can be found in directory - [%s]", currentDir));

        executor.generateInputAndExecute(1, Arrays.asList("Warehouse", "Deposit"), Arrays.asList(1, 2), 3, Paths.get(currentDir));
    }
}
