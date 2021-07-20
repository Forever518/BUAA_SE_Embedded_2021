import { createLocalVue, shallowMount } from '@vue/test-utils'
import Main from '../../../src/views/Main.vue';
import Build from '../../../src/views/Build.vue';
import Mark from '../../../src/views/Mark.vue';
import Serve from '../../../src/views/Serve.vue';
import ElementUI from 'element-ui';
import VueRouter from 'vue-router';

const localVue = createLocalVue()
localVue.use(ElementUI)
localVue.use(VueRouter)
const router = new VueRouter()

const mainWrapper = shallowMount(Main, {
  localVue,
  router
})
const buildWrapper = shallowMount(Build, {
  localVue,
  router
})
const markWrapper = shallowMount(Mark, {
  localVue,
  router
})
const serveWrapper = shallowMount(Serve, {
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
  it('测试用例3',() => {
    expect(mainWrapper.html()).toContain("小智")
  })
  it('测试用例4',() => {
    expect(mainWrapper.html()).toContain("小智")
  })
  it('测试用例5',() => {
    expect(mainWrapper.html()).toContain("小智")
  })
  it('测试用例6',() => {
    expect(mainWrapper.html()).toContain("小智")
  })
  it('测试用例7',() => {
    expect(mainWrapper.html()).toContain("小智")
  })
  it('测试用例8',() => {
    expect(mainWrapper.html()).toContain("小智")
  })
  it('测试用例9',() => {
    expect(mainWrapper.html()).toContain("小智")
  })
  it('测试用例10',() => {
    expect(mainWrapper.html()).toContain("小智")
  })
  it('测试用例11',() => {
    expect(mainWrapper.html()).toContain("小智")
  })
})

describe('建图模式页面跳转逻辑测试',() => {
  it('测试用例1',() => {
    expect(buildWrapper.html()).toContain("建图模式")
  })
  it('测试用例2',() => {
    expect(buildWrapper.html()).toContain("建图模式")
  })
  it('测试用例3',() => {
    expect(buildWrapper.html()).toContain("建图模式")
  })
})

describe('标注模式页面跳转逻辑测试',() => {
  it('测试用例1',() => {
    expect(markWrapper.html()).toContain("标注模式")
  })
})

describe('服务模式页面跳转逻辑测试',() => {
  it('测试用例1',() => {
    expect(serveWrapper.html()).toContain("服务模式")
  })
})