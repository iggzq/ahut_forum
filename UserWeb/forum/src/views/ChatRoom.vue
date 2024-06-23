<template>
  <div class="topArea">
    <van-sticky :offset-top="0" position="top">
      <van-nav-bar left-arrow title="聊天室">
      </van-nav-bar>
    </van-sticky>
  </div>

  <div class="chatHistory">
    <div v-for="(comment, index) in othersComments" :key="index" class="otherComment">
      <strong>{{ comment.id }}:</strong> {{ comment.comment }}
    </div>
  </div>

  <div class="bottomArea">
    <div class="bottomContent">
      <van-field
        v-model="commentValue"
        autosize
        placeholder="加入聊天吧!   :)"
        rows="2"
        show-word-limit
        type="textarea"
      >
      </van-field>
      <div class="bottomCommentIcon">
        <van-button round style="width: 50px;height: 39px" type="success" @click="sendComment">
          <van-icon name="guide-o" size="20px"/>
        </van-button>
      </div>
    </div>
  </div>
</template>

<script>
import { defineComponent, onMounted, onUnmounted, reactive, ref } from 'vue'

export default defineComponent({
  name: 'ChatRoom',
  setup () {
    const socket = ref(null)
    const commentValue = ref('')
    const othersComments = reactive([])
    onMounted(() => {
      const randomUserId = Math.floor(Math.random() * 1000).toString()
      socket.value = new WebSocket('ws://172.20.10.3:8082/chat/' + randomUserId) // 替换为你的WebSocket服务器地址
      socket.value.addEventListener('open', (event) => {
        console.log('WebSocket连接已打开')
      })
      socket.value.addEventListener('message', (event) => {
        console.log('接收到消息:', event.data)
        // 在这里可以处理接收到的消息，比如将其显示在聊天记录中
        const newComment = JSON.parse(event.data);
        othersComments.push(newComment);
        console.log(othersComments)
      })
      socket.value.addEventListener('error', (error) => {
        console.error('WebSocket错误:', error)
      })
      socket.value.addEventListener('close', (event) => {
        console.log('WebSocket连接已关闭')
      })
    })
    onUnmounted(() => {
      if (socket.value && socket.value.readyState === WebSocket.OPEN) {
        socket.value.close()
      }
    })
    const sendComment = () => {
      if (socket.value && socket.value.readyState === WebSocket.OPEN) {
        socket.value.send(commentValue.value)
        commentValue.value = '' // 清空输入框
      } else {
        console.warn('WebSocket连接未建立')
      }
    }
    return {
      commentValue,
      sendComment,
      othersComments
    }
  }
})

</script>

<style scoped>
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
