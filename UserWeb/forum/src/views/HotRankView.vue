<script setup>
import {onActivated} from 'vue';
import store from '@/store';
import HotRankCard from '@/components/HotRankCard.vue';

onActivated(() => {
  store.commit('setActiveTab', 1)
})
</script>
<template>
  <div class="mainHotRank">
    <div class="hotRankList">
      <hot-rank-card></hot-rank-card>
      <hot-rank-card></hot-rank-card>
      <hot-rank-card></hot-rank-card>
      <hot-rank-card></hot-rank-card>
      <hot-rank-card></hot-rank-card>
      <hot-rank-card></hot-rank-card>
      <hot-rank-card></hot-rank-card>
      <hot-rank-card></hot-rank-card>
      <hot-rank-card></hot-rank-card>
    </div>
  </div>
</template>

<style scoped>
@import "@/assets/css/HotRankView.css";
</style>
