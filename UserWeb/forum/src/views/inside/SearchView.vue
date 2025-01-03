<script setup>
import {onMounted, ref} from 'vue'
import router from "@/router";
import axios from "axios";
import {showFailToast} from "vant";
import SearchArticleList from "@/components/SearchArticleList.vue";

const searchContent = ref('')
const searchOld = ref('')
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
      searchOld.value = searchContent.value
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
  <router-view/>
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
      <SearchArticleList :article-list="searchResult" :search-old="searchOld"/>
    </div>
  </div>
</template>

<style scoped>
@import "@/assets/css/SearchView.css";
</style>
