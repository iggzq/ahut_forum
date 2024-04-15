<template>
  <router-view/>
  <van-tabbar v-model="active" route >
    <van-tabbar-item icon="home-o" replace to="/article">文章</van-tabbar-item>
    <van-tabbar-item icon="manager-o" replace  to="/myProfile">我的</van-tabbar-item>
  </van-tabbar>
</template>
<script>
import { defineComponent, onMounted } from 'vue'
import { useRouter } from 'vue-router'
import { useStore } from 'vuex'

export default defineComponent({
  setup () {
    const router = useRouter()
    const store = useStore()
    const active = store.state.activeTab
    onMounted(() => {
      router.push(active === 0 ? '/article' : '/myProfile')
    })
    return {
      active
    }
  }
})
</script>
<style scoped>

</style>
