/*********************************************************************
* Software License Agreement (BSD License)
* 
*  Copyright (c) 2017-2020, Waterplus http://www.6-robot.com
*  All rights reserved.
* 
*  Redistribution and use in source and binary forms, with or without
*  modification, are permitted provided that the following conditions
*  are met:
* 
*   * Redistributions of source code must retain the above copyright
*     notice, this list of conditions and the following disclaimer.
*   * Redistributions in binary form must reproduce the above
*     copyright notice, this list of conditions and the following
*     disclaimer in the documentation and/or other materials provided
*     with the distribution.
*   * Neither the name of the WaterPlus nor the names of its
*     contributors may be used to endorse or promote products derived
*     from this software without specific prior written permission.
* 
*  THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
*  "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT
*  LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS
*  FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE
*  COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT,
*  FOOTPRINTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING,
*  BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
*  LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER
*  CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT
*  LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN
*  ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
*  POSSIBILITY OF SUCH DAMAGE.
*********************************************************************/
/* @author Kong Xianghao and Peng Weikun                            */
#include <ros/ros.h>
#include <std_msgs/String.h>
#include "sensor_msgs/Imu.h"
#include "wpb_home_tutorials/Follow.h"
#include <geometry_msgs/Twist.h>
#include "xfyun_waterplus/IATSwitch.h"
#include <sound_play/SoundRequest.h>
#include "wpb_home_tutorials/Follow.h"
#include <move_base_msgs/MoveBaseAction.h>
#include <actionlib/client/simple_action_client.h>
#include <waterplus_map_tools/Waypoint.h>
#include <waterplus_map_tools/GetWaypointByName.h>
#include <tf/transform_listener.h>
#include <geometry_msgs/PoseStamped.h>

using namespace std;

#define STATE_READY     0
#define STATE_FOLLOW    1
#define STATE_ASK       2
#define STATE_ASK2       3
#define STATE_GOTO      4
#define STATE_GRAB      5
#define STATE_COMEBACK  6
#define STATE_PASSWAIT  7
#define STATE_PASS      8
#define STATE_WEATHER   9
#define STATE_CHAT      10
#define STATE_SLEEP     11
#define STATE_SLEEP2    12

typedef actionlib::SimpleActionClient<move_base_msgs::MoveBaseAction> MoveBaseClient;
static string strGoto;
static std_msgs::String spk_msg;
static ros::Publisher spk_pub;
static ros::Publisher vel_pub;
static string strToSpeak = "";
static string strKeyWord = "";
static ros::ServiceClient clientIAT;
static xfyun_waterplus::IATSwitch srvIAT;
static ros::ServiceClient cliGetWPName;
static waterplus_map_tools::GetWaypointByName srvName;
static ros::Publisher add_waypoint_pub;
static ros::ServiceClient follow_start;
static ros::ServiceClient follow_stop;
static ros::ServiceClient follow_resume;
static wpb_home_tutorials::Follow srvFlw;
static ros::Publisher behaviors_pub;
static std_msgs::String behavior_msg;

static ros::Subscriber grab_result_sub;
static ros::Subscriber pass_result_sub;
static bool bGrabDone;
static bool bGrabFailed;
static bool bPassDone;

static int nState = STATE_READY;
static int nDelay = 0;

static vector<string> arKeyword;
static vector<string> arKeywordIndex;
static vector<string> arObjectWant;
static vector<string> arObjectWantIndex;
static int nIndexWant = 0;

static bool fallSpeaking = false;
static string chatMsg;
static string passDst;

void logErr(char* errMsg) {
    time_t t_cur;
    time(&t_cur);
    struct tm *p = localtime(&t_cur);
    FILE *f = fopen("/home/robot/catkin_ws/src/2021_embedded/fri_01/fri01_apps/error.log", "w+");
    fprintf(f, "[%d/%d/%d %d:%d:%d] %s\n", 1900 + p->tm_year, 1 + p->tm_mon, p->tm_mday, p->tm_hour, p->tm_min, p->tm_sec, errMsg);
    fclose(f);
}

// 添加航点关键词
void InitKeyword()
{
    FILE *wp = fopen("/home/robot/index.csv", "r");
    int tmpIndex;
    char tmpName[128];
    string tmpStr;
    stringstream tempSs;
    fscanf(wp, "%s", &tmpName);
    arKeyword.push_back("start");   //机器人开始启动的地点,最后要回去
    arKeywordIndex.push_back("start");
    while (fscanf(wp, "%d,%s", &tmpIndex, &tmpName) != EOF) {
        arKeyword.push_back(string(tmpName));
        tempSs << tmpIndex;
        tempSs >> tmpStr;
        arKeywordIndex.push_back(tmpStr);
        tempSs.clear();
        // printf("%s %s\n", tmpStr.c_str(), tmpName);
    }
    fclose(wp);
}

