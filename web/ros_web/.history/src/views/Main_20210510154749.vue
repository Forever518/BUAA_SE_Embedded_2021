<template>
  <el-container direction="vertical" class="homepage">
    <el-header height="">
      <el-row :gutter="20" type="flex" justify="space-between">
      <el-col :span="4" :offset="0"><div style="line-height:60px;height:100%">小智</div></el-col>
      <el-col :span="8" :offset="0"><el-menu
        :default-active="activeIndex"
        mode="horizontal"
        class="my_menu"
        @select="handleSelect"
        background-color="fff"
        text-color="#000"
        active-text-color="#000"
      >
        <el-menu-item index="1"><template><i class="el-icon-s-home"></i><span>首页</span></template></el-menu-item>
        <el-menu-item index="2"><template><i class="el-icon-question"></i><span>帮助</span></template></el-menu-item>
        <el-menu-item index="3"><template><i class="el-icon-info"></i><span>关于我们</span></template></el-menu-item>
        <el-submenu index="1">
          <template slot="title"><i class="el-icon-s-tools"></i><span>设置</span></template>
          <el-menu-item index="1" @click="checkUpdate"><span>系统升级</span></el-menu-item>
          <el-menu-item index="1" @click="reset">
            <el-popconfirm confirm-button-text='确定'
              cancel-button-text='再想一想'
              icon='el-icon-info'
              icon-color='red'
              title='确定要恢复出厂设置吗？'>
                <span slot="reference">恢复出厂设置</span>
            </el-popconfirm>
          </el-menu-item>
        </el-submenu>
        <!-- <el-menu-item index="4"><template><i class="el-icon-s-tools"></i><span>设置</span></template></el-menu-item> -->
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
    async checkUpdate() {
      await this.$message({
        dangerouslyUseHTMLString: true,
        message: '<span>正在检查更新中，请稍等  </span><i class="el-icon-loading"></i>',
        duration: 2000,
        type: 'warning',
      })
      await this.$message({
        message: '已更新到最新版本',
        duration: 2000,
        type: 'success',
      })
    },
    async reset() {
      await this.$message({
        dangerouslyUseHTMLString: true,
        message: '<span>正在恢复出厂设置中，请稍等  </span><i class="el-icon-loading"></i>',
        duration: 2000,
        type: 'warning',
      })
      await this.$message({
        message: '恢复出厂设置成功',
        duration: 2000,
        type: 'success',
      })
    },
  },
  components: {
    home,
    help,
    about,
  }
};
</script>
