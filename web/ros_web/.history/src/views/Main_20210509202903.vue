<template>
  <el-container direction="vertical" class="homepage">
    <el-header height="">
      <el-row :gutter="20" type="flex" justify="space-between">
      <el-col :span="4" :offset="0"><div style="line-height:60px;height:100%">小智</div></el-col>
      <el-col :span="7" :offset="0"><el-menu
        :default-active="activeIndex"
        mode="horizontal"
        class="my_menu"
        @select="handleSelect"
        background-color=""
        text-color="#000"
        active-text-color="#000"
      >
        <el-menu-item index="1"><template><i class="el-icon-s-home"></i><span>首页</span></template></el-menu-item>
        <el-menu-item index="2"><template><i class="el-icon-question"></i><span>帮助</span></template></el-menu-item>
        <el-menu-item index="3"><template><i class="el-icon-info"></i><span>关于我们</span></template></el-menu-item>
        <el-menu-item index="4"><template><i class="el-icon-s-tools"></i><span>设置</span></template></el-menu-item>
      </el-menu></el-col>
    </el-row>
    </el-header>
    <el-container>
      <el-main>
        <home v-if="activeIndex == '1'"></home>
        <help v-if="activeIndex == '2'"></help>
        <about v-if="activeIndex == '3'"></about>
      </el-main>
    </el-container>
  </el-container>
</template>

<script>
import home from '../components/home';
import help from '../components/help';
import about from '../components/about';
export default {
  data() {
    return {
      activeIndex: '1',
    };
  },
  methods: {
    handleSelect(key) {
      this.activeIndex = key
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
.homepage {
  background-image: url("../assets/b2.jpg");
  background-repeat: no-repeat;
  background-attachment: fixed;
  background-size: cover;
}
</style>
