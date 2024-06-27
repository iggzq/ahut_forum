<template>
  <div class="topArea">
    <van-sticky :offset-top="0" position="top">
      <van-nav-bar left-arrow @click-left="goBack">
        <template #title>
          <div class="title-container">
            <p class="animate__animated animate__fadeInRight title">
              聊天室
              <span v-if="isConnected" class="connection-status"></span>
              <svg v-else class="outlineIcon" height="12px" p-id="7669"
                   version="1.1" viewBox="0 0 1024 1024" width="12px" x="1719456864823"
                   xmlns="http://www.w3.org/2000/svg">
                <path d="M512 512m-512 0a512 512 0 1 0 1024 0 512 512 0 1 0-1024 0Z" fill="#C4C4C4" p-id="7670"></path>
              </svg>
            </p>
          </div>
        </template>
        <template #right>
          <span v-if="isConnected" class="currentUserCount">{{ currentOnlineUserCount }}人</span>
        </template>
      </van-nav-bar>
    </van-sticky>
  </div>
  <div class="chatHistory">
    <div class="hint">
      <p v-if="isConnected">——————— 已成功连接,本次随机id为 {{ randomUserId }} ———————</p>
      <p v-else>连接失败,请刷新重试</p>
    </div>
    <div v-for="(comment, index) in othersComments" :key="index" class="otherComment">
      <strong>{{ comment.id }} {{ comment.time }}:</strong>
      <br>
      {{ comment.comment }}
    </div>
  </div>

  <div class="animate__animated animate__fadeInRight animate__faster bottomArea">
    <div class="bottomContent">
      <van-field
        v-model="commentValue"
        placeholder="加入聊天吧! 🥳 :)"
        clearable
        maxlength="80"
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

<script setup>
import { onMounted, onUnmounted, reactive, ref } from 'vue'
import router from '@/router'
import { showFailToast } from 'vant'

const socket = ref(null)
const commentValue = ref('')
const othersComments = reactive([])
const isConnected = ref(false)
const randomUserId = Math.floor(Math.random() * 1000).toString()
const currentOnlineUserCount = ref(0)

function adjustChatHistoryHeight () {
  const navBarContent = document.querySelector('.topArea')
  const bottomArea = document.querySelector('.bottomArea')
  const chatHistoryElement = document.querySelector('.chatHistory')

  if (navBarContent && bottomArea && chatHistoryElement) {
    const navBarContentHeight = navBarContent.getBoundingClientRect().height
    const bottomAreaHeight = bottomArea.getBoundingClientRect().height
    chatHistoryElement.style.height = `calc(100vh - ${navBarContentHeight}px - ${bottomAreaHeight}px)`
  }
}

onMounted(() => {
  adjustChatHistoryHeight()
  window.addEventListener('resize', adjustChatHistoryHeight)
  socket.value = new WebSocket('ws://172.20.10.3:8082/chat/' + randomUserId)
  socket.value.addEventListener('open', (event) => {
    isConnected.value = true
    console.log(event)
  })
  socket.value.addEventListener('message', (event) => {
    // 在这里可以处理接收到的消息，比如将其显示在聊天记录中
    const newComment = JSON.parse(event.data)
    console.log(newComment)
    if (Object.prototype.hasOwnProperty.call(newComment, 'clientCount')) {
      currentOnlineUserCount.value = newComment.clientCount
      console.log(currentOnlineUserCount.value)
    } else {
      othersComments.push(newComment)
    }
  })
  socket.value.addEventListener('error', () => {
  })
  socket.value.addEventListener('close', () => {
  })
})
onUnmounted(() => {
  window.removeEventListener('resize', adjustChatHistoryHeight)
  if (socket.value && socket.value.readyState === WebSocket.OPEN) {
    socket.value.close()
  }
})
const sendComment = () => {
  if (socket.value && socket.value.readyState === WebSocket.OPEN) {
    socket.value.send(commentValue.value)
    commentValue.value = '' // 清空输入框
  } else {
    showFailToast('未连接到服务器，请检查网络连接')
  }
}

const goBack = async () => {
  router.back()
}

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

.bottomCommentIcon {
  display: flex;
  flex-direction: column;
  justify-content: space-around;
}

.chatHistory {
  overflow-y: auto;
}

.hint {
  font-size: small;
  color: #cccbcb;
  display: flex;
  justify-content: center;
}

.bottomContent .van-cell :deep(.van-field) {
  padding-top: 0;
  padding-bottom: 0;
}

.otherComment {
  padding-top: 7px;
  padding-bottom: 7px;
  padding-left: 5px;
}

.animate__fadeInRight {
  animation-duration: 0.2s;
}

.title-container {
  position: relative; /* 保留相对定位 */
}

.connection-status {
  margin-left: 4px;
  margin-right: 4px;
  width: 10px;
  height: 10px;
  background-color: #30bf36; /* 绿色 */
  border-radius: 50%; /* 圆形 */
  animation: breathing 2s infinite; /* 呼吸灯效果动画 */
}

@keyframes breathing {
  0% {
    transform: scale(0.6);
    opacity: 0.6;
  }
  50% {
    transform: scale(1);
    opacity: 1;
  }
  100% {
    transform: scale(0.6);
    opacity: 0.6;
  }
}

.title {
  display: flex;
  align-items: center;
}

.outlineIcon {
  margin-left: 4px;
}

.currentUserCount {
  font-size: small;
  color: #ccc9c9;
}
</style>
