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
    <div>
      <u-comment :config="config" @like="like" @submit="submit">
        <template>
          <div>单个comment数据</div>
        </template>
      </u-comment>
    </div>
  </div>
</template>
<script>

import { defineComponent, nextTick, onMounted, reactive, ref, watch } from 'vue'
import axios from 'axios'
import { showSuccessToast } from 'vant'
import emoji from '@/assets/emoji'
import { useRoute } from 'vue-router'
import { UComment } from 'undraw-ui'
import { useStore } from 'vuex'

export default defineComponent({
  name: 'ArticleDetailView',
  components: { UComment },

  setup () {
    const route = useRoute()
    const store = useStore()
    const articleId = route.params.articleId
    const articleDetail = ref({})
    const buttonColor = ref('#afb0b2')
    const likeAnimate = ref('')
    const comment = ref('')
    const bottom = ref(false)
    const writeComment = ref({
      comment: ''
    })
    const commentPositionId = route.params.commentId
    const comments = ref([])
    const skeletonShow = ref(true)
    const goBack = () => {
      window.history.go(-1)
    }
    const getArticleDetail = async () => {
      await axios.get('http://172.20.10.3:8081/article/getArticleById', {
        params: {
          articleId: articleId
        }
      }).then(res => {
        articleDetail.value = res.data.data
      })
    }
    const sendLikeArticle = () => {
      axios.post('http://172.20.10.3:8081/article/likeArticle', {
        userId: articleDetail.value.userId,
        articleId: articleDetail.value.id
      }).then(res => {
        if (res.data.code === 200) {
          articleDetail.value.likeCount += 1
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
      await axios.get('http://172.20.10.3:8081/comment/getCommentByArticleId?id=' + articleId).then(res => {
        if (res.data.code === 200) {
          comments.value = res.data.data
          config.comments = comments.value
          skeletonShow.value = false
        }
      })
    }
    onMounted(() => {
        getArticleDetail()
        getComments()
        store.commit('setActiveTab', 0)
        if (commentPositionId !== undefined) {
          const targetCommentElement = document.querySelector(`[data-id="${commentPositionId}"]`)

          if (targetCommentElement) {
            // 计算目标元素距离页面顶部的距离
            const targetOffsetTop = targetCommentElement.offsetTop
            // 可能需要考虑滚动容器的偏移量，如固定导航栏的高度
            const containerOffsetTop = document.querySelector('.scroll-container').offsetTop // 假设有一个类名为'scroll-container'的滚动容器
            const adjustedTargetTop = targetOffsetTop - containerOffsetTop

            // 平滑滚动到目标位置
            window.scrollTo({
              top: adjustedTargetTop,
              behavior: 'smooth'
            })
          }
        }
      }
    )

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
      const comment = {
        parentId: parentId,
        likes: 0,
        content: content,
        articleId: articleDetail.value.id,
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

</style>
