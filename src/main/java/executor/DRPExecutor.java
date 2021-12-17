package executor;

import static dagger.DRPModule.BILL_OF_DISTRIBUTION_PROVIDER;
import static dagger.DRPModule.ON_HAND_INVENTORY_PROVIDER;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.inject.Inject;
import javax.inject.Named;

import dagger.DaggerDRPComponent;
import model.BillOfDistribution;
import model.OnHandInventory;
import provider.CustomerDemandProvider;
import provider.FileInputProvider;

import lombok.extern.log4j.Log4j2;

@Log4j2
public class DRPExecutor {

    @Inject
    @Named(BILL_OF_DISTRIBUTION_PROVIDER)
    FileInputProvider<BillOfDistribution> billOfDistributionProvider;

    @Inject
    @Named(ON_HAND_INVENTORY_PROVIDER)
    FileInputProvider<OnHandInventory> onHandInventoryProvider;

    @Inject
    CustomerDemandProvider customerDemandProvider;

    public DRPExecutor() {
        DaggerDRPComponent.create().inject(this);
    }

    public void execute() {
        log.info(billOfDistributionProvider.provide(getFile(Paths.get("/Volumes/workplace/DRP2/src/main/resources/bill_of_distribution.csv"))));
        log.info(onHandInventoryProvider.provide(getFile(Paths.get("/Volumes/workplace/DRP2/src/main/resources/on_hand_inventory.csv"))));
        log.info(customerDemandProvider.provide(getFile(Paths.get("/Volumes/workplace/DRP2/src/main/resources/customer_demand.csv"))));
    }

    private File getFile(Path path) {
        return new File(path.toString());
    }
}
