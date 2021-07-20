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
      <el-form :model="dynamicForm" @submit.native.prevent>
        <el-form-item 
        v-for="(wayPoint, index) in dynamicForm.wayPoints" 
        :label="'航点' + index"
        :key="wayPoint.key"
        :prop="wayPoints.index.name">
          <el-input v-model="wayPoint.name"></el-input>
        </el-form-item>
      </el-form>
      <span slot="footer" class="dialog-footer">
        <el-button type="primary" @click="mapName">确 定</el-button>
      </span>
    </el-dialog>
  </div>
</template>

<script>
  export default {
    data() {
      return {
        dialogVisible: false,
        form: {
          name: '',
        },
        dynamicForm: {
          wayPoints: [{
            name: '',
          }]
        }
      }
    },
    methods: {
      handleClose(done) {
        this.$confirm('确认关闭？')
          .then(() => {
            done();
          })
      },
      async mapName() {
        this.dialogVisible = false;
        await this.$message({
          message:'命名成功，地图名为：' + this.form.name,
          type: 'success',
        })
        await this.$message({
          message: '新创建的地图请进行标注',
        })
        this.$router.push({
          name: 'Main',
        })
      },
    }
  }
</script>