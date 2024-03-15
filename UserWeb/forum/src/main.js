import { createApp } from 'vue'
import App from './App.vue'
import router from './router'
import store from './store'
import { Button, Field, Form, Swipe, SwipeItem, Tab, Tabbar, TabbarItem, Tabs, Toast } from 'vant'
import 'vant/lib/index.css'
import axios from 'axios'

const app = createApp(App)
app.use(Form).use(Button).use(Field).use(Toast).use(store).use(router)
  .use(Swipe).use(SwipeItem).use(Tabbar).use(TabbarItem).use(Tab).use(Tabs)
  .mount('#app')
axios.defaults.baseURL = 'http://172.20.10.3:8080/'
