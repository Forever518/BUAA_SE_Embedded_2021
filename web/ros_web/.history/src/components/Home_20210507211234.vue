<template>
  <div class="component-content">
    <el-row>
      <el-col :span="24" :offset="0" class="big  blue center" >
        <span>服务模式选择</span>
      </el-col>
    </el-row>
    <el-row class="space"></el-row>
    <el-row>
      <el-col :span="24" :offset="0" class="small gray center">
        <span>SERVICE MODE SELECTION</span>
      </el-col>
    </el-row>
    <el-row class="space"></el-row>
    <el-row class="space"></el-row>
    <el-row>
      <el-col :span="24" :offset="0" class="small gray center">
        <span>小智机器人提供以下三种服务模式：</span>
      </el-col>
    </el-row>
    <el-row class="space"></el-row>
    <el-row class="space"></el-row>
    <el-row class="space"></el-row>
    <el-row :gutter="20">
      <el-col :span="8" :offset="0">
        <el-button round class="center back-white button-box b-shadow">
          <div class="image-box">
            <el-image :src="src_build">
              <div slot="placeholder" class="image-slot">
                <i class="el-icon-loading"></i>
              </div>
              <div slot="error" class="image-slot">
                <i class="el-icon-picture-outline"></i>
              </div>
            </el-image>
          </div>
          <div class="space"></div>
          <div class="item-box middle">
            <span>建图模式</span>
          </div>
          <div class="space"></div>
          <div class="item-box small gray">
            <span>使用手柄进行建图</span>
          </div>
          <div class="space"></div>
        </el-button>
      </el-col>
      <el-col :span="8" :offset="0">
        <el-button round class="center back-white button-box b-shadow">
          <div class="image-box">
            <el-image :src="src_mark">
              <div slot="placeholder" class="image-slot">
                <i class="el-icon-loading"></i>
              </div>
              <div slot="error" class="image-slot">
                <i class="el-icon-picture-outline"></i>
              </div>
            </el-image>
          </div>
          <div class="space"></div>
          <div class="item-box middle">
            <span>标注模式</span>
          </div>
          <div class="space"></div>
          <div class="item-box small gray">
            <span>使用Rviz对地图进行航点标注</span>
          </div>
          <div class="space"></div>
        </el-button>
      </el-col>
      <el-col :span="8" :offset="0">
        <el-button round class="center back-white button-box b-shadow">
          <div class="image-box">
            <el-image :src="src_serve">
              <div slot="placeholder" class="image-slot">
                <i class="el-icon-loading"></i>
              </div>
              <div slot="error" class="image-slot">
                <i class="el-icon-picture-outline"></i>
              </div>
            </el-image>
          </div>
          <div class="space"></div>
          <div class="item-box middle">
            <span>服务模式</span>
          </div>
          <div class="space"></div>
          <div class="item-box small gray">
            <span>开始服务</span>
          </div>
          <div class="space"></div>
        </el-button>
      </el-col>
    </el-row>
    
  </div>
</template>

<script>
  export default {
    data() {
      return {
        src_build: require('../assets/map.png'),
        src_mark: require('../assets/mark.png'),
        src_serve: require('../assets/serve.png'),
      }
    }
  }
</script>

<style>
.center {
  text-align: center;
}
.small {
  font-size: 14px;
}
.middle {
  font-size: 20px;
}
.big {
  font-size: 28px;
}
.blue {
  color: #034DFF;
}
.gray {
  color: gray;
}
.space {
  height: 20px;
}
.component-content {
  padding-left: 13%;
  padding-right: 13%;
  height: 100%;
}
.back-white {
  background-color: rgb(255, 255, 255)!important;
}
.item-box {
  min-height: 30px;
}
.image-box {
  min-height: 60px;
  padding-top: 30px;
  padding-bottom: 30px;
}
.button-box {
  width: 100% !important;
}
.b-shadow {
  box-shadow: 10px 10px 5px grey!important;
}
</style>