<script setup>
import {ref, onMounted, reactive} from 'vue';
import { shopItemState, setShopItemState, clearShopItemState } from "@/store/shopItemState.js";
import { userState } from "@/store/userState.js";
import {setCurrentShop, shopState} from "@/store/shopState.js";
import {addItem, addShopItem, updateMerchant} from "@/network/api";

const emit = defineEmits(['navigate']);
const showMerchantOptions = ref(false);
const userData = ref({ merchantID: '', merchantName: '',  merchantDescription: '', merchantPhone: '', merchantPassword: '' });
const isEditing = ref(false);
const showNewItemDetails = ref(false);
const shopItemData = reactive({
  shopItemName: '',
  shopItemPrice: '',
  shopItemDescription: ''
});
const editableShopItemData = reactive({
  name: '',
  price: '',
  description: ''
});

onMounted(() => {
  if (userState.value && userState.value.data) {
    userData.value = userState.value.data;
  }
  if (shopItemState.value && shopItemState.value.data) {
    shopItemData.value = shopItemState.value.data;
    editableShopItemData.itemName = shopItemState.value.data.itemName;
    editableShopItemData.itemPrice = shopItemState.value.data.itemPrice;
    editableShopItemData.itemDescription = shopItemState.value.data.itemDescription;
  }

});

// const toggleEdit = () => {
//   isEditing.value = !isEditing.value;
//   // 如果正在关闭编辑模式，则重置 editableUserData
//   if (!isEditing.value) {
//     resetEditableUserData();
//   }
// };
//
// const resetEditableUserData = () => {
//   editableUserData.phone = userData.value.merchantPhone;
//   editableUserData.password = userData.value.merchantPassword;
//   editableUserData.description = userData.value.merchantDescription;
//   editableUserData.name = userData.value.merchantName;
// };
//
// const saveChanges = async () => {
//   try {
//     const merchantID = userData.value.merchantID; // 确保商家ID已经设置
//     const response = await addShopItem(
//         merchantID,
//         editableShopItemData.name,
//         editableShopItemData.price,
//         editableShopItemData.description
//     );
//     // 处理响应，例如更新 userData 或显示成功消息
//     console.log("更新成功:", response);
//
//     // 重置商品数据
//     editableShopItemData.itemName = '';
//     editableShopItemData.itemPrice = '';
//     editableShopItemData.itemDescription = '';
//
//     // 关闭编辑模式
//     // isEditing.value = false;
//   } catch (error) {
//     // 处理错误，例如显示错误消息
//     console.error("更新失败:", error);
//   }
// };

const addNewShopItem = async () => {
  try {
    const response = await addShopItem(
        userData.value.merchantID,
        editableShopItemData.name,
        editableShopItemData.price,
        editableShopItemData.description
    );
    console.log("商品添加成功:", response);

    shopItemData.value = response.data;
    // 清空输入字段
    editableShopItemData.name = '';
    editableShopItemData.price = '';
    editableShopItemData.description = '';

    showNewItemDetails.value = true;
  } catch (error) {
    console.error("商品添加失败:", error);
  }
};

const publishShopItem = async () => {
  try {
    let merchantID = '';
    let shopID = '';
    if (userState.value.data && shopState.value.currentShop) {
      merchantID = userState.value.data.merchantID;
      shopID = shopState.value.currentShop.shopID;
    }
    else{
      alert("新增商品为空，无法上架！");
    }
    await addItem(shopItemData.value.shopItemID, merchantID, shopID);
    console.log("商品上架成功！");
    alert("商品上架成功！");
    await navigateToMerchantShop();
  } catch (error) {
    console.error("商品上架失败:", error);
  }
};

const navigateToMerchantShops = async () => {
  emit('navigate', 'MerchantShops');
};

const navigateToMerchantShop = async () => {
  emit('navigate', 'MerchantShop');
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
    <!-- 商品信息输入表单 -->
    <div v-if="!showNewItemDetails" class="flex flex-col gap-6 ml-20 mt-8" style="width: 400px;">
      <div class="flex items-center gap-4">
        <label class="text-lg w-20">商品名称:</label>
        <input v-model="editableShopItemData.name" class="outline-none text-lg flex-1 rounded-xl pl-5 bg-gray-100 h-12" placeholder="商品名称" />
      </div>

      <div class="flex items-center gap-4">
        <label class="text-lg w-20">商品价格:</label>
        <input v-model="editableShopItemData.price" class="outline-none text-lg flex-1 rounded-xl pl-5 bg-gray-100 h-12" placeholder="商品价格" />
      </div>

      <div class="flex items-center gap-4">
        <label class="text-lg w-20">商品描述:</label>
        <input v-model="editableShopItemData.description" class="outline-none text-lg flex-1 rounded-xl pl-5 bg-gray-100 h-12" placeholder="商品描述" />
      </div>

      <div class="flex justify-around mt-6">
        <button @click="addNewShopItem" class="w-28 h-12 text-lg bg-blue-200 hover:bg-blue-300 rounded-2xl">新增商品</button>
      </div>
    </div>

    <!-- 显示新增的商品信息 -->
    <div v-else class="flex flex-col gap-6 ml-32 mt-6" style="width: 400px;">
      <div class="flex items-center gap-4" style="display: flex; align-items: center;">
        <label class="text-lg w-20">商品ID:</label>
        <span class="text-lg">{{ shopItemData.value.shopItemID }}</span>
      </div>

      <div class="flex items-center gap-4" style="display: flex; align-items: center;">
        <label class="text-lg w-20">商品名称:</label>
        <span class="text-lg">{{ shopItemData.value.shopItemName }}</span>
      </div>

      <div class="flex items-center gap-4" style="display: flex; align-items: center;">
        <label class="text-lg w-20">商品价格:</label>
        <span class="text-lg">{{ shopItemData.value.shopItemPrice }}</span>
      </div>

      <div class="flex items-center gap-4" style="display: flex; align-items: center;">
        <label class="text-lg w-20">商品描述:</label>
        <span class="text-lg">{{ shopItemData.value.shopItemDescription }}</span>
      </div>

      <div class="flex justify-start mt-6">
        <button @click="publishShopItem" class="w-28 h-12 text-lg bg-blue-200 hover:bg-blue-300 rounded-2xl mr-12">上架商品</button>
        <button @click="navigateToMerchantShop" class="w-28 h-12 text-lg bg-blue-200 hover:bg-blue-300 rounded-2xl">返回</button>
      </div>
    </div>
  </div>

</template>

<style scoped>

</style>