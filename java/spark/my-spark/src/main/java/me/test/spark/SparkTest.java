package me.test.spark;

import org.apache.spark.Accumulable;
import org.apache.spark.AccumulableParam;
import org.apache.spark.Accumulator;
import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.api.java.function.FlatMapFunction;
import org.apache.spark.api.java.function.Function;
import org.apache.spark.api.java.function.Function2;
import org.apache.spark.api.java.function.PairFunction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import scala.Tuple2;

import java.security.SecureRandom;
import java.util.*;
import java.util.stream.IntStream;

public class SparkTest {

    static Logger log = LoggerFactory.getLogger(SparkTest.class);

    public static void main(String[] args) {

        //hello();
        //withIterator();
        stopAll();
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
     * 如何快速终止其他worker？
     */
    public static void stopAll() {

        SparkConf conf = new SparkConf()
                .setAppName("btpka3")
                .setMaster("local[4]");

        final JavaSparkContext jsc = new JavaSparkContext(conf);
        final Accumulator<Integer> acc1 = jsc.accumulator(0);
        final Accumulator<Integer> acc2 = jsc.accumulator(0);

        new Thread() {
            public void run() {
                System.out.println("----- started at : " + new Date());
                try {
                    while (acc2.value() == 0) {
                        Thread.sleep(500);
                    }
                    jsc.cancelAllJobs();
                    System.out.println("watching thread exited on success");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    System.out.println("watching thread exited on error");
                }
                System.out.println("----- finished at : " + new Date());
            }
        }.start();

        //final Accumulator<Integer> accMap = jsc.accumulator(new HashMap<String, Integer>(), new MapAccumulator());
        final Accumulable<Map<String, Integer>, Map<String, Integer>> accMap1 = jsc.accumulable(new HashMap<String, Integer>(), new MapAccumulable());
        final Accumulable<Map<String, List>, Map<String, List>> accMap2 = jsc.accumulable(new HashMap<String, List>(), new MapListAccumulable());

        final List<Integer> a = Arrays.asList(0);

        // 准备一组数据，并随机插入，期待总执行时间小于等于25秒
        List<Integer> data = new LinkedList<>();
        for (int i = 0; i < 99; i++) {
            data.add(i);
        }
        SecureRandom r = new SecureRandom();
        int pos = r.nextInt(data.size());
        data.add(pos, -1);


        JavaRDD<Integer> distData = jsc.parallelize(data);

        JavaRDD<Integer> counts = distData.map(new Function<Integer, Integer>() {

            @Override
            public Integer call(Integer v1) throws Exception {
                Thread.sleep(1000);
                acc1.add(1);

                Map<String, Integer> m = new HashMap<String, Integer>();
                m.put(String.valueOf(Thread.currentThread().getId()), 1);
                accMap1.add(m);

                Map<String, List> m2 = new HashMap<String, List>();
                m2.put(String.valueOf(Thread.currentThread().getId()), Arrays.asList(v1));
                accMap2.add(m2);

                if (v1 < 0) {
                    acc2.add(1);
                }
                a.set(0, a.get(0) + 1);
                return v1 + 1;
            }

        });
        System.out.println("=======================================" + Thread.currentThread().getId());
        try {
            System.out.println(counts.toArray());
        } catch (Exception e) {
            e.printStackTrace();
            log.error("toArray err");
        }
        System.out.println(a);
        System.out.println(acc1.value());
        System.out.println("accMap1 = " + accMap1);
        System.out.println("accMap2 = " + accMap2);
        System.out.println("---------------------------------------");
        jsc.stop();
    }

    // NOTICE: MapAccumulator是scala的tratit，并实现了部分方法，是否因此无法被Java类实现？

//    public static class MapAccumulator implements AccumulatorParam<Map<String, Integer>> {
//        private Map<String, Integer> value = new HashMap<String, Integer>();
//
//        @Override
//        public Map addAccumulator(Map<String, Integer> t1, Map<String, Integer> t2) {
//            return addInPlace(t1, t2);
//        }
//
//        @Override
//        public Map addInPlace(Map<String, Integer> t1, Map<String, Integer> t2) {
//            for (Map.Entry<String, Integer> entry : t2.entrySet()) {
//                String key = entry.getKey();
//                Integer value = entry.getValue();
//                Integer i = t1.get(key);
//                if (i == null) {
//                    i = 0;
//                }
//                if (value != null) {
//                    i = i + value;
//                }
//                t1.put(key, i);
//            }
//            return t1;
//        }
//
//        @Override
//        public Map zero(Map initialValue) {
//            return initialValue;
//        }
//    }

    public static class MapAccumulable implements AccumulableParam<Map<String, Integer>, Map<String, Integer>> {
        private Map<String, Integer> value = new HashMap<String, Integer>();

        @Override
        public Map addAccumulator(Map<String, Integer> t1, Map<String, Integer> t2) {
            return addInPlace(t1, t2);
        }

        @Override
        public Map addInPlace(Map<String, Integer> t1, Map<String, Integer> t2) {
            for (Map.Entry<String, Integer> entry : t2.entrySet()) {
                String key = entry.getKey();
                Integer value = entry.getValue();
                Integer i = t1.get(key);
                if (i == null) {
                    i = 0;
                }
                if (value != null) {
                    i = i + value;
                }
                t1.put(key, i);
            }
            return t1;
        }

        @Override
        public Map zero(Map initialValue) {
            return initialValue;
        }
    }


    public static class MapListAccumulable implements AccumulableParam<Map<String, List>, Map<String, List>> {
        private Map<String, Integer> value = new HashMap<String, Integer>();

        @Override
        public Map addAccumulator(Map<String, List> t1, Map<String, List> t2) {
            return addInPlace(t1, t2);
        }

        @Override
        public Map addInPlace(Map<String, List> t1, Map<String, List> t2) {
            for (Map.Entry<String, List> entry : t2.entrySet()) {
                String key = entry.getKey();
                List value = entry.getValue();
                List i = t1.get(key);
                if (i == null) {
                    i = new ArrayList();
                }
                if (value != null) {
                    i.addAll(value);
                }
                t1.put(key, i);
            }
            return t1;
        }

        @Override
        public Map zero(Map initialValue) {
            return initialValue;
        }
    }

}
