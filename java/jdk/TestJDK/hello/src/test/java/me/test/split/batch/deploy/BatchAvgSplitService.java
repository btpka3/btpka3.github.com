package me.test.split.batch.deploy;

import org.apache.commons.lang3.StringUtils;

import java.util.*;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collectors;

/**
 * @author dangqian.zll
 * @date 2023/11/23
 */
public class BatchAvgSplitService {

    protected Function<Machine, String> mostDetailGroupMapper = (Machine m) -> StringUtils.trim(m.getCluster())
            + "-" + StringUtils.trim(m.getUnit())
            + "-" + StringUtils.trim(m.getGroup())
            + "-" + StringUtils.trim(m.getRoom());
    protected Comparator<Machine> machineComparator = Comparator.nullsLast(Comparator.comparing(Machine::getCluster))
            .thenComparing(Comparator.nullsLast(Comparator.comparing(Machine::getUnit)))
            .thenComparing(Comparator.nullsLast(Comparator.comparing(Machine::getGroup)))
            .thenComparing(Comparator.nullsLast(Comparator.comparing(Machine::getRoom)))
            .thenComparing(Comparator.nullsLast(Comparator.comparing(Machine::getIp)));

    public List<List<Machine>> split(List<Machine> list, int batchSize) {
        // 平均数（下边界），结果批次中的机器数量是该值，或者该值+1.
        int batchAvgSize = list.size() / batchSize;

        List<List<Machine>> mostDetailList = groupToDetailList(
                list,
                mostDetailGroupMapper,
                machineComparator
        );
        List<Machine> orderList = toOrderList(mostDetailList);
        int modCount = orderList.size() % batchSize;
        List<Machine> list1 = new LinkedList<>(orderList.subList(0, orderList.size() - modCount));
        List<Machine> list2 = new LinkedList<>(orderList.subList(orderList.size() - modCount, orderList.size()));
        List<List<Machine>> resultList = new ArrayList<>(batchSize);
        for (int i = 0; i < batchSize; i++) {
            List<Machine> batchList = new ArrayList<>(batchAvgSize + 1);
            resultList.add(batchList);
            for (int j = 0; j < batchAvgSize; j++) {
                batchList.add(list1.remove(0));
            }
            if (!list2.isEmpty()) {
                batchList.add(list2.remove(0));
            }

        }
        return resultList;
    }


    public static <T, K extends Comparable<K>> List<List<T>> groupToDetailList(
            List<T> list,
            Function<T, K> mostDetailGroupMapper,
            Comparator<T> valueComparator
    ) {
        return list.stream()
                .collect(Collectors.groupingBy(mostDetailGroupMapper, TreeMap::new, Collectors.toList()))
                .entrySet().stream()
                .map(Map.Entry::getValue)
                .peek(l -> Collections.sort(l, valueComparator))
                .collect(Collectors.toList());
    }

    public static <T> List<T> toOrderList(List<List<T>> list) {
        List<List<T>> clonedList = list.stream()
                .map(LinkedList::new)
                .collect(Collectors.toList());

        int totalSize = clonedList.stream().mapToInt(List::size).sum();
        List<T> resultList = new ArrayList<>(totalSize);

        Supplier<Boolean> isEmpty = () -> clonedList.stream().allMatch(List::isEmpty);

        int nextListIdx = 0;
        while (!isEmpty.get()) {
            List<T> nextList = clonedList.get(nextListIdx);
            nextListIdx = (nextListIdx + 1) % clonedList.size();
            while (nextList.isEmpty()) {
                nextList = clonedList.get(nextListIdx);
                nextListIdx = (nextListIdx + 1) % clonedList.size();
            }
            T o = nextList.remove(0);
            resultList.add(o);
        }
        return resultList;
    }

}
