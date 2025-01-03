<script setup>
import {computed, defineProps} from 'vue'
import router from "@/router";

const prop = defineProps({
  articleList: {
    type: Array,
    required: true
  },
  searchOld: {
    type: String,
    required: true
  }
})


const topicTypeToString = {
  0: '#学习提问',
  1: '#校园逸事',
  2: '#生活闲谈',
  3: '#丢失/拾获'
}
const tagTypeMap = {
  '#学习提问': 'primary',
  '#校园逸事': 'success',
  '#生活闲谈': 'warning',
  '#丢失/拾获': 'danger'
}

const formattedData = computed(() => {
  return prop.articleList.map(item => ({
    ...item,
    label: topicTypeToString[item.topicType] || '未知'
  }))
})

const goArticleDetail = (article) => {
  router.push(
    {
      name: 'SearchArticleDetail',
      params: {
        articleId: article.articleId
      }
    }
  )
}
</script>

<template>
  <div class="CommonArticleContent">
    <var-list
      class="itemList"
    >
      <div v-for="item in formattedData" :key="item.id" class="articleShow">
        <var-card
          ripple
          @click="goArticleDetail(item)"
        >
          <template #title>
            <van-highlight
              :keywords="searchOld"
              :source-string="item.title"
              class="itemTitle"
              highlight-class="custom-class"
            />
          </template>
          <template #subtitle>
            <div class="subtitleCss">
              <p class="itemCreateTime">{{ item.createTime }}</p>
            </div>
          </template>
          <template #description>
            <van-text-ellipsis
              :content="item.content"
              class="itemContent"
              rows="3"
            />
          </template>
          <template #extra>
            <div class="itemPopular">
              <div class="likeCss">
                <van-icon name="like-o" size="1rem"/>
                &nbsp;
                <p style="margin: 0">{{ item.likeCount }}</p>
              </div>

              <div class="commentCss">
                <van-icon name="comment-o" size="1rem"/>
                &nbsp;
                <p style="margin: 0">{{ item.commentCount }}</p>
              </div>
            </div>
            <var-space>
              <van-tag :type="tagTypeMap[item.label]" size="medium">{{ item.label }}</van-tag>
            </var-space>
          </template>
        </var-card>
      </div>
    </var-list>
  </div>
</template>

<style scoped>
@import "@/assets/css/SearchArticleList.css";
</style>
