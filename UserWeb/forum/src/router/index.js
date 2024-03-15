import { createRouter, createWebHashHistory } from 'vue-router'

const routes = [
  {
    path: '/',
    name: '首页',
    component: () => import('../views/LoginView.vue')
  },
  {
    path: '/login',
    name: '登陆',
    component: () => import('../views/LoginView.vue')
  },
  {
    path: '/register',
    name: '注册',
    component: () => import('../views/RegisterView.vue')
  }
]

const router = createRouter({
  history: createWebHashHistory(),
  routes
})

export default router
