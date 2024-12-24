<script setup>
import { useRoute } from 'vue-router'
import router from '@/router'
import ArticleListView from '@/views/inside/ArticleListView.vue'
import { onActivated, onBeforeUnmount, onMounted, ref } from 'vue'

const route = useRoute()
const topicId = route.params.topicId
const topicName = route.params.topicName
const scrollPosition = ref(0)
const scrollableArea = ref(null)
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
    <div class="placement-container">
      <var-menu placement="bottom">
        <var-button size="normal" type="primary">
          排序
        </var-button>
        <template #menu>
          <var-cell>时间排序</var-cell>
          <var-cell>热度排序</var-cell>
        </template>
      </var-menu>
    </div>
    <ArticleListView :article-detail-name="articleDetailName" :topic-type="topicId"/>
  </div>
</template>

<style scoped>
@import "@/assets/css/TopicArticleView.css";
</style>
