package me.test.spark;

import org.apache.spark.SparkConf;

public class SparkTest {

    public static void main(String[] args) {
        String appName = "l";

        SparkConf conf = new SparkConf().setAppName(appName).setMaster(master);
        JavaSparkContext sc = new JavaSparkContext(conf);
    }
}
