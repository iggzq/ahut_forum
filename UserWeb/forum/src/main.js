import { createApp } from 'vue'
import App from './App.vue'
import router from './router'
import store from './store'
import { Button, Field, Form } from 'vant'
import 'vant/lib/index.css'
import axios from 'axios'

const app = createApp(App)
app.use(Form).use(Button).use(Field).use(store).use(router).mount('#app')
axios.defaults.baseURL = 'http://localhost:8080/'
