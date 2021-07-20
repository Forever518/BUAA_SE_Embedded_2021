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
import {post_resetSettings} from '../api/request';
import axios from 'axios';
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
      axios.post('/control/check-update')
      .then(res => {
        if (res.msg === '已更新到最新版本。') {
          ld_message.close();
          this.$message({
            message: '已更新到最新版本',
            duration: 2000,
            type: 'success',
          })
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
    },
    reset() {
      this.dialogVisible = false;
      post_resetSettings().then((res) => {
        this.$message({
          message: '恢复出厂设置成功',
          duration: 2 * 1000,
          type: 'success'
        })
      })
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
  }
};
</script>
