<template>
  <van-sticky>
    <van-nav-bar left-arrow title="标题">
      <template #left>
        <van-icon name="arrow-left" @click="goBack"/>
      </template>
      <template #right>
        <van-icon name="search" size="18"/>
      </template>
    </van-nav-bar>
  </van-sticky>
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
          rows="3"
        />
      </template>
      <template #extra>
        <van-button round type="success">
          <van-icon name="good-job" />
          {{ articleDetail.likeCount }} 点赞 {{ articleDetail.commentCount }} 评论数
        </van-button>
      </template>
    </var-card>
  </div>

</template>
<script>
import { defineComponent, ref } from 'vue'
import { useStore } from 'vuex'

export default defineComponent({
  name: 'ArticleDetailView',

  setup () {
    const store = useStore()
    const articleDetail = store.state.articleDetail
    const name = ref('information')

    function toggle () {
      name.value = name.value === 'information'
        ? 'checkbox-marked-circle'
        : 'information'
    }

    const goBack = () => {
      window.history.go(-1)
    }
    console.log(articleDetail)
    return {
      articleDetail,
      goBack,
      toggle
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
