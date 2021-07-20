import Vue from 'vue'
import App from './App.vue'
import router from './router'
import { mavonEditor } from 'mavon-editor'
import 'mavon-editor/dist/css/index.css'
import './plugins/element.js'

Vue.component("mavon-editor", mavonEditor);
Vue.config.productionTip = false

new Vue({
  router,
  render: h => h(App)
}).$mount('#app')
