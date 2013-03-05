import java.math.BigDecimal;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner cin = new Scanner(System.in);
        while (cin.hasNext()) {

            // 读取输入
            BigDecimal r = cin.nextBigDecimal();
            int n = cin.nextInt();

            // 计算结果
            String result = r.pow(n).toPlainString();

            // 删除打头0
            result = result.replaceAll("^0*", "");

            // 删除行尾. 和 0
            result = result.replaceAll("\\.?0*?$", "");

            System.out.println(result);
        }
    }
}
