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
            path: '/articlDetail/:articleId&&:commentId?',
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
  }, {
    path: '/commentArticlDetail/:articleId&&:commentId?',
    name: 'commentArticleDetail',
    component: () => import('../views/inside/ArticleDetailView.vue')
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
