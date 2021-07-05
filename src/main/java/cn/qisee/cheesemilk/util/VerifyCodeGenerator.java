package cn.qisee.cheesemilk.util;

import java.util.Arrays;
import java.util.Random;

public class VerifyCodeGenerator {

    static Random random = new Random();
    public static String generate(int size){
        return Arrays.toString(random.ints(size, 0, 10).toArray()).replaceAll("[\\[, \\]]", "");
    }
}
