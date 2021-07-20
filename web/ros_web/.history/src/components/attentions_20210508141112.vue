<template>
  <div>
    <p> {{type}} </p>
    <el-button @click="back(building)">开始建图</el-button>
  </div>
</template>

<script>
  export default {
   data() {

   },
   props: {
     type: {
       type: String,
       require: true,
     }
   },
   methods: {
     back(str) {
       this.$emit('gochild', str);
     }
   }
  }
</script>