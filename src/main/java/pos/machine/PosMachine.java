package pos.machine;

import java.util.ArrayList;
import java.util.Arrays;
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

    private Map<String, Integer> countBarcodes(List<String> barcodes) {
        Map<String, Integer> barcodeCount = new HashMap<>();
        for (String barcode : barcodes) {
            barcodeCount.put(barcode, barcodeCount.getOrDefault(barcode, 0) + 1);
        }
        return barcodeCount;
    }

    private Map<Item, Integer> buildCartItems(Map<String, Integer> barcodeCount) {
        Map<Item, Integer> cartItems = new HashMap<>();
        for (Map.Entry<String, Integer> entry : barcodeCount.entrySet()) {
            String barcode = entry.getKey();
            int quantity = entry.getValue();
            Item item = itemDatabase.get(barcode);
            if (item != null) {
                cartItems.put(item, quantity);
            }
        }
        return cartItems;
    }

    public String printReceipt(List<String> barcodes) {
        return null;
    }

    // 测试用的main方法
    public static void main(String[] args) {

        // 创建POS机实例
        PosMachine posMachine = new PosMachine();

        // 测试数据：与测试用例相同的条码
        List<String> testBarcodes = Arrays.asList(
            "ITEM000000", "ITEM000000", "ITEM000000", "ITEM000000",
            "ITEM000001", "ITEM000001",
            "ITEM000004", "ITEM000004", "ITEM000004"
        );

        System.out.println("输入的条码数组: " + testBarcodes);

        // 直接测试第三步：从条码到购物车的完整转换
        Map<String, Integer> barcodeCount = posMachine.countBarcodes(testBarcodes);
        Map<Item, Integer> cartItems = posMachine.buildCartItems(barcodeCount);

        System.out.println("\n测试buildCartItems:");
        for (Map.Entry<Item, Integer> entry : cartItems.entrySet()) {
            Item item = entry.getKey();
            int quantity = entry.getValue();
            System.out.println(item.getName() + quantity + item.getPrice()  + (item.getPrice() * quantity) );
        }
    }
}
