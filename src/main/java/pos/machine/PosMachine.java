package pos.machine;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;
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
        Map<String, Integer> barcodeCount = new LinkedHashMap<>();  // 改为LinkedHashMap保持顺序
        for (String barcode : barcodes) {
            barcodeCount.put(barcode, barcodeCount.getOrDefault(barcode, 0) + 1);
        }
        return barcodeCount;
    }

    private Map<Item, Integer> buildCartItems(Map<String, Integer> barcodeCount) {
        Map<Item, Integer> cartItems = new LinkedHashMap<>();  // 改为LinkedHashMap保持插入顺序
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

    private int calculateSubtotal(Item item, int quantity) {
        return item.getPrice() * quantity;
    }

    private int calculateTotal(Map<Item, Integer> cartItems) {
        int total = 0;
        for (Map.Entry<Item, Integer> entry : cartItems.entrySet()) {
            Item item = entry.getKey();
            int quantity = entry.getValue();
            total += calculateSubtotal(item, quantity);
        }
        return total;
    }

    private String buildReceiptHeader() {
        return "***<store earning no money>Receipt***";
    }

    private String buildItemLine(Item item, int quantity) {
        int subtotal = calculateSubtotal(item, quantity);
        return "Name: " + item.getName() +
               ", Quantity: " + quantity +
               ", Unit price: " + item.getPrice() + " (yuan)" +
               ", Subtotal: " + subtotal + " (yuan)";
    }

    private String buildReceiptFooter(int total) {
        return "----------------------\n" +
               "Total: " + total + " (yuan)\n" +
               "**********************";
    }

    public String printReceipt(List<String> barcodes) {
        // 第一步：商品数据库已在构造函数中初始化

        // 第二步：统计条码数量
        Map<String, Integer> barcodeCount = countBarcodes(barcodes);

        // 第三步：构建购物车商品
        Map<Item, Integer> cartItems = buildCartItems(barcodeCount);

        // 第四步：计算总价
        int total = calculateTotal(cartItems);

        // 第五步：格式化收据
        StringBuilder receipt = new StringBuilder();

        // 添加收据头部
        receipt.append(buildReceiptHeader()).append("\n");

        // 添加每个商品行
        for (Map.Entry<Item, Integer> entry : cartItems.entrySet()) {
            Item item = entry.getKey();
            int quantity = entry.getValue();
            receipt.append(buildItemLine(item, quantity)).append("\n");
        }

        // 添加收据尾部
        receipt.append(buildReceiptFooter(total));

        return receipt.toString();
    }

//    // 测试用的main方法
//    public static void main(String[] args) {
//        PosMachine posMachine = new PosMachine();
//
//        List<String> testBarcodes = Arrays.asList(
//            "ITEM000000", "ITEM000000", "ITEM000000", "ITEM000000",
//            "ITEM000001", "ITEM000001",
//            "ITEM000004", "ITEM000004", "ITEM000004"
//        );
//        System.out.println("输入的条码: " + testBarcodes);
//        System.out.println("\n生成的收据:");
//        System.out.println("==================");

//        String receipt = posMachine.printReceipt(testBarcodes);
//        System.out.println(receipt);
/
//    }
}
