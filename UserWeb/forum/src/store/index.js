import { createStore } from 'vuex'

export default createStore({
  state: {
    articleDetail: null,
    activeTab: 0,
    refreshArticle: false
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
    },
    SET_REFRESH_ARTICLE (state, payload) {
      state.refreshArticle = payload
    }
  },
  actions: {
    triggerArticleRefresh ({ commit }) {
      commit('SET_REFRESH_ARTICLE', true)
    }
  },
  modules: {}
})
