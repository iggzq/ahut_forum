<template>
  <van-sticky :offset-top="0">
    <van-nav-bar title="个人信息"/>
  </van-sticky>
  <van-cell is-link to="myComments">
    <template #title>
      <van-badge dot>
        我的消息
      </van-badge>
    </template>
  <template #value>
  </template>
  </van-cell>
</template>
<script>
export default {
  name: 'myProfile',
  data () {
    return {}
  },
  methods: {}
}
</script>
