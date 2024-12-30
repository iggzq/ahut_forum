<template>
  <div class="entireBody">
    <div class="inputForm">
      <van-form required="auto" style="display: block" @submit="onSubmit">
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
            v-model="admissionYearKey"
            is-link
            label="请选择"
            name="picker"
            placeholder="点击选择入学年份"
            :rules="[{ required: true, message: '请选择入学年份'}]"
            readonly
            @click="showPicker = true"
          />
          <van-popup v-model:show="showPicker" position="bottom">
            <van-picker
              :columns="columns"
              @cancel="showPicker = false"
              @confirm="onConfirm"
            />
          </van-popup>
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
<script setup>
import {ref} from 'vue'
import axios from 'axios'
import {showFailToast, showSuccessToast} from 'vant'
import router from '@/router'

const name = ref('')
const password = ref('')
const question = ref('')
const answer = ref('')
const onSubmit = (values) => {
  registerRequest()
}
const admissionYearKey = ref('')
const admissionYearValue = ref('')
const showPicker = ref(false)
const columns = [
  {
    text: '2021级',
    value: '2021'
  },
  {
    text: '2022级',
    value: '2022'
  },
  {
    text: '2023级',
    value: '2023'
  },
  {
    text: '2024级',
    value: '2024'
  },
  {
    text: '2025级',
    value: '2025'
  }
]

const onConfirm = ({selectedOptions}) => {
  admissionYearKey.value = selectedOptions[0]?.text
  admissionYearValue.value = selectedOptions[0]?.value
  showPicker.value = false
}
const registerRequest = () => {
  axios.post(`${process.env.VUE_APP_USER_LOGIN}` + 'user/register', {
    name: name.value,
    password: password.value,
    admissionYear: admissionYearValue.value,
    question: question.value,
    answer: answer.value
  }).then(res => {
    if (res.data.code === 200) {
      name.value = ''
      password.value = ''
      question.value = ''
      answer.value = ''
      admissionYearValue.value = ''
      admissionYearKey.value = ''
      showSuccessToast('注册成功')
      router.push('/login')
    } else {
      showFailToast('注册失败，请重试')
    }
  })
}

</script>
<style scoped>
@import "@/assets/css/RegisterView.css";
</style>
