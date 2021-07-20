<template>
  <div class="bodyBoxOut">
    <div class="messageBox">
      <mavon-editor class="markdown"
        :value="tips"
        :subfield = "false"    
        :defaultOpen = "'preview'"
        :toolbarsFlag = "false"
        :editable="false"
        :scrollStyle="false"
        :boxShadow = "true"
      ></mavon-editor>
    </div>
    <el-row :gutter="20" type="flex" justify="space-around">
      <el-col :span="4">
      </el-col>
      <el-col :span="4">
        <el-button @click="map_finish">完成建图</el-button>
      </el-col>
    </el-row>

    <el-dialog
      title="地图命名"
      :visible.sync="dialogVisible"
      width="30%"
      :before-close="handleClose">
      <el-form :model="form" @submit.native.prevent>
        <el-form-item label="地图名称">
          <el-input v-model="form.name"></el-input>
        </el-form-item>
      </el-form>
      <span slot="footer" class="dialog-footer">
        <el-button type="primary" @click="mapName">确 定</el-button>
      </span>
    </el-dialog>
  </div>
</template>

<script>
import {post_saveMap, post_renameMap_name} from '../api/request';
  export default {
    data() {
      return {
        dialogVisible: false,
        form: {
          name: '',
        },
        tips: '',
      }
    },
    methods: {
      handleClose(done) {
        this.$confirm('确认关闭？')
          .then(() => {
            this.form.name = '';
            done();
          }).catch(()=>{})
      },
      mapName() {
        post_renameMap_name({
          name: this.form.name,
          mapLocation: '/home/forever518/map/map_message',
          viewMapLocation: '/home/forever518/assets/map/map_view',
        }).then(() => {
          this.$router.push({
            name: 'Main',
          })
        })
        // if (this.form.name.length == 0) {
        //   this.$message.error('地图名不能为空！');
        // } else {
        //   this.dialogVisible = false;
        //   await this.$message({
        //     message:'命名成功，地图名为：' + this.form.name,
        //     type: 'success',
        //   })
        //   await this.$message({
        //     message: '新创建的地图请进行标注',
        //   })
        //   this.$router.push({
        //     name: 'Main',
        //   })
        // }
      },
      get_tips_data() {
        this.tips = '## Building Tips';
      },
      map_finish() {
        post_saveMap().then(() => {
          this.dialogVisible = true;
        })
      }
    },
    mounted() {
      this.get_tips_data();
    }
  }
</script>