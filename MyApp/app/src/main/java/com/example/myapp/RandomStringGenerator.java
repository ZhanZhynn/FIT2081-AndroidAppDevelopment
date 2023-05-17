package com.example.myapp;

import java.util.Random;

public class RandomStringGenerator {
    public static final String upper = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";

    public static final String lower = upper.toLowerCase();

    public static final String digits = "0123456789";

    public static final String alphaNummeric = upper + lower + digits;


    public static String generateNewRandomString(int length) {
        char[] buf;
        Random random=new Random();
        if (length < 1) throw new IllegalArgumentException();
        buf = new char[length];
        for (int idx = 0; idx < buf.length; ++idx)
            buf[idx] = alphaNummeric.charAt(random.nextInt(alphaNummeric.length()));
        return new String(buf);
    }
}
