<template>
  <keep-alive>
    <div>
      <router-view/>
    </div>
  </keep-alive>
  <div :class="totalShow">
    <van-sticky :offset-top="0">
      <van-nav-bar title="新鲜事">
        <template #right>
          <van-icon name="edit" size="25" @click="bottom = true"/>
        </template>
      </van-nav-bar>
    </van-sticky>
    <var-popup v-model:show="bottom" :default-style="true" position="bottom" safe-area-top>
      <van-nav-bar title="发布">
        <template #left>
          <van-icon name="cross" size="25" @click="cancelPublish"/>
        </template>
        <template #right>
          <van-button style="height: 30px" type="primary" @click="sendArticle">发布</van-button>
        </template>
      </van-nav-bar>
      <van-cell-group inset>
        <van-field v-model="writeArticle.title" autosize
                   maxlength="50" placeholder="请输入帖子标题(限50字)" show-word-limit/>
        <van-field placeholder="可选标签，格式：#+文字"/>
        <van-field
          v-model="writeArticle.content"
          autosize
          class="writeArticleContent"
          maxlength="400"
          placeholder="请输入帖子正文"
          rows="2"
          show-word-limit
          type="textarea"
        />
      </van-cell-group>
    </var-popup>
    <div ref="scrollableArea" class="mainContent">
      <van-grid>
        <van-grid-item v-ripple class="vanItem" @click="goChatRoom">
          <template #icon>
            <svg class="icon" height="auto" p-id="8573" t="1719191102166" version="1.1" viewBox="0 0 1024 1024"
                 width="90%" xmlns="http://www.w3.org/2000/svg">
              <path
                d="M966.08 738.12l-54.26-182.22c16.74-37.82 25.24-77.78 25.24-118.82 0-177.22-160.46-321.38-357.68-321.38s-357.68 144.16-357.68 321.38c0 2.26 0.02 4.58 0.08 6.92C137.28 486.36 80 567.54 80 660.56c0 31.38 6.42 61.94 19.12 90.88l-41.2 138.3a9.94 9.94 0 0 0 2.26 9.66 10.006 10.006 0 0 0 9.48 2.96l140.96-31.02c43.42 24.2 93.42 36.98 144.84 36.96 113.74 0 211.6-62.32 253.6-150.98 56.54-4.18 111.48-20.5 159.52-47.46l185.76 40.88c3.46 0.76 7.06-0.36 9.48-2.96a10.036 10.036 0 0 0 2.26-9.66z m-610.62 150.18c-49.28 0.02-97.08-12.52-138.26-36.22-1.52-0.88-3.24-1.32-4.98-1.32-0.72 0-1.44 0.08-2.16 0.22l-128.12 28.2 37.4-125.58c0.68-2.32 0.52-4.82-0.5-7.02-12.5-27.34-18.84-56.28-18.84-86.02 0-125.6 114.6-227.76 255.46-227.76 140.88 0 255.48 102.16 255.48 227.76 0 125.58-114.6 227.74-255.48 227.74z m413.68-198.8a9.992 9.992 0 0 0-7.14 1.1c-43.4 24.98-93 40.68-144.32 45.88 8.6-23.94 13.26-49.44 13.26-75.92 0-136.62-123.58-247.76-275.48-247.76-40.52 0-79.04 7.92-113.74 22.12 1.3-165.2 152.26-299.22 337.66-299.22 186.2 0 337.68 135.2 337.68 301.38 0 39.4-8.4 77.74-24.98 113.96a10.12 10.12 0 0 0-0.5 7.02l50.48 169.5-172.92-38.06z"
                fill="#23130D" p-id="8574"></path>
              <path
                d="M355.46 432.8C214.6 432.8 100 534.96 100 660.56c0 29.74 6.34 58.68 18.84 86.02 1.02 2.2 1.18 4.7 0.5 7.02l-37.4 125.58 128.12-28.2c0.72-0.14 1.44-0.22 2.16-0.22 1.74 0 3.46 0.44 4.98 1.32 41.18 23.7 88.98 36.24 138.26 36.22 140.88 0 255.48-102.16 255.48-227.74 0-125.6-114.6-227.76-255.48-227.76z m-82.96 96.28h143.44c5.52 0 10 4.46 10 10 0 5.52-4.48 10-10 10h-143.44c-5.52 0-10-4.48-10-10 0-5.54 4.48-10 10-10zM498.18 784.8H190.26c-5.52 0-10-4.46-10-10 0-5.52 4.48-10 10-10h307.92c5.52 0 10 4.48 10 10 0 5.54-4.48 10-10 10z m0-58.92H190.26c-5.52 0-10-4.48-10-10s4.48-10 10-10h307.92c5.52 0 10 4.48 10 10s-4.48 10-10 10z m0-58.94H190.26c-5.52 0-10-4.48-10-10s4.48-10 10-10h307.92c5.52 0 10 4.48 10 10s-4.48 10-10 10z m0-58.94H190.26c-5.52 0-10-4.48-10-10s4.48-10 10-10h307.92c5.52 0 10 4.48 10 10s-4.48 10-10 10z"
                fill="#EC7063" p-id="8575"></path>
              <path
                d="M942.06 727.56l-172.92-38.06a9.992 9.992 0 0 0-7.14 1.1c-43.4 24.98-93 40.68-144.32 45.88 8.6-23.94 13.26-49.44 13.26-75.92 0-136.62-123.58-247.76-275.48-247.76-40.52 0-79.04 7.92-113.74 22.12 1.3-165.2 152.26-299.22 337.66-299.22 186.2 0 337.68 135.2 337.68 301.38 0 39.4-8.4 77.74-24.98 113.96a10.12 10.12 0 0 0-0.5 7.02l50.48 169.5z"
                fill="#F9C139" p-id="8576"></path>
              <path
                d="M942.06 727.56l-172.92-38.06a9.992 9.992 0 0 0-7.14 1.1c-43.4 24.98-93 40.68-144.32 45.88 8.6-23.94 13.26-49.44 13.26-75.92 0-136.62-123.58-247.76-275.48-247.76-40.52 0-79.04 7.92-113.74 22.12 0.66-82.68 38.78-157.56 99.98-211.72-44.9 50.8-71.92 114.74-72.46 184.2 34.7-14.2 73.22-22.12 113.74-22.12 151.9 0 275.48 111.14 275.48 247.76 0 26.48-4.66 51.98-13.26 75.92 51.32-5.2 100.92-20.9 144.32-45.88 2.16-1.24 4.7-1.64 7.14-1.1l134.7 29.64 10.7 35.94z"
                fill="#23130D" opacity=".2" p-id="8577"></path>
              <path
                d="M425.94 539.08c0 5.52-4.48 10-10 10h-143.44c-5.52 0-10-4.48-10-10 0-5.54 4.48-10 10-10h143.44c5.52 0 10 4.46 10 10zM508.18 598c0 5.52-4.48 10-10 10H190.26c-5.52 0-10-4.48-10-10s4.48-10 10-10h307.92c5.52 0 10 4.48 10 10zM508.18 656.94c0 5.52-4.48 10-10 10H190.26c-5.52 0-10-4.48-10-10s4.48-10 10-10h307.92c5.52 0 10 4.48 10 10zM508.18 715.88c0 5.52-4.48 10-10 10H190.26c-5.52 0-10-4.48-10-10s4.48-10 10-10h307.92c5.52 0 10 4.48 10 10zM508.18 774.8c0 5.54-4.48 10-10 10H190.26c-5.52 0-10-4.46-10-10 0-5.52 4.48-10 10-10h307.92c5.52 0 10 4.48 10 10z"
                fill="#FFFFFF" p-id="8578"></path>
            </svg>
          </template>
          <template #text>
            <p>聊天室</p>
          </template>
        </van-grid-item>
        <van-grid-item v-ripple icon="photo-o" text="文字"/>
        <van-grid-item v-ripple icon="photo-o" text="文字"/>
        <van-grid-item v-ripple icon="photo-o" text="文字"/>
      </van-grid>
      <div class="articleContent">
        <var-pull-refresh v-model="refreshing" @refresh="refresh">
          <var-list
            v-model:loading="loading"
            :finished="finished"
            class="itemList"
            @load="load"
          >
            <div v-for="item in articles" :key="item.id" class="articleShow">
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
                          fill="#0094ff" p-id="4290">
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
                          fill="#0094ff" p-id="11716"></path>
                        <path
                          d="M988.7744 159.80544a87.00416 87.00416 0 0 0-86.49216-74.56256H131.96288c-47.7184 0-86.49728 38.77376-86.49728 86.49728v472.28416c2.97984 47.71328 31.51872 75.97056 79.23712 75.97056H293.4272c23.8592 0 44.73856-10.35776 44.73856-34.21696 0-23.86432-20.87936-34.2272-44.73856-34.2272H113.90976v-462.7968c2.98496-26.12224 8.94976-35.072 56.67328-38.05184H878.5408c50.70848 0 44.01664 40.22272 44.01664 96.896v414.46912h-408.41728c-14.91968 0-29.82912 8.94976-38.77888 20.87936l-217.728 191.57504c-17.89952 17.89952-23.11168 39.43424-5.21216 57.32352 24.92416 21.9904 49.95072 2.176 67.84512-15.72864l211.76832-185.60512h382.61248c44.73856 0 68.16256-25.5488 74.12736-70.29248V159.80544zM113.90976 590.336v18.56-18.56zM830.29504 672.3072l33.36704-10.24-33.36704 10.24zM925.5424 602.41408v27.51488-27.51488z"
                          fill="#0094ff" p-id="11717"></path>
                        <path
                          d="M925.5424 602.41408c0 50.70848-14.16192 59.648-61.88032 59.648h58.89536l2.98496-59.648zM113.90976 590.336v61.21472h54.44096c-42.51136 0-51.456-16.48128-54.44096-61.21472zM785.5616 368.54784h-92.47232c-23.85408 0-44.73344 16.27136-44.73344 34.8672 0 18.59072 20.87936 34.86208 44.73344 34.86208h92.47232c23.84896 0 44.73344-16.27136 44.73344-34.86208 0-18.59584-20.88448-34.8672-44.73344-34.8672zM561.85856 438.272c23.85408 0 44.73856-16.27136 44.73856-34.86208 0-18.59584-20.88448-34.8672-44.73856-34.8672H469.39648c-23.8592 0-44.73856 16.27136-44.73856 34.8672 0 18.59072 20.87936 34.86208 44.73856 34.86208h92.46208z"
                          fill="#0094ff" p-id="11718"></path>
                      </svg>
                      &nbsp;
                      <p style="margin: 0">{{ item.commentCount }}</p>
                    </div>
                  </div>
                  <div class="trendNum">

                  </div>
                  <!--                  <p class="itemPopular" style="color: #0094ff">-->
                  <!--                  </p>-->
                </template>
              </var-card>
            </div>
          </var-list>
        </var-pull-refresh>
      </div>
    </div>
  </div>
