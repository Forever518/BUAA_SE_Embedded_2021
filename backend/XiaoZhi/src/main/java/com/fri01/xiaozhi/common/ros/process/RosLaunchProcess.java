package com.fri01.xiaozhi.common.ros.process;

import com.fri01.xiaozhi.common.ros.RobotConfig;
import com.fri01.xiaozhi.common.util.command.Command;
import com.fri01.xiaozhi.common.util.command.CommandProcess;
import com.fri01.xiaozhi.common.util.command.CommandSequence;
import com.fri01.xiaozhi.common.util.command.CommonLinuxCommand;

/**
 * @author forever518
 * @since 2021-05-05
 */
public class RosLaunchProcess extends CommandProcess {

    private final String packageName;
    private final String launchFileName;

    public RosLaunchProcess(String name, String packageName, String launchFileName) {
        super(name, new CommandSequence());
        this.packageName = packageName;
        this.launchFileName = launchFileName;
        this.addCommand(new Command(CommonLinuxCommand.SOURCE, RobotConfig.ROS_SETUP_BASH_FILE));
        this.addCommand(new Command(CommonLinuxCommand.ROS_LAUNCH, packageName, launchFileName));
    }

    public String getPackageName() {
        return packageName;
    }

    public String getLaunchFileName() {
        return launchFileName;
    }
}
