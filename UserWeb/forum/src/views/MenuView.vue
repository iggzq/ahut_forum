<template>
  <router-view/>
  <van-tabbar  v-if="!isTabbarHidden" v-model="active" route>
    <van-tabbar-item icon="home-o" to="/article">文章</van-tabbar-item>
    <van-tabbar-item icon="manager-o" to="/myProfile">我的</van-tabbar-item>
  </van-tabbar>
</template>
<script>
import { computed, defineComponent, onMounted } from 'vue'
import { useRouter } from 'vue-router'
import { useStore } from 'vuex'

export default defineComponent({
  setup () {
    const router = useRouter()
    const store = useStore()
    const active = store.state.activeTab
    const isTabbarHidden = computed(() => {
      return router.currentRoute.value.meta.hideTabbar
    })
    onMounted(() => {
      router.push(active === 0 ? '/article' : '/myProfile')
    })
    return {
      active,
      isTabbarHidden
    }
  }
})
</script>
<style scoped>

</style>
