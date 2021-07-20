<template>
  <div>
    <p> {{type}} </p>
    <el-button @click="back(nextComponent)">开始建图</el-button>
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
     back(nextComponent) {
       this.$emit('gochild', nextComponent);
     }
   },
   computed: {
     nextComponent() {
       if(type === 'build') {
         return 'building';
       }
     }
   }
  }
</script>