<template>
  <div class="entireBody">
    <div class="inputForm">
      <van-form style="display: block; width: 100%" @submit="onSubmit">
        <van-cell-group class="infoInputForm">
          <h2 style="width: 100%; text-align: center;">安工大校园论坛</h2>
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
        </van-cell-group>
        <div class="buttons" style="margin: 16px 16px 0px">
          <van-button block native-type="submit" round style="margin-right: 3%" type="primary">
            登陆
          </van-button>
          <van-button block round style="margin-left: 3%" type="primary" @click="registerRequest">
            去注册
          </van-button>
        </div>
      </van-form>
    </div>

  </div>

</template>
<script setup>
import { ref } from 'vue'
import { useRouter } from 'vue-router'
import axios from 'axios'
import { showFailToast, showSuccessToast } from 'vant'

const name = ref('')
const password = ref('')
const router = useRouter()
const onSubmit = (values) => {
  axios.post(`${process.env.VUE_APP_USER_LOGIN}` + 'user/login', {
    name: name.value,
    password: password.value
  }).then(res => {
    if (res.data.code === 200) {
      showSuccessToast('登陆成功')
      localStorage.setItem('satoken', res.data.data)
      router.push('/article')
    } else {
      showFailToast('登陆失败，请重试')
    }
  })
}
const registerRequest = () => {
  router.push('/register')
}

</script>
<style scoped>
@import "@/assets/css/LoginView.css";
</style>
