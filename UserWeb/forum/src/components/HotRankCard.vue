<script setup>
import {defineProps} from 'vue'

const props = defineProps({
  articleContent: {
    type: Object, // 指定 prop 的类型
    required: true // 指定此 prop 是否为必须
  },
  articleRank: {
    type: Number,
    required: true
  }
});
</script>

<template>
  <div class="mainLayout">
    <div class="leftContent">
      {{ props.articleRank }}
    </div>
    <div class="rightContent">
      <div class="rightContentTitle">
        {{ props.articleContent.title }}
      </div>
      <div class="hotNumClass">
        <van-icon name="fire-o" size="1rem"/>
        {{ props.articleContent.hotNum }}
      </div>
    </div>
  </div>
</template>

<style scoped>
@import "@/assets/css/HotRankCard.css";
</style>
