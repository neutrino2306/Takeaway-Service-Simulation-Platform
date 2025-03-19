<script setup>
import {ref} from "vue";
import { userState, setUserState, clearUserState } from "@/store/userState";
import {addCustomer, addMerchant, findCustomerByCustomerPhone, findMerchantByMerchantPhone} from "@/network/api.js";

const emit = defineEmits(['navigate']);
const userPhone = ref('');
const password = ref('');
const registerType = ref('customer');

const registerAsCustomer = async () => {
  try {
    // 检查手机号是否已被顾客使用
    try {
      const customerExistResponse = await findCustomerByCustomerPhone(userPhone.value);
      if (customerExistResponse.status === 200 && customerExistResponse.data) {
        alert('顾客已存在！');
        return;
      }
    } catch (error) {
      // 如果错误是 404，继续执行注册，否则抛出错误
      if (!(error.response && error.response.status === 404)) {
        throw error;
      }
    }

    // 执行注册流程
    const registerResponse = await addCustomer(userPhone.value, password.value, '无', '无');
    if (registerResponse && registerResponse.status === 200) {
      setUserState('Customer', registerResponse.data);
      alert('注册成功！欢迎光临！');
      emit('navigate', 'CustomerShops');
    } else {
      alert('注册失败，请稍后再试！');
    }
  } catch (error) {
    console.error('注册失败:', error);
    alert('注册请求失败！');
  }
};

const registerAsMerchant = async () => {
  try {
    // 检查手机号是否已被商家使用
    try {
      const merchantExistResponse = await findMerchantByMerchantPhone(userPhone.value);
      if (merchantExistResponse.status === 200 && merchantExistResponse.data) {
        alert('商家已存在！');
        return;
      }
    } catch (error) {
      // 如果错误是 404，继续执行注册，否则抛出错误
      if (!(error.response && error.response.status === 404)) {
        throw error;
      }
    }

    // 执行注册流程
    const registerResponse = await addMerchant(userPhone.value, password.value, '无', '无');
    if (registerResponse && registerResponse.status === 200) {
      setUserState('Merchant', registerResponse.data);
      alert('注册成功！欢迎营业！');
      emit('navigate', 'MerchantShops');
    } else {
      alert('注册失败，请稍后再试！');
    }
  } catch (error) {
    console.error('注册失败:', error);
    alert('注册请求失败！');
  }
};

const handleRegister = () => {
  if (userPhone.value.trim() === '' || password.value.trim() === '') {
    alert('用户名或密码不能为空！');
    return;
  }

  if (registerType.value === 'customer') {
    registerAsCustomer();
  } else {
    registerAsMerchant();
  }
};

const navigateToLogin = () => {
  emit('navigate', 'Login');
};

</script>

<template>
  <div class="flex items-center h-screen ml-64">
    <div class="relative flex-col bg-white bg-opacity-80 shadow-xl rounded-2xl p-8 w-96 h-128 ml-40">

      <div class="text-center mb-4">
        <span class="text-3xl font-semibold">注册</span>
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
        <label class="block text-gray-700 text-base font-bold mb-2" for="registerType">
          注册类型:
        </label>
        <select v-model="registerType" class="shadow border rounded w-full py-2 px-3 text-gray-700 mb-3 leading-tight focus:outline-none focus:shadow-outline">
          <option value="customer">顾客</option>
          <option value="merchant">商家</option>
        </select>
      </div>

      <div class="flex items-center justify-center">
        <button class="bg-yellow-300 hover:bg-yellow-400 text-white font-bold py-2 px-6 rounded focus:shadow-outline mt-1" type="button" @click="handleRegister">
          注册
        </button>
      </div>
      <button class="mt-5 ml-16 text-base font-semibold text-black bg-transparent border-none cursor-pointer hover:underline focus:outline-none" @click="navigateToLogin">
        已经有账号了？直接登录吧！
      </button>
    </div>
  </div>
</template>

<style scoped>

</style>