// 从句子里找arKeyword里存在的关键词
static std::pair<string, string> FindKeyword(string inSentence)
{
    string res = "", resIndex = "";
    int nSize = arKeyword.size();
    for(int i=0;i<nSize;i++)
    {
        int nFindIndex = inSentence.find(arKeyword[i]);
        if(nFindIndex >= 0)
        {
            res = arKeyword[i];
            resIndex = arKeywordIndex[i];
            break;
        }
    }
    return std::make_pair(res, resIndex);
}

// 从句子里找多个arKeyword关键词
static string FindMultiKeyword(string inSentence)
{
    string res = "";
    int nSize = arKeyword.size();
    for(int i=0;i<nSize;i++)
    {
        int nFindIndex = inSentence.find(arKeyword[i]);
        if(nFindIndex >= 0)
        {
            arObjectWant.push_back(arKeyword[i]);
            arObjectWantIndex.push_back(arKeywordIndex[i]);
        }
    }
    int nNumObject = arObjectWant.size();
    for(int i=0;i<nNumObject;i++)
    {
        res += arObjectWant[i];
        res += " , ";
    }
    return res;
}

// 将机器人当前位置保存为新航点
void AddNewWaypoint(string inStr)
{
    tf::TransformListener listener;
    tf::StampedTransform transform;
    try
    {
        listener.waitForTransform("/map","/base_footprint",  ros::Time(0), ros::Duration(10.0) );
        listener.lookupTransform("/map","/base_footprint", ros::Time(0), transform);
    }
    catch (tf::TransformException &ex) 
    {
        ROS_ERROR("[lookupTransform] %s",ex.what());
        return;
    }

    float tx = transform.getOrigin().x();
    float ty = transform.getOrigin().y();
    tf::Stamped<tf::Pose> p = tf::Stamped<tf::Pose>(tf::Pose(transform.getRotation() , tf::Point(tx, ty, 0.0)), ros::Time::now(), "map");
    geometry_msgs::PoseStamped new_pos;
    tf::poseStampedTFToMsg(p, new_pos);

    waterplus_map_tools::Waypoint new_waypoint;
    new_waypoint.name = inStr;
    new_waypoint.pose = new_pos.pose;
    add_waypoint_pub.publish(new_waypoint);

    ROS_WARN("[New Waypoint] %s ( %.2f , %.2f )" , new_waypoint.name.c_str(), tx, ty);
}

// 语音说话
void Speak(string inStr)
{
    spk_msg.data = inStr;
    spk_pub.publish(spk_msg);
    ros::spinOnce();
}

// 跟随模式开关
static void FollowSwitch(bool inActive, float inDist)
{
    if(inActive == true)
    {
        srvFlw.request.thredhold = inDist;
        if (!follow_start.call(srvFlw))
        {
            ROS_WARN("[CActionManager] - follow start failed...");
        }
    }
    else
    {
        if (!follow_stop.call(srvFlw))
        {
            ROS_WARN("[CActionManager] - failed to stop following...");
        }
    }
}

// 物品抓取模式开关
static void GrabSwitch(bool inActive)
{
    if(inActive == true)
    {
        behavior_msg.data = "grab start";
        behaviors_pub.publish(behavior_msg);
    }
    else
    {
        behavior_msg.data = "grab stop";
        behaviors_pub.publish(behavior_msg);
    }
}

// 物品递给开关
static void PassSwitch(bool inActive)
{
    if(inActive == true)
    {
        behavior_msg.data = "pass start";
        behaviors_pub.publish(behavior_msg);
    }
    else
    {
        behavior_msg.data = "pass stop";
        behaviors_pub.publish(behavior_msg);
    }
}

