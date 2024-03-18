import { createStore } from 'vuex'

export default createStore({
  state: {
    articleDetail: null
  },
  getters: {
    getArticleDetail: (state) => state.articleDetail
  },
  mutations: {
    setArticleDetail (state, articleDetail) {
      state.articleDetail = articleDetail
    }
  },
  actions: {
  },
  modules: {
  }
})
