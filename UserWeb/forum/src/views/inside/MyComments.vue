<script>
import {defineComponent, onMounted, ref} from 'vue'
import axios from 'axios'
import router from '@/router'
import CommentCard from '@/components/commentCard.vue'
import {useStore} from 'vuex'

export default defineComponent({
  name: 'myComments',
  components: {CommentCard},
  setup() {
    const myComments = ref([])
    const store = useStore()
    const goBack = () => {
      router.push({
        name: 'MyProfileView'
      })
    }
    const getMyComments = () => {
      axios.get('comment/getCommentsByUserId').then(res => {
        myComments.value = res.data.data
      })
    }
    onMounted(() => {
        store.commit('setActiveTab', 1)
        getMyComments()
      }
    )
    return {
      goBack,
      myComments
    }
  }
})

</script>

<template>
  <van-nav-bar
    left-arrow
    right-text="按钮"
    title="回复我的"
    @click-left="goBack"
  >
    <template #right>
      <p>清除所有</p>
    </template>
  </van-nav-bar>
  <div class="myCommentsContent">
    <div v-for="(item,index) in myComments" :key="index">
      <comment-card :comment="item"></comment-card>
    </div>
    <van-empty v-if="null === myComments" description="无回复"/>
  </div>

</template>

<style scoped>
@import "@/assets/css/MyComments.css";
</style>
