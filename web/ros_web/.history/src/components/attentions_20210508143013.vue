<template>
  <div>
    <p> {{type}} </p>
    <el-button @click="back(next_component)">开始建图</el-button>
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
       console.log(next_component);
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