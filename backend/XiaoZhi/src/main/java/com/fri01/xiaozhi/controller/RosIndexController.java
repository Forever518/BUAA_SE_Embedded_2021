package com.fri01.xiaozhi.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.csvreader.CsvWriter;
import com.fri01.xiaozhi.common.database.TableMapColumn;
import com.fri01.xiaozhi.common.exception.CommonException;
import com.fri01.xiaozhi.common.exception.index.IndexChangeException;
import com.fri01.xiaozhi.common.exception.index.IndexFileManageException;
import com.fri01.xiaozhi.common.exception.index.IndexNotExistsException;
import com.fri01.xiaozhi.common.exception.index.IndexSaveException;
import com.fri01.xiaozhi.common.exception.map.MapFileManageException;
import com.fri01.xiaozhi.common.exception.map.MapNotExistsException;
import com.fri01.xiaozhi.common.exception.ros.CommandProcessExecutedException;
import com.fri01.xiaozhi.common.exception.service.RobotServiceCommonException;
import com.fri01.xiaozhi.common.exception.service.WebNullPointerException;
import com.fri01.xiaozhi.common.lang.Result;
import com.fri01.xiaozhi.common.ros.RobotConfig;
import com.fri01.xiaozhi.common.ros.process.RosCommandProcessPool;
import com.fri01.xiaozhi.common.ros.process.RosLaunchProcess;
import com.fri01.xiaozhi.common.ros.process.RosRunProcess;
import com.fri01.xiaozhi.common.util.system.FileManager;
import com.fri01.xiaozhi.entity.Map;
import com.fri01.xiaozhi.model.IndexRenameModel;
import com.fri01.xiaozhi.model.NameModel;
import com.fri01.xiaozhi.service.MapService;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

/**
 * @author forever518
 * @since 2021-05-11
 */
@RestController
@RequestMapping("/ros-index")
public class RosIndexController {

    @Autowired
    MapService mapService;

    /**
     * After choosing a map, start mark service to mark some target indexes.
     *
     * @param nameModel contains map name.
     * @return Execution Result.
     * @throws Exception when any exception occurs.
     */
    @CrossOrigin
    @PostMapping("/mark")
    public Result mark(@NonNull NameModel nameModel) throws Exception {
        if (nameModel.hasNullAttribute()) {
            throw new WebNullPointerException("Got null or empty message from web.", "???????????????????????????");
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
                throw new MapNotExistsException("Message of the map named \"" + mapName + "\" does not exist.",
                        "????????????????????????????????????");
            }
            mapPath = map.getMapPath();
            indexPath = map.getIndexPath();
        } catch (CommonException e) {
            throw e;
        } catch (Exception e) {
            throw new RobotServiceCommonException(e.toString(), "???????????????????????????");
        }

        // copy map pgm and yaml to ros path.
        if (!FileManager.isValidDirectory(mapPath)) {
            throw new MapFileManageException("Target map directory does not exist.",
                    "?????????????????????????????????????????????");
        }

        try {
            new File(RobotConfig.ROS_MAP_PGM_FILE).delete();
            new File(RobotConfig.ROS_MAP_YAML_FILE).delete();
            new File(RobotConfig.ROS_MAP_INDEX_FILE).delete();
            FileManager.copyFile(mapPath + mapName + ".pgm", RobotConfig.ROS_MAP_PGM_FILE);
            FileManager.copyFile(mapPath + mapName + ".yaml", RobotConfig.ROS_MAP_YAML_FILE);
            if (indexPath != null && FileManager.isValidFile(indexPath + mapName + ".xml")) {
                FileManager.copyFile(indexPath + mapName + ".xml", RobotConfig.ROS_MAP_INDEX_FILE);
            }
        } catch (Exception e) {
            throw new MapFileManageException(e.toString(), "???????????????????????????????????????");
        }

