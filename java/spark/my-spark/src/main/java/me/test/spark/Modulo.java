package me.test.spark;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import scala.Tuple2;

import java.util.Arrays;

/**
 * Modulo
 */
public class Modulo {

    public static void main(String[] args) {

        SparkConf conf = new SparkConf()
                .setAppName("btpka3")
                .setMaster("local[2]");

        JavaSparkContext sc = new JavaSparkContext(conf);

        JavaRDD<String> rdd1 = sc.parallelize(Arrays.asList("a", "b", "c"));
        JavaRDD<String> rdd2 = sc.parallelize(Arrays.asList("1", "2", "3"));
        JavaRDD<String> rdd3 = sc.parallelize(Arrays.asList("x", "y", "z"));

        JavaPairRDD<String, String> s1 = rdd1.cartesian(rdd2);
        JavaPairRDD<Tuple2<String, String>, String> s2 = s1.cartesian(rdd3);
        System.out.println("=======================================");
        System.out.println(s1.collect());
        System.out.println(s2.collect());
        System.out.println("---------------------------------------");
        sc.stop();
    }

}
