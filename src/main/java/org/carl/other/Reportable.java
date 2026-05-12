package org.carl.other;

import org.carl.itemmanagement.Item;

import java.util.List;

public interface Reportable {
    void generateReport(List<Item> items);
}
