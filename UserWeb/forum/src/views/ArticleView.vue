<template>
  <van-sticky :offset-top="0">
    <van-nav-bar title="新鲜事"/>
  </van-sticky>
  <van-grid>
    <van-grid-item icon="photo-o" text="文字"/>
    <van-grid-item icon="photo-o" text="文字"/>
    <van-grid-item icon="photo-o" text="文字"/>
    <van-grid-item icon="photo-o" text="文字"/>
  </van-grid>
  <div class="articleContent">
    <div v-for="item in articles" :key="item.id" class="articleShow">
      <var-card
        :subtitle="item.content"
        layout="row"
        src="https://varlet.gitee.io/varlet-ui/cat.jpg"
        title="本草纲目"
      >
        <template #extra>
          <var-button round text>
            <var-icon name="star"/>
          </var-button>
          <var-button round text>
            <var-icon name="heart"/>
          </var-button>
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
    const getArticlesByPage = () => {
      console.log('getArticlesByPage')
      axios.get('http://172.20.10.3:8081/article/getArticles?page=0&size=10').then(res => {
        console.log(res)
        articles.value = res.data.data
      })
    }
    onMounted(() => {
      getArticlesByPage()
    })
    return { articles }
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

</style>
