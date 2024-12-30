import {createStore} from 'vuex'

export default createStore({
  state: {
    articleDetail: null,
    activeTab: 0,
    refreshArticle: false,
    isMainBack: true
  },
  getters: {
    getArticleDetail: (state) => state.articleDetail,
    getActiveTab: (state) => state.activeTab,
    getRefreshArticle: (state) => state.refreshArticle,
    getIsMainBack: (state) => state.isMainBack
  },
  mutations: {
    setArticleDetail(state, articleDetail) {
      state.articleDetail = articleDetail
    },
    setActiveTab(state, activeTab) {
      state.activeTab = activeTab
    },
    SET_REFRESH_ARTICLE(state, payload) {
      state.refreshArticle = payload
    },
    SET_IS_MAIN_BACK(state, payload) {
      state.isMainBack = payload
    }
  },
  actions: {
    triggerArticleRefresh({commit}) {
      commit('SET_REFRESH_ARTICLE', true)
    }
  },
  modules: {}
})
