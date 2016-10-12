
//f = { n ->
//    ((3 / 2 * n - 1 / 2) * (2G**n)) as BigInteger
//}
//
//[1G, 2G, 3G, 4G, 10G, 10000G].each {
//    println "$it -> ${f(it)}"
//}

// key = [digit, n]
// value = digit ^ n
powTabl = [:]



List<BigInteger> narcissistic  (int n){

}



show = {n, list->
    list.each{
        println "$n -> $it"
    }
}


a=[
        ([1,2]): "aaa"
]

b=[]
b<<1
b<<2

c=[1,2]
println "a = $a"
println "b = $b"
println "a[b] = ${a[b]}"
println "a[c] = ${a[c]} "
println "b.is(c) = "+(b.is(c))
println "b == c  = "+(b == c)

//100 ~ 999

println " c  = "+(0..24)
