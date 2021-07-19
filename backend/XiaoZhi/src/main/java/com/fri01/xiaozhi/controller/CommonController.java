package com.fri01.xiaozhi.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.fri01.xiaozhi.common.database.TableMapColumn;
import com.fri01.xiaozhi.common.exception.CommonException;
import com.fri01.xiaozhi.common.exception.index.IndexFileManageException;
import com.fri01.xiaozhi.common.exception.index.IndexNotExistsException;
import com.fri01.xiaozhi.common.exception.map.MapFileManageException;
import com.fri01.xiaozhi.common.exception.map.MapNotExistsException;
import com.fri01.xiaozhi.common.exception.service.RobotServiceCommonException;
import com.fri01.xiaozhi.common.exception.service.WebNullPointerException;
import com.fri01.xiaozhi.common.git.process.GitPullProcess;
import com.fri01.xiaozhi.common.git.process.GitStatusProcess;
import com.fri01.xiaozhi.common.lang.Result;
import com.fri01.xiaozhi.common.ros.RobotConfig;
import com.fri01.xiaozhi.common.ros.process.RosCommandProcessPool;
import com.fri01.xiaozhi.common.ros.process.RosLaunchProcess;
import com.fri01.xiaozhi.common.util.system.FileManager;
import com.fri01.xiaozhi.entity.Map;
import com.fri01.xiaozhi.model.NameModel;
import com.fri01.xiaozhi.service.MapService;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.File;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author forever518
 * @since 2021-05-05
 */
@RestController
@RequestMapping("/control")
public class CommonController {

    @Autowired
    MapService mapService;

    /**
     * Return the direct message.
     *
     * @param nameModel contains direct file name.
     * @return string of markdown file.
     * @throws Exception when IOException occurs or get null message from web.
     */
    @CrossOrigin
    @PostMapping("/direct")
    public Result direct(@NonNull NameModel nameModel) throws Exception {
        if (nameModel.hasNullAttribute()) {
            throw new WebNullPointerException("/control/direct: Got null or empty message from web.", "请求空的导引信息。");
        }

        String name = nameModel.getName();
        String ans = FileManager.getFileString(RobotConfig.DIRECT_MARKDOWN_HOME + name + ".md");

        return Result.success("", ans);
    }

    /**
     * After receive map name, check database, locate map and index path, then start service.
     *
     * @param nameModel contains map name.
     * @return Execution Result.
     * @throws Exception then any exception occurs.
     */
    @CrossOrigin
    @PostMapping("/robot-service")
    public Result robotService(@NonNull NameModel nameModel) throws Exception {
        if (nameModel.hasNullAttribute()) {
            throw new WebNullPointerException("/control/robot-service: Got null or empty message from web.", "地图名称不能为空。");
        }

        String mapName = nameModel.getName();
        String mapPath;
        String indexPath;

        // query database.
        try {
            QueryWrapper<Map> mapQueryWrapper = new QueryWrapper<>();
            mapQueryWrapper.eq(TableMapColumn.NAME.value(), mapName);
            Map map = mapService.getOne(mapQueryWrapper);
            if (map == null || map.getMapPath() == null) {
                throw new MapNotExistsException("/control/robot-message: Message of the map named \""
                        + mapName + "\" does not exist.", "所选地图内容信息不存在。");
            }
            if (map.getIndexPath() == null) {
                throw new IndexNotExistsException("Message of the index file " + mapName + ".xml dos not exist.",
                        "所选地图航点信息不存在。");
            }
            mapPath = map.getMapPath();
            indexPath = map.getIndexPath();
        } catch (CommonException e) {
            throw e;
        } catch (Exception e) {
            throw new RobotServiceCommonException(e.toString(), "机器人开启服务异常");
        }

        // move file.
        if (!FileManager.isValidDirectory(mapPath)) {
            throw new MapFileManageException("Target map directory does not exist.",
                    "存储地图执行文件的目录不存在。");
        }
        if (!FileManager.isValidDirectory(indexPath)) {
            throw new IndexFileManageException("Target index directory does not exist.",
                    "存储地图航点文件的目录不存在。");
        }

        try {
            new File(RobotConfig.ROS_MAP_PGM_FILE).delete();
            new File(RobotConfig.ROS_MAP_YAML_FILE).delete();
            FileManager.copyFile(mapPath + mapName + ".pgm", RobotConfig.ROS_MAP_PGM_FILE);
            FileManager.copyFile(mapPath + mapName + ".yaml", RobotConfig.ROS_MAP_YAML_FILE);
        } catch (Exception e) {
            throw new MapFileManageException(e.toString(), "地图执行文件复制过程异常。");
        }

        try {
            new File(RobotConfig.ROS_MAP_INDEX_FILE).delete();
            new File(RobotConfig.ROS_MAP_INDEX_CSV_FILE).delete();
            FileManager.copyFile(indexPath + mapName + ".xml", RobotConfig.ROS_MAP_INDEX_FILE);
            FileManager.copyFile(indexPath + mapName + ".csv", RobotConfig.ROS_MAP_INDEX_CSV_FILE);
        } catch (Exception e) {
            throw new MapFileManageException(e.toString(), "地图航点文件复制过程异常。");
        }

        // launch process.
        try {
            RosLaunchProcess rosLaunchProcess = new RosLaunchProcess(RobotConfig.ROS_BASIC_SERVICE_NAME,
                    RobotConfig.ROS_APP_PACKAGE, RobotConfig.ROS_BASIC_SERVICE_LAUNCH);
            RosCommandProcessPool.getInstance().addProcess(rosLaunchProcess);
            rosLaunchProcess.executeCommand();
            rosLaunchProcess.waitForExitAndReadInputStream();
        } catch (CommonException e) {
            throw e;
        } catch (Exception e) {
            throw new RobotServiceCommonException(e.toString(), "机器人开启服务异常。");
        }
        return Result.success("小智开始为您服务。");
    }

    /**
     * Force to kill the service process.
     *
     * @return Execution Result.
     * @throws CommonException when any exception occurs.
     */
    @CrossOrigin
    @PostMapping("/stop-service")
    public Result stopService() throws CommonException {
        try {
            RosCommandProcessPool.getInstance().sigintByName(RobotConfig.ROS_BASIC_SERVICE_NAME);
        } catch (CommonException e) {
            throw e;
        } catch (Exception e) {
            throw new RobotServiceCommonException(e.toString(), "机器人中断服务异常。");
        }
        return Result.success("服务已结束。");
    }

    /**
     * Update robot service.
     *
     * @return Execution Result.
     */
    @CrossOrigin
    @PostMapping("/update-service")
    public Result updateService() {
        GitPullProcess gitPullProcess = new GitPullProcess(RobotConfig.ROS_UPDATE_SERVICE_PROCESS_NAME, "master");
        try {
            gitPullProcess.executeCommand();
            gitPullProcess.waitForExitAndReadInputStream(true);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Result.success("更新完成。");
    }

    /**
     * Check if need updating.
     *
     * @return Execution Result.
     */
    @CrossOrigin
    @PostMapping("/check-update")
    public Result checkUpdate() {
        GitStatusProcess gitStatusProcess = new GitStatusProcess(RobotConfig.ROS_CHECK_UPDATE_PROCESS_NAME);
        String result = "";
        try {
            gitStatusProcess.executeCommand();
            gitStatusProcess.waitForExitAndReadInputStream(true);
            result = gitStatusProcess.getProcessInputString();
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (result.contains("already")) {
            return Result.success("目前已是最新版本。", false);
        }

        return Result.success("有新的更新可用。", true);
    }
}