        try {
            RosLaunchProcess rosLaunchProcess = new RosLaunchProcess(RobotConfig.ROS_INDEX_MARK_SERVICE_NAME,
                    RobotConfig.ROS_WATER_PLUS_MAP_TOOLS, RobotConfig.ROS_ADD_WAY_POINT_LAUNCH);
            RosCommandProcessPool.getInstance().addProcess(rosLaunchProcess);
            rosLaunchProcess.executeCommand();
            rosLaunchProcess.waitForExitAndReadInputStream();
        } catch (CommonException e) {
            throw e;
        } catch (Exception e) {
            throw new CommonException(e.toString(), "???????????????????????????");
        }
        return Result.success("?????????????????????????????????");
    }

    /**
     * Save current Rviz index xml file.
     *
     * @return Execution Result.
     * @throws Exception when any exception occurs.
     */
    @CrossOrigin
    @PostMapping("/save")
    public Result save() throws Exception {
        try {
            RosRunProcess rosRunProcess = new RosRunProcess(RobotConfig.ROS_INDEX_SAVE_SERVICE_NAME,
                    RobotConfig.ROS_WATER_PLUS_MAP_TOOLS, RobotConfig.ROS_INDEX_SAVER);
            RosCommandProcessPool.getInstance().addProcess(rosRunProcess);
            rosRunProcess.executeCommand();
            rosRunProcess.waitForExitAndReadInputStream(true);
        } catch (CommonException e) {
            throw e;
        } catch (Exception e) {
            throw new IndexSaveException(e.toString(), "ROS???????????????????????????");
        }
        return Result.success("ROS?????????????????????");
    }

    /**
     * Get index name list from the index xml file.
     *
     * @return Execusion Result.
     * @throws CommonException when any exception occurs.
     */
    @CrossOrigin
    @PostMapping("/count")
    public Result count() throws CommonException {
        DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();

        if (!FileManager.isValidFile(RobotConfig.ROS_MAP_INDEX_FILE)) {
            throw new IndexFileManageException("Source xml file does not exists.",
                    "??????????????????????????????????????????????????????????????????1????????????");
        }

        try {
            DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
            Document document = documentBuilder.parse(new File(RobotConfig.ROS_MAP_INDEX_FILE));
            NodeList nodeList = document.getElementsByTagName("Name");
            if (nodeList == null || nodeList.getLength() == 0) {
                throw new IndexNotExistsException("Index count is 0.", "??????????????????");
            }
            List<String> indexes = new ArrayList<>();
            for (int i = 0; i < nodeList.getLength(); ++i) {
                indexes.add(nodeList.item(i).getFirstChild().getNodeValue());
            }
            return Result.success("", indexes);
        } catch (CommonException e) {
            throw e;
        } catch (Exception e) {
            throw new CommonException(e.toString(), "?????????????????????????????????");
        }
    }

    /**
     * Rename the indexes marked recently.
     *
     * @param indexRenameModel contains map name, map index path and the dictionary of index.
     * @return Execution Result.
     * @throws Exception when any exception occurs.
     */
    @CrossOrigin
    @PostMapping("/rename-mark")
    public Result renameMark(@NonNull IndexRenameModel indexRenameModel) throws Exception {
        if (indexRenameModel.hasNullAttribute()) {
            throw new WebNullPointerException("Got null or empty message from web.", "???????????????????????????");
        }
        String mapName = indexRenameModel.getName();
        String indexPath = RobotConfig.ROS_INDEX_STORE_PATH;
        List<String> labels = indexRenameModel.getLabels();
        List<String> names = indexRenameModel.getNames();

        DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
        Document document = documentBuilder.parse(new File(RobotConfig.ROS_MAP_INDEX_FILE));
        NodeList nodeList = document.getElementsByTagName("Name");
        if (labels.size() != nodeList.getLength() || names.size() != nodeList.getLength()) {
            throw new IndexChangeException("The number of parameters and index file don't match.",
                    "????????????????????????????????????????????????");
        }

        // check if database has map named mapName.
        try {
            QueryWrapper<Map> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq(TableMapColumn.NAME.value(), mapName);
            Map map = mapService.getOne(queryWrapper);
            if (map == null) {
                throw new MapNotExistsException("The map named " + mapName + " does not exist.", "???????????????????????????");
            }
        } catch (CommonException e) {
            throw e;
        } catch (Exception e) {
            throw new IndexChangeException(e.toString(), "?????????????????????");
        }

        // check validity of index path.
        if (!FileManager.isValidDirectory(indexPath)) {
            FileManager.makeDirectory(indexPath);
        }

        // product file.
        try {
            new File(indexPath + mapName + ".xml").delete();
            FileManager.copyFile(RobotConfig.ROS_MAP_INDEX_FILE, indexPath + mapName + ".xml");
            CsvWriter csvWriter = new CsvWriter(indexPath + mapName + ".csv", ',', StandardCharsets.UTF_8);
            String[] writeList = {"label", "name"};
            csvWriter.writeRecord(writeList);
            for (int i = 0; i < labels.size(); ++i) {
                writeList[0] = labels.get(i);
                writeList[1] = names.get(i);
                csvWriter.writeRecord(writeList);
            }
            csvWriter.close();
        } catch (Exception e) {
            throw new IndexFileManageException("Index file copy exception.", "?????????????????????????????????");
        }

        // store message to database.
        try {
            QueryWrapper<Map> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq(TableMapColumn.NAME.value(), mapName);
            Map map = mapService.getOne(queryWrapper);
            map.setIndexPath(indexPath);
            mapService.saveOrUpdate(map);
        } catch (Exception e) {
            throw new IndexChangeException("Database manage exception.", "????????????????????????????????????");
        }

        return Result.success("????????????????????????");
    }

    @CrossOrigin
    @PostMapping("/finish-mark")
    public Result finishMark() throws CommonException {
        try {
            RosCommandProcessPool.getInstance().sigintByName(RobotConfig.ROS_INDEX_MARK_SERVICE_NAME);
        } catch (CommonException e) {
            throw e;
        } catch (Exception e) {
            throw new CommandProcessExecutedException(e.toString(), "?????????????????????????????????");
        }
        return Result.success("?????????????????????");
    }

}
