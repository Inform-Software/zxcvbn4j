package com.nulabinc.zxcvbn.matchers;

import java.util.HashMap;
import java.util.Map;

public class CustomDictionary {

    private final Dictionary.LineReader lineReader;
    private final String resourcePackagePath;
    private final String[] filenames;

    private volatile Map<String, String[]> frequencyLists = null;

    public CustomDictionary(Dictionary.LineReader lineReader, String resourcePackagePath, String... filenames) {
        this.lineReader = lineReader;
        this.resourcePackagePath = resourcePackagePath;
        this.filenames = filenames;
    }

    public CustomDictionary(String resourcePackagePath, String... filenames) {
        this.lineReader = new Dictionary.DefaultLineReader();
        this.resourcePackagePath = resourcePackagePath;
        this.filenames = filenames;
    }

    public Map<String, String[]> getFrequencyLists() {
        if (frequencyLists == null) {
            synchronized (this) {
                if (frequencyLists == null) {
                    Map<String, String[]> lists = new HashMap<>();
                    for (String filename : filenames) {
                        lists.put(filename, Dictionary.read(lineReader, resourcePackagePath, filename));
                    }
                    frequencyLists = lists;
                }
            }
        }
        return frequencyLists;
    }
}
