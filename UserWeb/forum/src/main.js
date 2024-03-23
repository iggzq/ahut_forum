import { createApp } from 'vue'
import App from './App.vue'
import router from './router'
import store from './store'
import {
  Button,
  Field,
  Form,
  Grid,
  GridItem,
  Icon,
  NavBar,
  Skeleton,
  Sticky,
  Swipe,
  SwipeItem,
  Tab,
  Tabbar,
  TabbarItem,
  Tabs,
  Tag,
  TextEllipsis,
  Toast
} from 'vant'
import 'vant/lib/index.css'
import Varlet from '@varlet/ui'
import '@varlet/ui/es/style'
import axios from 'axios'
import '@/assets/text/text.css'
import 'animate.css'
import 'animate.css/animate.min.css'
import UComment from 'undraw-ui'
import 'undraw-ui/dist/style.css'

const app = createApp(App)
app.use(Form).use(Button).use(Field).use(Toast).use(store).use(router)
  .use(Swipe).use(SwipeItem).use(Tabbar).use(TabbarItem).use(Tab).use(Tabs).use(NavBar)
  .use(Grid).use(GridItem).use(Skeleton).use(Varlet).use(Tag).use(Sticky).use(TextEllipsis)
  .use(Icon).use(UComment)
  .mount('#app')
axios.defaults.baseURL = 'http://172.20.10.3:8080/'

axios.interceptors.request.use((config) => {
  config.headers.satoken = localStorage.getItem('satoken')
  return config
})
