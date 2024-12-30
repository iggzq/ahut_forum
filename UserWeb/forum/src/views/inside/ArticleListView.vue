<script setup>
import {computed, defineProps, onMounted, ref, toRefs, watch} from 'vue'
import {useStore} from 'vuex'
import {showFailToast} from 'vant'
import axios from 'axios'
import {useRouter} from 'vue-router'

const router = useRouter()
const loading = ref(true)
const finished = ref(false)
const refreshing = ref(false)
const page = ref(-1)
const size = ref(5)
const articles = ref([])
const store = useStore()
const refreshArticle = computed(() => store.getters.getRefreshArticle)
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
const props = defineProps({
  articleDetailName: {
    type: String,
    required: true
  },
  topicType: {
    type: Number,
    required: false,
    default: -1
  },
  orderValue: {
    type: Number,
    required: false,
    default: 0
  }
})
const articleDetail = toRefs(props)

async function load() {
  loading.value = true
  page.value++
  let result = []
  if (articleDetail.orderValue.value === 0) {
    result = await getArticlesByPageAndTopic(page.value, size.value, articleDetail.topicType.value)
  } else if (articleDetail.orderValue.value === 1) {
    result = await getArticlesByPageAndTopic(page.value, size.value, articleDetail.topicType.value)
  }
  if (!result) {
    page.value--
  }
  loading.value = false
}

async function refresh() {
  page.value = 0
  finished.value = true
  articles.value = []
  loading.value = false
  refreshing.value = true
  const result = await getArticlesByPageAndTopic(page.value, size.value, articleDetail.topicType.value)
  if (!result) {
    refreshing.value = false
    showFailToast('没有更多了')
  } else {
    refreshing.value = false
  }
}

const getArticlesByPageAndTopic = async (page, size, topicType) => {
  const fetchedArticles = await axios.get('article/getArticles?page=' + page + '&size=' + size + '&topicType=' + topicType)
  if (fetchedArticles.data.data.length === 5) {
    finished.value = false
    articles.value = [...articles.value, ...fetchedArticles.data.data]
    return true
  } else if (fetchedArticles.data.code === 200 && fetchedArticles.data.data.length < 5 && fetchedArticles.data.data.length > 0) {
    articles.value = [...articles.value, ...fetchedArticles.data.data]
    finished.value = true
    return true
  } else if (fetchedArticles.data.code === 200 && fetchedArticles.data.data.length === 0) {
    finished.value = true
    return false
  }
}

const getArticlesByPageTopicAndDate = async (page, size, topicType) => {
  const fetchedArticles = await axios.get('article/getArticlesOrderByDate?page=' + page + '&size=' + size + '&topicType=' + topicType)
  if (fetchedArticles.data.data.length === 5) {
    finished.value = false
    articles.value = [...articles.value, ...fetchedArticles.data.data]
    return true
  } else if (fetchedArticles.data.code === 200 && fetchedArticles.data.data.length < 5 && fetchedArticles.data.data.length > 0) {
    articles.value = [...articles.value, ...fetchedArticles.data.data]
    finished.value = true
    return true
  } else if (fetchedArticles.data.code === 200 && fetchedArticles.data.data.length === 0) {
    finished.value = true
    return false
  }
}

const formattedData = computed(() => {
  return articles.value.map(item => ({
    ...item,
    label: topicTypeToString[item.topicType] || '未知'
  }))
})

const goArticleDetail = (article) => {
  router.push(
    {
      name: articleDetail.articleDetailName.value,
      params: {
        articleId: article.id,
        topicId: articleDetail.topicType.value,
        topicName: '123'
      }
    }
  )
}
const doubleClickRefresh = async () => {
  articles.value = []
  loading.value = true
  page.value = 0
  const result = await getArticlesByPageAndTopic(page.value, size.value, articleDetail.topicType.value)
  if (!result) {
    showFailToast('加载失败')
  } else {
    loading.value = false
  }
}
onMounted(async () => {
  // 初始化逻辑，如数据请求
  await load()
})

// 监听 orderValue 的变化
watch(() => props.orderValue, async (newVal) => {
  loading.value = true
  articles.value = []
  page.value = 0
  size.value = 5
  if (newVal === 1) {
    console.log('watch' + newVal)
    await getArticlesByPageTopicAndDate(page.value, size.value, articleDetail.topicType.value)
  } else if (newVal === 0) {
    await getArticlesByPageAndTopic(page.value, size.value, articleDetail.topicType.value)
  }
  loading.value = false
})

watch(refreshArticle, (newVal) => {
  if (newVal) {
    doubleClickRefresh()
    store.commit('SET_REFRESH_ARTICLE', false)
    store.commit('SET_IS_MAIN_BACK', true)
  }
})
</script>

<template>
  <div class="articleContent">
    <var-pull-refresh v-model="refreshing" @refresh="refresh">
      <var-list
        v-model:loading="loading"
        :finished="finished"
        class="itemList"
        @load="load"
      >
        <div v-for="item in formattedData" :key="item.id" class="articleShow">
          <var-card
            ripple
            @click="goArticleDetail(item)"
          >
            <template #title>
              <h3 class="itemTitle">{{ item.title }}</h3>
            </template>
            <template #subtitle>
              <div class="subtitleCss">
                <p class="itemUserName">{{ item.admissionYear }}级 发帖人：{{ item.userName }}</p>
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
                <div class="hotCss">
                  <van-icon name="fire-o" size="1rem"/>
                  &nbsp;
                  <p style="margin: 0">{{ item.hotNum }}</p>
                </div>
              </div>
              <var-space>
                <van-tag :type="tagTypeMap[item.label]" size="medium">{{ item.label }}</van-tag>
              </var-space>
            </template>
          </var-card>
        </div>
      </var-list>
    </var-pull-refresh>
  </div>
</template>

<style scoped>

</style>
