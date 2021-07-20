<template>
  <div class="bodyBoxOut">
    <div v-if="type === 'build'" class="bodyBoxIn">
      <div class="messageBox">
        <p>注意事项</p>
      </div>
      <el-row :gutter="20" type="flex" justify="space-around">
        <el-col :span="4">
          <el-checkbox v-model="haveRead">我已阅读完毕</el-checkbox>
        </el-col>
        <el-col :span="4">
          <el-button @click="back(next_component)">开始建图</el-button>
        </el-col>
      </el-row>
    </div>
    <div v-if="type === 'mark'" class="bodyBoxIn">
      <div class="messageBox">
        <p>注意事项</p>
      </div>
      <el-row :gutter="20" type="flex" justify="space-around">
        <el-col :span="4">
          <el-checkbox v-model="haveRead">我已阅读完毕</el-checkbox>
        </el-col>
        <el-col :span="4">
          <el-button @click="back(next_component)">开始标注</el-button>
        </el-col>
      </el-row>
    </div>
  </div>
</template>

<script>
  export default {
   data() {
     return {
       haveRead: false,
     }
   },
   props: {
     type: {
       type: String,
       require: true,
     }
   },
   methods: {
     back(next_component) {
       switch (this.type) {
         case 'build':
           if(this.haveRead === true) {
              this.$emit('next-step-click', next_component);
              this.$notify({
                title: '开始建图',
                message: '请跟随提示在Rviz界面完成建图',
                type: 'success',
                showClose: false,
                duration: 2000,
              })
            } else {
              this.$notify({
                title: '警告',
                message: '请先阅读注意事项',
                type: 'warning',
                showClose: false,
                duration: 2000,
              })
            }
            break;
          default:
            this.$notify.error({
              title: '错误',
              message: '出现未知错误',
              showClose: false,
              duration: 2000,
            })
       }
       
     }
   },
   computed: {
     next_component() {
       if(this.type === 'build') {
         return 'building';
       }
       return 'building';
     }
   }
  }
</script>
