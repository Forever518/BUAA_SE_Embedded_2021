package com.fri01.xiaozhi.common.ros;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.springframework.stereotype.Component;

import java.io.*;
import java.util.Scanner;

import static java.nio.charset.StandardCharsets.UTF_8;

/**
 * <p>
 * This class contains some basic configs, including map location, index location in ROS,
 * java home path, vue home path, etc.
 * </p>
 *
 * @author forever518
 * @since 2021-05-04
 */
@Component
public class RobotConfig {

    public static final String HOME;
    public static final String TUTORIAL_HOME;
    public static final String ROS_HOME;
    public static final String SPRING_URL;

    private RobotConfig() {
    }

    static {
        Scanner scanner = new Scanner(System.in);
        String configFilePath = scanner.nextLine();
        scanner.close();
        File configFile = new File(configFilePath);
        String jsonStr = "";
        try {
            FileReader configFileReader = new FileReader(configFile);
            Reader reader = new InputStreamReader(new FileInputStream(configFile), UTF_8);
            int ch;
            StringBuilder sb = new StringBuilder();
            while ((ch = reader.read()) != -1) {
                sb.append((char) ch);
            }
            configFileReader.close();
            reader.close();
            jsonStr = sb.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        JSONObject jsonObject = JSON.parseObject(jsonStr);
        HOME = (String) jsonObject.get("home");
        TUTORIAL_HOME = HOME + jsonObject.get("tutorial_home");
        ROS_HOME = HOME + jsonObject.get("ros_home");
        SPRING_URL = (String) jsonObject.get("spring_url");
    }

    public static final String ROS_MAP_PGM_FILE = TUTORIAL_HOME + "map.pgm";
    public static final String ROS_MAP_YAML_FILE = TUTORIAL_HOME + "map.yaml";
    public static final String ROS_MAP_INDEX_FILE = HOME + "waypoints.xml";
    public static final String ROS_MAP_INDEX_CSV_FILE = HOME + "index.csv";

    public static final String DIRECT_MARKDOWN_HOME = ROS_HOME + "direct/";
    public static final String ROS_CODE_HOME = ROS_HOME + "fri01_apps/";

    public static final String ROS_MAP_STORE_PATH = ROS_HOME + "assets/map/ros/";
    public static final String ROS_INDEX_STORE_PATH = ROS_HOME + "assets/index/";
    public static final String ROS_VIEW_MAP_STORE_PATH = ROS_HOME + "assets/map/view/";
    public static final String ROS_MAP_URL = SPRING_URL + "map-picture/";

    public static final String ROS_SETUP_BASH_FILE = HOME + "catkin_ws/devel/setup.bash";

    public static final String ROS_VERSION_FILE = DIRECT_MARKDOWN_HOME + "version.dat";

    /*
     * package names.
     */
    public static final String ROS_APP_PACKAGE = "fri01_apps";
    public static final String ROS_WPB_HOME_TUTORIALS = "wpb_home_tutorials";
    public static final String ROS_WATER_PLUS_MAP_TOOLS = "waterplus_map_tools";
    public static final String ROS_MAP_SERVER = "map_server";

    /*
     * launch file names.
     */
    public static final String ROS_BASIC_SERVICE_LAUNCH = "fri01_basic.launch";
    public static final String ROS_CREATE_MAP_LAUNCH = "gmapping.launch";
    public static final String ROS_ADD_WAY_POINT_LAUNCH = "add_waypoint.launch";

    /*
     * run names.
     */
    public static final String ROS_INDEX_SAVER = "wp_saver";
    public static final String ROS_MAP_SAVER = "map_saver -f map";

    /*
     * used in service pool.
     */
    public static final String ROS_BASIC_SERVICE_NAME = "ROS_BASIC_SERVICE";
    public static final String ROS_CREATE_MAP_SERVICE_NAME = "ROS_MAP_CREATE";
    public static final String ROS_MAP_SAVE_SERVICE_NAME = "ROS_MAP_SAVE";
    public static final String ROS_INDEX_MARK_SERVICE_NAME = "ROS_INDEX_MARK";
    public static final String ROS_INDEX_SAVE_SERVICE_NAME = "ROS_INDEX_SAVE";

    public static final String ROS_CHECK_UPDATE_PROCESS_NAME = "ROS_CHECK_UPDATE";
    public static final String ROS_UPDATE_SERVICE_PROCESS_NAME = "ROS_UPDATE_SERVICE";

}
