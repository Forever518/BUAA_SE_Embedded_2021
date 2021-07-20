<template>
  <el-container>
    <el-header>
      <el-page-header @back="goBack" content="建图模式">
      </el-page-header>
    </el-header>
    <el-container>
      <el-aside>
        <div style="height: 300px;">
          <el-steps direction="vertical" :active="1">
            <el-step title="步骤 1" description="阅读注意事项"></el-step>
            <el-step title="步骤 2" description="建图"></el-step>
          </el-steps>
        </div>
      </el-aside>
      <el-container>
        <el-main>
          this is main
        </el-main>
      </el-container>
    </el-container>
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