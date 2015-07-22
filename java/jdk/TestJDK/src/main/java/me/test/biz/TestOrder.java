package me.test.biz;

import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;
import java.util.Queue;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 平台商允许商户发布商品，但是显示时按照发布时间排序，按照商户分组; 最后需要额外处理，确保同一个商户发布的产品之间间隔随后3个其他商户的产品。
 * <p>
 * 比如: (假设 11aaa 代表商户11的产品aaa )
 * 1a,1b,1c,1d,1e, 2a,2b,2c,2d, 3a,3b,3c, 4a,4b, 5a,6a,7a
 * 应当处理为：
 * 1a,2a,3a,4a, 1b,2b,3b,4b, 1c,2c,3c,5a, 1d,2d,6a,7a, 1e
 */
public class TestOrder {

    public static final int INTERVAL = 3;

    public static class Item {
        int userId;
        String itemName;

        public String toString() {
            return userId + itemName;
        }
    }

    // 只有当商品有录入或者更新的时候才执行本方法，并将结果缓存。
    // FIXME 如何避免商户恶意重新上下架商品，以使其商品 录入时间 最新，而获得更靠前的展示机会？
    // FIXME 如何避免商户恶意更新商品描述，以使其商品 修改时间 最新，而获得更靠前的展示机会？
    public static void main(String[] args) {

        // 模拟按照录入时间或者修改时间排好序的记录
        String[] inputList = new String[]{ // 命名规则： "{num}{name}", 其中num为userId，name为商品名称
                "1f",
                "101a",
                "102b",
                "1a", "1b", "1c", "1d", "1e",
                "2a", "2b", "2c", "2d",
                "3a", "3b", "3c",
                "4a", "4b",
                "5a",
                "6a",
                "7a",
                "8a"
        };

        List<Item> itemList = toItemList(inputList);
        System.out.println("------------------------- 用户收入(已经按照一定规则排序过)：");
        System.out.println(itemList);

        List<Queue<Item>> groupedList = groupByUser(itemList);
        System.out.println("------------------------- 分组结果：");
        System.out.println(groupedList);

        List<Item> sortedItemList = sort(groupedList);
        System.out.println("------------------------- 再排序后的结果为：");
        System.out.println(sortedItemList);

        // TODO 存入缓存
    }

    private static List<Item> toItemList(String[] inputList) {
        List<Item> itemList = new LinkedList<Item>();

        Pattern p = Pattern.compile("^(\\d+)(\\S+)$");
        for (String rec : inputList) {
            Matcher m = p.matcher(rec);
            if (!m.matches()) {
                throw new RuntimeException("输入格式不正确： `" + rec + "`");
            }
            Item curItem = new Item();
            curItem.userId = Integer.valueOf(m.group(1));
            curItem.itemName = m.group(2);
            itemList.add(curItem);
        }
        return itemList;
    }


    private static List<Queue<Item>> groupByUser(List<Item> itemList) {

        List<Queue<Item>> groupedList = new LinkedList<Queue<Item>>();

        Queue<Item> lastQueue = null;

        for (Item curItem : itemList) {
            if (lastQueue == null) {
                lastQueue = new LinkedList<Item>();
                groupedList.add(lastQueue);
            } else {
                Item lastItem = lastQueue.peek();
                if (lastItem.userId != curItem.userId) {
                    lastQueue = new LinkedList<Item>();
                    groupedList.add(lastQueue);
                }
            }
            lastQueue.add(curItem);
        }
        return groupedList;
    }

    private static List<Item> sort(List<Queue<Item>> groupedList) {
        List<Item> sortedItemList = new LinkedList<Item>();


        ListIterator<Queue<Item>> it = groupedList.listIterator();
        while (it.hasNext()) {

            Queue<Item> lastQueue = it.next();
            //int lastQueueIndex = it.nextIndex();
            //System.out.println("lastQueue = [" + lastQueueIndex + "] " + lastQueue);

            Item item = lastQueue.poll();
            if (item == null) {
                continue;
            }
            sortedItemList.add(item);

            // 当前列表已经空了，则继续
            if (lastQueue.peek() == null) {
                continue;
            }

            // 当前列表还有元素，就要将后面其他用户的提前数据插入几个
            int interval = 0;
            int forwardCount = 0;
            while (it.hasNext() && interval < INTERVAL) {
                Queue<Item> queue = it.next();
                forwardCount++;

                Item i = queue.poll();
                if (i == null) {
                    continue;
                }
                sortedItemList.add(i);
                interval++;
            }
            for (int i = 0; i <= forwardCount; i++) { // 注意这里需要回退一次
                it.previous(); // 这里一定不会抛出错误
            }
        }

        return sortedItemList;
    }

}