// 语音识别结果处理函数
void KeywordCB(const std_msgs::String::ConstPtr & msg)
{
    printf("------ Keyword = %s ------",msg->data.c_str());
    if(nState == STATE_FOLLOW)
    {
        // 从识别结果句子中查找物品（航点）关键词
        std::pair<string, string> res = FindKeyword(msg->data);
        string strKeyword = res.first;
        int nLenOfKW = strlen(strKeyword.c_str());
        if(nLenOfKW > 0)
        {
            // 发现物品（航点）关键词
            AddNewWaypoint(res.second);
            string strSpeak = res.second + " . OK. I have memoried. Next one , please"; 
            Speak(strSpeak);
        }

        // 停止跟随的指令
        int nFindIndex = msg->data.find("top follow");
        if(nFindIndex >= 0)
        {
            FollowSwitch(false, 0);
            AddNewWaypoint("master");
            nState = STATE_ASK;
            nDelay = 0;
            Speak("OK. What do you want me to fetch?");
        }
    }

    if(nState == STATE_ASK)
    {
        // 从识别结果句子中查找多个物品（航点）关键词
        string strKeyword = FindMultiKeyword(msg->data);
        int nLenOfKW = strlen(strKeyword.c_str());
        if(nLenOfKW > 0)
        {
            // 发现物品（航点）关键词
            string strSpeak = "小智重复一下您说的物品：" + strKeyword + "，请问您要我送到哪儿？"; 
            Speak(strSpeak);
            nIndexWant = 0;
            nState = STATE_SLEEP2;
        }
        else if (msg->data.find("天气") != -1) {
            nState = STATE_WEATHER;
        }
        else if (strlen(msg->data.c_str()) > 0) {
            chatMsg = msg->data;
            nState = STATE_CHAT;
        }
    }

    if(nState == STATE_ASK2) {
        // 从识别结果句子中查找航点关键词
        std::pair<string, string> ret = FindKeyword(msg->data);
        passDst = ret.second;
        if(!passDst.empty()) {
            // 发现航点关键词
            string strSpeak = "小智重复一下您说的地点：" + ret.first + "，好的，这就帮您去取。"; 
            Speak(strSpeak);
            sleep(10);   //暂停一会，等机器人把话说完再行动
            nState = STATE_GOTO;
        }
        else {
            Speak("对不起，没有找到该航点。");
            logErr("返回航点未找到");
            nState = STATE_SLEEP2;
        }
    }

    if (nState == STATE_PASSWAIT) {
        if (msg->data.find("不") == -1 && (msg->data.find("可以") != -1 || msg->data.find("好") != -1 || msg->data.find("行") != -1)) {
            nState = STATE_PASS;
        }
    }
}

// 物品抓取状态
void GrabResultCallback(const std_msgs::String::ConstPtr& res)
{
    int nFindIndex = 0;
    nFindIndex = res->data.find("done");
    if( nFindIndex >= 0 )
    {
        bGrabDone = true;
    }
    nFindIndex = res->data.find("failed");
    if( nFindIndex >= 0 )
    {
        bGrabFailed = true;
    }
}

// 物品递给状态
void PassResultCallback(const std_msgs::String::ConstPtr& res)
{
    int nFindIndex = 0;
    nFindIndex = res->data.find("done");
    if( nFindIndex >= 0 )
    {
        bPassDone = true;
    }
}

//IMU测试
void imuCallback(const sensor_msgs::Imu::ConstPtr& msg)
{
    // tf::Quaternion q(msg->orientation.x,msg->orientation.y,msg->orientation.z,msg->orientation.w);
    //float fCurYaw = tf::getYaw(q)*180/3.1415;
    // ROS_INFO("[WX] %f", msg->angular_velocity.x);
    // ROS_INFO("[WY] %f", msg->angular_velocity.y);
    // ROS_INFO("[WZ] %f", msg->angular_velocity.z);
    // ROS_INFO("[AX] %f", msg->linear_acceleration.x);
    // ROS_INFO("[AY] %f", msg->linear_acceleration.y);
    // ROS_INFO("[AZ] %f", msg->linear_acceleration.z);
    double pitch = 180 * atan2(msg->linear_acceleration.x, sqrt(msg->linear_acceleration.y * msg->linear_acceleration.y + msg->linear_acceleration.z * msg->linear_acceleration.z)) / 3.1415;
    // ROS_INFO("pitch: %f", pitch);
    double roll = 180 * atan2(msg->linear_acceleration.y, sqrt(msg->linear_acceleration.x * msg->linear_acceleration.x + msg->linear_acceleration.z * msg->linear_acceleration.z)) / 3.1415;
    // ROS_INFO("roll: %f", roll);
    if (fabs(pitch) > 10 || fabs(roll) > 10) {
        if (!fallSpeaking) {
            fallSpeaking = true;
            Speak("机器人有倾倒风险！");
            logErr("机器人有倾倒风险");
            sleep(5);
            fallSpeaking = false;
        }
    }
}


