<template>
  <keep-alive>
    <div>
      <router-view/>
    </div>
  </keep-alive>
  <div>
    <van-sticky :offset-top="0">
      <van-nav-bar title="新鲜事">
        <template #right>
          <van-icon name="edit" size="25" @click="bottom = true"/>
        </template>
      </van-nav-bar>
    </van-sticky>
    <van-search
      v-model="searchValue"
      placeholder="请输入搜索关键词"
      input-align="center"
    />
    <van-popup
      v-model:show="bottom"
      :style="{ height: '100%' }"
      position="bottom"
    >
      <van-nav-bar title="发布">
        <template #left>
          <van-icon name="cross" size="25" @click="cancelPublish"/>
        </template>
        <template #right>
          <van-button style="height: 30px" type="primary" @click="sendArticle">发布</van-button>
        </template>
      </van-nav-bar>
      <van-cell-group inset>
        <van-row :gutter="[0, 20]">
          <van-col span="24">
            <van-field v-model="writeArticle.title" autosize
                       maxlength="50" placeholder="请输入帖子标题(限50字)" show-word-limit/>
          </van-col>
          <van-col span="24">
            <van-button :plain="isButtoned" icon="plus" size="small" style="margin: 10px" type="primary"
                        @click="showPicker=true">
              {{ topicButtonValue }}
            </van-button>
          </van-col>
          <van-popup v-model:show="showPicker" destroy-on-close position="bottom" round>
            <van-picker
              :columns="topicType"
              :model-value="writeArticle.topic"
              @cancel="showPicker = false"
              @confirm="topicTypeConfirm"
            />
          </van-popup>
          <van-col span="24">
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
          </van-col>
        </van-row>
      </van-cell-group>
    </van-popup>
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
        <van-grid-item v-ripple class="vanItem" text="话题" @click="goTopic">
          <template #icon>
            <svg class="icon" height="auto" p-id="7439" t="1734789199015" version="1.1"
                 viewBox="0 0 1024 1024" width="90%" xmlns="http://www.w3.org/2000/svg">
              <path
                d="M512 118.016c240.064 0 436.032 175.808 436.032 394.496v2.944c-0.768 89.344-34.432 173.824-94.208 241.984l-0.96 1.152 48.064 102.848 0.32 0.768a32 32 0 0 1-37.952 43.584l-151.68-42.496-0.256 0.192a471.808 471.808 0 0 1-199.36 43.52c-240.064 0-436.032-175.808-436.032-394.496S272 118.016 512 118.016z m0 64c-206.208 0-372.032 148.8-372.032 330.496 0 181.696 165.824 330.496 372.032 330.496 64.832 0 127.104-14.72 182.272-42.368l0.512-0.256a32 32 0 0 1 22.464-1.92l96.128 26.88-27.648-59.2a32 32 0 0 1 5.888-35.712l0.32-0.32c59.072-59.968 92.032-136.576 92.032-217.6 0-181.76-165.76-330.496-371.968-330.496z"
                fill="#1296db" p-id="7440"></path>
              <path d="M732.544 404.992v64h-400v-64zM692.032 556.032v64h-400v-64z" fill="#1296db" p-id="7441"></path>
              <path
                d="M444.224 309.952l63.04 11.072-69.44 393.92-63.04-11.072zM586.24 310.592l63.04 11.136-69.44 393.92-63.04-11.136z"
                fill="#1296db" p-id="7442"></path>
            </svg>
          </template>
        </van-grid-item>
        <van-grid-item v-ripple icon="photo-o" text="文字"/>
        <van-grid-item v-ripple icon="photo-o" text="文字"/>
      </van-grid>
      <ArticleListView :article-detail-name="articleDetailName"/>
    </div>
  </div>
</template>

<script setup>
import {computed, onActivated, onBeforeUnmount, onMounted, ref, watch} from 'vue'
import axios from 'axios'
import {showFailToast, showSuccessToast} from 'vant'
import {useRoute, useRouter} from 'vue-router'
import {useStore} from 'vuex'
import ArticleListView from '@/views/inside/ArticleListView.vue'

const articles = ref([])
const writeArticle = ref({
  title: '',
  content: '',
  topic: []
})
const page = ref(-1)
const router = useRouter()
const store = useStore()
const route = useRoute()
const searchValue = ref('');
const scrollPosition = ref(0)
const scrollableArea = ref(null)
const articleDetailName = 'articleDetail'
const isButtoned = ref(true)
const topicType = [
  {
    text: '学习提问',
    value: 0
  },
  {
    text: '校园逸事',
    value: 1
  },
  {
    text: '生活闲谈',
    value: 2
  },
  {
    text: '丢失/拾获',
    value: 3
  }
]
const showPicker = ref(false)
const fieldValue = ref('')
const topicButtonValue = ref('添加话题类型')
const isMainBack = computed(() => store.getters.getRefreshArticle)
watch(isMainBack, (newVal) => {
  if (!newVal) {
    scrollPosition.value = 0
    scrollableArea.value.scrollTop = 0
  }
})

const topicTypeConfirm = ({
                            selectedValues,
                            selectedOptions
                          }) => {
  isButtoned.value = false
  showPicker.value = false
  writeArticle.value.topic = selectedValues
  fieldValue.value = selectedOptions[0].text
  topicButtonValue.value = selectedOptions[0].text
}

const sendArticle = async () => {
  console.log(writeArticle.value)
  const res = await axios.post('article/saveArticle', {
    title: writeArticle.value.title,
    content: writeArticle.value.content,
    topicType: writeArticle.value.topic[0]
  })
  if (res.data.code === 200) {
    writeArticle.value.title = ''
    writeArticle.value.content = ''
    writeArticle.value.topic = []
    fieldValue.value = ''
    topicButtonValue.value = '添加话题类型'
    page.value = 0
    articles.value = []
    showSuccessToast('发布成功')
    bottom.value = false
  } else {
    showFailToast('发布失败，请重试')
  }
}
const cancelPublish = () => {
  writeArticle.value.content = ''
  writeArticle.value.title = ''
  writeArticle.value.topic = []
  fieldValue.value = ''
  topicButtonValue.value = '添加话题类型'
  bottom.value = false
  isButtoned.value = true
}
const bottom = ref(false)
const goChatRoom = () => {
  store.commit('setActiveTab', 0)
  router.push(
    {
      name: 'ChatRoom'
    }
  )
}
const goTopic = () => {
  store.commit('setActiveTab', 0)
  router.push(
    {
      name: 'TopicView'
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
  store.commit('setActiveTab', 0)
})

</script>
<script>
export default {
  name: 'ArticleView'
}
</script>
<style scoped>
@import '@/assets/css/ArticleListView.css';
</style>
