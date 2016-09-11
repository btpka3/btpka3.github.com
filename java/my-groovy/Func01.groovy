
/*

已知 f(n), n是自然数，
当n=1时，f(n) = 2;
当n>1时，f(n) = 2*f(n-1)+3*2^(n-1)
当 n = 10000 时 ， f(n)是?

参考:
    http://edu.163.com/13/0409/16/8S1JVRU400294LNK.html
*/

f = { n ->
    ((3 / 2 * n - 1 / 2) * (2G**n)) as BigInteger
}

[1G, 2G, 3G, 4G, 10G, 10000G].each {
    println "$it -> ${f(it)}"
}