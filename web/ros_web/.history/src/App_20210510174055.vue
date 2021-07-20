<template>
  <router-view/>
</template>

<script>
</script>

<style>
#app {
  font-family: 'Avenir', Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  text-align: center;
  color: #2c3e50;
  margin-top: 60px;
}
html,
body,
#app{
  height: 100%;
}
* {
  padding: 0;
  margin: 0;
}
.el-container {
  height: 100%
}
.el-menu--horizontal > .el-menu-item {
  border-bottom: none!important;
}
.el-menu--horizontal > .el-menu-item:hover {
  border-bottom: none!important;
  /* color: rgb(51, 51, 243)!important; */
}
.el-menu--horizontal > .el-menu-item.is-active {
  border-bottom: none!important;
}
.el-menu--horizontal {
  border-bottom: none!important;
}
.messageBox {
  height: 80%!important;
}
.bodyBoxOut {
  height: 100%!important;
  margin-left: 13%;
  margin-right: 13%;
}
.bodyBoxIn {
  height: 100%!important;;
}
.el-page-header {
  height: 100%!important;
  line-height: 60px!important;
}
.el-aside {
  width: 150px!important;
}
.el-steps {
  padding-left: 30%;
  padding-top: 30%;
}
.el-menu--collapse .el-menu .el-submenu, .el-menu--popup{
  min-width: 117px!important;
  background-color: transparent!important;
}
.el-submenu__title {
  background-color: transparent!important;
}
.el-menu--horizontal {
  background-color: transparent!important;
}
.homepage {
  background-image: url("../src/assets/b2.jpg");
  background-repeat: no-repeat;
  background-attachment: fixed;
  background-size: cover;
}
.el-menu-item {
  background-color: transparent!important;
}
.v-show-content {
  min-height: 100%!important;
}
</style>
