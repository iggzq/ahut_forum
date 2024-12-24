<script setup>
import { computed, defineProps, onMounted, ref, toRefs, watch } from 'vue'
import { showFailToast } from 'vant'
import axios from 'axios'
import { useRouter } from 'vue-router'

const router = useRouter()
const loading = ref(true)
const finished = ref(false)
const refreshing = ref(false)
const page = ref(-1)
const size = ref(5)
const articles = ref([])
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

async function load () {
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

async function refresh () {
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
                  <svg class="icon" height="auto" p-id="4289" t="1725329817714"
                       version="1.1" viewBox="0 0 1024 1024" width="1em" xmlns="http://www.w3.org/2000/svg">
                    <path
                      d="M857.28 344.992h-264.832c12.576-44.256 18.944-83.584 18.944-118.208 0-78.56-71.808-153.792-140.544-143.808-60.608 8.8-89.536 59.904-89.536 125.536v59.296c0 76.064-58.208 140.928-132.224 148.064l-117.728-0.192A67.36 67.36 0 0 0 64 483.04V872c0 37.216 30.144 67.36 67.36 67.36h652.192a102.72 102.72 0 0 0 100.928-83.584l73.728-388.96a102.72 102.72 0 0 0-100.928-121.824zM128 872V483.04c0-1.856 1.504-3.36 3.36-3.36H208v395.68H131.36A3.36 3.36 0 0 1 128 872z m767.328-417.088l-73.728 388.96a38.72 38.72 0 0 1-38.048 31.488H272V476.864a213.312 213.312 0 0 0 173.312-209.088V208.512c0-37.568 12.064-58.912 34.72-62.176 27.04-3.936 67.36 38.336 67.36 80.48 0 37.312-9.504 84-28.864 139.712a32 32 0 0 0 30.24 42.496h308.512a38.72 38.72 0 0 1 38.048 45.888z"
                      fill="#567f9d" p-id="4290">
                    </path>
                  </svg>
                  &nbsp;
                  <p style="margin: 0">{{ item.likeCount }}</p>
                </div>
                <div class="commentCss">
                  <svg class="icon" height="auto" p-id="11715" t="1725331534539"
                       version="1.1" viewBox="0 0 1024 1024" width="1em" xmlns="http://www.w3.org/2000/svg">
                    <path
                      d="M206.9248 651.55072h-30.38208 30.38208zM248.68352 440.59648H341.1456c23.8592 0 44.73856-16.26624 44.73856-34.8672 0-18.59072-20.87936-34.86208-44.73856-34.86208H248.68352c-23.8592 0-44.73856 16.27136-44.73856 34.86208 0 18.60096 17.89952 34.8672 44.73856 34.8672z"
                      fill="#567f9d" p-id="11716"></path>
                    <path
                      d="M988.7744 159.80544a87.00416 87.00416 0 0 0-86.49216-74.56256H131.96288c-47.7184 0-86.49728 38.77376-86.49728 86.49728v472.28416c2.97984 47.71328 31.51872 75.97056 79.23712 75.97056H293.4272c23.8592 0 44.73856-10.35776 44.73856-34.21696 0-23.86432-20.87936-34.2272-44.73856-34.2272H113.90976v-462.7968c2.98496-26.12224 8.94976-35.072 56.67328-38.05184H878.5408c50.70848 0 44.01664 40.22272 44.01664 96.896v414.46912h-408.41728c-14.91968 0-29.82912 8.94976-38.77888 20.87936l-217.728 191.57504c-17.89952 17.89952-23.11168 39.43424-5.21216 57.32352 24.92416 21.9904 49.95072 2.176 67.84512-15.72864l211.76832-185.60512h382.61248c44.73856 0 68.16256-25.5488 74.12736-70.29248V159.80544zM113.90976 590.336v18.56-18.56zM830.29504 672.3072l33.36704-10.24-33.36704 10.24zM925.5424 602.41408v27.51488-27.51488z"
                      fill="#567f9d" p-id="11717"></path>
                    <path
                      d="M925.5424 602.41408c0 50.70848-14.16192 59.648-61.88032 59.648h58.89536l2.98496-59.648zM113.90976 590.336v61.21472h54.44096c-42.51136 0-51.456-16.48128-54.44096-61.21472zM785.5616 368.54784h-92.47232c-23.85408 0-44.73344 16.27136-44.73344 34.8672 0 18.59072 20.87936 34.86208 44.73344 34.86208h92.47232c23.84896 0 44.73344-16.27136 44.73344-34.86208 0-18.59584-20.88448-34.8672-44.73344-34.8672zM561.85856 438.272c23.85408 0 44.73856-16.27136 44.73856-34.86208 0-18.59584-20.88448-34.8672-44.73856-34.8672H469.39648c-23.8592 0-44.73856 16.27136-44.73856 34.8672 0 18.59072 20.87936 34.86208 44.73856 34.86208h92.46208z"
                      fill="#567f9d" p-id="11718"></path>
                  </svg>
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
    </var-pull-refresh>
  </div>
</template>

<style scoped>

</style>
