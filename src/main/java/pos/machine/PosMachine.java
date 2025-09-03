package pos.machine;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PosMachine {
    private Map<String, Item> itemDatabase;

    public PosMachine() {
        initializeItemDatabase();
    }

    private void initializeItemDatabase() {
        itemDatabase = new HashMap<>();
        List<Item> allItems = ItemsLoader.loadAllItems();
        for (Item item : allItems) {
            itemDatabase.put(item.getBarcode(), item);
        }
    }

    public String printReceipt(List<String> barcodes) {
        return null;
    }
}
