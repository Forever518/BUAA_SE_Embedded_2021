<template>
  <div class="bodyBoxOut">
    <div class="messageBox">
      <p>提示信息</p>
    </div>
    <el-row :gutter="20" type="flex" justify="space-around">
      <el-col :span="4">
      </el-col>
      <el-col :span="4">
        <el-button @click="finishMap">完成建图</el-button>
      </el-col>
    </el-row>
  </div>
</template>

<script>
  export default {
    data() {
      return {

      }
    },
    methods: {
      finishMap() {
        
      }
    }
  }
</script>