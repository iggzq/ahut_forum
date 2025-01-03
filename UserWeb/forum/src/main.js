import {createApp} from 'vue'
import App from './App.vue'
import router from './router'
import store from './store'
import {
  Badge,
  Button,
  Cell,
  CellGroup,
  Col,
  ConfigProvider,
  DropdownItem,
  DropdownMenu,
  Empty,
  Field,
  Form,
  Grid,
  GridItem,
  Highlight,
  Icon,
  Loading,
  NavBar,
  Picker,
  Popup,
  Row,
  Search,
  showFailToast,
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
  .use(Loading).use(Empty).use(Col).use(Row).use(DropdownItem).use(DropdownMenu).use(Search)
  .use(ConfigProvider).use(Highlight)
  .mount('#app')
axios.defaults.baseURL = process.env.VUE_APP_BASE_URL

axios.interceptors.request.use((config) => {
  config.headers.satoken = localStorage.getItem('satoken')
  return config
})

axios.interceptors.response.use((response) => {
  if (response.data.code === 401) {
    showFailToast(response.data.desc)
    router.push('/login')
  }
  return response
})
