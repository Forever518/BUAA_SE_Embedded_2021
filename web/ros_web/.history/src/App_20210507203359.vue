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
  color: rgb(167, 167, 255)!important;
}
.el-menu--horizontal > .el-menu-item.is-active {
  border-bottom: none!important;
}
.el-menu--horizontal {
  border-bottom: none!important;
}
</style>
