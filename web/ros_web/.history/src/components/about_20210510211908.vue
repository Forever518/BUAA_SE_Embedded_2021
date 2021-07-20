<template>
  <div class="bodyBoxOut">
    <mavon-editor class="markdown"
        :value="about"
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
  export default {
    data() {
      return {
        about: '',
      }
    },
    methods: {
      get_about_data() {
        this.help = '## About Content';
      }
    },
    mounted() {
      this.get_about_data();
    }
  }
</script>