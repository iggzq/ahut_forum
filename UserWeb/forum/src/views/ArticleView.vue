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
    <div v-for="item in articles" :key="item.id" class="articleShow">
      <var-card
        :style-vars="styleVars"
        ripple
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
  </div>

</template>
<script>
import { defineComponent, onMounted, ref } from 'vue'
import axios from 'axios'

export default defineComponent({
  name: 'ArticleView',
  setup () {
    const articles = ref([{}])
    const writeArticle = ref({
      title: '',
      content: ''
    })
    const getArticlesByPage = () => {
      console.log('getArticlesByPage')
      axios.get('http://172.20.10.3:8081/article/getArticles?page=0&size=10').then(res => {
        console.log(res)
        articles.value = res.data.data
      })
    }
    const sendArticle = () => {
      axios.post('http://172.20.10.3:8081/article/saveArticle', {
        title: writeArticle.value.title,
        content: writeArticle.value.content
      }).then(res => {
        console.log(res)
      })
    }
    const styleVars = {
      '--card-footer-padding': '0 5px'
    }
    onMounted(() => {
      getArticlesByPage()
    })
    const bottom = ref(false)
    return {
      articles,
      styleVars,
      bottom,
      writeArticle,
      sendArticle
    }
  }
})
</script>
<style scoped>
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
