<script setup>
import {onActivated, ref} from 'vue';
import store from '@/store';
import HotRankCard from '@/components/HotRankCard.vue';
import axios from "axios";

const hotRankListContent = ref([])
axios.get('article/getHotRank').then(res => {
  hotRankListContent.value = res.data.data
})

onActivated(() => {
  store.commit('setActiveTab', 1)
})

</script>
<template>
  <div class="mainHotRank">
    <div class="hotRankList">
      <div v-for="(item,index) in hotRankListContent">
        <hot-rank-card :articleContent="item" :articleRank="index+1"/>
      </div>
    </div>
  </div>
</template>

<style scoped>
@import "@/assets/css/HotRankView.css";
</style>
