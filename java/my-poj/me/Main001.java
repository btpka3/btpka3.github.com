import java.util.Arrays;

/**
 *
 * 有4个球，其中一个重量不规则，提供2个额外的标准球，要求2次用天枰称重找出该球，并判定其是重了还是轻了。
 *
 */
public class Main001 {

    public static void main(String[] args) {

        int [][] testData = new int[][]{
             { -1, 0, 0, 0, -1},
             { +1, 0, 0, 0, +1},
             { 0, -1, 0, 0, -2},
             { 0, +1, 0, 0, +2},
             { 0, 0, -1, 0, -3},
             { 0, 0, +1, 0, +3},
             { 0, 0, 0, -1, -4},
             { 0, 0, 0, +1, +4},
        };
        for (int[] data : testData) {
            String dataStr = Arrays.toString(data);
            int result = calc(data);
            boolean ok = result == data[4];
            System.out.println(dataStr + " = " + result + " " + ok);
        }

    }

    public static int calc(int[] arr) {
        int c = 0;

        int result = (arr[0] + arr[1]) - (arr[2] + c);
        // arr[3] 不规则
        if (result == 0) {
            if (arr[3] < c) {
                return -4;
            } else {
                return +4;
            }
        }

        // arr[0], arr[1] 中有一个轻，或者 arr[2] 重
        else if (result < 0) {

            result = (arr[0] + arr[2]) - (c + c);
            if (result < 0) {
                return -1;
            } else if (result == 0) {
                return -2;
            } else {
                return +3;
            }

            // arr[0], arr[1] 中有一个重，或者 arr[2] 轻
        } else {
            result = (arr[0] + arr[2]) - (c + c);
            if (result < 0) {
                return -3;
            } else if (result == 0) {
                return +2;
            } else {
                return +1;
            }
        }

    }

    public static void main3(String[] args) {

    }
}