</template>

<script setup>
import { onActivated, onBeforeUnmount, onMounted, ref, watch } from 'vue'
import axios from 'axios'
import { showFailToast, showSuccessToast } from 'vant'
import { useRoute, useRouter } from 'vue-router'
import { useStore } from 'vuex'

const totalShow = ref('')
const articles = ref([])
const writeArticle = ref({
  title: '',
  content: ''
})
const loading = ref(true)
const finished = ref(false)
const refreshing = ref(false)
const page = ref(-1)
const size = ref(5)
const router = useRouter()
const store = useStore()
const route = useRoute()
const scrollPosition = ref(0)
const scrollableArea = ref(null)

async function load () {
  loading.value = true
  page.value++
  const result = await getArticlesByPage(page.value, size.value)
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
  const result = await getArticlesByPage(page.value, size.value)
  if (!result) {
    showFailToast('加载失败')
  } else {
    refreshing.value = false
  }
}

const getArticlesByPage = async (page, size) => {
  const fetchedArticles = await axios.get('article/getArticles?page=' + page + '&size=' + size)
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
const sendArticle = async () => {
  console.log(writeArticle.value)
  const res = await axios.post('article/saveArticle', {
    title: writeArticle.value.title,
    content: writeArticle.value.content
  })
  if (res.data.code === 200) {
    writeArticle.value.title = ''
    writeArticle.value.content = ''
    page.value = 0
    articles.value = []
    showSuccessToast('发布成功')
    bottom.value = false
    await getArticlesByPage(0, 5)
  } else {
    showFailToast('发布失败，请重试')
  }
}
const cancelPublish = () => {
  writeArticle.value.content = ''
  writeArticle.value.title = ''
  bottom.value = false
}
onMounted(async () => {
  // 初始化逻辑，如数据请求
  await load()
})
const bottom = ref(false)
const goArticleDetail = (article) => {
  store.commit('setActiveTab', 0)
  router.push(
    {
      name: 'articleDetail',
      params: {
        articleId: article.id
      }
    }
  )
}
const goChatRoom = () => {
  store.commit('setActiveTab', 0)
  router.push(
    {
      name: 'ChatRoom'
    }
  )
}
// 监听路由变化
watch(route, (to, from) => {
  if (to.name === 'article') {
    console.log('ArticleView activated')
  } else if (from.name === 'article') {
    console.log('ArticleView deactivated')
  }
})
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

const doubleClickRefresh = async () => {
  articles.value = []
  loading.value = true
  page.value = 0
  const result = await getArticlesByPage(page.value, size.value)
  if (!result) {
    showFailToast('加载失败')
  } else {
    loading.value = false
  }
}

watch(
  () => store.state.refreshArticle,
  async (newVal) => {
    console.log(newVal)
    if (newVal) {
      scrollableArea.value.scrollTop = 0
      await doubleClickRefresh()
      store.commit('SET_REFRESH_ARTICLE', false)
    }
  }
)

// onDeactivated(() => {
//   // 保存滚动位置
//   console.log(scrollableArea.value)
//   scrollPosition.value = scrollableArea.value.scrollTop
// })

</script>
<script>
export default {
  name: 'ArticleView'
}
</script>
<style scoped>
@import '@/assets/css/ArticleView.css';
</style>
