<template>
  <div>
    <div v-if="type === 'build'">
      <div class="messageBox">
        <p>注意事项</p>
      </div>
      <el-row :gutter="20" type="flex" justify="space-between">
        <el-col :span="4">
          
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
       this.$emit('next-step-click', next_component);
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
</style>