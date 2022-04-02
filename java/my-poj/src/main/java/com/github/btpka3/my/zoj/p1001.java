package com.github.btpka3.my.zoj;

import java.util.Scanner;
import java.util.stream.Stream;

public class p1001 {
    public static void main(String[] args) {
        try (Scanner cin = new Scanner(System.in)) {
            while (cin.hasNextLine()) {
                String line = cin.nextLine();
                if (line.length() == 0) {
                    break;
                }
                int sum = Stream.of(line.split(" "))
                        .filter(s -> s.length() != 0)
                        .mapToInt(Integer::valueOf)
                        .sum();
                System.out.println(sum);
            }
        }
    }
}
