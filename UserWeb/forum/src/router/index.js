import { createRouter, createWebHashHistory } from 'vue-router'

const routes = [
  {
    path: '/',
    name: '首页',
    component: () => import('../views/MenuView.vue'),
    meta: {
      keepAlive: true
    },
    children: [
      {
        path: 'article',
        name: 'ArticleView',
        component: () => import('../views/ArticleView.vue'),
        meta: {
          keepAlive: true
        },
        children: [
          {
            path: 'articleDetail/:articleId/:commentId?',
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
        name: 'MyProfileView',
        component: () => import('../views/MyProfileView.vue'),
        meta: {
          keepAlive: true // 表示此路由不需要被缓存
        }
      }, {
        path: '/hotRank',
        name: 'HotRank',
        component: () => import('../views/HotRankView.vue'),
        meta: {
          keepAlive: false
        }
      }
    ]
  },
  {
    path: '/commentArticlDetail/:articleId/:commentId?',
    name: 'commentArticleDetail',
    component: () => import('../views/inside/ArticleDetailView.vue'),
    meta: {
      keepAlive: false // 表示此路由不需要被缓存
    }
  },
  {
    path: '/login',
    name: '登陆',
    component: () => import('../views/LoginView.vue'),
    meta: {
      keepAlive: false // 表示此路由不需要被缓存
    }
  }, {
    path: '/register',
    name: '注册',
    component: () => import('../views/RegisterView.vue'),
    meta: {
      keepAlive: false // 表示此路由不需要被缓存
    }
  }, {
    path: '/myComments',
    name: 'myComments',
    component: () => import('../views/inside/MyComments.vue'),
    meta: {
      keepAlive: false // 表示此路由不需要被缓存
    }
  }, {
    path: '/chatroom',
    name: 'ChatRoom',
    component: () => import('../views/ChatRoom.vue'),
    meta: {
      keepAlive: false
    }
  }, {
    path: '/topic',
    name: 'TopicView',
    component: () => import('../views/TopicView.vue'),
    meta: {
      keepAlive: false
    }
  }, {
    path: '/topic/:topicId/:topicName',
    name: 'topicArticle',
    component: () => import('../views/inside/TopicArticleView.vue'),
    meta: {
      keepAlive: true
    },
    children: [
      {
        path: 'articleDetail/:articleId/:commentId?',
        name: 'topicArticleDetail',
        component: () => import('../views/inside/ArticleDetailView.vue')
      }
    ]
  }
]

const router = createRouter({
  history: createWebHashHistory(),
  routes
})
export default router
