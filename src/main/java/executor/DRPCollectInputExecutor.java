package executor;

import static dagger.DRPModule.BILL_OF_DISTRIBUTION_PROVIDER;
import static dagger.DRPModule.ON_HAND_INVENTORY_PROVIDER;

import java.io.File;
import java.nio.file.Path;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import dagger.DaggerDRPComponent;
import model.BillOfDistribution;
import model.Demand;
import model.OnHandInventory;
import provider.CustomerDemandProvider;
import provider.FileInputProvider;

import lombok.extern.log4j.Log4j2;

@Log4j2
public class DRPCollectInputExecutor {

    @Inject
    @Named(BILL_OF_DISTRIBUTION_PROVIDER)
    FileInputProvider<BillOfDistribution> billOfDistributionProvider;

    @Inject
    @Named(ON_HAND_INVENTORY_PROVIDER)
    FileInputProvider<OnHandInventory> onHandInventoryProvider;

    @Inject
    CustomerDemandProvider customerDemandProvider;

    @Inject
    DRPExecutor drpExecutor;

    public DRPCollectInputExecutor() {
        DaggerDRPComponent.create().inject(this);
    }

    /*
    Executor will do following:
    * Collect input files and load bill of distribution, on hand inventory & customer demand
    * Pass the input to Resource Planner
    * Save the output files
     */
    public void execute(Path billOfDistributionFilePath, Path onHandInventoryFilePath, Path customerDemandFilePath, Path outputDir) {
        List<BillOfDistribution> distributionBills = billOfDistributionProvider.provide(getFile(billOfDistributionFilePath));
        List<OnHandInventory> onHandInventories = onHandInventoryProvider.provide(getFile(onHandInventoryFilePath));
        List<Demand> customerDemand = customerDemandProvider.provide(getFile(customerDemandFilePath));
        drpExecutor.executeDRP(distributionBills, onHandInventories, customerDemand, outputDir);
    }

    private File getFile(Path path) {
        return new File(path.toString());
    }
}
