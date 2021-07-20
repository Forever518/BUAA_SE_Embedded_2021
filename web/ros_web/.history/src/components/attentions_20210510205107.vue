<template>
  <div class="bodyBoxOut">
    <div v-if="type === 'build'" class="bodyBoxIn">
      <div class="messageBox">
        <mavon-editor class="markdown"
          :value="attention"
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
          <el-checkbox v-model="haveRead">我已阅读完毕</el-checkbox>
        </el-col>
        <el-col :span="4">
          <el-button @click="back(next_component)">开始建图</el-button>
        </el-col>
      </el-row>
    </div>
    <div v-if="type === 'mark'" class="bodyBoxIn">
      <div class="messageBox">
        <mavon-editor class="markdown"
          :value="attention"
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
          <el-checkbox v-model="haveRead">我已阅读完毕</el-checkbox>
        </el-col>
        <el-col :span="4">
          <el-button @click="back(next_component)">开始标注</el-button>
        </el-col>
      </el-row>
    </div>
    <div v-if="type === 'serve'" class="bodyBoxIn">
      <div class="messageBox">
        <mavon-editor class="markdown"
          :value="attention"
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
          <el-checkbox v-model="haveRead">我已阅读完毕</el-checkbox>
        </el-col>
        <el-col :span="4">
          <el-button @click="back(next_component)">开始服务</el-button>
        </el-col>
      </el-row>
    </div>
  </div>
</template>

<script>
  export default {
   data() {
     return {
       haveRead: false,
       attention: '',
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
       switch (this.type) {
         case 'build':
           if(this.haveRead === true) {
              this.$emit('next-step-click', next_component);
              this.$notify({
                title: '开始建图',
                message: '请跟随提示在Rviz界面完成建图',
                type: 'success',
                showClose: false,
                duration: 2000,
              })
            } else {
              this.$notify({
                title: '警告',
                message: '请先阅读注意事项',
                type: 'warning',
                showClose: false,
                duration: 2000,
              })
            }
            break;
          case 'mark':
           if(this.haveRead === true) {
              this.$emit('next-step-click', next_component);
              this.$notify({
                title: '开始标注',
                message: '请跟随提示在Rviz界面完成标注',
                type: 'success',
                showClose: false,
                duration: 2000,
              })
            } else {
              this.$notify({
                title: '警告',
                message: '请先阅读注意事项',
                type: 'warning',
                showClose: false,
                duration: 2000,
              })
            }
            break;
          case 'serve':
           if(this.haveRead === true) {
              this.$emit('next-step-click', next_component);
              this.$notify({
                title: '开始服务',
                message: '请跟随提示使用服务',
                type: 'success',
                showClose: false,
                duration: 2000,
              })
            } else {
              this.$notify({
                title: '警告',
                message: '请先阅读注意事项',
                type: 'warning',
                showClose: false,
                duration: 2000,
              })
            }
            break;
          default:
            this.$notify.error({
              title: '错误',
              message: '出现未知错误',
              showClose: false,
              duration: 2000,
            })
       }
     },
     get_attention_data() {
       if (type === 'build') {
         this.attention = '## Building Attention';
       } else if (type === 'mark') {
         this.attention = '## Marking Attention';
       } else {
         this.attention = '## Serving Attention';
       }
     }
   },
   mounted() {
     this.get_attention_data();
   },
   computed: {
     next_component() {
       if(this.type === 'build') {
         return 'building';
       } else if(this.type === 'mark') {
         return 'marking';
       } else {
         return 'serving';
       }
     }
   }
  }
</script>
