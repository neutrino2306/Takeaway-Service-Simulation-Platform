<script setup>
import {ref} from "vue";
import { userState, setUserState, clearUserState } from "@/store/userState";
import { customerLogin, merchantLogin } from "@/network/api.js";

const emit = defineEmits(['navigate']);
const userPhone = ref('');
const password = ref('');
const loginType = ref('customer');

// const navigateToCustomerShops = async () => {
//   try {
//     const response = await customerLogin(userPhone.value, password.value);
//     if (response && response.status === 200) {
//       usernameState.value = username.value;
//       alert('登录成功！欢迎光临！');
//       emit('navigate', 'CustomerShops');
//     } else {
//       alert('顾客账号或密码错误！');
//     }
//   } catch (error) {
//     console.error('登录失败:', error);
//     alert('登录请求失败！');
//   }
// };

const navigateToCustomerShops = async () => {
  try {
    const response = await customerLogin(userPhone.value, password.value);
    if (response.status === 200) {
      setUserState('Customer', response.data);
      alert('登录成功！欢迎光临！');
      emit('navigate', 'CustomerShops');
    } else {
      // 如果状态码为 404，假定为“用户名或密码错误”
      if (response.status === 404) {
        alert('顾客账号或密码错误！');
      } else {
        alert('登录请求失败！');
      }
    }
  } catch (error) {
    if (error.response && error.response.status === 404) {
      alert('顾客账号或密码错误！');
    } else {
      console.error('登录失败:', error);
      alert('登录请求失败！');
    }
  }
};

const navigateToMerchantShops = async () => {
  try {
    const response = await merchantLogin(userPhone.value, password.value);
    if (response.status === 200) {
      setUserState('Merchant', response.data);
      alert('登录成功！欢迎营业！');
      emit('navigate', 'MerchantShops');
    } else {
      // 如果状态码为 404，假定为“用户名或密码错误”
      if (response.status === 404) {
        alert('商家账号或密码错误！');
      } else {
        alert('登录请求失败！');
      }
    }
  } catch (error) {
    if (error.response && error.response.status === 404) {
      alert('商家账号或密码错误！');
    } else {
      console.error('登录失败:', error);
      alert('登录请求失败！');
    }
  }
};

const handleLogin = () => {
  if (userPhone.value.trim() === '' || password.value.trim() === '') {
    alert('用户名或密码不能为空！');
    return;
  }

  if (loginType.value === 'customer') {
    navigateToCustomerShops();
  } else {
    navigateToMerchantShops();
  }
};

const navigateToRegister = () => {
  emit('navigate', 'Register');
};

</script>

<template>
  <div class="flex items-center h-screen ml-64">
    <div class="relative flex-col bg-white bg-opacity-80 shadow-xl rounded-2xl p-8 w-96 h-128 ml-40">

      <div class="text-center mb-4">
        <span class="text-3xl font-semibold">登录</span>
      </div>

      <div class="mb-6">
        <label class="block text-gray-700 text-base font-bold mb-2" for="username">
          账号:
        </label>
        <input v-model="userPhone" class="shadow appearance-none border rounded w-full py-2 px-3 text-gray-700 leading-tight focus:outline-none focus:shadow-outline" id="username" type="text" placeholder="请输入账号">
      </div>

      <div class="mb-3">
        <label class="block text-gray-700 text-base font-bold mb-2" for="password">
          密码:
        </label>
        <input v-model="password" class="shadow appearance-none border rounded w-full py-2 px-3 text-gray-700 mb-3 leading-tight focus:outline-none focus:shadow-outline" id="password" type="password" placeholder="请输入密码">
      </div>

      <div class="mb-3">
        <label class="block text-gray-700 text-base font-bold mb-2" for="loginType">
          登录类型:
        </label>
        <select v-model="loginType" class="shadow border rounded w-full py-2 px-3 text-gray-700 mb-3 leading-tight focus:outline-none focus:shadow-outline">
          <option value="customer">顾客</option>
          <option value="merchant">商家</option>
        </select>
      </div>

      <div class="flex items-center justify-center">
        <button class="bg-yellow-300 hover:bg-yellow-400 text-white font-bold py-2 px-6 rounded focus:shadow-outline mt-1" type="button" @click="handleLogin">
          登录
        </button>
      </div>
      <button class="mt-5 ml-16 text-base font-semibold text-black bg-transparent border-none cursor-pointer hover:underline focus:outline-none" @click="navigateToRegister">
        还没有账号吗？注册一个吧！
      </button>
    </div>
  </div>
</template>

<style scoped>

</style>