<script setup>
import { ref } from 'vue'
const article = ref({
  rank: 1,
  title: ' 啥v句JSAV操作v你车v你先擦不拿秩序传达干撒',
  hotNum: 123
})
</script>

<template>
  <div class="mainLayout">
    <div class="leftContent">
      {{ article.rank }}
    </div>
    <div class="rightContent">
      {{ article.title }}
      <div class="hotNumClass">
        <van-icon name="fire-o" size="1rem"/>
        {{ article.hotNum }}
      </div>
    </div>
  </div>
</template>

<style scoped>
@import "@/assets/css/HotRankCard.css";
</style>
