<template>
  <div class="bodyBoxOut">
    <div class="messageBox">
      <p>提示信息</p>
    </div>
    <el-row :gutter="20" type="flex" justify="space-around">
      <el-col :span="4">
      </el-col>
      <el-col :span="4">
        <el-button @click="dialogVisible = true">完成标注</el-button>
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
        :rules="{
            required: true, message: '航点名称不能为空'
          }">
          <el-input v-model="wayPoint.name"></el-input>
        </el-form-item>
      </el-form>
      <span slot="footer" class="dialog-footer">
        <el-button type="primary" @click="wayPointName">确 定</el-button>
      </span>
    </el-dialog>
  </div>
</template>

<script>
  export default {
    data() {
      return {
        dialogVisible: false,
        dynamicForm: {
          wayPoints: [{
            name: '',
          }],
        }
      };
    },
    methods: {
      handleClose(done) {
        this.$confirm('确认关闭？')
          .then(() => {
            done();
          })
      },
      wayPointName() {
        console.log(this.dynamicForm.wayPoints[0].name);
        let names = this.dynamicForm.wayPoints[0].name;
        // for (let i = 1; i <= this.dynamicForm.wayPoints.length; i++) {
        //   names.append('、' + this.dynamicForm.wayPoints[i].name);
        // }
        this.dialogVisible = false;
        this.$message({
          message:'命名成功，航点名称分别为：' + names,
          type: 'success',
        })
        this.$router.push({
          name: 'Main',
        })
      },
    }
  }
</script>