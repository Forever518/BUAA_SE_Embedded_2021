## 2021软件工程：家政服务机器人小智

### 目录

* docs：存放项目文档。
* backend：后端代码、配置文件与最终可执行脚本。
* config：后端配置文件。
* direct：前端用户使用说明与版本号。
* fri01_apps：ROS部分代码。

### 分支

- master：存放项目文档；
- ROS：存放机器人ROS相关代码；
- spring：存放web后端代码；
- web：存放web前端代码。

### 其他

* 小组会议纪要：[【腾讯文档】会议纪要](https://docs.qq.com/doc/DZFFGRlJuaVdNc0xn)

## 家政服务机器人小智ROS端说明

### 环境需求

- Ubuntu 16.04 LTS
- ROS Kinetic Kame
- Python3 + requests
- 启智机器人官方包wpb_home

### 运行说明

- 软件包名为`fri01_apps`，使用`fri01_basic.launch`可以启动ROS端服务模式软件包。

## 家政服务机器人小智  Web系统后端说明

### 开发环境

* 语言：`Java 1.8`。
* 数据库：`MySql 5.7.33`。
* 开发框架：`SpringBoot`。
* 数据库访问框架：`MyBatis Plus`。

### 运行说明

* 后端运行在本地`localhost:8081`，需要避免端口被占用。

### 附加脚本

脚本位于`script`目录下。

* `sql/db_init.sql`：数据库建立脚本。
* `exec/xiaozhi.sh`：启动服务端脚本。

