package com.fri01.xiaozhi.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.fri01.xiaozhi.common.database.TableMapColumn;
import com.fri01.xiaozhi.common.exception.CommonException;
import com.fri01.xiaozhi.common.exception.map.*;
import com.fri01.xiaozhi.common.exception.service.WebNullPointerException;
import com.fri01.xiaozhi.common.lang.Result;
import com.fri01.xiaozhi.common.ros.RobotConfig;
import com.fri01.xiaozhi.common.util.system.FileManager;
import com.fri01.xiaozhi.entity.Map;
import com.fri01.xiaozhi.model.MapChangeNameModel;
import com.fri01.xiaozhi.model.MapViewModel;
import com.fri01.xiaozhi.model.NameModel;
import com.fri01.xiaozhi.service.MapService;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author fri01
 * @since 2021-05-03
 */
@RestController
@RequestMapping("/map")
public class MapController {

    @Autowired
    MapService mapService;

    /**
     * Clears table `map` in database. please be careful to call this function.
     *
     * @return Execution Result.
     * @throws MapDeleteException if any exception occurs.
     */
    @CrossOrigin
    @PostMapping("/remove-all")
    public Result removeAll() throws MapDeleteException {
        try {
            QueryWrapper<Map> mapQueryWrapper = new QueryWrapper<>();
            mapService.remove(mapQueryWrapper);
            return Result.success("清除地图完成。");
        } catch (Exception e) {
            throw new MapDeleteException(e.toString(), "清除地图过程出现错误。");
        }
    }

    /**
     * List all map messages from database.
     *
     * @return list of map messages.
     * @throws CommonException when no map exists or any other exception occurs.
     */
    @CrossOrigin
    @PostMapping("/display-all")
    public Result displayAll() throws CommonException {
        try {
            QueryWrapper<Map> mapQueryWrapper = new QueryWrapper<>();
            mapQueryWrapper.orderByAsc(TableMapColumn.NAME.value());
            List<Map> list = mapService.list(mapQueryWrapper);
            if (list.isEmpty()) {
                throw new MapNotExistsException("There are no maps.", "暂无地图，请先引导机器人建图。");
            }
            List<MapViewModel> returnList = new ArrayList<>();
            for (Map map : list) {
                MapViewModel mapViewModel = new MapViewModel();
                mapViewModel.setMapName(map.getMapName());
                mapViewModel.setMapUrl(RobotConfig.ROS_MAP_URL + map.getMapName() + ".png");
                returnList.add(mapViewModel);
            }
            return Result.success(returnList);
        } catch (MapNotExistsException e) {
            throw e;
        } catch (Exception e) {
            throw new CommonException(e.toString(), "检索地图数据库过程异常。");
        }
    }

    /**
     * Delete a map: remove database's message and files.
     *
     * @param nameModel contains the name of the map to be deleted.
     * @return Execution Result.
     * @throws Exception when any error occurs.
     */
    @CrossOrigin
    @PostMapping("/remove")
    public Result remove(@NonNull NameModel nameModel) throws Exception {
        // judge message from web.
        if (nameModel.hasNullAttribute()) {
            throw new WebNullPointerException("/map/remove: Got null or empty message from web.", "待删除地图名称不能为空。");
        }

        String name = nameModel.getName();

        try {
            QueryWrapper<Map> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq(TableMapColumn.NAME.value(), name);
            Map map = mapService.getOne(queryWrapper);

            if (map == null) {
                throw new MapNotExistsException("The map named " + name + " does not exist.", "地图不存在。");
            }

            // delete files.
            String mapPath = map.getMapPath();
            String mapShowPath = map.getMapShowPath();
            String mapIndexPath = map.getIndexPath();

            new File(mapPath + name + ".pgm").delete();
            new File(mapPath + name + ".yaml").delete();
            new File(mapShowPath + name + ".png").delete();
            if (mapIndexPath != null) {
                new File(mapIndexPath + name + ".xml").delete();
                new File(mapIndexPath + name + ".csv").delete();
            }

            // delete database.
            mapService.remove(queryWrapper);
        } catch (CommonException e) {
            throw e;
        } catch (Exception e) {
            throw new MapDeleteException(e.toString(), "删除地图异常。");
        }

        return Result.success("删除地图成功。");
    }

