<template>
  <van-sticky :offset-top="0">
    <van-nav-bar title="新鲜事">
      <template #right>
        <van-icon name="edit" size="25" @click="bottom = true"/>
      </template>
    </van-nav-bar>
  </van-sticky>
  <var-popup v-model:show="bottom" :default-style="true" position="bottom">
    <van-nav-bar title="新鲜事">
      <template #left>
        <van-icon name="cross" size="25" @click="bottom = false"/>
      </template>
      <template #right>
        <van-button style="height: 30px" type="primary" @click="sendArticle">发布</van-button>
      </template>
    </van-nav-bar>
    <van-cell-group inset>
      <van-field v-model="writeArticle.title" placeholder="请输入帖子标题"/>
      <van-field
        v-model="writeArticle.content"
        autosize
        class="writeArticleContent"
        maxlength="50"
        placeholder="请输入帖子正文"
        rows="2"
        show-word-limit
        type="textarea"
      />
    </van-cell-group>
  </var-popup>
  <van-grid>
    <van-grid-item icon="photo-o" text="文字"/>
    <van-grid-item icon="photo-o" text="文字"/>
    <van-grid-item icon="photo-o" text="文字"/>
    <van-grid-item icon="photo-o" text="文字"/>
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
            :style-vars="styleVars"
            ripple
            @click="goArticleDetail(item.id)"
          >
            <template #subtitle>
              <p class="itemUserName">发帖人：{{ item.userName }}</p>
            </template>
            <template #title>
              <h3 class="itemTitle">{{ item.title }}</h3>
            </template>
            <template #description>
              <van-text-ellipsis
                :content="item.content"
                class="itemContent"
                rows="3"
              />
            </template>
            <template #extra>
              <p class="itemPopular">{{ item.likeCount }} 点赞 {{ item.commentCount }} 评论数</p>
            </template>
          </var-card>
        </div>
      </var-list>
    </var-pull-refresh>
  </div>

</template>
<script>
import { defineComponent, onMounted, ref } from 'vue'
import axios from 'axios'
import { showFailToast, showSuccessToast } from 'vant'
import router from '@/router'

export default defineComponent({
  name: 'ArticleView',
  setup () {
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

    async function load () {
      console.log('load')
      page.value++
      const result = await getArticlesByPage(page.value, size.value)
      if (!result) {
        page.value--
      }
      loading.value = false
    }
    async function refresh () {
      console.log('refresh')
      page.value = 0
      finished.value = false
      articles.value = []
      loading.value = true
      const result = await getArticlesByPage(0, 5)
      if (!result) {
        showFailToast('加载失败')
      } else {
        refreshing.value = false;
        loading.value = false
      }
    }

    const getArticlesByPage = async (page, size) => {
      console.log('getArticlesByPage')
      const fetchedArticles = await axios.get('http://172.20.10.3:8081/article/getArticles?page=' + page + '&size=' + size)
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
      const res = await axios.post('http://172.20.10.3:8081/article/saveArticle', {
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
    const styleVars = {
      '--card-footer-padding': '0 5px'
    }
    onMounted(() => {
      load()
    })
    const bottom = ref(false)
    const goArticleDetail = (id) => {
      console.log('goArticleDetail')
      router.push({
        path: '/articleDetail',
        query: { id: id }
      })
    }
    return {
      articles,
      styleVars,
      bottom,
      writeArticle,
      sendArticle,
      load,
      loading,
      finished,
      goArticleDetail,
      refresh,
      refreshing
    }
  }
})
</script>
<style scoped>
.itemList {
  display: flex;
  flex-wrap: wrap;
  justify-content: space-around;
}

.articleShow {
  width: 95%;
  margin-top: 5px;
  margin-bottom: 3px;
}

.articleContent {
  width: 100vw;
  display: flex;
  flex-wrap: wrap;
  justify-content: space-around;
}

.van-text-ellipsis {
  font-size: 17px;
}

.itemTitle {
  padding-left: 5px;
  font-family: 'DYH';
  text-align: left;
  margin-bottom: 7px;
}

.itemContent {
  text-align: left;
  padding-left: 5px;
}

.itemUserName {
  text-align: left;
  padding-left: 5px;
  margin-top: 4px;
  margin-bottom: 4px;
  color: darkgrey;
}

.var-card >>> .var-card__footer {
  padding: 0px 5px !important;
  margin-top: 3px;
}

.itemPopular {
  width: 100%;
  text-align: left;
  margin: 0px;
  color: darkgrey;
}

:root {
  --card-footer-padding: 0px 5px;
}

.writeArticleContent {
  height: 78vh;
}
</style>
