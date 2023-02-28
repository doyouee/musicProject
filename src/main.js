import { createApp } from 'vue'
// import Vue from 'vue'
import App from './App.vue'
import axios from 'axios'
import router from './router'

const app = createApp(App).use(router)
// Vue.prototype.$http=axios;

app.config.globalProperties.$bulidMode = "DEVELOP"
app.config.globalProperties.$userToken = ""
//기본 URL설정
if(app.config.globalProperties.$bulidMode=="DEVELOP"){
    // 개발 server
    axios.defaults.baseURL="http://localhost:8586"
}
if(app.config.globalProperties.$bulidMode=="RELEASE"){
    // 운영 server
    axios.defaults.baseURL="http://flo.com:8585"
}
// createApp(App).mount('#app')

//this.$http를 사용해서 axios를 모든 컴포넌트에서 사용가능하게 함
app.config.globalProperties.$http = axios
app.mount("#app")

