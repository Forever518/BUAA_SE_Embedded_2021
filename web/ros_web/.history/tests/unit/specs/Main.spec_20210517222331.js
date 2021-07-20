import { createLocalVue, shallowMount, config } from '@vue/test-utils'
import Main from '../../../src/views/Main.vue';
import ElementUI from 'element-ui';
import VueRouter from 'vue-router';

config.showDeprecationWarnings = true

const localVue = createLocalVue()
localVue.use(ElementUI)
localVue.use(VueRouter)
const router = new VueRouter()

const mainWrapper = shallowMount(Main, {
  localVue,
  router
})

describe('主页跳转逻辑测试',() => {
  it('测试用例1',() => {
    expect(mainWrapper.html()).toContain("小智")
  })
  it('测试用例2',() => {
    expect(mainWrapper.html()).toContain("小智")
  })
})