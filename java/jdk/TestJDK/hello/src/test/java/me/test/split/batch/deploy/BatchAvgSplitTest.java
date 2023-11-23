package me.test.split.batch.deploy;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author dangqian.zll
 * @date 2023/11/22
 */
public class BatchAvgSplitTest {
    BatchAvgSplitService service = new BatchAvgSplitService();

    protected static List<List<String>> toIpList(List<List<Machine>> result) {
        return result.stream()
                .map(l -> l.stream()
                        .map(Machine::getIp)
                        .collect(Collectors.toList()))
                .collect(Collectors.toList());
    }

    @Test
    public void testDivide() {
        Assertions.assertEquals(1, 5 / 3);
        Assertions.assertEquals(2, 5 / 2);
        Assertions.assertEquals(0, 5 / 6);
    }

    @Test
    public void groupToDetailList01() {
        List<Machine> list = Arrays.asList(
                Machine.builder().cluster("C1").unit("U1").group("G1").room("R2").ip("I3").build(),
                Machine.builder().cluster("C1").unit("U1").group("G1").room("R1").ip("I2").build(),
                Machine.builder().cluster("C1").unit("U1").group("G1").room("R1").ip("I1").build(),
                Machine.builder().cluster("C1").unit("U1").group("G1").room("R2").ip("I4").build(),
                Machine.builder().cluster("C1").unit("U1").group("G1").room("R2").ip("I5").build()
        );

        List<List<Machine>> result = BatchAvgSplitService.groupToDetailList(
                list,
                service.mostDetailGroupMapper,
                service.machineComparator
        );
        Assertions.assertEquals(2, result.size());

        List<List<String>> newResult = toIpList(result);
        Assertions.assertEquals(Arrays.asList("I1", "I2"), newResult.get(0));
        Assertions.assertEquals(Arrays.asList("I3", "I4", "I5"), newResult.get(1));
    }

    @Test
    public void toOrderList01() {
        List<List<String>> list = Arrays.asList(
                Arrays.asList("I1", "I2"),
                Arrays.asList("I3", "I4", "I5"),
                Collections.singletonList("I6")
        );
        List<String> result = BatchAvgSplitService.toOrderList(list);
        Assertions.assertEquals(
                Arrays.asList("I1", "I3", "I6", "I2", "I4", "I5"),
                result
        );
    }

    /**
     * 不同单元，机器少，期望分的批次多。
     */
    @Test
    public void split01() {
        List<Machine> list = Arrays.asList(
                Machine.builder().cluster("C1").unit("U1").group("G1").room("R1").ip("I1").build(),
                Machine.builder().cluster("C1").unit("U2").group("G2").room("R2").ip("I2").build(),
                Machine.builder().cluster("C1").unit("U3").group("G3").room("R3").ip("I3").build(),
                Machine.builder().cluster("C1").unit("U4").group("G4").room("R4").ip("I4").build(),
                Machine.builder().cluster("C1").unit("U5").group("G5").room("R5").ip("I5").build()
        );

        List<List<Machine>> result = service.split(list, 7);
        List<List<String>> newResult = toIpList(result);

        Assertions.assertEquals(7, newResult.size());
        Assertions.assertEquals(Collections.singletonList("I1"), newResult.get(0));
        Assertions.assertEquals(Collections.singletonList("I2"), newResult.get(1));
        Assertions.assertEquals(Collections.singletonList("I3"), newResult.get(2));
        Assertions.assertEquals(Collections.singletonList("I4"), newResult.get(3));
        Assertions.assertEquals(Collections.singletonList("I5"), newResult.get(4));
        Assertions.assertTrue(newResult.get(5).isEmpty());
        Assertions.assertTrue(newResult.get(6).isEmpty());
    }

    /**
     * 不同单元，机器少，期望分的批次少。
     */
    @Test
    public void split02() {
        List<Machine> list = Arrays.asList(
                Machine.builder().cluster("C1").unit("U1").group("G1").room("R1").ip("I1").build(),
                Machine.builder().cluster("C1").unit("U2").group("G2").room("R2").ip("I2").build(),
                Machine.builder().cluster("C1").unit("U3").group("G3").room("R3").ip("I3").build(),
                Machine.builder().cluster("C1").unit("U4").group("G4").room("R4").ip("I4").build(),
                Machine.builder().cluster("C1").unit("U5").group("G5").room("R5").ip("I5").build()
        );

        List<List<Machine>> result = service.split(list, 3);
        List<List<String>> newResult = toIpList(result);

        Assertions.assertEquals(3, newResult.size());
        Assertions.assertEquals(Arrays.asList("I1", "I4"), newResult.get(0));
        Assertions.assertEquals(Arrays.asList("I2", "I5"), newResult.get(1));
        Assertions.assertEquals(Collections.singletonList("I3"), newResult.get(2));
    }

    @Test
    public void split102() {
        List<Machine> list = Arrays.asList(
                Machine.builder().cluster("C1").unit("U1").group("G1").room("R1").ip("I1").build(),
                Machine.builder().cluster("C1").unit("U1").group("G1").room("R1").ip("I2").build(),

                Machine.builder().cluster("C1").unit("U1").group("G1").room("R2").ip("I3").build(),
                Machine.builder().cluster("C1").unit("U1").group("G1").room("R2").ip("I4").build(),
                Machine.builder().cluster("C1").unit("U1").group("G1").room("R2").ip("I5").build(),

                Machine.builder().cluster("C1").unit("U1").group("G1").room("R3").ip("I6").build()
        );

        // I1, I3,I6,I2
        List<List<Machine>> result = service.split(list, 3);
        List<List<String>> newResult = toIpList(result);

        Assertions.assertEquals(3, newResult.size());
        Assertions.assertEquals(Arrays.asList("I1", "I3"), newResult.get(0));
        Assertions.assertEquals(Arrays.asList("I6", "I2"), newResult.get(1));
        Assertions.assertEquals(Arrays.asList("I4", "I5"), newResult.get(2));
    }


    @Test
    public void split201() {
        List<Machine> list = Arrays.asList(
                Machine.builder().cluster("C1").unit("U1").group("G1").room("R1").ip("I1").build(),
                Machine.builder().cluster("C1").unit("U1").group("G1").room("R1").ip("I2").build(),
                Machine.builder().cluster("C1").unit("U1").group("G1").room("R1").ip("I3").build(),
                Machine.builder().cluster("C1").unit("U1").group("G1").room("R1").ip("I4").build(),
                Machine.builder().cluster("C1").unit("U1").group("G1").room("R1").ip("I5").build(),

                Machine.builder().cluster("C1").unit("U1").group("G1").room("R2").ip("I6").build(),
                Machine.builder().cluster("C1").unit("U1").group("G1").room("R2").ip("I7").build()
        );

        List<List<Machine>> result = service.split(list, 6);
        List<List<String>> newResult = toIpList(result);

        Assertions.assertEquals(6, newResult.size());
        Assertions.assertEquals(Arrays.asList("I1", "I5"), newResult.get(0));
        Assertions.assertEquals(Arrays.asList("I6"), newResult.get(1));
        Assertions.assertEquals(Arrays.asList("I2"), newResult.get(2));
        Assertions.assertEquals(Arrays.asList("I7"), newResult.get(3));
        Assertions.assertEquals(Arrays.asList("I3"), newResult.get(4));
        Assertions.assertEquals(Arrays.asList("I4"), newResult.get(5));
    }


}
