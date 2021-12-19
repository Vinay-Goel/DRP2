package network;

import java.io.PrintWriter;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;

import model.network.DispatchedInventoryKey;
import model.network.LocationDayInventoryUsedKey;
import writer.WriterProvider;

public class InventoryStore {
    private Map<LocationDayInventoryUsedKey, Integer> locationDayInventoryUsed;
    private Map<DispatchedInventoryKey, Integer> dispatchedInventory;

    public InventoryStore() {
        locationDayInventoryUsed = new HashMap<>();
        dispatchedInventory = new HashMap<>();
    }

    public int getLocationInventoryUsedForDay(String location, Integer day) {
        return locationDayInventoryUsed.getOrDefault(new LocationDayInventoryUsedKey(location, day), 0);
    }

    public void useInventory(String from, String to, Integer dispatchDay, Integer inventoryDispatched) {
        LocationDayInventoryUsedKey locationDayInventoryUsedKey = new LocationDayInventoryUsedKey(from, dispatchDay);
        int inventoryAlreadyUsed = locationDayInventoryUsed.getOrDefault(locationDayInventoryUsedKey, 0);
        locationDayInventoryUsed.put(locationDayInventoryUsedKey, inventoryAlreadyUsed + inventoryDispatched);

        DispatchedInventoryKey dispatchedInventoryKey = new DispatchedInventoryKey(from, to, dispatchDay);
        int inventoryAlreadyDispatched = dispatchedInventory.getOrDefault(dispatchedInventoryKey, 0);
        dispatchedInventory.put(dispatchedInventoryKey, inventoryAlreadyDispatched + inventoryDispatched);
    }

    public void write(Path outputFilePath) {
        PrintWriter writer = WriterProvider.getPrintWriter(outputFilePath);
        for (DispatchedInventoryKey key: dispatchedInventory.keySet()) {
            writer.println(
                    String.format("From: [%s], to: [%s], day: [%s] - inventory dispatched: [%s]",
                            key.getSource(), key.getDestination(), key.getDay(), dispatchedInventory.get(key))
            );
        }
        writer.close();
    }
}
