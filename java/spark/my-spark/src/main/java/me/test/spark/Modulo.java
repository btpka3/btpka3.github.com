package me.test.spark;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.mllib.linalg.Matrices;
import org.apache.spark.mllib.linalg.Matrix;
import org.apache.spark.mllib.linalg.SparseMatrix;
import org.apache.spark.mllib.linalg.Vectors;
import org.apache.spark.mllib.regression.LabeledPoint;
import scala.Tuple2;

import java.util.Arrays;
import java.util.List;

/**
 * Modulo
 */
public class Modulo {

    public static void main1(String[] args) {

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


    public static void main(String[] args) {
        List l = Arrays.asList(
                new LabeledPoint(1.0, Vectors.dense(0.0, 1.1, 0.1)),
                new LabeledPoint(0.0, Vectors.dense(2.0, 1.0, -1.0)),
                new LabeledPoint(0.0, Vectors.dense(2.0, 1.3, 1.0)),
                new LabeledPoint(1.0, Vectors.dense(0.0, 1.2, -0.5))
        );

        System.out.println(l);

        Matrix dm = Matrices.dense(3, 2, new double[]{1.0, 3.0, 5.0, 2.0, 4.0, 6.0});
        System.out.println(dm);

        System.out.println("---------------------------------1");

        Matrix sm = ((SparseMatrix) Matrices.sparse(3, 2, new int[]{0, 1, 3}, new int[]{0, 2, 1}, new double[]{9, 6, 8})).toDense();

        System.out.println(sm);

        System.out.println("---------------------------------");
        System.out.println(Matrices.ones(3, 3));

        System.out.println("---------------------------------3");
        System.out.println(Matrices.eye(3));
        System.out.println("---------------------------------4");
        System.out.println(Matrices.eye(3).toBreeze());
        System.out.println("---------------------------------");
        System.out.println(Matrices.vertcat(new Matrix[]{
                Matrices.ones(3, 3),
                Matrices.eye(3)
        }));

    }

}
