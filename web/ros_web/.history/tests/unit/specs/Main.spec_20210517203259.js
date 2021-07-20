import { shallowMount } from '@vue/test-utils'
import Main from '../../../src/views/Main.vue';

const mainWrapper = shallowMount(Main)

describe('主页单元测试',() => {
  it('测试用例1',() => {
    expect(mainWrapper.html()).toContain("小智")
  })
})