<template>
  <el-container>
    <el-header height="">
      <el-menu
        :default-active="activeIndex"
        mode="horizontal"
        class="el-menu-demo"
        @select="handleSelect"
        background-color="#545c64"
        text-color="#fff"
        active-text-color="#ffd04b"
      >
        <el-menu-item index="1">首页</el-menu-item>
        <el-menu-item index="2">系统设置</el-menu-item>
        <el-menu-item index="3">帮助</el-menu-item>
        <el-menu-item index="4">关于我们</el-menu-item>
      </el-menu>
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
