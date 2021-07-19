package com.fri01.xiaozhi.common.util.command;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author forever518
 * @since 2021-05-05
 */
public class CommandSequence {

    private List<Command> commandList;

    public CommandSequence() {
        commandList = new ArrayList<>();
    }

    public CommandSequence(Command... commands) {
        commandList = new ArrayList<>();
        Collections.addAll(commandList, commands);
    }

    public void append(Command command) {
        commandList.add(command);
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder("");
        if (!commandList.isEmpty()) {
            stringBuilder.append(commandList.get(0).toString());
        }
        for (int i = 1; i < commandList.size(); ++i) {
            stringBuilder.append(" && ").append(commandList.get(i).toString());
        }
        return stringBuilder.toString();
    }
}
