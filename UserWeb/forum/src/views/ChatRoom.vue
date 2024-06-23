<template>
  <div class="main">

    <van-sticky :offset-top="0" position="top">
      <van-nav-bar left-arrow title="聊天室">
      </van-nav-bar>
    </van-sticky>
    <div class="bottomArea">
      <div class="bottomContent">
        <van-field
          autosize
          placeholder="请输入留言"
          rows="2"
          show-word-limit
          type="textarea"
        >
        </van-field>
        <div class="bottomCommentIcon">
          <van-button round style="width: 50px;height: 39px" type="success">
            <van-icon name="guide-o" size="20px"/>
          </van-button>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import { defineComponent } from 'vue'

export default defineComponent({
  name: 'ChatRoom',
  setup () {
    return {}
  }
})

</script>

<style scoped>
.main {
  height: 100vh;
}
.bottomArea {
  position: fixed;
  bottom: 0;
  width: 100%;
  height: 6vh;
  background-color: white;
  overflow: hidden;
}

.bottomContent {
  height: 100%;
  display: flex;
  justify-content: space-around;
}

.bottomCommentInput {
  width: 83%;
  height: 85%;
}

.bottomCommentIcon {
  display: flex;
  flex-direction: column;
  justify-content: space-around;
}
</style>
