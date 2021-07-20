import Vue from 'vue'
import VueRouter from 'vue-router'
import Main from '../views/Main.vue'
import Mark from '../views/Mark.vue';
import Build from '../views/Build.vue';
import Serve from '../views/Serve.vue';

Vue.use(VueRouter)

const routes = [
  {
    path: '/Main',
    name: 'Main',
    component: Main
  },
  {
    path: '/Build',
    name: 'Build',
    component: Build
  },
  {
    path: '/Mark',
    name: 'Mark',
    component: Mark
  },
  {
    path: '/Serve',
    name: 'Serve',
    component: Serve
  },
  {
    path: '/',
    redirect: '/Main' 
  }
]

const router = new VueRouter({
  routes
})

export default router
