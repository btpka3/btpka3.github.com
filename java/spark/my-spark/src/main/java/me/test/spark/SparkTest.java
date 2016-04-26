package me.test.spark;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.api.java.function.FlatMapFunction;
import org.apache.spark.api.java.function.Function2;
import org.apache.spark.api.java.function.PairFunction;
import scala.Tuple2;

import java.util.ArrayList;
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
     * 1. 尝试使用 Iterator 作为数据源。
     * 2. 一条记录生成多条
     * 3. 生成的多条记录再分配给其他work执行
     */
    public static void withIterator() {

        SparkConf conf = new SparkConf()
                .setAppName("btpka3")
                .setMaster("local[4]");

        final JavaSparkContext jsc = new JavaSparkContext(conf);

        JavaRDD<Integer> rdd = jsc.parallelize(Arrays.asList(1, 2));
        rdd = rdd.flatMap(
                new FlatMapFunction<Integer, Integer>() {
                    @Override
                    public Iterable<Integer> call(Integer t) throws Exception {
                        return new Iterable<Integer>() {
                            @Override
                            public Iterator<Integer> iterator() {
                                return IntStream.range(t * 100 + 0, t * 100 + 20).iterator();
                            }
                        };
                    }
                }
        );

        // NOTICE: 将数据重新分片，否则仅会在当前worker上执行，不会分给其他worker执行。
        rdd = rdd.repartition(4);
        JavaPairRDD<Integer, List<Integer>> counts = rdd.mapToPair(
                new PairFunction<Integer, Integer, List<Integer>>() {

                    // key = thread id , value = number
                    @Override
                    public Tuple2<Integer, List<Integer>> call(Integer s) {
                        return new Tuple2<Integer, List<Integer>>(
                                Integer.valueOf((int) Thread.currentThread().getId()),
                                Arrays.asList(s));
                    }
                })
                .reduceByKey(new Function2<List<Integer>, List<Integer>, List<Integer>>() {
                    @Override
                    public List<Integer> call(List<Integer> i1, List<Integer> i2) {
                        List<Integer> l = new ArrayList<Integer>();
                        l.addAll(i1);
                        l.addAll(i2);
                        return l;
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
