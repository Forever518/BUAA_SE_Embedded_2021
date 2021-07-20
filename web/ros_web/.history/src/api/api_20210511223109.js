import axios from 'axios'
import {
  Message,
  Loading
} from "element-ui";

const baseURL = "http://10.135.250.218:8081";

let loading = "";

const service = axios.create({
  baseURL: baseURL,
  timeout: 30000,
})

service.interceptors.request.use(
  (config) => {
    console.log(config.data)
    loading = Loading.service({
      lock: true,
      text: "加载中...",
      spinner: "el-icon-loading",
      background: "rgba(255,255,255,0.7)",
      customClass: "request-loading",
    });
    if (config.method == 'post') {
      config.headers['Content-Type'] = 'application/x-www-form-urlencoded;charset=UTF-8';
      let params = new URLSearchParams();
      for (let item in config.data) {
        console.log(item)
        params.append(item, config.data['' + item + '']);
      }
      config.data = params;
    }
    return config
  },
  (error) => {
    loading.close();
    console.log('发送失败', error)
    return Promise.reject(error)
  }
)

service.interceptors.response.use(
  (response) => {
    loading.close();
    switch(response.data.code) {
      case 200:
        Message.success({
          message: response.data.msg,
          duration: 2000
        })
        break;
      case 400:
        Message.error({
          message: '系统繁忙，请稍后重试',
          duration: 2000
        })
        break;
      case 603:
        Message.error({
          message: '地图名称重复！',
          duration: 2000
        })
        break;
      case 601,602,630:
        Message.error({
          message: '出现未知错误，请联系管理员，错误代码：' + response.code,
          duration: 2000
        })
        switch(response.code) {
          case 601:
            console.log('保存地图异常');
            break;
          case 602:
            console.log('删除地图异常');
            break;
          case 630:
            console.log('地图文件操作异常');
            break;
          default:
            break;
        }
        break;
      default:
        break;
    }
    return response.data;
  },
  (error) => {
    Message({
      message: '系统错误，请稍后重试！',
      type: 'error',
      duration: 2000
    })
    loading.close();
    console.log(error);
    return Promise.reject(error);
});

export default service