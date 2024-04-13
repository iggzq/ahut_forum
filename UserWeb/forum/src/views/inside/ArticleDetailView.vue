<template>
  <van-sticky>
    <van-nav-bar left-arrow title="文章详情">
      <template #left>
        <van-icon name="arrow-left" @click="goBack"/>
      </template>
    </van-nav-bar>
  </van-sticky>
  <div class="articleDetail">
    <div class="articleShow">
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

    <!--    <div v-for="(comment, index) in comments" :key="index" class="commentShow">-->
    <!--      <var-card v-if="comments.length > 0" class="commentCard" ripple>-->
    <!--        <template #title>-->
    <!--          <p class="commentUserName">{{ comment.userName }}说：</p>-->
    <!--        </template>-->
    <!--        <template #description>-->
    <!--          <p class="commentContent">{{ comment.comment }}</p>-->
    <!--        </template>-->
    <!--      </var-card>-->
    <!--    </div>-->
    <div>
      <u-comment :config="config" @like="like" @submit="submit">
        <!-- <template>导航栏卡槽</template> -->
        <!-- <template #info>用户信息卡槽</template> -->
        <!-- <template #card>用户信息卡片卡槽</template> -->
        <!-- <template #opearte>操作栏卡槽</template> -->
        <div v-if="skeletonShow" class="skeletonShow">
          <var-card class="skeletonShowCard">
            <template #description>
              <van-skeleton :row="3" class="commentSkeleton" title/>
            </template>
          </var-card>
        </div>
      </u-comment>
    </div>
  </div>
</template>
<script>

import { defineComponent, nextTick, onMounted, reactive, ref, watch } from 'vue'
import { useStore } from 'vuex'
import axios from 'axios'
import { showSuccessToast } from 'vant'
import emoji from '@/assets/emoji'

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
    const comments = ref([])
    const skeletonShow = ref(true)
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
    const showWriteComment = () => {
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
    const getComments = async () => {
      await axios.get('http://172.20.10.3:8081/comment/getCommentByArticleId?id=' + articleDetail.id).then(res => {
        if (res.data.code === 200) {
          comments.value = res.data.data
          config.comments = comments.value
          skeletonShow.value = false
        }
      })
    }
    onMounted(() => {
      getComments()
    })

    const config = reactive({
      user: {
        id: 1,
        username: 'jack',
        avatar: '',
        // 评论id数组 建议:存储方式用户uid和评论id组成关系,根据用户uid来获取对应点赞评论id,然后加入到数组中返回
        likeIds: [1, 2, 3]
      },
      emoji: emoji,
      comments: [],
      total: 100,
      showLevel: false,
      showAddress: false,
      placeholder: '欢迎评论',
      showHomeLink: false
    })

    // 提交评论事件
    const submit = async ({
      content,
      parentId,
      reply,
      finish
    }) => {
      console.log(1)
      console.log(reply.id)
      const comment = {
        parentId: parentId,
        likes: 0,
        content: content,
        articleId: articleDetail.id,
        toUserId: reply.uid,
        user: {
          username: ''
        }
      }
      await axios.post('http://172.20.10.3:8081/article/commentArticle', {
        parentId: comment.parentId,
        content: comment.content,
        articleId: comment.articleId,
        toUserId: comment.toUserId
      }).then(res => {
        if (res.data.code === 200) {
          getComments()
          finish(comment)
          articleDetail.commentCount += 1
          showSuccessToast('评论成功')
        }
      })
    }
    // 点赞按钮事件 将评论id返回后端判断是否点赞，然后在处理点赞状态
    const like = (id, finish) => {
      console.log('点赞: ' + id)
      setTimeout(() => {
        finish()
      }, 200)
    }

    console.log(config.comments)
    return {
      articleDetail,
      goBack,
      sendLikeArticle,
      buttonColor,
      likeAnimate,
      comment,
      showWriteComment,
      bottom,
      writeComment,
      skeletonShow,
      comments,
      like,
      submit,
      config
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

.articleDetail {
  height: 88vh;
  overflow-y: auto;
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

.skeletonShow {
  width: 93vw;
  justify-content: space-around;
}

.skeletonShowCard {
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
  height: 6vh;
  background-color: white;
  overflow: hidden;
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

.commentShow {
  width: 100%;
  display: flex;
  margin-top: 10px;
  justify-content: space-around;
}

.commentCard {
  margin-top: 5px;
  width: 95%;
}

.commentUserName {
  padding-left: 5px;
  font-family: 'DYH';
  text-align: left;
  margin-bottom: 7px;
}

.commentContent {
  text-align: left;
  padding-left: 5px;
}

#app {
  text-align: left;
}
</style>
