<script setup>
import router from '@/router'

const topics = [{
  id: 0,
  name: '学习提问',
  icon: ''
}, {
  id: 1,
  name: '校园逸事',
  icon: ''
}, {
  id: 2,
  name: '生活闲谈',
  icon: ''
}, {
  id: 3,
  name: '丢失/拾获',
  icon: ''
}]

const goTopicArticle = (id, name) => {
  router.push({
    name: 'topicArticle',
    params: {
      topicId: id,
      topicName: name
    }
  })
}
</script>

<template>
  <van-sticky :offset-top="0">
    <van-nav-bar title="话题">
      <template #left>
        <van-icon name="arrow-left" size="25" @click="router.back()"/>
      </template>
    </van-nav-bar>
  </van-sticky>
  <van-grid :column-num="2">
    <van-grid-item v-for="value in topics" :key="value.id" :text="value.name" icon="photo-o"
                   @click="goTopicArticle(value.id,value.name)"/>
  </van-grid>
</template>

<style scoped>

</style>
