<script>
import { defineComponent, onMounted } from 'vue'
import axios from 'axios'

export default defineComponent({
  name: 'myComments',
  setup () {
    const goBack = () => {
      window.history.back()
    }
    const getMyComments = () => {
      axios.get('comment/getCommentsByUserId').then(res => {
        console.log(res)
      })
    }
    onMounted(() => { getMyComments() }
    )
    return {
      goBack
    }
  }
})

</script>

<template>
  <van-nav-bar
    left-arrow
    right-text="按钮"
    title="标题"
    @click-left="goBack"
  >
    <template #right>
      <p>清除所有</p>
    </template>
  </van-nav-bar>
  <div>
    我的消息
  </div>
</template>

<style scoped>

</style>
