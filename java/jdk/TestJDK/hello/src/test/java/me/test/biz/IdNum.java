package me.test.biz;

/**
 *
 * 验证身份证号码格式是否合法。
 * 参考国标 GB 11643-1999 公民身份号码.pdf
 *
 * @author zll
 */
public class IdNum {

    public static void main(String[] args) {

        System.out.println(isValid18("11010519491231002X"));
        System.out.println(isValid18("440524188001010014"));
    }

    private static final int[] WEIGHTS = new int[] {7, 9, 10, 5, 8, 4, 2, 1, 6, 3, 7, 9, 10, 5, 8, 4, 2, 1};
    private static final char[] VERIFY_CODES = new char[] {'1', '0', 'X', '9', '8', '7', '6', '5', '4', '3', '2'};

    public static boolean isValid18(String idNo) {

        if (idNo == null || idNo.length() != 18) {
            return false;
        }

        int sum = 0;

        for (int i = 0; i < idNo.length() - 1; i++) {

            char c = idNo.charAt(i);

            // 非0~9
            if (!(c >= 0x30 && c <= 0x39)) {
                return false;
            }

            int num = c - 0x30;
            sum += num * WEIGHTS[i];
        }

        int mod = sum % 11;
        int verifyCode = VERIFY_CODES[mod];

        return verifyCode == idNo.charAt(17);
    }
}
