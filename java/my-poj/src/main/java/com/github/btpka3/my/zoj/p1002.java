package com.github.btpka3.my.zoj;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.Stack;

public class p1002 {
    public static void main(String[] args) {
        try (Scanner cin = new Scanner(System.in)) {
            while (cin.hasNextLine()) {
                int n = cin.nextInt();
                if (n == 0) {
                    break;
                }
                char[][] charArr = new char[n][];

                for (int i = 0; i < n; i++) {
                    charArr[i] = cin.nextLine().toCharArray();
                }

                int result = calc(charArr);

                System.out.println(result);
            }
        }
    }

    public static int calc(char[][] charArr) {

        List<Pos> maxList = new ArrayList<>();

        Stack<Pos> stack = new Stack<>();
        Pos lastPos = null;
        while (true) {
            Pos pos = findNextBlockhousePos(charArr, lastPos);
            if (pos != null) {
                charArr[pos.row][pos.col] = 'B';
                stack.push(pos);
                lastPos = pos;
                fillForbid(charArr, lastPos);
                calc(charArr, stack, lastPos);

            } else {
                if (stack.size() > maxList.size()) {
                    maxList.clear();
                    maxList.addAll(stack);
                }
            }
        }
    }

    public static int calc(char[][] charArr, Stack<Pos> stack, Pos lastBlockhousePos) {
        return 0;
    }


    public static void fillForbid(char[][] charArr, Pos lastBlockhousePos) {
        int r = lastBlockhousePos.row;
        int c = lastBlockhousePos.col;

        for (int i = r + 1; i < charArr.length; i++) {
            if (charArr[i][c] == 'X') {
                break;
            }
            if (charArr[i][c] == '.') {
                charArr[i][c] = 'F';
            }
        }

        for (int i = c + 1; i < charArr.length; i++) {
            if (charArr[r][i] == 'X') {
                break;
            }
            if (charArr[r][i] == '.') {
                charArr[r][i] = 'F';
            }
        }
    }

    public static Pos findNextBlockhousePos(char[][] charArr, Pos lastBlockhousePos) {
        int r = -1;
        int c = 0;

        if (lastBlockhousePos != null) {
            r = lastBlockhousePos.row;
            c = lastBlockhousePos.col;
        }
        for (int i = r + 1; i < charArr.length; i++) {
            for (int j = c; i < charArr.length; i++) {
                if (charArr[i][j] == '.') {
                    Pos p = new Pos();
                    p.row = i;
                    p.col = j;
                    return p;
                }
            }
        }
        return null;
    }

    public static class Pos {
        int row;
        int col;
    }
}
