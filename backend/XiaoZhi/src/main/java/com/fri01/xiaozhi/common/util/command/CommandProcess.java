package com.fri01.xiaozhi.common.util.command;

import com.fri01.xiaozhi.common.exception.ros.CommandProcessExecutedException;
import com.fri01.xiaozhi.common.ros.process.RosRunProcess;
import com.fri01.xiaozhi.common.util.system.ProcessManager;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Collection;

/**
 * <p>
 * This class is to execute a command and store its process on Linux.
 * The order of the callable methods:
 * - CommandProcess()
 * - executeCommand()
 * - waitForExitAndReadInputStream()
 * - [sigint()]
 * - getProcessInputString()
 * </p>
 *
 * @author forever518
 * @since 2021-05-05
 */
public class CommandProcess {

    private static final String EXECUTOR = "/bin/bash";
    private static final String EXECUTOR_OPTION = "-c";

    private String name;
    private CommandSequence commandSequence;
    private Process process = null;
    private CommandProcessState state = CommandProcessState.NONE;

    private ProcessInputReader processInputReader;

    public CommandProcess(String name, CommandSequence commandSequence) {
        this.name = name;
        this.commandSequence = commandSequence;
    }

    public CommandProcess(String name, Command... commands) {
        this(name, new CommandSequence(commands));
    }

    public CommandProcess(String name, Command command) {
        this(name, new CommandSequence(command));
    }

    public CommandProcess(String name) {
        this(name, new CommandSequence());
    }

    public String[] showRuntimeCommandConfig() {
        return new String[]{EXECUTOR, EXECUTOR_OPTION, commandSequence.toString()};
    }

    public String showCommands() {
        return commandSequence.toString();
    }

    public void addCommand(Command command) {
        commandSequence.append(command);
    }

    public void addCommandAll(Command... commands) {
        for (Command command : commands) {
            commandSequence.append(command);
        }
    }

    public void addCommandAll(Collection<Command> commands) {
        for (Command command : commands) {
            commandSequence.append(command);
        }
    }

    public String getName() {
        return name;
    }

    public synchronized boolean hasProcess() {
        return state != CommandProcessState.NONE;
    }

    public synchronized boolean hasBeenDestroyed() {
        return state == CommandProcessState.DESTROYED;
    }

    public synchronized boolean isAlive() {
        return state == CommandProcessState.ALIVE;
    }

    public synchronized void executeCommand() throws Exception {
        if (state != CommandProcessState.NONE) {
            throw new CommandProcessExecutedException("This object has executed one process.", "后端进程重复执行。");
        }
        String[] runtimeConfig = new String[]{EXECUTOR, EXECUTOR_OPTION, commandSequence.toString()};
        process = Runtime.getRuntime().exec(runtimeConfig);
        state = CommandProcessState.ALIVE;
    }

    public synchronized boolean waitForExitAndReadInputStream(boolean waitForSubThreadEnd)
            throws CommandProcessExecutedException, InterruptedException {
        if (state == CommandProcessState.ALIVE && process.isAlive()) {
            processInputReader = new ProcessInputReader();
            Thread waiter = new Thread(new ProcessWaiter(this));
            Thread reader = new Thread(processInputReader);
            waiter.start();
            reader.start();
            if (waitForSubThreadEnd) {
                waiter.join(10000);
            }
            return true;
        } else if (!process.isAlive()) {
            process.destroy();
            state = CommandProcessState.DESTROYED;
            throw new CommandProcessExecutedException("Process " + name + " start exception," +
                    " please check the correction of command sequence.", "进程未成功启动。");
        }
        return false;
    }

    public boolean waitForExitAndReadInputStream() throws CommandProcessExecutedException, InterruptedException {
        return waitForExitAndReadInputStream(false);
    }

    public synchronized boolean sigint() throws Exception {
        if (state == CommandProcessState.ALIVE) {
            String pid = ProcessManager.getProcessId(process);
            String[] subProcessIds = ProcessManager.getSubProcessIds(pid);
            for (String subProcessId : subProcessIds) {
                ProcessManager.sigintProcessById(subProcessId);
            }
            return true;
        }
        return false;
    }

    public synchronized String getProcessInputString() {
        if (state == CommandProcessState.DESTROYED) {
            return processInputReader.readString;
        }
        return null;
    }

    class ProcessWaiter implements Runnable {

        private final CommandProcess commandProcess;

        public ProcessWaiter(CommandProcess commandProcess) {
            this.commandProcess = commandProcess;
        }

        @Override
        public void run() {
            try {
                process.waitFor();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            synchronized (commandProcess) {
                process.destroy();
                state = CommandProcessState.DESTROYED;
            }
        }
    }

    class ProcessInputReader implements Runnable {

        private BufferedReader bufferedReader;
        private String readString;

        public ProcessInputReader() {
            InputStream inputStream = process.getInputStream();
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, StandardCharsets.UTF_8);
            bufferedReader = new BufferedReader(inputStreamReader);
        }

        @Override
        public void run() {
            String temp;
            StringBuilder stringBuilder = new StringBuilder();
            try {
                while ((temp = bufferedReader.readLine()) != null) {
                    stringBuilder.append(temp).append("\n");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            readString = stringBuilder.toString();
        }
    }
}
