import { createRouter, createWebHashHistory } from 'vue-router'

const routes = [
  {
    path: '/',
    name: '首页',
    component: () => import('../views/MenuView.vue'),
    children: [
      {
        path: '',
        name: 'article',
        component: () => import('../views/ArticleView.vue')
      }, {
        path: '/article',
        name: 'article',
        component: () => import('../views/ArticleView.vue')
      },
      {
        path: '/myProfile',
        name: 'myProfile',
        component: () => import('../views/MyProfileView.vue')
      }
    ]
  },
  {
    path: '/login',
    name: '登陆',
    component: () => import('../views/LoginView.vue')
  }, {
    path: '/register',
    name: '注册',
    component: () => import('../views/RegisterView.vue')
  }, {
    path: '/articleDetail',
    name: 'articleDetail',
    component: () => import('../views/inside/ArticleDetailView.vue')
  }, {
    path: '/test',
    name: 'test',
    component: () => import('../views/TestView.vue')
  }

]

const router = createRouter({
  history: createWebHashHistory(),
  routes
})

export default router
