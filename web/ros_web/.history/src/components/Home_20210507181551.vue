<template>
  <div>
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
  </div>
</template>

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
  color: blue;
}
.gray {
  color: gray;
}
.space {
  height: 20px;
}
</style>