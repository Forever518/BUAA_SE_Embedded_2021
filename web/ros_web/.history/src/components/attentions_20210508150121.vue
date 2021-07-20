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
                duration: 2000,
              })
            } else {
              this.$notify({
                title: '警告',
                message: '请先阅读注意事项',
                type: 'warning',
                duration: 2000,
              })
            }
            break;
          default:
            this.$notify.error({
              title: '错误',
              message: '出现未知错误',
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

<style>
.messageBox {
  height: 80%!important;
}
.bodyBoxOut {
  height: 100%!important;
  margin-left: 13%;
  margin-right: 13%;
}
.bodyBoxIn {
  height: 100%!important;;
}
</style>