<template>
  <el-container direction="vertical">
    <el-header height="">
      <el-row :gutter="20">
      <el-col :span="9" :offset="0"><template>小智</template></el-col>
      <el-col :span="15" :offset="0"><el-menu
        :default-active="activeIndex"
        mode="horizontal"
        class="my_menu"
        @select="handleSelect"
        background-color=""
        text-color="#000"
        active-text-color="#ffd04b"
      >
        <el-menu-item index="1"><template><i class="el-icon-s-home"></i><span>首页</span></template></el-menu-item>
        <el-menu-item index="2"><template><i class="el-icon-s-tools"></i><span>设置</span></template></el-menu-item>
        <el-menu-item index="3"><template><i class="el-icon-question"></i><span>帮助</span></template></el-menu-item>
        <el-menu-item index="4"><template><i class="el-icon-info"></i><span>关于我们</span></template></el-menu-item>
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
import home from '../components/home';
import settings from '../components/settings';
import help from '../components/help';
import about from '../components/about';
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
  components: {
    home,
    settings,
    help,
    about,
  }
};
</script>

<style>
.el-menu{
  background-color: transparent!important;
}
</style>
