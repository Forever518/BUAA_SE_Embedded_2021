package com.fri01.xiaozhi.controller;

import com.fri01.xiaozhi.common.exception.CommonException;
import com.fri01.xiaozhi.common.exception.map.MapSaveException;
import com.fri01.xiaozhi.common.lang.Result;
import com.fri01.xiaozhi.common.ros.RobotConfig;
import com.fri01.xiaozhi.common.ros.process.RosCommandProcessPool;
import com.fri01.xiaozhi.common.ros.process.RosLaunchProcess;
import com.fri01.xiaozhi.common.ros.process.RosRunProcess;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author fri01
 * @since 2021-05-03
 */
@RestController
@RequestMapping("/ros-map")
public class RosMapController {

    @CrossOrigin
    @PostMapping("/create")
    public Result create() throws CommonException {
        try {
            RosLaunchProcess rosLaunchProcess = new RosLaunchProcess(RobotConfig.ROS_CREATE_MAP_SERVICE_NAME,
                    RobotConfig.ROS_WPB_HOME_TUTORIALS, RobotConfig.ROS_CREATE_MAP_LAUNCH);
            RosCommandProcessPool.getInstance().addProcess(rosLaunchProcess);
            rosLaunchProcess.executeCommand();
            rosLaunchProcess.waitForExitAndReadInputStream();
        } catch (CommonException e) {
            throw e;
        } catch (Exception e) {
            throw new CommonException(e.toString(), "建图服务启动异常。");
        }
        return Result.success("启动建图服务成功。");
    }

    @CrossOrigin
    @PostMapping("/save")
    public Result save() throws CommonException {
        try {
            RosRunProcess rosRunProcess = new RosRunProcess(RobotConfig.ROS_MAP_SAVE_SERVICE_NAME,
                    RobotConfig.ROS_MAP_SERVER, RobotConfig.ROS_MAP_SAVER, RobotConfig.TUTORIAL_HOME);
            RosCommandProcessPool.getInstance().addProcess(rosRunProcess);
            rosRunProcess.executeCommand();
            rosRunProcess.waitForExitAndReadInputStream(true);
            RosCommandProcessPool.getInstance().sigintByName(RobotConfig.ROS_CREATE_MAP_SERVICE_NAME);
        } catch (CommonException e) {
            throw e;
        } catch (Exception e) {
            throw new MapSaveException(e.toString(), "ROS地图保存异常。");
        }
        return Result.success("建立地图完成。");
    }

}
