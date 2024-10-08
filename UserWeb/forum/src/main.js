import { createApp } from 'vue'
import App from './App.vue'
import router from './router'
import store from './store'
import {
  Badge,
  Button,
  Cell,
  CellGroup,
  Field,
  Form,
  Grid,
  GridItem,
  Icon,
  Loading,
  NavBar,
  Picker,
  Popup,
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
  .use(Icon).use(UComment).use(Cell).use(CellGroup).use(Badge).use(Picker).use(Popup)
  .use(Loading)
  .mount('#app')
axios.defaults.baseURL = process.env.VUE_APP_BASE_URL

axios.interceptors.request.use((config) => {
  config.headers.satoken = localStorage.getItem('satoken')
  return config
})
