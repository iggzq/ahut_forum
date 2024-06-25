import { createRouter, createWebHashHistory } from 'vue-router'

const routes = [
  {
    path: '/',
    name: '首页',
    component: () => import('../views/MenuView.vue'),
    children: [
      {
        path: '/article',
        name: 'article',
        component: () => import('../views/ArticleView.vue'),
        children: [
          {
            path: '/article/:articleId&&:commentId?',
            name: 'articleDetail',
            component: () => import('../views/inside/ArticleDetailView.vue'),
            meta: {
              hideTabbar: true
            }
          }
        ]
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
    path: '/test',
    name: 'test',
    component: () => import('../views/TestView.vue')
  }, {
    path: '/myComments',
    name: 'myComments',
    component: () => import('../views/inside/MyComments.vue')
  }, {
    path: '/chatroom',
    name: 'ChatRoom',
    component: () => import('../views/ChatRoom.vue')
  }

]

const router = createRouter({
  history: createWebHashHistory(),
  routes
})
export default router
