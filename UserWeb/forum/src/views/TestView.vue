<script setup>
// 下载表情包资源emoji.zip [emoji.zip下载](https://gitee.com/undraw/undraw-ui/releases/tag/v0.0.0)
// static文件放在public下,引入emoji.ts文件可以移动assets下引入,也可以自定义到指定位置
import { reactive } from 'vue'
import emoji from '@/assets/emoji'
import axios from 'axios'
import { showSuccessToast } from 'vant'

const config = reactive({
  user: {
    id: 1,
    username: 'jack',
    avatar: 'https://static.juzicon.com/avatars/avatar-200602130320-HMR2.jpeg?x-oss-process=image/resize,w_100',
    // 评论id数组 建议:存储方式用户uid和评论id组成关系,根据用户uid来获取对应点赞评论id,然后加入到数组中返回
    likeIds: [1, 2, 3]
  },
  emoji: emoji,
  comments: [],
  total: 10
})

let tempId = 100
// 提交评论事件
const submit = async ({
  content,
  parentId,
  finish
}) => {
  const comment = {
    id: String((tempId += 1)),
    parentId: parentId,
    uid: config.user.id,
    content: content,
    likes: 0,
    user: {
      username: config.user.username
    },
    reply: null
  }

  await axios.post('http://172.20.10.3:8081/article/commentArticle', {
    parentId: null,
    content: '123',
    articleId: '1769554054627688448'
  }).then(res => {
    finish(comment)
    // const inputElement = document.querySelector('.rich-input')
    // const inputActive = document.querySelector('.active')
    // inputActive.className = 'item'
    // inputElement.innerHTML = ''
    showSuccessToast('评论成功')
  })
}
// 点赞按钮事件 将评论id返回后端判断是否点赞，然后在处理点赞状态
const like = (id, finish) => {
  console.log('点赞: ' + id)
  setTimeout(() => {
    finish()
  }, 200)
}

config.comments = [
  {
    id: '1',
    parentId: null,
    uid: '1',
    content:
      '缘生缘灭，缘起缘落，我在看别人的故事，别人何尝不是在看我的故事?别人在演绎人生，我又何尝不是在这场戏里?谁的眼神沧桑了谁?我的眼神，只是沧桑了自己[喝酒]',
    likes: 2,
    user: {
      username: '落🤍尘'
    },
    reply: {
      total: 1,
      list: [{
        id: '2',
        parentId: '1',
        uid: '1',
        content: '缘生缘灭，缘起缘落',
        likes: 0,
        user: {
          username: 'Test'
        }
      }]
    }
  }
]
</script>

<template>
  <u-comment :config="config" @like="like" @submit="submit">
    <!-- <template>导航栏卡槽</template> -->
    <!-- <template #info>用户信息卡槽</template> -->
    <!-- <template #card>用户信息卡片卡槽</template> -->
    <!-- <template #opearte>操作栏卡槽</template> -->
  </u-comment>
</template>

<style scoped>

</style>
