<template>
  <div class="bodyBoxOut out_mark_t">
    <mavon-editor class="markdown"
        :value="help"
        :subfield = "false"    
        :defaultOpen = "'preview'"
        :toolbarsFlag = "false"
        :editable="false"
        :scrollStyle="false"
        :boxShadow = "false"
      ></mavon-editor>
  </div>
</template>

<script>
import { post_getDirect } from "../api/request";
  export default {
    data() {
      return {
        help: '',
      }
    },
    methods: {
      get_help_data() {
        post_getDirect({
          name: 'all'
        }).then((res) => {
          if (res.code === 200) {
            this.help = res.data;
          }
        })
      }
    },
    mounted() {
      this.get_help_data();
    }
  }
</script>