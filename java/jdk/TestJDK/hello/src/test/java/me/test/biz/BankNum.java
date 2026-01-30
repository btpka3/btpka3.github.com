package me.test.biz;

/**
 * 发行者标识代码 （Issuer identification number - IIN）。
 * <p>
 * 其中，银行卡卡号一般是16位或19位。。
 * <p>
 * # 第一部分
 * 前六位是主要产业标识符（Major Industry Identifier(MII)）
 * 当前已经用 IIN 代替了之前的 BIN （Bank identification number）
 * | 卡号首位 | 适用的目录|
 * |---------|----------|
 * |0        | ISO/TC 68 和其他行业使用 |
 * |1        | 航空 |
 * |2        | 航空和其他未来行业 |
 * |3        | 运输、娱乐和金融财务 |
 * |4        | 金融财务 |
 * |5        | 金融财务 |
 * |6        | 商业和金融财务 |
 * |7        | 石油和其他未来行业使用 |
 * |8        | 医疗、电信和其他未来行业使用 |
 * |9        | 由本国标准机构分配 |
 * <p>
 * # 中间部分
 * 第七位到倒数第二位（含），是个人账户标识。由发卡行自定义。
 * <p>
 * # 校验位
 * 最后一位。
 * <p>
 * <p>
 * 参考：
 * [ISO/IEC 7812](https://en.wikipedia.org/wiki/ISO/IEC_7812)
 * 《2016年5月7日全国各银行最新卡BIN表.xls》
 *
 */
public class BankNum {

    public static void main(String[] args) {

    }

    static boolean isValid(String iin) {

        if (iin == null || iin.length() == 0) {
            return false;
        }
        int sum = 0;
        for (int i = iin.length() - 2; i >= 0; i--) {
            char c = iin.charAt(i);
            int n = c - 0x30;

            if ((n >= 0x30 && n <= 0x39)) {
                return false;
            }

            int num = n;
            boolean shouldDobule = iin.length() % 2 == 0 ? i % 2 == 0 : i % 2 == 1;
            if (shouldDobule) {
                num = n * 2;
            }

            sum += num % 10 + num / 10;
        }

        int verifyCode = sum * 9 % 10;
        return verifyCode == (iin.charAt(iin.length() - 1) - 0x30);
    }
}
