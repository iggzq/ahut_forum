<template>
  <van-sticky :offset-top="0">
    <van-nav-bar title="个人信息"/>
  </van-sticky>
  <van-cell class="myComments" size="large" @click="goMyComments">
    <template #title>
      <van-badge dot is-link>
        我的消息
      </van-badge>
    </template>
    <template #value>
    </template>
  </van-cell>
</template>
<script setup>
import router from '@/router'
import {onActivated, onMounted} from 'vue'
import {useStore} from 'vuex'

onMounted(() => {
  store.commit('setActiveTab', 2)
  if (localStorage.getItem('satoken') == null) {
    router.push('/login')
  }
})
onActivated(() => {
  store.commit('setActiveTab', 2)
})

const store = useStore()
const goMyComments = () => {
  store.commit('setActiveTab', 2)
  router.push('/myComments')
}
</script>
<script>
export default {
  name: 'MyProfileView'
}
</script>
<style scoped>
@import "@/assets/css/MyProfileView.css";
</style>
