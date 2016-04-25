package me.test.spark;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.api.java.function.FlatMapFunction;
import org.apache.spark.api.java.function.Function2;
import org.apache.spark.api.java.function.PairFunction;
import scala.Tuple2;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.stream.IntStream;

public class SparkTest {

    public static void main(String[] args) {

        //hello();
        withIterator();
    }

    /**
     * 第一个学习例子。测试每个线程到底跑了多少个job。
     */
    public static void hello() {

        SparkConf conf = new SparkConf()
                .setAppName("btpka3")
                .setMaster("local[2]");

        JavaSparkContext jsc = new JavaSparkContext(conf);

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


    /**
     * FIXME 尝试使用 Iterator 作为数据源。
     */
    public static void withIterator() {

        SparkConf conf = new SparkConf()
                .setAppName("btpka3")
                .setMaster("local[4]");

        JavaSparkContext jsc = new JavaSparkContext(conf);

        JavaRDD<Integer> rdd = jsc.parallelize(Arrays.asList(1, 2));
        // FIXME： 如何flatMap后再让多worker进行后续的工作？
        rdd = rdd.flatMap(
                new FlatMapFunction<Integer, Integer>() {
                    @Override
                    public Iterable<Integer> call(Integer t) throws Exception {
                        return new Iterable<Integer>() {
                            @Override
                            public Iterator<Integer> iterator() {
                                return IntStream.range(0, 100).iterator();
                            }

//                            @Override
//                            public void forEach(Consumer<? super Integer> action) {
//                            }
//
//                            @Override
//                            public Spliterator<Integer> spliterator() {
//                                return null;
//                            }
                        };
                    }
                }
        );
        JavaPairRDD<Integer, Integer> counts = rdd.mapToPair(
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

    /**
     * 一条记录生成多条
     */
//    public static void oneToMany() {
//
//        SparkConf conf = new SparkConf()
//                .setAppName("btpka3")
//                .setMaster("local[2]");
//
//        JavaSparkContext jsc = new JavaSparkContext(conf);
//
//        JavaRDD<Integer> rdd = jsc.emptyRDD();
//        rdd.flatMap(
//                new FlatMapFunction<Integer, Object>() {
//                    @Override
//                    Iterable<Integer> call(Integer t) throws Exception {
//
//
//                        return new Iterable<Integer>() {
//
//                            @Override
//                            public Iterator<Integer> iterator() {
//                                return null;
//                            }
//
//                            @Override
//                            public void forEach(Consumer<? super Integer> action) {
//
//                            }
//
//                            @Override
//                            public Spliterator<Integer> spliterator() {
//                                return null;
//                            }
//                        };
//                    }
//                }
//        );
//        System.out.println("=======================================");
//        System.out.println(counts.collectAsMap());
//        System.out.println("---------------------------------------");
//        jsc.stop();
//    }


    // FIXME 如何一条变多条？

}
