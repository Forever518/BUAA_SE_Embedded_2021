<template>
  <el-container>
    <el-header>
      <el-page-header @back="goBack" content="建图模式">
      </el-page-header>
    </el-header>
    <el-main>

    </el-main>
  </el-container>  
</template>

<script>
  export default {
    data() {
      return {

      }
    },
    methods: {
      goBack() {
        this.$router.push({
          name: 'Main'
        })
      }
    }
  }
</script>

<style>
.el-page-header {
  height: 100%!important;
  line-height: 60px!important;
}
</style>