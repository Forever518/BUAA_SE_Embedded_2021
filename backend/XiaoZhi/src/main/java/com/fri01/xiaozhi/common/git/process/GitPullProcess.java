package com.fri01.xiaozhi.common.git.process;

import com.fri01.xiaozhi.common.ros.RobotConfig;
import com.fri01.xiaozhi.common.util.command.Command;
import com.fri01.xiaozhi.common.util.command.CommandProcess;
import com.fri01.xiaozhi.common.util.command.CommandSequence;
import com.fri01.xiaozhi.common.util.command.CommonLinuxCommand;

public class GitPullProcess extends CommandProcess {

    private final String branchName;

    public GitPullProcess(String name, String branchName) {
        super(name, new CommandSequence());
        this.branchName = branchName;
        this.addCommand(new Command(CommonLinuxCommand.CHANGE_DIRECTORY, RobotConfig.ROS_CODE_HOME));
        this.addCommand(new Command(CommonLinuxCommand.GIT, "pull origin", branchName));
        this.addCommand(new Command(CommonLinuxCommand.CATKIN_MAKE));
    }

    public String getBranchName() {
        return branchName;
    }
}
