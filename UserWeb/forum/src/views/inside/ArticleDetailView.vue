<template>
  <div class="animate__animated animate__fadeInRight animate__faster">
    <div class="top">
      <van-sticky>
        <van-nav-bar left-arrow size="25" title="文章详情">
          <template #left>
            <van-icon name="arrow-left" @click="goBack"/>
          </template>
        </van-nav-bar>
      </van-sticky>
    </div>
    <div class="articleDetail">
      <div class="articleDetailShowCard">
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
            <var-space>
              <van-tag :type="tagTypeMap[topicTypeToString[articleDetail.topicType]]" size="medium">{{
                  topicTypeToString[articleDetail.topicType]
                }}
              </van-tag>
            </var-space>
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

import {nextTick, onMounted, reactive, ref, watch} from 'vue'
import axios from 'axios'
import {showSuccessToast} from 'vant'
import emoji from '@/assets/emoji'
import {useRoute} from 'vue-router'
import {UComment} from 'undraw-ui'

const route = useRoute()
const articleId = route.params.articleId
const articleDetail = ref({})
const buttonColor = ref('#afb0b2')
const likeAnimate = ref('')
const bottom = ref(false)
const comments = ref([])
const skeletonShow = ref(true)
const goBack = () => {
  window.history.go(-1)
}
const tagTypeMap = {
  '#学习提问': 'primary',
  '#校园逸事': 'success',
  '#生活闲谈': 'warning',
  '#丢失/拾获': 'danger'
}
const topicTypeToString = {
  0: '#学习提问',
  1: '#校园逸事',
  2: '#生活闲谈',
  3: '#丢失/拾获'
}

const getArticleDetail = async () => {
  await axios.get('article/getArticleById', {
    params: {
      articleId: articleId
    }
  }).then(res => {
    articleDetail.value = res.data.data
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
  {immediate: true}
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

function caculateArticleDetailHeight() {
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
  showHomeLink: false,
  show: {
    level: false,    // 关闭等级显示
    homeLink: false, // 关闭个人主页链接跳转
    address: false, // 关闭地址信息
    likes: false    // 关闭点赞按钮显示
  }
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
