
/** 螺旋矩阵 */
public class Main002 {

    public static void main(String[] args) {
        for (int i = 1; i < 21; i++) {
            System.out.println("======================== " + i);
            resolve(i);
        }
    }

    public static void resolve(int len) {
        if (len <= 0) {
            throw new IllegalArgumentException("输入值必须时正整数");
        }
        if (len == 1) {
            System.out.println(1);
        } else {
            int[][] arr = new int[len][len];
            setFind(arr, 1, 0, 0);
            printArr(arr);
        }
    }

    public static void printArr(int[][] arr) {
        int fmtLen = Integer.toString(arr.length * arr.length).length();
        setFind(arr, 1, 0, 0);

        for (int i = 0; i < arr.length; i++) {
            for (int j = 0; j < arr.length; j++) {
                System.out.printf(" %" + fmtLen + "d", arr[i][j]);
            }
            System.out.println();
        }
    }

    public static void setFind(int[][] arr, int num, int i, int j) {
        if (arr[i][j] != 0) {
            return;
        }
        arr[i][j] = num;

        boolean downOk = i + 1 < arr.length && arr[i + 1][j] == 0;
        boolean rightOk = j + 1 < arr.length && arr[i][j + 1] == 0;
        boolean upOk = i - 1 >= 0 && arr[i - 1][j] == 0;
        boolean leftOk = j - 1 >= 0 && arr[i][j - 1] == 0;

        // 逆时针检测
        if (downOk) {
            if (leftOk) {
                setFind(arr, num + 1, i, j - 1); // 向左
            } else {
                setFind(arr, num + 1, i + 1, j); // 向下
            }
            // 向右
        } else if (rightOk) {
            setFind(arr, num + 1, i, j + 1);

            // 向上
        } else if (upOk) {
            if (rightOk) {
            }
            setFind(arr, num + 1, i - 1, j);

            // 向左
        } else if (leftOk) {
            setFind(arr, num + 1, i, j - 1);
        }
        return;
    }
}
