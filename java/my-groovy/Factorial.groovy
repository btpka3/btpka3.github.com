// groovy Factorial.groovy
// 斐波那契数列(Fibonaci)
// [0,1,1,2,3,5,8,...]
// 五大常用算法：分治、动态规划、贪心、回溯、分支限界
class Factorial {

    static void main(String[] args) {

        int n = 38;

        println "------------------------ 递归"
        println "start  : " + new Date();
        println "result : " + factorial(n) + ", count = $count"
        println "end    : " + new Date();

        println "------------------------ 尾递归"
        count = 0
        println "start  : " + new Date();
        println "result : " + factorial1(n) + ", count = $count"
        println "end    : " + new Date();

        println "------------------------ 通项公式"
        println "start  : " + new Date();
        println "result : " + (factorial2(n) as Integer)
        println "end    : " + new Date();

        println "------------------------ 循环"
        println "start  : " + new Date();
        println "result : " + (factorial3(n) as Integer)
        println "end    : " + new Date();
    }

    // 方法执行了多少次
    static def count = 0;

    // 递归
    static factorial(int n, def accu = 1G) {
        if (n <= 2) return accu

        count++;
        return factorial(n - 1) + factorial(n - 2)
    }

    // 尾递归
    static factorial1(int n, def acc1 = 1G, def acc2 = 2G) {
        if (n <= 2) return acc1

        count++;
        return factorial1(n - 1, acc2, acc1 + acc2)
    }

    // 直接通过 通项公式 计算
    static factorial2(int n) {
        def sqrt5 = Math.sqrt(5)
        return 1 / sqrt5 * Math.pow((1 + sqrt5) / 2, n) - Math.pow((1 - sqrt5) / 2, n)
    }

    // 循环 / 动态规划(dynamic programing)???
    static factorial3(int n) {

        def list = new ArrayList(n)
        list[0] = list[1] = 1
        for (int i = 2; i <= n; i++) {
            list[i] = list[i - 2] + list[i - 1]
        }
        return list[n-1]
    }
}