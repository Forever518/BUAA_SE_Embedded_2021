<template>
  <div class="bodyBoxOut">
    <div class="messageBox">
      <mavon-editor class="markdown"
        :value="tips"
        :subfield = "false"    
        :defaultOpen = "'preview'"
        :toolbarsFlag = "false"
        :editable="false"
        :scrollStyle="false"
        :boxShadow = "true"
      ></mavon-editor>
    </div>
    <el-row :gutter="20" type="flex" justify="space-around">
      <el-col :span="4">
      </el-col>
      <el-col :span="4">
        <el-button @click="serveEnd">结束服务</el-button>
      </el-col>
    </el-row>
  </div>
</template>

<script>
import {post_stopService} from '../api/request'
  export default {
    data() {
      return {
        tips: '',
      }
    },
    methods: {
      serveEnd() {
        const h = this.$createElement;
        this.$msgbox({
          title: '消息',
          message: h('p', null, [
            h('span', null, '确认要停止服务吗？'),
          ]),
          showCancelButton: true,
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          beforeClose: (action, instance, done) => {
            if (action === 'confirm') {
              instance.confirmButtonLoading = true;
              instance.confirmButtonText = '正在停止...';
              post_stopService().then(() => {
                setTimeout(() => {
                  done();
                  setTimeout(() => {
                    instance.confirmButtonLoading = false;
                  }, 200);
                }, 2 * 1000);
              })
            } else {
              done();
            }
          }
        }).then(() => {
          this.$message({
            type: 'success',
            message: '服务已经成功停止',
            duration: 2000
          });
          this.$router.push({
            name: 'Main',
          })
        });
      },
      get_tips_data() {
        this.tips = '## Serving Tips';
      }
    },
    mounted() {
      this.get_tips_data();
    }
  }
</script>