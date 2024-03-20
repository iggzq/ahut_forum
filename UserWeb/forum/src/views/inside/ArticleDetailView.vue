<template>
  <van-sticky>
    <van-nav-bar left-arrow title="文章详情">
      <template #left>
        <van-icon name="arrow-left" @click="goBack"/>
      </template>
    </van-nav-bar>
  </van-sticky>
  <div class="animate__animated animate__fadeInRight articleDetail">
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
    <div class="commentShow">
      <var-card class="commentDetailShow">
        <template #description>
          <van-skeleton :row="3" class="commentSkeleton" title/>
        </template>
      </var-card>
    </div>
  </div>
  <div class="bottomArea">
    <div class="bottomContent">
      <van-field
        v-model="writeComment.comment"
        autosize
        maxlength="50"
        placeholder="请输入留言"
        readonly
        rows="2"
        show-word-limit
        type="textarea"
        @click="showWriteComment"
      />
      <div class="bottomCommentIcon">
        <van-button round type="success">
          <van-icon name="guide-o" size="24px" @click="sendComment"/>
        </van-button>
      </div>
    </div>
  </div>
  <var-popup v-model:show="bottom" :default-style="true" position="bottom">
    <van-nav-bar title="留言">
      <template #left>
        <van-icon name="cross" size="25" @click="bottom = false"/>
      </template>
      <template #right>
        <van-button style="height: 30px" type="primary" @click="sendComment">发布</van-button>
      </template>
    </van-nav-bar>
    <van-cell-group inset>
      <van-field
        v-model="writeComment.comment"
        :autofocus="true"
        autosize
        class="writeCommentContent"
        maxlength="100"
        placeholder="请输入留言"
        rows="2"
        show-word-limit
        type="textarea"
      />
    </van-cell-group>
  </var-popup>
</template>
<script>

import { defineComponent, nextTick, ref, watch } from 'vue'
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
    const comment = ref('')
    const bottom = ref(false)
    const writeComment = ref({
      comment: ''
    })
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
    const sendComment = () => {
      axios.post('http://172.20.10.3:8081/article/commentArticle', {
        articleId: articleDetail.id,
        comment: writeComment.value.comment
      }).then(res => {
        if (res.data.code === 200) {
          bottom.value = false
          writeComment.value.comment = ''
          articleDetail.commentCount += 1
          showSuccessToast('评论成功')
        }
      })
    }
    const showWriteComment = () => {
      console.log(123)
      bottom.value = true
    }
    watch(() => bottom.value,
      (newVal) => {
        if (newVal) {
          nextTick(() => {
            const textarea = document.querySelector('.writeCommentContent textarea')
            if (textarea) {
              textarea.focus()
            }
          })
        }
      },
      { immediate: true }
    )
    console.log(articleDetail)
    return {
      articleDetail,
      goBack,
      sendLikeArticle,
      buttonColor,
      likeAnimate,
      comment,
      sendComment,
      showWriteComment,
      bottom,
      writeComment
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
.articleDetail{
  position: fixed;
  top: 40px;
  margin-top: 6px;
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

.commentShow {
  width: 100%;
  display: flex;
  margin-top: 10px;
  justify-content: space-around;
}

.commentDetailShow {
  margin-top: 5px;
  width: 95%;
}

.commentSkeleton {
  margin-top: 10px;
}

.itemPopular {
  width: 100%;
  text-align: right;
  margin: 0px;
  color: darkgrey;
}

.bottomArea {
  position: fixed;
  bottom: 0;
  width: 100%;
  height: 50px;
  background-color: white;
}

.bottomContent {
  height: 100%;
  display: flex;
  justify-content: space-around;
}

.bottomCommentInput {
  width: 83%;
  height: 85%;
}

.bottomCommentIcon {
  display: flex;
  flex-direction: column;
  justify-content: space-around;
}

.writeCommentContent {
  height: 50vh;
}
</style>
