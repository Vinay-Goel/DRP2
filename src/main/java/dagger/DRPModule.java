package dagger;

import javax.inject.Named;

import model.OnHandInventory;
import provider.FileInputProvider;
import model.BillOfDistribution;

import com.univocity.parsers.common.processor.BeanListProcessor;

@Module
public class DRPModule {

    public static final String BILL_OF_DISTRIBUTION_PROVIDER = "BILL_OF_DISTRIBUTION_PROVIDER";
    public static final String ON_HAND_INVENTORY_PROVIDER = "ON_HAND_INVENTORY_PROVIDER";

    @Provides
    @Named(BILL_OF_DISTRIBUTION_PROVIDER)
    public static FileInputProvider<BillOfDistribution> provideBillOfDistributionProvider() {
        return new FileInputProvider<>(new BeanListProcessor<>(BillOfDistribution.class));
    }

    @Provides
    @Named(ON_HAND_INVENTORY_PROVIDER)
    public static FileInputProvider<OnHandInventory> provideOnHandInventoryProvider() {
        return new FileInputProvider<>(new BeanListProcessor<>(OnHandInventory.class));
    }
}
