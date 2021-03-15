package com.ohh.domainname.util;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

public class Algorithm {

    private static final char[] CHARS = new char[37];

    private static String defaultHead = null;
    private static String defaultTail = null;

    static {
        int p = 0;
        CHARS[p++] = '-';
        for (int i = 0; i < 10; i++) {
            CHARS[p++] = (char) ('0' + i);
        }
        for (int i = 0; i < 26; i++) {
            CHARS[p++] = (char) ('a' + i);
        }
    }

    public static void setRound(String head, String tail) {
        defaultHead = head;
        defaultTail = tail;
    }

    public static List<String> getWords(int total) {
        if (Objects.isNull(defaultHead)) {
            char[] chars = new char[total];
            Arrays.fill(chars, '-');
            defaultHead = new String(chars);
        }
        if (Objects.isNull(defaultTail)) {
            char[] chars = new char[total];
            Arrays.fill(chars, 'z');
            defaultTail = new String(chars);
        }
        System.out.println("defaultHead: " + defaultHead + ", defaultTail: " + defaultTail);
        LinkedList<String> list = new LinkedList<>();
        StringBuilder builder = new StringBuilder(total);
        builder.setLength(total);
        getWords(total, 0, list, builder);
        System.out.println("words list size:" + list.size());
        return list;
    }

    public static void getWords(int total, int curr, List<String> list, StringBuilder builder) {
        if (curr == total) {
            String word = builder.toString();
            if (word.compareTo(defaultHead) >= 0 && word.compareTo(defaultTail) <= 0) {
                list.add(word);
            }
            return;
        }
        for (char c : CHARS) {
            builder.setCharAt(curr, c);
            getWords(total, curr + 1, list, builder);
        }
    }
}
