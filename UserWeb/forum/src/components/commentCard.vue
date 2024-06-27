<script>
import { defineComponent, ref } from 'vue'
import { useRouter } from 'vue-router'

export default defineComponent({
  name: 'commentCard',
  props: {
    comment: {
      type: Object,
      default: null
    }
  },
  setup (props) {
    const commentNow = ref(props.comment)
    const router = useRouter()
    const goArticleComment = () => {
      router.push({
        name: 'commentArticleDetail',
        params: {
          articleId: commentNow.value.articleId,
          commentId: commentNow.value.id
        }
      })
    }
    return {
      commentNow,
      goArticleComment
    }
  }
})
</script>

<template>
  <div class="commentCardBody" @click="goArticleComment">
    <div class="commentCardLeft">
      <img class="leftImg" src="src/assets/logo.png"/>
    </div>
    <div class="commentCardCenter">
      <div class="centerTop">
        <p class="userNameShow">{{ commentNow.username }} </p>
        <p style="color: #686868; width: 50%; margin: 0">回复了我的评论:</p>
      </div>
      <van-text-ellipsis
        :content="commentNow.content"
        class="centerContent"
        rows="2"
      />
    </div>
    <div class="commentCardRight">
      <van-text-ellipsis
        :content="commentNow.articleTitle"
        class="rightContent"
        rows="3"
      />
    </div>
  </div>
</template>

<style scoped>
.commentCardBody {
  border-bottom: 1px #42b983;
  width: 100vw;
  height: 14vh;
  display: flex;
  flex-direction: row;
  padding-top: 1%;
  padding-bottom: 1%;
}

.commentCardLeft {
  height: 100%;
  width: 20%;
}

.leftImg {
  width: 18vw;
  height: 40%;
}

.userNameShow {
  margin: 0;
  font-weight: 700;
}

.commentCardCenter {
  height: 100%;
  width: 60%;
}

.centerContent {
  font-weight: 600;
}

.commentCardRight {
  width: 17vw;
  height: 100%;
  font-weight: lighter;
  color: #acacac;
  margin-left: 2%;
}
</style>
