<template>
  <el-container direction="vertical" class="homepage">
    <vue-particles
      color="#fff"
      :particleOpacity="0.7"
      :particlesNumber="100"
      shapeType="star"
      :particleSize="4"
      linesColor="#fff"
      :linesWidth="1"
      :lineLinked="true"
      :lineOpacity="0.4"
      :linesDistance="150"
      :moveSpeed="3"
      :hoverEffect="true"
      hoverMode="grab"
      :clickEffect="true"
      clickMode="push"
    >
    </vue-particles> 
    <el-header height="" style="position: relative!important">
      <el-row :gutter="20" type="flex" justify="space-between">
        <el-col :span="4" :offset="0"><div style="line-height:60px;height:100%">小智</div></el-col>
        <!-- <el-col :span="4" :offset="0"><el-image style="height:60px" :fit="cover" :src="require('../assets/logo.png')"></el-image></el-col> -->
        <el-col :span="10" :offset="0"><el-menu
          :default-active="activeIndex"
          mode="horizontal"
          class="my_menu"
          @select="handleSelect"
          background-color="fff"
          text-color="#000"
          active-text-color="#000"
        >
          <el-menu-item index="1"><template><i class="el-icon-s-home"></i><span>首页</span></template></el-menu-item>
          <el-menu-item index="2"><template><i class="el-icon-s-grid"></i><span>地图</span></template></el-menu-item>
          <el-menu-item index="3"><template><i class="el-icon-question"></i><span>帮助</span></template></el-menu-item>
          <el-menu-item index="4"><template><i class="el-icon-info"></i><span>关于我们</span></template></el-menu-item>
          <el-submenu index="1">
            <template slot="title"><i class="el-icon-s-tools"></i><span>设置</span></template>
            <el-menu-item index="1" @click="checkUpdate"><span>系统升级</span></el-menu-item>
            <el-menu-item index="1" @click="dialogVisible = true"><span>恢复出厂设置</span></el-menu-item>
          </el-submenu>
          <el-dialog
            title="确定要恢复出厂设置吗"
            :visible.sync="dialogVisible"
            width="30%"
            :before-close="handleClose">
            <span slot="footer" class="dialog-footer">
              <el-button type="primary" @click="dialogVisible = false">取 消</el-button>
              <el-button type="danger" @click="reset">确 定</el-button>
            </span>
          </el-dialog>
        </el-menu></el-col>
      </el-row>
    </el-header>
    <el-container style="position: relative!important">
      <el-main>
        <home v-if="activeIndex == '1'"></home>
        <maps v-if="activeIndex == '2'"></maps>
        <help v-if="activeIndex == '3'"></help>
        <about v-if="activeIndex == '4'"></about>
      </el-main>
    </el-container>
  </el-container>
</template>

<script>
import home from '../components/home';
import help from '../components/help';
import about from '../components/about';
import maps from '../components/maps.vue';
import {post_resetSettings, post_checkUpstate} from '../api/request';
export default {
  data() {
    return {
      activeIndex: '1',
      dialogVisible: false,
    };
  },
  methods: {
    handleSelect(key) {
      this.activeIndex = key
    },
    checkUpdate() {
      let ld_message = this.$message({
        dangerouslyUseHTMLString: true,
        message: '<span>正在检查更新中，请稍等  </span><i class="el-icon-loading"></i>',
        duration: 0,
        type: 'warning',
      })
      setTimeout(() => {
        post_checkUpstate()
        .then((res) => {
          if (res.msg === '目前已是最新版本。') {
            ld_message.close();
          } else {
            ld_message.close();
            let ld_message = this.$message({
              dangerouslyUseHTMLString: true,
              message: '<span>正在更新中，请稍等  </span><i class="el-icon-loading"></i>',
              duration: 0,
              type: 'warning',
            })
          }
        })
        .catch(err => {
          console.error(err); 
        })
      }, 1 * 1000);
    },
    reset() {
      this.dialogVisible = false;
      post_resetSettings();
      // let ld_message = this.$message({
      //   dangerouslyUseHTMLString: true,
      //   message: '<span>正在恢复出厂设置中，请稍等  </span><i class="el-icon-loading"></i>',
      //   duration: 0,
      //   type: 'warning',
      // })
      // axios.post('/map/remove-all')({
      //   method: 'post',
      //   url: '/map/remove-all',
      // }).then((res) => {
      //   if (res.msg === '复原成功。') {
      //     ld_message.close();
      //     this.$message({
      //       message: '恢复出厂设置成功',
      //       duration: 2000,
      //       type: 'success',
      //     })
      //   } else {
      //     ld_message.close();
      //     this.$message({
      //       message: '恢复出厂设置失败，请重试',
      //       duration: 2000,
      //       type: 'error',
      //     })
      //   }
      // })
      // .catch(err => {
      //   console.error(err); 
      // })
    },
    handleClose(done) {
        this.$confirm('确认关闭？')
          .then(() => {
            done();
          })
          .catch(() => {});
      }
  },
  components: {
    home,
    help,
    about,
    maps,
  },
};
</script>
