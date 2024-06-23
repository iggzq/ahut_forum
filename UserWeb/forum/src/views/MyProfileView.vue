<template>
  <van-sticky :offset-top="0">
    <van-nav-bar title="个人信息"/>
  </van-sticky>
  <van-cell class="myComments" @click="goMyComments">
    <template #title>
      <van-badge dot is-link>
        我的消息
      </van-badge>
    </template>
  <template #value>
  </template>
  </van-cell>
</template>
<script>
import router from '@/router'
import { defineComponent } from 'vue'
import { useStore } from 'vuex'

export default defineComponent({
  name: 'MyProfileView',
  setup () {
    // 查看token是否有值
    if (localStorage.getItem('satoken') == null) {
      alert(localStorage.getItem('satoken'))
      router.push('/login')
    }
    const store = useStore()
    const goMyComments = () => {
      store.commit('setActiveTab', 1);
      router.push('/myComments')
    }
    return {
      goMyComments
    }
  }
})
</script>
<style scoped>
.myComments{
  font-size: medium;
}
</style>
