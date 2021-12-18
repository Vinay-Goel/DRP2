package model.network;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import model.BillOfDistribution;

import lombok.Getter;

@Getter
public class NetworkLocations {
    private List<String> locations;

    public NetworkLocations(List<BillOfDistribution> distributionBills) {
        Set<String> locationSet = new HashSet<>();
        for (BillOfDistribution distributionBill: distributionBills) {
            locationSet.add(distributionBill.getSource());
            locationSet.add(distributionBill.getDestination());
        }
        locations = new ArrayList<>(locationSet);
    }
}
