package com.fri01.xiaozhi.common.util.command;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author forever518
 * @since 2021-05-05
 */
public class Command {

    private List<String> wordList;

    public Command() {
        wordList = new ArrayList<>();
    }

    public Command(String... words) {
        wordList = new ArrayList<>();
        Collections.addAll(wordList, words);
    }

    public void append(String word) {
        wordList.add(word);
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder("");
        if (!wordList.isEmpty()) {
            stringBuilder.append(wordList.get(0));
        }
        for (int i = 1; i < wordList.size(); ++i) {
            stringBuilder.append(" ").append(wordList.get(i));
        }
        return stringBuilder.toString();
    }
}
