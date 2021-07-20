<template>
  <div class="bodyBoxOut">
    <div class="messageBox">
      <mavon-editor class="markdown"
        :value="get_mark_data()"
        :subfield = "false"    
        :defaultOpen = "prop.defaultOpen"
        :toolbarsFlag = "prop.toolbarsFlag"
        :editable="prop.editable"
        :scrollStyle="prop.scrollStyle"
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
  export default {
    data() {
      return {
        dialogVisible: false,
        dynamicForm: {
          wayPoints: [
            {name: '',},
            {name: '',},
          ],
        }
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
          this.dialogVisible = false;
          this.$message({
            message:'命名成功，航点名称分别为:' + names,
            type: 'success',
          })
          this.$router.push({
            name: 'Main',
          })
        }
      },
      markDone() {
        if (this.dynamicForm.wayPoints.length !== 0) {
          this.dialogVisible = true;
        } else {
          this.$message.error('系统检测到您未设置航点！');
        }
      }
    }，
    computed: {
      prop() {
        let data = {
          subfield: false,// 单双栏模式
          defaultOpen: 'preview',//edit： 默认展示编辑区域 ， preview： 默认展示预览区域 
          editable: false,
          toolbarsFlag: false,
          scrollStyle: true
        }
        return data
      }
    }
  }
</script>