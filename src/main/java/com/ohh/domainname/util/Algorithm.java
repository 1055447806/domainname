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

    private static List<String> getWords(int total) {
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

    private static void getWords(int total, int curr, List<String> list, StringBuilder builder) {
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

    public static Builder builder() {
        return Builder.getInstance();
    }

    public static class Builder {

        private static final Builder instance = new Builder();

        private Builder() {
        }

        private static Builder getInstance() {
            return instance;
        }

        public Builder setHead(String head) {
            defaultHead = head;
            return this;
        }

        public Builder setTail(String tail) {
            defaultTail = tail;
            return this;
        }

        public List<String> getWords(int total) {
            return Algorithm.getWords(total);
        }
    }
}
