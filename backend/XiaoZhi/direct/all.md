# 用户操作说明

## 建图模式

#### 注意事项

+ 地图的命名不能重复
+ 尽量不要自己关闭`Rviz`界面
+ 手柄操控时可能存在机器人漂移现象，危险时请按下急停开关

#### 操作提示

+ 在进入建图模式后，机器人将利用其所携带的激光雷达扫描自身周围的障碍物，并结合里程计信息进行建图
+ 此时，您可以**使用手柄控制**机器人平移和旋转，直至整个房间的地图在`Rviz`中被显示出来
+ 在建图结束后，请单击“**保存地图**”按钮将地图保存下来

## 标注模式

#### 注意事项

+ 同一个地图中航点的名称不能重复
+ 航点不能不命名
+ 尽量不要自己关闭`Rviz`界面
+ 手柄操控时可能存在机器人漂移现象，危险时请按下急停开关

#### 操作提示

+ 在标注模式中，您可以在`Rviz`中为您曾建立的地图标注航点
+ 首先选择一个地图文件
+ 然后在`Rviz`窗口上方的工具栏中单击“**Add Waypoint**”按钮
+ 再在您需要建立航点的地方长按鼠标左键
+ `Rviz`中将会出现一个绿色箭头提示您选择机器人的朝向
+ 选择完毕后，释放鼠标左键，即可标注航点
+ 在标注完成后，您还需要在界面中为每个航点**命名**
+ 命名完成后，单击“**保存**”按钮即可完成标注。

## 服务模式

#### 注意事项

+ 需要良好的网络
+ 尽量不要自己关闭`Rviz`界面
+ 手柄操控时可能存在机器人漂移现象，危险时请按下急停开关

#### 操作提示

+ 在进入服务模式后，您将有20秒的时间在Rviz内指定机器人的初始位置，具体操作步骤为
  + 在Rviz内单击“**2D Pose Estimation**”按钮
  + 在机器人的初始位置长按鼠标左键
  + Rviz中将会出现一个绿色箭头提示您选择机器人的朝向
  + 选择完毕后，释放鼠标左键，即可指定机器人的初始位置
+ 指定完毕后，机器人将会进入正式服务流程。
+ 此时您可以使用语音与机器人交互。

