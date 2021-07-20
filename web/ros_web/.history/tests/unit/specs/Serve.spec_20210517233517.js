import { createLocalVue, shallowMount } from '@vue/test-utils'
import Serve from '../../../src/views/Serve.vue';
import ElementUI from 'element-ui';
import VueRouter from 'vue-router';

const localVue = createLocalVue()
localVue.use(ElementUI)
localVue.use(VueRouter)
const router = new VueRouter()

const serveWrapper = shallowMount(Serve, {
  localVue,
  router
})

describe('服务模式页面跳转逻辑测试',() => {
  it('测试用例1',() => {
    expect(serveWrapper.html()).toContain("服务模式")
  })
})