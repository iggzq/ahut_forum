<script setup>
import {onMounted, ref} from 'vue'
import router from "@/router";
import axios from "axios";
import {showFailToast} from "vant";
import CommonArticleList from "@/components/CommonArticleList.vue";

const searchContent = ref('')
const searchRef = ref(null)
const searchResult = ref([])

onMounted(() => {
  if (searchRef.value) {
    searchRef.value.focus()
  }
})

const onSearch = () => {
  axios.get(`${process.env.VUE_APP_SEARCH}` + 'article/search/byKey', {
    params: {
      key: searchContent.value
    }
  }).then(res => {
    if (res.data.code === 200) {
      searchResult.value = res.data.data
      console.log(searchResult.value)
    }else {
      showFailToast("查询失败")
    }
  })
}

const onSearchCancel = () => {
  searchContent.value = ''
}
const goBack = () => {
  router.go(-1)
}


</script>

<template>
  <div class="searchViewMain">
    <div class="searchViewTop">
      <van-nav-bar>
        <template #left>
          <van-icon name="arrow-left" size="large" @click="goBack"/>
        </template>
        <template #title>
          <div class="searchContent">
            <van-search ref="searchRef" v-model="searchContent"
                        placeholder="请输入搜索关键词"
                        @cancel="onSearchCancel"
                        @search="onSearch"/>
          </div>
        </template>
      </van-nav-bar>
    </div>
    <div class="searchResult">
      <CommonArticleList :article-list="searchResult"/>
    </div>
  </div>
</template>

<style scoped>
@import "@/assets/css/SearchView.css";
</style>