int main(int argc, char** argv)
{
    ros::init(argc, argv, "fri01_basic");

    ros::NodeHandle n;
    ros::Subscriber sub_sr;
    follow_start = n.serviceClient<wpb_home_tutorials::Follow>("wpb_home_follow/start");
    follow_stop = n.serviceClient<wpb_home_tutorials::Follow>("wpb_home_follow/stop");
    follow_resume = n.serviceClient<wpb_home_tutorials::Follow>("wpb_home_follow/resume");
    cliGetWPName = n.serviceClient<waterplus_map_tools::GetWaypointByName>("/waterplus/get_waypoint_name");
    add_waypoint_pub = n.advertise<waterplus_map_tools::Waypoint>( "/waterplus/add_waypoint", 1);
    spk_pub = n.advertise<std_msgs::String>("/xfyun/tts", 10);
    vel_pub = n.advertise<geometry_msgs::Twist>("/cmd_vel", 10);
    clientIAT = n.serviceClient<xfyun_waterplus::IATSwitch>("xfyun_waterplus/IATSwitch");
    behaviors_pub = n.advertise<std_msgs::String>("/wpb_home/behaviors", 30);
    grab_result_sub = n.subscribe<std_msgs::String>("/wpb_home/grab_result",30,&GrabResultCallback);
    pass_result_sub = n.subscribe<std_msgs::String>("/wpb_home/pass_result",30,&PassResultCallback);
    ros::Subscriber imu_sub = n.subscribe("imu/data_raw", 100, imuCallback);

    InitKeyword();

    ROS_WARN("[main] fri01_shopping");
    ros::Rate r(30);
    while(ros::ok())
    {
        // 1、刚启动，准备
        if(nState == STATE_READY)
        {
            sleep(2);
            Speak("请标定机器人的初始位置，二十秒后启动服务流程。");
            sleep(20);
            //ROS_WARN("[STATE_READY] - nDelay = %d", nDelay);
            sub_sr = n.subscribe("/xfyun/iat", 10, KeywordCB);
            AddNewWaypoint("start");
            nDelay = 0;
            Speak("您想让我取什么？");
            sleep(5);
            nState = STATE_ASK;
        }

        // 2、跟随阶段
        if(nState == STATE_FOLLOW)
        {
            if(nDelay == 0)
            {
               FollowSwitch(true, 0.7);
            }
            nDelay ++;
        }

        // 3、询问要去哪个航点
        if(nState == STATE_ASK)
        {
            
        }

        // 4、导航去指定航点
        if(nState == STATE_GOTO)
        {
            int nNumWant = arObjectWantIndex.size();
            if(nIndexWant < nNumWant)
            {
                strGoto = arObjectWantIndex[nIndexWant];
            }
            srvName.request.name = strGoto;
            if (cliGetWPName.call(srvName))
            {
                std::string name = srvName.response.name;
                float x = srvName.response.pose.position.x;
                float y = srvName.response.pose.position.y;
                ROS_INFO("[STATE_GOTO] Get_wp_name = %s (%.2f,%.2f)", strGoto.c_str(),x,y);

                MoveBaseClient ac("move_base", true);
                if(!ac.waitForServer(ros::Duration(5.0)))
                {
                    ROS_INFO("The move_base action server is no running. action abort...");
                }
                else
                {
                    move_base_msgs::MoveBaseGoal goal;
                    goal.target_pose.header.frame_id = "map";
                    goal.target_pose.header.stamp = ros::Time::now();
                    goal.target_pose.pose = srvName.response.pose;
                    ac.sendGoal(goal);
                    ac.waitForResult(ros::Duration(60));
                    if(ac.getState() == actionlib::SimpleClientGoalState::SUCCEEDED)
                    {
                        ROS_INFO("Arrived at %s!",strGoto.c_str());
                        Speak("准备取物品。");
                        nState = STATE_GRAB;
                        nDelay = 0;
                    }
                    else
                    {
                        ROS_INFO("Failed to get to %s ...",strGoto.c_str() );
                        Speak("不好意思，小智没有成功到达航点。");
                        logErr("取物航点导航失败");
                        arObjectWant.clear();
                        arObjectWantIndex.clear();
                        sleep(5);
                        nState = STATE_ASK;
                    }
                }
                
            }
            else
            {
                ROS_ERROR("Failed to call service GetWaypointByName");
                Speak("不好意思，小智没有搜索到您要的物品，请记得添加航点或说出准确的名称哦。");
                logErr("物品航点未找到");
                arObjectWant.clear();
                arObjectWantIndex.clear();
                sleep(5);
                nState = STATE_ASK;
            }
        }

        // 5、抓取物品
        if(nState == STATE_GRAB)
        {
            if(nDelay == 0)
            {
                bGrabDone = false;
                bGrabFailed = false;
                GrabSwitch(true);
            }
            nDelay ++;
            if(bGrabFailed == true)
            {
                GrabSwitch(false);
                Speak("取物失败，小智将复位。");
                logErr("取物失败");
                arObjectWant.clear();
                arObjectWantIndex.clear();
                sleep(5);
                nState = STATE_ASK;
            }
            else if(bGrabDone == true)
            {
                GrabSwitch(false);
                Speak("拿到物品了。");
                nState = STATE_COMEBACK;
            }
        }

        // 6、抓完物品回来
        if(nState == STATE_COMEBACK)
        {
            srvName.request.name = passDst;
            if (cliGetWPName.call(srvName))
            {
                std::string name = srvName.response.name;
                float x = srvName.response.pose.position.x;
                float y = srvName.response.pose.position.y;
                ROS_INFO("[STATE_COMEBACK] Get_wp_name = %s (%.2f,%.2f)", passDst.c_str(),x,y);

                MoveBaseClient ac("move_base", true);
                if(!ac.waitForServer(ros::Duration(5.0)))
                {
                    ROS_INFO("The move_base action server is no running. action abort...");
                }
                else
                {
                    move_base_msgs::MoveBaseGoal goal;
                    goal.target_pose.header.frame_id = "map";
                    goal.target_pose.header.stamp = ros::Time::now();
                    goal.target_pose.pose = srvName.response.pose;
                    ac.sendGoal(goal);
                    ac.waitForResult(ros::Duration(60));
                    if(ac.getState() == actionlib::SimpleClientGoalState::SUCCEEDED)
                    {
                        ROS_INFO("Arrived at %s!",passDst.c_str());
                        Speak("主人，您要的物品小智取来了！现在可以把物品递给您吗？");
                        sleep(5);
                        nState = STATE_PASSWAIT;
                        nDelay = 0;
                    }
                    else
                    {
                        ROS_INFO("Failed to get to %s ...",passDst.c_str() );
                        Speak("主人，小智没法赶到您的位置。");
                        logErr("返回航点导航失败");
                        arObjectWant.clear();
                        arObjectWantIndex.clear();
                        sleep(5);
                        nState = STATE_ASK;
                    }
                }
                
            }
            else
            {
                ROS_ERROR("Failed to call service GetWaypointByName");
                Speak("主人您在哪儿？");
                logErr("返回航点未找到");
                arObjectWant.clear();
                arObjectWantIndex.clear();
                sleep(5);
                nState = STATE_ASK;
            }
        }

        if(nState == STATE_PASSWAIT)
        {
            
        }

        // 7、将物品给主人
        if(nState == STATE_PASS)
        {
            if(nDelay == 0)
            {
                bPassDone = false;
                PassSwitch(true);
            }
            nDelay++;
            if(bPassDone == true)
            {
                PassSwitch(false);
                nIndexWant ++;
                int nNumWant = arObjectWant.size();
                if(nIndexWant < nNumWant)
                {
                    string strSpeak = "好的，这就去取下一个物品，"+arObjectWant[nIndexWant];
                    Speak(strSpeak);
                    sleep(5);   //暂停一会，让机器人把话说完
                    nState = STATE_GOTO;
                }
                else
                {
                    Speak("所有物品已经取回了！您还想要什么？");
                    arObjectWant.clear();
                    arObjectWantIndex.clear();
                    sleep(5);
                    nState = STATE_ASK;
                }
            }
        }

        if (nState == STATE_SLEEP) {
            sleep(7);
            nState = STATE_ASK;
        }

        if (nState == STATE_SLEEP2) {
            sleep(7);
            nState = STATE_ASK2;
        }

        if (nState == STATE_WEATHER) {
            FILE *pipe = popen("python3 /home/robot/catkin_ws/src/2021_embedded/fri_01/fri01_apps/src/scripts/weather.py", "r");
            char buffer[4096];
            fgets(buffer, sizeof(buffer), pipe);
            Speak(string(buffer));
            printf("[Weather] %s\n", buffer);
            pclose(pipe);
            sleep(7);
            nState = STATE_ASK;
        }

        if (nState == STATE_CHAT) {
            string cmd = "python3 /home/robot/catkin_ws/src/2021_embedded/fri_01/fri01_apps/src/scripts/chat.py " + chatMsg;
            FILE *pipe = popen(cmd.c_str(), "r");
            char buffer[4096];
            fgets(buffer, sizeof(buffer), pipe);
            Speak(string(buffer));
            printf("[Chat] %s\n", buffer);
            pclose(pipe);
            sleep(7);
            nState = STATE_ASK;
        }
        
        ros::spinOnce();
        r.sleep();
    }

    return 0;
}
