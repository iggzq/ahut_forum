<template>
  <router-view/>
  <van-tabbar v-model="active" route>
    <van-tabbar-item icon="home-o" replace to="/article">文章</van-tabbar-item>
    <van-tabbar-item icon="manager-o" replace  to="/myProfile">我的</van-tabbar-item>
  </van-tabbar>
</template>
<script>
import { defineComponent, onMounted } from 'vue'
import { useRouter } from 'vue-router'

export default defineComponent({
  setup () {
    const router = useRouter()
    onMounted(() => {
      router.push('/article')
    })
    return {
      active: 0
    }
  }
})
</script>
<style scoped>

</style>
