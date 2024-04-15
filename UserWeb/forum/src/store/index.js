import { createStore } from 'vuex'

export default createStore({
  state: {
    articleDetail: null,
    activeTab: 0
  },
  getters: {
    getArticleDetail: (state) => state.articleDetail,
    getActiveTab: (state) => state.activeTab
  },
  mutations: {
    setArticleDetail (state, articleDetail) {
      state.articleDetail = articleDetail
    },
    setActiveTab (state, activeTab) {
      state.activeTab = activeTab
    }
  },
  actions: {},
  modules: {}
})
