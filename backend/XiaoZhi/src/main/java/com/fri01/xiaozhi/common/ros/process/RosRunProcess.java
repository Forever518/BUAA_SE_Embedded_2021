package com.fri01.xiaozhi.common.ros.process;

import com.fri01.xiaozhi.common.ros.RobotConfig;
import com.fri01.xiaozhi.common.util.command.Command;
import com.fri01.xiaozhi.common.util.command.CommandProcess;
import com.fri01.xiaozhi.common.util.command.CommandSequence;
import com.fri01.xiaozhi.common.util.command.CommonLinuxCommand;

/**
 * @author forever518
 * @since 2021-05-11
 */
public class RosRunProcess extends CommandProcess {

    private final String packageName;
    private final String runName;

    public RosRunProcess(String name, String packageName, String runName) {
        super(name, new CommandSequence());
        this.packageName = packageName;
        this.runName = runName;
        this.addCommand(new Command(CommonLinuxCommand.SOURCE, RobotConfig.ROS_SETUP_BASH_FILE));
        this.addCommand(new Command(CommonLinuxCommand.ROS_RUN, packageName, runName));
    }

    public RosRunProcess(String name, String packageName, String runName, String workPath) {
        super(name, new CommandSequence());
        this.packageName = packageName;
        this.runName = runName;
        this.addCommand(new Command(CommonLinuxCommand.CHANGE_DIRECTORY, workPath));
        this.addCommand(new Command(CommonLinuxCommand.SOURCE, RobotConfig.ROS_SETUP_BASH_FILE));
        this.addCommand(new Command(CommonLinuxCommand.ROS_RUN, packageName, runName));
    }

    public String getPackageName() {
        return packageName;
    }

    public String getRunName() {
        return runName;
    }
}
