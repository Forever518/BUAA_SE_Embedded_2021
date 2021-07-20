<template>
  <el-container direction="vertical">
    <el-header height="">
      <el-row :gutter="20">
      <el-col :span="12" :offset="0">小智</el-col>
      <el-col :span="12" :offset="0"><el-menu
        :default-active="activeIndex"
        mode="horizontal"
        class="my_menu"
        @select="handleSelect"
        text-color="#fff"
        active-text-color="#ffd04b"
      >
        <el-menu-item index="1">首页</el-menu-item>
        <el-menu-item index="2">系统设置</el-menu-item>
        <el-menu-item index="3">帮助</el-menu-item>
        <el-menu-item index="4">关于我们</el-menu-item>
      </el-menu></el-col>
    </el-row>
    </el-header>
    <el-container>
      <el-main>
        <!-- Main content -->
      </el-main>
    </el-container>
  </el-container>
</template>

<script>
export default {
  data() {
    return {
      activeIndex: "1",
    };
  },
  methods: {
    handleSelect(key, keyPath) {
      console.log(key, keyPath);
    },
  },
};
</script>

<style>
/deep/.my_menu {
  background-color: transparent!important;
}
</style>
