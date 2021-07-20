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
        <el-button @click="markDone">完成标注</el-button>
      </el-col>
    </el-row>
    <el-dialog
      title="航点命名"
      :visible.sync="dialogVisible"
      width="30%"
      :before-close="handleClose">
      <el-form :model="dynamicForm" ref="dynamicForm" @submit.native.prevent>
        <el-form-item 
        v-for="(wayPoint, index) in dynamicForm.wayPoints" 
        :label="'航点' + index"
        :key="wayPoint.key"
        :prop="dynamicForm.wayPoints[index].name"
        >
          <el-input clearable v-model="wayPoint.name"></el-input>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="wayPointName">保 存</el-button>
        </el-form-item>
      </el-form>
    </el-dialog>
  </div>
</template>

<script>
import {post_getWayPoint, post_renameMark} from '../api/request';
  export default {
    data() {
      return {
        dialogVisible: false,
        dynamicForm: {
          wayPoints: [
            {label: '', name: '',},
            {label: '', name: '',},
          ],
        },
        tips: '',
      }
    },
    methods: {
      handleClose(done) {
        this.$confirm('确认关闭？')
          .then(() => {
            let i = 0;
            for (i = 0; i < this.dynamicForm.wayPoints.length; i++) {
              this.dynamicForm.wayPoints[i].name = '';
            }
            done();
          }).catch(()=>{})
      },
      wayPointName() {
        let names = '';
        let i = 0;
        let wrong = false;
        let hash = {};
        for (i = 0; i < this.dynamicForm.wayPoints.length; i++) {
          if (this.dynamicForm.wayPoints[i].name.length === 0) {
            this.$message.error('航点名称不能为空！');
            wrong = true;
            break;
          }
          if (hash[this.dynamicForm.wayPoints[i].name]) {
            this.$message.error('航点名称不能相同！');
            wrong = true;
            break;
          }
          hash[this.dynamicForm.wayPoints[i].name] = true;
          names = names + ' ' + this.dynamicForm.wayPoints[i].name;
        }
        if (!wrong) {
          post_renameMark({
            name: this.$cookie.getJSON('nowMap').name,
            wayPoints: this.dynamicForm.wayPoints
          }).then(() => {
            this.dialogVisible = false;
            this.$message({
              message:'命名成功，航点名称分别为:' + names,
              type: 'success',
            })
            this.$router.push({
              name: 'Main',
            })
          })
        }
      },
      markDone() {
        post_getWayPoint().then((res) => {
          let array = res.data;
          let form = [];
          for (let i = 0; i <= array.length; i++) {
            form.push({ label: array[i], name: '' });
          }
          this.dynamicForm.wayPoints = form;
          if (this.dynamicForm.wayPoints.length !== 0) {
            this.dialogVisible = true;
          } else {
            this.$message.error('系统检测到您未设置航点！');
          }
        });
      },
      get_tips_data() {
        this.tips = '## 标注模式操作步骤\n\
\
+ 在标注模式中，您可以在`Rviz`中为您曾建立的地图标注航点\n\
+ 首先选择一个地图文件\n\
+ 然后在`Rviz`窗口上方的工具栏中单击“**Add Waypoint**”按钮\n\
+ 再在您需要建立航点的地方长按鼠标左键\n\
+ `Rviz`中将会出现一个绿色箭头提示您选择机器人的朝向\n\
+ 选择完毕后，释放鼠标左键，即可标注航点\n\
+ 在标注完成后，您还需要在界面中为每个航点**命名**\n\
+ 命名完成后，单击“**保存**”按钮即可完成标注。';
      }
    },
    mounted() {
      this.get_tips_data();
    }
  }
</script>