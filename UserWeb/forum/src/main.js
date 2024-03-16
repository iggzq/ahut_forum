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
  Toast
} from 'vant'
import 'vant/lib/index.css'
import Varlet from '@varlet/ui'
import '@varlet/ui/es/style'
import axios from 'axios'

const app = createApp(App)
app.use(Form).use(Button).use(Field).use(Toast).use(store).use(router)
  .use(Swipe).use(SwipeItem).use(Tabbar).use(TabbarItem).use(Tab).use(Tabs).use(NavBar)
  .use(Grid).use(GridItem).use(Skeleton).use(Varlet).use(Tag).use(Sticky)
  .mount('#app')
axios.defaults.baseURL = 'http://172.20.10.3:8080/'
