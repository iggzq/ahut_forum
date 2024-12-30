<script setup>
import {useRoute} from 'vue-router'
import router from '@/router'
import ArticleListView from '@/views/inside/ArticleListView.vue'
import {onActivated, onBeforeUnmount, onMounted, ref} from 'vue'

const route = useRoute()
const topicId = route.params.topicId
const topicName = route.params.topicName
const scrollPosition = ref(0)
const scrollableArea = ref(null)
const orderValue = ref(0)
const orderOption = [
  {
    text: '全部文章',
    value: 0
  },
  {
    text: '时间排序',
    value: 1
  },
  {
    text: '热度排序',
    value: 2
  }
]
const articleDetailName = 'topicArticleDetail'
onMounted(() => {
  if (scrollableArea.value) {
    scrollableArea.value.addEventListener('scroll', handleScroll)
  }
})
const handleScroll = () => {
  scrollPosition.value = scrollableArea.value.scrollTop
}
onBeforeUnmount(() => {
  if (scrollableArea.value) {
    scrollableArea.value.removeEventListener('scroll', handleScroll)
  }
})
onActivated(() => {
  // 恢复滚动位置
  scrollableArea.value.scrollTop = scrollPosition.value
})

</script>

<template>
  <keep-alive>
    <div>
      <router-view/>
    </div>
  </keep-alive>
  <van-sticky :offset-top="0">
    <van-nav-bar :title="topicName">
      <template #left>
        <van-icon name="arrow-left" size="25" @click="router.back()"/>
      </template>
    </van-nav-bar>
  </van-sticky>
  <div ref="scrollableArea" class="topicMainContent">
    <van-dropdown-menu>
      <van-dropdown-item v-model="orderValue" :options="orderOption"/>
    </van-dropdown-menu>
    <ArticleListView :article-detail-name="articleDetailName" :orderValue="orderValue" :topic-type="topicId"/>
  </div>
</template>

<style scoped>
@import "@/assets/css/TopicArticleView.css";
</style>
