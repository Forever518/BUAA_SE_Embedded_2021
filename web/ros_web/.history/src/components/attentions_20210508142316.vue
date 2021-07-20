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
       console.log(nextComponent);
       this.$emit('nextStepClik', nextComponent);
     }
   },
   computed: {
     nextComponent() {
       if(this.type === 'build') {
         return 'building';
       }
       return 'building';
     }
   }
  }
</script>