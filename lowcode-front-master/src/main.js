import Vue from 'vue'
import App from './App.vue'
import router from './router'
import store from './store'

import Element from 'element-ui'
import 'element-ui/lib/theme-chalk/index.css'
import api from './api'

import meta from 'vue-meta'
import dayjs from 'dayjs'
import Cookies from 'js-cookie'
import './assets/icons'
import VueClipboard from 'vue-clipboard2'
import './plugin'
import '@/assets/styles/index.scss' // global css
// import '@/mock/index'


Vue.prototype.HOST = 'api'
Vue.config.productionTip = false
Vue.use(VueClipboard)
Vue.use(meta)
Vue.use(Element, { size: 'small', zIndex: 3000 })
Vue.config.productionTip = false

Vue.prototype.$api = api
Vue.prototype.$dayjs = dayjs
Vue.prototype.$store = store
Vue.prototype.$cookies = Cookies
Vue.prototype.msgSuccess = function (msg) {
  this.$message({ showClose: true, message: msg, type: 'success' })
}
Vue.prototype.msgError = function (msg) {
  this.$message({ showClose: true, message: msg, type: 'error' })
}
Vue.prototype.msgWarning = function (msg) {
  this.$message({ showClose: true, message: msg, type: 'warning' })
}
Vue.prototype.msgInfo = function (msg) {
  this.$message.info(msg)
}

new Vue({
  router,
  store,
  render: h => h(App)
}).$mount('#app')
