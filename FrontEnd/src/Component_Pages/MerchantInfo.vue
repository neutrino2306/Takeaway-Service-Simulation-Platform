<script setup>
import {ref, onMounted, reactive} from 'vue';
import { userState, setUserState, clearUserState } from "@/store/userState";
import { updateMerchant } from "@/network/api";

const emit = defineEmits(['navigate']);
const showMerchantOptions = ref(false);
const isEditing = ref(false);
const userData = ref({ merchantID: '', merchantName: '',  merchantDescription: '', merchantPhone: '', merchantPassword: '' });
const editableUserData = reactive({ name: '', description: '', phone: '', password: '' });

onMounted(() => {
  if (userState.value && userState.value.data) {
    userData.value = userState.value.data;
    editableUserData.phone = userState.value.data.merchantPhone;
    editableUserData.password = userState.value.data.merchantPassword;
    editableUserData.name = userState.value.data.merchantName;
    editableUserData.description = userState.value.data.merchantDescription;
  }
});

const toggleEdit = () => {
  isEditing.value = !isEditing.value;
  // 如果正在关闭编辑模式，则重置 editableUserData
  if (!isEditing.value) {
    resetEditableUserData();
  }
};

const resetEditableUserData = () => {
  editableUserData.phone = userData.value.merchantPhone;
  editableUserData.password = userData.value.merchantPassword;
  editableUserData.description = userData.value.merchantDescription;
  editableUserData.name = userData.value.merchantName;
};

const saveChanges = async () => {
  try {
    // 调用 updateMerchant 函数并传递必要的参数
    const response = await updateMerchant(
        userData.value.merchantID,
        editableUserData.name,
        editableUserData.description,
        editableUserData.phone,
        editableUserData.password
    );

    // 处理响应，例如更新 userData 或显示成功消息
    console.log("更新成功:", response);

    // 更新 userData 以反映更改
    userData.value.merchantPhone = editableUserData.phone;
    userData.value.merchantPassword = editableUserData.password;
    userData.value.merchantName = editableUserData.name;
    userData.value.merchantDescription = editableUserData.description;

    // 关闭编辑模式
    isEditing.value = false;
  } catch (error) {
    // 处理错误，例如显示错误消息
    console.error("更新失败:", error);
  }
};

const navigateToMerchantShops = async () => {
  emit('navigate', 'MerchantShops');
};

const navigateToMerchantInfo = () => {
  emit('navigate', 'MerchantInfo');
};

const navigateToMerchantOrders = () => {
  emit('navigate', 'MerchantOrders');
};

</script>

<template>
  <div class="bg-white bg-opacity-90 shadow-md fixed w-full flex top-0 left-0 z-10 h-17 py-1 px-6">
    <img src="../img/merchantWord.png" alt="商家界面" class="w-20 h-20 mr-4 ml-32 cursor-pointer" @click="navigateToMerchantShops">
    <!-- 商家头像和下拉菜单 -->
    <div class="relative flex items-center cursor-pointer ml-[750px] mt-2"
         @mouseenter="showMerchantOptions = true"
         @mouseleave="showMerchantOptions = false">
      <img src="../img/userIcon.png" alt="商家头像" class="w-16 h-16 rounded-full object-cover"/>
      <div class="absolute top-full mt-0 w-40 bg-white border rounded shadow-md"
           v-show="showMerchantOptions" style="left: 50%; transform: translateX(-50%);">
        <ul class="flex flex-col items-center p-1">
          <li class="hover:bg-gray-100 cursor-pointer text-sm px-5 py-1" @click="navigateToMerchantInfo">
            个人信息
          </li>
          <li class="hover:bg-gray-100 cursor-pointer text-sm px-5 py-1" @click="navigateToMerchantOrders">
            订单列表
          </li>
        </ul>
      </div>
    </div>
  </div>

  <div class="user-info-container flex flex-col items-center justify-center mt-40 ml-80">
    <!-- 非编辑模式 -->
    <div v-if="!isEditing" class="flex flex-col gap-8 ml-44">
      <div class="flex items-center gap-6" style="white-space: nowrap;">
        <label class="text-xl w-24">商户ID:</label>
        <span class="text-xl flex-1 overflow-hidden text-ellipsis">{{ userData.merchantID }}</span>
      </div>

      <div class="flex items-center gap-6" style="white-space: nowrap;">
        <label class="text-xl w-24">商户电话:</label>
        <span class="text-xl flex-1 overflow-hidden text-ellipsis">{{ userData.merchantPhone }}</span>
      </div>

      <div class="flex items-center gap-6" style="white-space: nowrap;">
        <label class="text-xl w-24">商户密码:</label>
        <span class="text-xl flex-1 overflow-hidden text-ellipsis">{{ userData.merchantPassword }}</span>
      </div>

      <div class="flex items-center gap-6" style="white-space: nowrap;">
        <label class="text-xl w-24">商户姓名:</label>
        <span class="text-xl flex-1 overflow-hidden text-ellipsis">{{ userData.merchantName }}</span>
      </div>

      <div class="flex items-center gap-6" style="white-space: nowrap;">
        <label class="text-xl w-24">商户描述:</label>
        <span class="text-xl flex-1 overflow-hidden text-ellipsis">{{ userData.merchantDescription }}</span>
      </div>

      <button @click="toggleEdit" class="w-24 h-12 text-lg bg-blue-200 hover:bg-blue-300 rounded-2xl ml-12 mt-3">修改</button>
    </div>

    <!-- 编辑模式 -->
    <div v-else class="flex flex-col gap-6 ml-32">
      <div class="flex items-center gap-4">
        <label class="text-lg w-20">商户电话:</label>
        <input v-model="editableUserData.phone" class="outline-none text-lg flex-1 rounded-xl pl-5 bg-gray-100 h-12" placeholder="电话" />
      </div>

      <div class="flex items-center gap-4">
        <label class="text-lg w-20">商户密码:</label>
        <input v-model="editableUserData.password" type="password" class="outline-none text-lg flex-1 rounded-xl pl-5 bg-gray-100 h-12" placeholder="密码" />
      </div>

      <div class="flex items-center gap-4">
        <label class="text-lg w-20">商户姓名:</label>
        <input v-model="editableUserData.name" class="outline-none text-lg flex-1 rounded-xl pl-5 bg-gray-100 h-12" placeholder="姓名" />
      </div>

      <div class="flex items-center gap-4">
        <label class="text-lg w-20">商户描述:</label>
        <input v-model="editableUserData.description" class="outline-none text-lg flex-1 rounded-xl pl-5 bg-gray-100 h-12" placeholder="描述" />
      </div>

      <div class="flex justify-around mt-6">
        <button @click="saveChanges" class="w-24 h-12 text-lg bg-blue-200 hover:bg-blue-300 rounded-2xl">确定</button>
        <button @click="toggleEdit" class="w-24 h-12 text-lg bg-blue-200 hover:bg-blue-300 rounded-2xl">取消</button>
      </div>
    </div>
  </div>

</template>

<style scoped>

</style>