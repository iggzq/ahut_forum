<template>
  <div class="entireBody">
    <div class="inputForm">
      <van-form style="display: block" @submit="onSubmit">
        <van-cell-group class="infoInputForm">
          <h2 style="text-align: center">注册</h2>
          <van-field
            v-model="name"
            :rules="[{ required: true, message: '请填写用户名' }]"
            class="inputField"
            label="用户名"
            name="用户名"
            placeholder="用户名"
          />
          <van-field
            v-model="password"
            :rules="[{ required: true, message: '请填写密码' }]"
            class="inputField"
            label="密码"
            name="密码"
            placeholder="密码"
            type="password"
          />
          <van-field
            v-model="question"
            :rules="[{ required: true, message: '请填写自定义安全问题' }]"
            class="inputField"
            label="问题"
            name="问题"
            placeholder="自定义安全问题"
          />
          <van-field
            v-model="answer"
            :rules="[{ required: true, message: '请填写自定义安全问题答案' }]"
            class="inputField"
            label="答案"
            name="答案"
            placeholder="自定义安全问题答案"
          />
        </van-cell-group>
        <div style="margin: 16px 16px 0px">
          <van-button block native-type="submit" round type="primary">
            注册
          </van-button>
        </div>
      </van-form>
    </div>

  </div>

</template>
<script>
import { defineComponent, ref } from 'vue'
import axios from 'axios'
import { showFailToast, showSuccessToast } from 'vant'
import router from '@/router'

export default defineComponent({
  setup () {
    const name = ref('')
    const password = ref('')
    const question = ref('')
    const answer = ref('')
    const onSubmit = (values) => {
      registerRequest()
      console.log('submit', values)
    }
    const registerRequest = () => {
      axios.post('/user/register', {
        name: name.value,
        password: password.value,
        question: question.value,
        answer: answer.value
      }).then(res => {
        if (res.data.code === 200) {
          name.value = ''
          password.value = ''
          question.value = ''
          answer.value = ''
          showSuccessToast('注册成功')
          router.push('/login')
        } else {
          showFailToast('注册失败，请重试')
        }
      })
    }
    return {
      name,
      password,
      question,
      answer,
      onSubmit
    }
  }
})

</script>
<style scoped>
html, body {
  height: 100%;

}

.entireBody {
  width: 100%;
  height: 100%;
  display: flex;
  justify-content: space-around;
  background-color: rgba(72, 50, 133, 0);
}

.entireBody .inputForm {
  height: 100%;
  display: flex;
  flex-direction: column;
  justify-content: space-around;
}

.infoInputForm {
  border-radius: 10px;
  border: 1px #42b983;
  display: flex;
  flex-wrap: wrap;
  flex-direction: column;
  width: 85vw;
}
.inputField{
  margin-top: 3px;
  margin-bottom: 4px;
}

</style>
