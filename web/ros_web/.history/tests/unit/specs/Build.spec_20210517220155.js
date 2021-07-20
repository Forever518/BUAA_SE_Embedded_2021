import { createLocalVue, shallowMount } from '@vue/test-utils'
import Build from '../../../src/views/Build.vue';
import ElementUI from 'element-ui';
import VueRouter from 'vue-router';

const localVue = createLocalVue()
localVue.use(ElementUI)
localVue.use(VueRouter)
const router = new VueRouter()

const mainWrapper = shallowMount(Build, {
  localVue,
  router
})

describe('主页单元测试',() => {
  it('测试用例1',() => {
    expect(mainWrapper.html()).toContain("建图模式")
  })
})