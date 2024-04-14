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

export default defineComponent({
  name: 'MyProfileView',
  setup () {
    const goMyComments = () => {
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
  height: 6vh;
  font-size: medium;
  line-height: 100%;
}
</style>
