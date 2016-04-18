package me.test.spark;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.api.java.function.Function2;
import org.apache.spark.api.java.function.PairFunction;
import org.apache.spark.streaming.api.java.JavaDStream;
import scala.Tuple2;

import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;

public class SparkTest {

    public static void main(String[] args) {

        SparkConf conf = new SparkConf()
                .setAppName("btpka3")
                .setMaster("local[2]");
        //.setMaster("spark://z:7077");

        JavaSparkContext jsc = new JavaSparkContext(conf);

//        IntStream.range(1, 100).forEach(
//                nbr -> System.out.println(nbr)
//        );
        List<Integer> data = Arrays.asList(1, 2, 3, 4, 5, 1, 1, 2);
        JavaRDD<Integer> distData = jsc.parallelize(data);
        JavaPairRDD<Integer, Integer> counts = distData.mapToPair(
                new PairFunction<Integer, Integer, Integer>() {
                    @Override
                    public Tuple2<Integer, Integer> call(Integer s) {
                        //return new Tuple2<Integer, Integer>(s, 1);
                        return new Tuple2<Integer, Integer>((int) Thread.currentThread().getId(), 1);
                    }
                })
                .reduceByKey(new Function2<Integer, Integer, Integer>() {
                    @Override
                    public Integer call(Integer i1, Integer i2) {
                        return i1 + i2;
                    }
                });
        System.out.println("=======================================");
        System.out.println(counts.collectAsMap());
        System.out.println("---------------------------------------");
        jsc.stop();
    }
}
