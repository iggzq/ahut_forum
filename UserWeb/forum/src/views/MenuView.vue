<template>
  <router-view v-slot="{ Component }">
    <keep-alive>
      <component :is="Component"/>
    </keep-alive>
  </router-view>
  <van-tabbar v-if="!isTabbarHidden" v-model="active">
    <van-tabbar-item icon="home-o" to="/article" @click="handleArticleRefresh">首页</van-tabbar-item>
    <van-tabbar-item icon="manager-o" to="/myProfile">我的</van-tabbar-item>
  </van-tabbar>
</template>
<script setup>
import { computed, onMounted } from 'vue'
import { useRouter } from 'vue-router'
import { useStore } from 'vuex'

const router = useRouter()
const store = useStore()
const active = store.state.activeTab
const isTabbarHidden = computed(() => {
  return router.currentRoute.value.meta.hideTabbar
})

onMounted(() => {
  console.log('active: ' + active)
  if (active === 0) {
    router.push({
      name: 'ArticleView'
    })
  } else {
    router.push({
      name: 'MyProfileView'
    })
  }
})

const handleArticleRefresh = () => {
  if (store.state.activeTab === 0) {
    console.log('handleArticleRefresh')
    store.dispatch('triggerArticleRefresh')
  }
}
</script>
<style scoped>
</style>
