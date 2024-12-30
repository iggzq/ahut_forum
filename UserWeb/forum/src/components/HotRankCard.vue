<script setup>
import {defineProps} from 'vue'

const tagTypeMap = {
  '#学习提问': 'primary',
  '#校园逸事': 'success',
  '#生活闲谈': 'warning',
  '#丢失/拾获': 'danger'
}
const topicTypeToString = {
  0: '#学习提问',
  1: '#校园逸事',
  2: '#生活闲谈',
  3: '#丢失/拾获'
}

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
    <div v-ripple class="mainContentLayout">
      <div class="leftContent">
        {{ props.articleRank }}
      </div>
      <div class="rightContent">
        <div class="rightContentTitle">
          {{ props.articleContent.title }}
        </div>
        <div class="rightBottomContent">
          <div class="leftBottom">
            <van-icon name="fire-o" size="1rem"/>
            {{ props.articleContent.hotNum }}
          </div>
          <div class="rightBottom">
            <van-tag :type="tagTypeMap[topicTypeToString[props.articleContent.topicType]]" size="medium">{{
                topicTypeToString[props.articleContent.topicType]
              }}
            </van-tag>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<style scoped>
@import "@/assets/css/HotRankCard.css";
</style>
