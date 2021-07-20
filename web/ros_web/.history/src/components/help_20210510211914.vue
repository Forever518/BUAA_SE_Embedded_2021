<template>
  <div class="bodyBoxOut">
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
  export default {
    data() {
      return {
        help: '',
      }
    },
    methods: {
      get_help_data() {
        this.help = '## Help Content';
      }
    },
    mounted() {
      this.get_help_data();
    }
  }
</script>