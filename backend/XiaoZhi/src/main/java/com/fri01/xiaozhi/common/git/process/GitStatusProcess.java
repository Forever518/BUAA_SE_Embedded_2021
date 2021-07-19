package com.fri01.xiaozhi.common.git.process;

import com.fri01.xiaozhi.common.ros.RobotConfig;
import com.fri01.xiaozhi.common.util.command.Command;
import com.fri01.xiaozhi.common.util.command.CommandProcess;
import com.fri01.xiaozhi.common.util.command.CommandSequence;
import com.fri01.xiaozhi.common.util.command.CommonLinuxCommand;

public class GitStatusProcess extends CommandProcess {

    public GitStatusProcess(String name) {
        super(name, new CommandSequence());
        this.addCommand(new Command(CommonLinuxCommand.CHANGE_DIRECTORY, RobotConfig.ROS_CODE_HOME));
        this.addCommand(new Command(CommonLinuxCommand.GIT, "status"));
    }

}
