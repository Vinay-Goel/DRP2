package sample;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.inject.Inject;

import constants.PlannerConstants;
import model.OnHandInventory;
import random.Random;

public class SampleInventoryGenerator {

    private final Integer minInventory;
    private final Integer maxInventory;
    private final Integer inventoryThreshold;

    @Inject
    public SampleInventoryGenerator(Integer minInventory, Integer maxInventory, Integer inventoryThreshold) {
        this.minInventory = minInventory;
        this.maxInventory = maxInventory;
        this.inventoryThreshold = inventoryThreshold;
    }

    public List<OnHandInventory> generate(List<String> sources, List<List<String>> intermediates) {
        List<OnHandInventory> inventories = new ArrayList<>();
        for (String source: sources) {
            OnHandInventory inventory = new OnHandInventory(source, PlannerConstants.INFINITE_STOCK_VALUE);
            inventories.add(inventory);
        }
        for (List<String> intermediate: intermediates)
            inventories.addAll(generateInventory(intermediate));
        return inventories;
    }

    public List<OnHandInventory> generateInventory(List<String> intermediate) {
        return intermediate.stream()
                .map(loc -> {
                    Integer stock = Random.nextInt(minInventory, maxInventory, inventoryThreshold);
                    return new OnHandInventory(loc, stock);
                })
                .collect(Collectors.toList());
    }
}
