package scripts;

import java.util.Arrays;

import executor.DRPGeneratedInputExecutor;

public class DRPGeneratedInputScript {
    public static void main(String[] args) {
        final DRPGeneratedInputExecutor executor = new DRPGeneratedInputExecutor();
        executor.generateInputAndExecute(1, Arrays.asList("Warehouse", "Deposit"), Arrays.asList(1, 2), 3);
    }
}
