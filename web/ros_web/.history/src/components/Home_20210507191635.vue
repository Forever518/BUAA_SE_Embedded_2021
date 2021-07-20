<template>
  <div class="component-content">
    <el-row>
      <el-col :span="24" :offset="0" class="big  blue center" >
        <span>服务模式</span>
      </el-col>
    </el-row>
    <el-row class="space"></el-row>
    <el-row>
      <el-col :span="24" :offset="0" class="small gray center">
        <span>SERVICE MODE</span>
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
    <el-row :gutter="20">
      <el-col :span="8" :offset="0">
        <div class="center back-white item-box">
          <div class="item-box">
            <el-image :src="src_build">
              <div slot="placeholder" class="image-slot">
                <i class="el-icon-loading"></i>
              </div>
              <div slot="error" class="image-slot">
                <i class="el-icon-picture-outline"></i>
              </div>
            </el-image>
          </div>
        </div>
      </el-col>
      <el-col :span="8" :offset="0">
        <div class="center back-white">
          标注模式
        </div>
      </el-col>
      <el-col :span="8" :offset="0">
        <div class="center back-white">
          服务模式
        </div>
      </el-col>
    </el-row>
    
  </div>
</template>

<script>
  export default {
    data() {
      return {
        src_build: '',
        src_mark: '',
        src_serve: '',
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
.big {
  font-size: 26px;
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
  background-color: white;
}
.item-box {
  min-height: 30px;
}
</style>