    /**
     * Change map's name to a new one. First judge if previous map or new map exists.
     * Rename the name of old files to new name, and update database.
     *
     * @param mapChangeNameModel contains old map name and new map name.
     * @return Execution Result.
     * @throws Exception when any error occurs.
     */
    @CrossOrigin
    @PostMapping("/change")
    public Result change(@NonNull MapChangeNameModel mapChangeNameModel) throws Exception {
        // judge message from web.
        if (mapChangeNameModel.hasNullAttribute()) {
            throw new WebNullPointerException("/map/change: Got null or empty message from web.", "地图名称不能为空。");
        }

        String mapPreviousName = mapChangeNameModel.getMapPreviousName();
        String mapNewName = mapChangeNameModel.getMapNewName();

        try {
            // judge previous map exists.
            QueryWrapper<Map> mapQueryWrapper = new QueryWrapper<>();
            mapQueryWrapper.eq(TableMapColumn.NAME.value(), mapPreviousName);
            Map tempMap = mapService.getOne(mapQueryWrapper);
            if (tempMap == null) {
                throw new MapNotExistsException("Previous map doesn't exist.", "要修改的地图不存在。");
            }

            // judge duplicate.
            mapQueryWrapper = new QueryWrapper<>();
            mapQueryWrapper.eq(TableMapColumn.NAME.value(), mapNewName);
            Map newMap = mapService.getOne(mapQueryWrapper);
            if (newMap != null) {
                throw new MapNameDuplicateException("Duplicated map name.", "地图名称重复。");
            }

            // change file name.
            String mapPath = tempMap.getMapPath();
            String mapShowPath = tempMap.getMapShowPath();
            String mapIndexPath = tempMap.getIndexPath();

            new File(mapPath + mapNewName + ".pgm").delete();
            new File(mapPath + mapNewName + ".yaml").delete();
            new File(mapShowPath + mapNewName + ".png").delete();
            FileManager.renameFile(mapPath + mapPreviousName + ".pgm", mapPath + mapNewName + ".pgm");
            FileManager.renameFile(mapPath + mapPreviousName + ".yaml", mapPath + mapNewName + ".yaml");
            FileManager.renameFile(mapShowPath + mapPreviousName + ".png", mapShowPath + mapNewName + ".png");

            if (mapIndexPath != null) {
                new File(mapIndexPath + mapNewName + ".xml").delete();
                new File(mapIndexPath + mapNewName + ".csv").delete();
                FileManager.renameFile(mapIndexPath + mapPreviousName + ".xml", mapIndexPath + mapNewName + ".xml");
                FileManager.renameFile(mapIndexPath + mapPreviousName + ".csv", mapIndexPath + mapNewName + ".csv");
            }

            // change database.
            mapQueryWrapper = new QueryWrapper<>();
            mapQueryWrapper.eq(TableMapColumn.NAME.value(), mapPreviousName);
            tempMap.setMapName(mapNewName);
            mapService.remove(mapQueryWrapper);
            mapService.save(tempMap);
        } catch (CommonException e) {
            throw e;
        } catch (Exception e) {
            throw new MapRenameException(e.toString(), "重命名地图异常。");
        }

        return Result.success("重命名成功。");
    }

    /**
     * Rename the map which created in CREATE service, here are a few steps:
     * - Judge duplicate map name.
     * - Judge if source pgm map file is valid.
     * - Judge if destination map directory and show map directory exist.
     * - Delete original file anyway.
     * - Copy pgm map file to target directory.
     * - Convert pgm image to png image.
     * - Store data to table `map` of database.
     *
     * @param nameModel is a package of map name.
     * @return Execution Result.
     * @throws Exception when any exception occurs.
     */
    @CrossOrigin
    @PostMapping("/insert")
    public Result insert(@NonNull NameModel nameModel) throws Exception {
        // judge message from web.
        if (nameModel.hasNullAttribute()) {
            throw new WebNullPointerException("/map/insert: Got null or empty message from web.", "地图名称不能为空。");
        }

        String mapName = nameModel.getName();
        String mapPath = RobotConfig.ROS_MAP_STORE_PATH;
        String mapShowPath = RobotConfig.ROS_VIEW_MAP_STORE_PATH;

        try {
            // judge duplicate.
            QueryWrapper<Map> mapQueryWrapper = new QueryWrapper<>();
            mapQueryWrapper.eq(TableMapColumn.NAME.value(), mapName);
            Map tempMap = mapService.getOne(mapQueryWrapper);
            if (tempMap != null) {
                throw new MapNameDuplicateException("Duplicated map name.", "地图名称重复。"); // TODO: Ask if rename.
            }

            // judge valid source pgm map file.
            if (!FileManager.isValidFile(RobotConfig.ROS_MAP_PGM_FILE)
                    || !FileManager.isValidFile(RobotConfig.ROS_MAP_YAML_FILE)) {
                throw new MapFileManageException("Source pgm file does not exists.",
                        "默认工作地图文件不存在，请再次确认建图已成功完成。");
            }

            // judge valid path.
            if (!FileManager.isValidDirectory(mapPath)) {
                FileManager.makeDirectory(mapPath);
            }
            if (!FileManager.isValidDirectory(mapShowPath)) {
                FileManager.makeDirectory(mapShowPath);
            }

            // if file exists, delete it anyway and print message to System.err.
            boolean mapDeleteResult = new File(mapPath + mapName + ".pgm").delete();
            boolean mapYamlDeleteResult = new File(mapPath + mapName + ".yaml").delete();
            boolean showMapDeleteResult = new File(mapShowPath + mapName + ".png").delete();
            if (mapDeleteResult || mapYamlDeleteResult || showMapDeleteResult) {
                System.err.println("[MapController] " + new Date().toString() + " cover original map file: " + mapName);
            }

            // copy map file to target.
            try {
                FileManager.copyFile(RobotConfig.ROS_MAP_PGM_FILE, mapPath + mapName + ".pgm");
                FileManager.copyFile(RobotConfig.ROS_MAP_YAML_FILE, mapPath + mapName + ".yaml");
            } catch (Exception e) {
                throw new MapFileManageException(e.toString(), "地图执行文件存储过程异常。");
            }

            // convert pgm to png image.
            try {
                FileManager.convertPgmToPng(RobotConfig.ROS_MAP_PGM_FILE, mapShowPath + mapName + ".png");
            } catch (Exception e) {
                throw new MapFileManageException(e.toString(), "地图图片转储过程异常。");
            }

            // store messages to database.
            Map map = new Map();
            map.setMapName(mapName);
            map.setMapPath(mapPath);
            map.setMapShowPath(mapShowPath);
            mapService.save(map);

            return Result.success("命名地图成功：" + mapName);
        } catch (CommonException e) {
            throw e;
        } catch (Exception e) {
            throw new CommonException(e.toString(), "修改地图信息过程异常。");
        }
    }

}
