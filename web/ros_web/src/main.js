import Vue from 'vue';
import App from './App.vue';
import router from './router';
import { mavonEditor } from 'mavon-editor';
import axios from 'axios';
import jsCookie from 'js-cookie';
import VueParticles from 'vue-particles';
import 'mavon-editor/dist/css/index.css';
import './plugins/element.js';

Vue.prototype.$axios = axios;
Vue.prototype.$cookie = jsCookie;
Vue.component("mavon-editor", mavonEditor);
Vue.use(VueParticles);
Vue.config.productionTip = false;

new Vue({
  router,
  render: h => h(App)
}).$mount('#app')
