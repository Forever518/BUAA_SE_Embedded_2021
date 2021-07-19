package com.fri01.xiaozhi.common.ros.process;

import com.fri01.xiaozhi.common.exception.ros.CommandProcessExecutedException;
import com.fri01.xiaozhi.common.util.command.CommandProcess;

import java.util.HashMap;
import java.util.Map;

/**
 * @author forever518
 * @since 2021-05-07
 */
public class RosCommandProcessPool {

    private static RosCommandProcessPool instance = new RosCommandProcessPool();

    private Map<String, CommandProcess> processMap = new HashMap<>();

    private RosCommandProcessPool() {
    }

    public static RosCommandProcessPool getInstance() {
        return instance;
    }

    public void sigintAll() throws Exception {
        for (Map.Entry<String, CommandProcess> entry : processMap.entrySet()) {
            entry.getValue().sigint();
        }
    }

    public void sigintByName(String name) throws Exception {
        CommandProcess commandProcess = processMap.get(name);
        if (commandProcess != null && commandProcess.isAlive()) {
            commandProcess.sigint();
        } else {
            throw new CommandProcessExecutedException("Interrupt process exception.", "中断服务进程异常。");
        }
    }

    public void addProcess(CommandProcess commandProcess) {
        processMap.put(commandProcess.getName(), commandProcess);
    }

    public String queryProcessInput(String name) {
        if (processMap.containsKey(name)) {
            return processMap.get(name).getProcessInputString();
        }
        return null;
    }
}
