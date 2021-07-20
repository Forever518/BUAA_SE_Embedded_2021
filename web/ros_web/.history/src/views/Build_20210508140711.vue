<template>
  <el-container>
    <el-header>
      <el-page-header @back="goBack" content="建图模式">
      </el-page-header>
    </el-header>
    <el-container>
      <el-aside>
        <div style="height: 300px; padding-top: 60px">
          <el-steps direction="vertical" :active="1">
            <el-step title="步骤 1" description="阅读注意事项"></el-step>
            <el-step title="步骤 2" description="建图"></el-step>
          </el-steps>
        </div>
      </el-aside>
      <el-container>
        <el-main>
          <attentions @gochild="to" :type="type" v-if="step === 'attentions'"></attentions>
          <building v-if="step === 'building'"></building>
        </el-main>
      </el-container>
    </el-container>
  </el-container>  
</template>

<script>
  import attentions from '../components/attentions';
  import building from '../components/buildings';
  export default {
    data() {
      return {
        step: 'attentions',
        type: 'build',
      }
    },
    methods: {
      goBack() {
        this.$router.push({
          name: 'Main'
        })
      },
      to(arg) {
        console.log(arg);
      }
    },
    components: {
      attentions,
      building,
    }
  }
</script>

<style>
.el-page-header {
  height: 100%!important;
  line-height: 60px!important;
}
.el-aside {
  width: 150px!important;
}
</style>