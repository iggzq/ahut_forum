<template>
  <div class="animate__animated animate__fadeInRight animate__faster">
    <div class="top">
      <van-sticky>
        <van-nav-bar left-arrow title="文章详情">
          <template #left>
            <van-icon name="arrow-left" @click="goBack"/>
          </template>
        </van-nav-bar>
      </van-sticky>
    </div>
    <div class="articleDetail">
      <div class="articleShow">
        <var-card
          class="articleDetailShow"
        >
          <template #subtitle>
            <p class="itemUserName">{{ articleDetail.admissionYear }}级 发帖人：{{ articleDetail.userName }}</p>
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
      <div>
        <u-comment :config="config" @like="like" @submit="submit">
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
  </div>
</template>
<script setup>

import { nextTick, onMounted, reactive, ref, watch } from 'vue'
import axios from 'axios'
import { showSuccessToast } from 'vant'
import emoji from '@/assets/emoji'
import { useRoute } from 'vue-router'
import { UComment } from 'undraw-ui'
import { useStore } from 'vuex'

const route = useRoute()
const store = useStore()
const articleId = route.params.articleId
const articleDetail = ref({})
const buttonColor = ref('#afb0b2')
const likeAnimate = ref('')
const bottom = ref(false)
// const commentPositionId = route.params.commentId
const comments = ref([])
const skeletonShow = ref(true)
const goBack = () => {
  window.history.go(-1)
}
const getArticleDetail = async () => {
  await axios.get('article/getArticleById', {
    params: {
      articleId: articleId
    }
  }).then(res => {
    articleDetail.value = res.data.data
    console.log(articleDetail)
  })
}
const sendLikeArticle = async () => {
  await axios.post('article/likeArticle', {
    userId: articleDetail.value.userId,
    articleId: articleDetail.value.id
  }).then(res => {
    if (res.data.code === 200 && articleDetail.value.isLike === false) {
      articleDetail.value.likeCount += 1
      articleDetail.value.isLike = true
      likeAnimate.value = 'animate__animated animate__wobble'
      changeLikeCss()
      showSuccessToast('点赞成功')
    } else if (res.data.code === 200 && articleDetail.value.isLike) {
      articleDetail.value.likeCount -= 1
      articleDetail.value.isLike = false
      likeAnimate.value = ''
      changeLikeCss()
      showSuccessToast('取消点赞')
    }
  })
}

const changeLikeCss = () => {
  if (articleDetail.value.isLike) {
    buttonColor.value = 'linear-gradient(to right, rgb(17, 153, 142), rgb(56, 239, 125))'
  } else {
    buttonColor.value = '#afb0b2'
  }
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
  await axios.get('comment/getCommentByArticleId?id=' + articleId).then(res => {
    if (res.data.code === 200) {
      comments.value = res.data.data
      config.comments = comments.value
      skeletonShow.value = false
    }
  })
}

function caculateArticleDetailHeight () {
  const top = document.querySelector('.top')
  const articleDetail = document.querySelector('.articleDetail')
  const topHeight = top.getBoundingClientRect().height
  articleDetail.style.height = `calc(100vh - ${topHeight}px)`
}

onMounted(async () => {
    caculateArticleDetailHeight()
    await getArticleDetail()
    await getComments()
    changeLikeCss()
    store.commit('setActiveTab', 0)
  }
)

const config = reactive({
  user: {
    id: 1,
    username: '我的',
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
  const comment = {
    parentId: parentId,
    likes: 0,
    content: content,
    articleId: articleDetail.value.id,
    toUserId: reply != null ? reply.uid : null,
    user: {
      username: ''
    }
  }
  await axios.post('article/commentArticle', {
    parentId: comment.parentId,
    content: comment.content,
    articleId: comment.articleId,
    toUserId: comment.toUserId
  }).then(res => {
    if (res.data.code === 200) {
      getComments()
      finish(comment)
      articleDetail.value.commentCount += 1
      showSuccessToast('评论成功')
    }
  })
}
// 点赞按钮事件 将评论id返回后端判断是否点赞，然后在处理点赞状态
const like = (id, finish) => {
  setTimeout(() => {
    finish()
  }, 200)
}
</script>
<style scoped>
@import "@/assets/css/ArticleDetailView.css";
</style>
