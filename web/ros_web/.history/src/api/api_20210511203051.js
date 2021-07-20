import axios from 'axios'
import {
  Message,
  Loading
} from "element-ui";

const baseURL = "http:// :8081";

let loading = "";

const service = axios.create({
  baseURL: baseURL,
  timeout: 30000,
})

service.interceptors.request.use(
  (config) => {
    if (!(config.headers['Content-Type'])) {
      loading = Loading.service({
        lock: true,
        text: "加载中...",
        spinner: "el-icon-loading",
        background: "rgba(255,255,255,0.7)",
        customClass: "request-loading",
      });
      if (config.method == 'post') {
        config.headers['Content-Type'] = 'application/x-www-form-urlencoded;charset=UTF-8'
      }
    }
    return config
  },
  (error) => {
    loading.close();
    console.log('发送失败', error)
    return Promise.reject(error)
  }
)

serviceaxios.interceptors.response.use(
  (response) => {
    loading.close();
    const dataAxios = response.data
    return dataAxios
  },
  (error) => {
    Message({
      message: '系统错误，请稍后重试！',
      type: 'error',
      duration: 2000
    })
    return Promise.reject(error);
});

export default service