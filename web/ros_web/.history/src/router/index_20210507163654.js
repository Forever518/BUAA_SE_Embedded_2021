import Vue from 'vue'
import VueRouter from 'vue-router'
import Main from '../views/Main.vue'

Vue.use(VueRouter)

const routes = [
  {
    path: '/Main',
    name: 'Main',
    component: Main
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
