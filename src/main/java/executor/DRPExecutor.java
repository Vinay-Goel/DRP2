package executor;

import static dagger.DRPModule.BILL_OF_DISTRIBUTION_PROVIDER;
import static dagger.DRPModule.ON_HAND_INVENTORY_PROVIDER;
import static dagger.DRPModule.SINGLE_NODE_SHORTEST_PATH_BASED_PLANNER;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Instant;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import constants.PlannerConstants;
import model.BillOfDistribution;
import model.Demand;
import model.OnHandInventory;
import network.Network;
import planner.ResourcePlanner;
import provider.CustomerDemandProvider;
import provider.FileInputProvider;

import lombok.extern.log4j.Log4j2;

@Log4j2
public class DRPExecutor {

    private final FileInputProvider<BillOfDistribution> billOfDistributionProvider;
    private final FileInputProvider<OnHandInventory> onHandInventoryProvider;
    private final CustomerDemandProvider customerDemandProvider;
    private final ResourcePlanner resourcePlanner;

    @Inject
    public DRPExecutor(@Named(BILL_OF_DISTRIBUTION_PROVIDER)FileInputProvider<BillOfDistribution> billOfDistributionProvider,
            @Named(ON_HAND_INVENTORY_PROVIDER) FileInputProvider<OnHandInventory> onHandInventoryProvider,
            CustomerDemandProvider customerDemandProvider,
            @Named(SINGLE_NODE_SHORTEST_PATH_BASED_PLANNER) ResourcePlanner resourcePlanner) {
        this.billOfDistributionProvider = billOfDistributionProvider;
        this.onHandInventoryProvider = onHandInventoryProvider;
        this.customerDemandProvider = customerDemandProvider;
        this.resourcePlanner = resourcePlanner;
    }

    public void executeDRP(List<BillOfDistribution> distributionBills, List<OnHandInventory> onHandInventories, List<Demand> customerDemand,
            Path outputDir) {
        log.info("Bill Of Distribution: [{}]", distributionBills);
        log.info("On Hand Inventory: [{}]", onHandInventories);
        log.info("Customer Demand: [{}]", customerDemand);

        String outputPrefix = Instant.now().toString();
        Path shortestPathOutputLoc = Paths.get(outputDir.toString(),  outputPrefix.concat(PlannerConstants.SHORTEST_PATH_OUTPUT_FILE));
        Path dispatchedInventoryOutputLoc = Paths.get(outputDir.toString(),
                outputPrefix.concat(PlannerConstants.DISPATCHED_INVENTORY_OUTPUT_FILE));
        createOutputDirectory(shortestPathOutputLoc);
        createOutputDirectory(dispatchedInventoryOutputLoc);

        Network network = resourcePlanner.plan(distributionBills, onHandInventories, customerDemand);
        network.getShortestPathStore().write(shortestPathOutputLoc);
        network.getInventoryStore().write(dispatchedInventoryOutputLoc);
    }

    private void createOutputDirectory(Path outputDir) {
        log.info("Creating path: [{}]", outputDir.toString());
        File directory = new File(outputDir.toString());
        if (!directory.exists())
            directory.mkdir();
    }
}
