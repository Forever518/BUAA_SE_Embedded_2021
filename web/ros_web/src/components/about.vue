<template>
  <div class="bodyBoxOut out_mark_t">
    <mavon-editor class="markdown"
        :value="about"
        :subfield = "false"    
        :defaultOpen = "'preview'"
        :toolbarsFlag = "false"
        :editable="false"
        :scrollStyle="false"
        :boxShadow = "false"
        :previewBackground = "'transparent'"
      ></mavon-editor>
  </div>
</template>

<script>
import {post_getDirect} from '../api/request';
  export default {
    data() {
      return {
        about: '',
      }
    },
    methods: {
      get_about_data() {
        post_getDirect({
          name: 'about'
        }).then((res) => {
          if (res.code === 200) {
            this.about = res.data;
          }
        })
      }
    },
    mounted() {
      this.get_about_data();
    }
  }
</script>