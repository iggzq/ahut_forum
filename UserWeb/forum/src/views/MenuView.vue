<template>
  <router-view v-slot="{ Component }">
    <keep-alive>
      <component :is="Component"/>
    </keep-alive>
  </router-view>
  <van-tabbar v-if="!isTabbarHidden" v-model="active">
    <van-tabbar-item icon="home-o" to="/article" @click="handleArticleRefresh">首页</van-tabbar-item>
    <van-tabbar-item icon="fire-o" to="/hotRank">热榜</van-tabbar-item>
    <van-tabbar-item icon="manager-o" to="/myProfile">我的</van-tabbar-item>
  </van-tabbar>
</template>
<script setup>
import {computed, onMounted} from 'vue'
import {useRouter} from 'vue-router'
import {useStore} from 'vuex'

const router = useRouter()
const store = useStore()
const active = computed(() => store.state.activeTab)
const isTabbarHidden = computed(() => {
  return router.currentRoute.value.meta.hideTabbar
})
onMounted(() => {
  if (active.value === 0) {
    router.push({
      name: 'ArticleView'
    })
  } else if (active.value === 1) {
    router.push({
      name: 'HotRankView'
    })
  } else if (active.value === 2) {
    router.push({
      name: 'MyProfileView'
    })
  }
})

const handleArticleRefresh = () => {
  if (store.state.activeTab === 0) {
    store.state.isMainBack = false
    store.state.refreshArticle = true
  }
}
</script>
<style scoped>
</style>
