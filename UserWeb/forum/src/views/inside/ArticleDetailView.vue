<template>
  <div class="animate__animated animate__fadeInRight">
    <van-sticky>
      <van-nav-bar left-arrow title="文章详情">
        <template #left>
          <van-icon name="arrow-left" @click="goBack"/>
        </template>
      </van-nav-bar>
    </van-sticky>
    <div class="animate__animated  articleShow">
      <var-card
        class="articleDetailShow"
      >
        <template #subtitle>
          <p class="itemUserName">发帖人：{{ articleDetail.userName }}</p>
        </template>
        <template #title>
          <h3 class="itemTitle">{{ articleDetail.title }}</h3>
        </template>
        <template #description>
          <van-text-ellipsis
            :content="articleDetail.content"
            class="itemContent"
            collapse-text="收起"
            expand-text="展开"
            rows="4"
          />
        </template>
        <template #extra>
          <van-button :color="buttonColor" round type="success" @click="sendLikeArticle()">
            <van-icon :class="likeAnimate" name="good-job"/>
            {{ articleDetail.likeCount }} 点赞 {{ articleDetail.commentCount }} 评论数
          </van-button>
        </template>
      </var-card>

    </div>
  </div>
</template>
<script>
import { defineComponent, ref } from 'vue'
import { useStore } from 'vuex'
import axios from 'axios'
import { showSuccessToast } from 'vant'

export default defineComponent({
  name: 'ArticleDetailView',

  setup () {
    const store = useStore()
    const articleDetail = store.state.articleDetail
    const buttonColor = ref('#afb0b2')
    const likeAnimate = ref('')
    const goBack = () => {
      window.history.go(-1)
    }
    const sendLikeArticle = () => {
      axios.post('http://172.20.10.3:8081/article/likeArticle', {
        userId: articleDetail.userId,
        articleId: articleDetail.id
      }).then(res => {
        if (res.data.code === 200) {
          articleDetail.likeCount += 1
          buttonColor.value = 'linear-gradient(to right, rgb(17, 153, 142), rgb(56, 239, 125))'
          likeAnimate.value = 'animate__animated animate__wobble'
          showSuccessToast('点赞成功')
        }
      })
    }
    console.log(articleDetail)
    return {
      articleDetail,
      goBack,
      sendLikeArticle,
      buttonColor,
      likeAnimate
    }
  }
})
</script>
<style scoped>
.articleShow {
  width: 100%;
  display: flex;
  justify-content: space-around;
}

.articleDetailShow {
  margin-top: 5px;
  width: 95%;
}

.itemTitle {
  padding-left: 5px;
  font-family: 'DYH';
  text-align: left;
  margin-bottom: 7px;
}

.itemUserName {
  text-align: left;
  padding-left: 5px;
  margin-top: 4px;
  margin-bottom: 4px;
  color: darkgrey;
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

.itemPopular {
  width: 100%;
  text-align: right;
  margin: 0px;
  color: darkgrey;
}
</style>
