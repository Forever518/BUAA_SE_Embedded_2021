<template>
  <el-container>
    <el-header>
      <el-page-header @back="goBack" content="服务模式">
      </el-page-header>
    </el-header>
    <el-container>
      <el-aside>
        <div style="height: 300px; padding-top: 60px">
          <el-steps direction="vertical" :active="nowStep">
            <el-step title="步骤 1" description="阅读注意事项"></el-step>
            <el-step title="步骤 2" description="服务"></el-step>
          </el-steps>
        </div>
      </el-aside>
      <el-container>
        <el-main>
          <attentions @next-step-click="attentions_click" :type="type" v-if="step === 'attentions'"></attentions>
          <serving v-if="step === 'serving'"></serving>
        </el-main>
      </el-container>
    </el-container>
  </el-container>  
</template>

<script>
  import attentions from '../components/attentions';
  import serving from '../components/serving';
  export default {
    data() {
      return {
        step: 'attentions',
        type: 'serve',
        nowStep: 1,
      }
    },
    methods: {
      goBack() {
        this.$router.push({
          name: 'Main'
        })
      },
      attentions_click(next_component) {
        this.step = next_component;
        this.nowStep = 2;
      }
    },
    components: {
      attentions,
      serving,
    }
  }
</script>