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
  export default {
    data() {
      return {
        about: '',
      }
    },
    methods: {
      get_about_data() {
        this.about = '## About Content';
      }
    },
    mounted() {
      this.get_about_data();
    }
  }
</script>