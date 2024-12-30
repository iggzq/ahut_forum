<script setup>
import {onActivated, ref} from 'vue';
import store from '@/store';
import HotRankCard from '@/components/HotRankCard.vue';
import axios from "axios";
import router from "@/router";

const hotRankListContent = ref([])
const rankListContentRefreshing = ref(false)

const getHotRankList = () => axios.get('article/getHotRank').then(res => {
  hotRankListContent.value = res.data.data
})

const goToArticleDetail = (item) => {
  router.push({
    name: 'CommonArticleDetailView',
    params: {
      articleId: item.id
    }
  })
}
const refreshRankListContent = () => {
  hotRankListContent.value = []
  rankListContentRefreshing.value = true
  getHotRankList().then(() => {
    rankListContentRefreshing.value = false
  })
}

onActivated(() => {
  store.commit('setActiveTab', 1)
  getHotRankList()
})

</script>
<template>
  <div class="mainHotRank">
    <div class="hotRankList">
      <var-pull-refresh v-model="rankListContentRefreshing" @refresh="refreshRankListContent">
      <div v-for="(item,index) in hotRankListContent">
        <hot-rank-card v-ripple :articleContent="item" :articleRank="index+1" @click="goToArticleDetail(item)"/>
      </div>
      </var-pull-refresh>
    </div>
  </div>
</template>

<style scoped>
@import "@/assets/css/HotRankView.css";
</style>
