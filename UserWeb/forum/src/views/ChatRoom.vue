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
                   viewBox="0 0 1024 1024" width="12px" x="1719456864823"
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
  <div ref="chatContainer" class="chatHistory" @scroll="onChatScroll">
    <div class="hint">
      <p v-if="isConnected">——————— 已成功连接,本次随机id为 {{ randomUserId }} ———————</p>
      <p v-else>连接失败,请刷新重试</p>
    </div>
    <div v-for="(comment, index) in othersComments" :key="index">
      <div v-if="comment.id.includes('您说')" class="myCommentShow">
        <div class="myCommentIdShow">
          <strong v-if="(othersComments.length > 1 && comment.id !== othersComments[index - 1]?.id) || index === 0"
                  style="color: #244cff">
            {{ comment.id }} {{
              comment.time
            }}:</strong>
        </div>
        <div class="myCommentContentShow">
          {{ comment.comment }}
        </div>
      </div>
      <div v-else>
        <div class="otherCommentIdShow">
          <strong v-if="(othersComments.length > 1 && comment.id !== othersComments[index - 1]?.id) || index === 0"
                  style="color: #65b36a">
            {{ comment.id }} {{
              comment.time
            }}:</strong>
        </div>
        <div class="otherCommentContentShow">
          {{ comment.comment }}
        </div>
      </div>
    </div>
  </div>

  <div class="animate__animated animate__fadeInRight animate__faster bottomArea">
    <div class="bottomContent">
      <van-field
        v-model="commentValue"
        placeholder="加入聊天吧! 🥳 :)"
        clearable
        maxlength="80"
        @keyup.enter="sendComment"
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
import {onMounted, onUnmounted, reactive, ref} from 'vue'
import router from '@/router'
import {showFailToast} from 'vant'

const socket = ref(null)
const commentValue = ref('')
const othersComments = reactive([])
const isConnected = ref(false)
const randomUserId = Math.floor(Math.random() * 1000).toString()
const currentOnlineUserCount = ref(0)
const chatContainer = ref(null)
const isAtBottom = ref(true)

function adjustChatHistoryHeight() {
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
  // 页面加载完成后默认滚动到底部
  scrollToBottom()
  window.addEventListener('resize', adjustChatHistoryHeight)
  socket.value = new WebSocket(`${process.env.VUE_APP_CHAT_ROOM}` + randomUserId)
  socket.value.addEventListener('open', () => {
    isConnected.value = true
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
      // 在新消息添加后，根据isAtBottom决定是否滚动
      setTimeout(() => {
        if (isAtBottom.value) {
          scrollToBottom()
        }
      })
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
    if (commentValue.value === '') {
      showFailToast('请输入聊天内容')
    } else {
      socket.value.send(commentValue.value)
      commentValue.value = ''
    }
  } else {
    showFailToast('未连接到服务器，请检查网络连接')
  }
}

// 监听滚动事件
const onChatScroll = () => {
  // 判断是否滚动到了底部
  const container = chatContainer.value
  if (container) {
    const {
      scrollTop,
      scrollHeight,
      clientHeight
    } = container
    isAtBottom.value = Math.abs(scrollHeight - scrollTop - clientHeight) < 1
  }
}

// 滚动到底部的方法
const scrollToBottom = () => {
  console.log(isAtBottom.value)
  if (chatContainer.value) {
    chatContainer.value.scrollTop = chatContainer.value.scrollHeight
  }
}

const goBack = () => {
  router.push('article')
}

</script>

<style scoped>
@import "@/assets/css/ChatRoom.css";
</style>
