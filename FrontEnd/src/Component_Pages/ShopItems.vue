<script setup>
import {ref, onMounted, computed} from 'vue';
import { userState } from "@/store/userState";
import { setShopItemState, shopItemState } from "@/store/shopItemState.js";
import { findShopItemByMerchantID, findShopItemByMerchantIDAndShopItemName } from "@/network/api";

const shopItems = ref([]);
const showMerchantOptions = ref(false);
const emit = defineEmits(['navigate']);
const searchQuery = ref('');

const merchantName = computed(() => {
  return userState.value.data ? userState.value.data.merchantName : '未知商家';
});

const merchantID = computed(() => {
  return userState.value.data ? userState.value.data.merchantID : 0;
});

onMounted(async () => {
  try {
      console.log("merchantid:",merchantID);
      const response = await findShopItemByMerchantID(merchantID.value);
      if (response && response.data) {
        shopItems.value = response.data;
      }
    } catch (error) {
      console.error("Error fetching shopItems: ", error);
    }
});

const searchItem = async () => {
  try {

    const response = await findShopItemByMerchantIDAndShopItemName(merchantID.value,searchQuery.value);
    if (response.status === 200) {
      let shopItemsData = Array.isArray(response.data) ? response.data : [response.data];

      shopItems.value = shopItemsData;
    } else {
      // const response = await findShopByShopNameContaining(searchQuery.value);
      // if (response.status === 200) {
      //   shops.value = response.data;
      //   return;
      // } else {
      //   const response = await findShopByShopAddressContaining(searchQuery.value);
      //   if (response.status === 200) {
      //     shops.value = response.data;
      //     return;
      //   } else {
      shopItems.value = [];
      alert("未搜索到相关店铺！");
      // }
      // }
    }
  } catch (error) {
    if (error.response && error.response.status === 404) {
      console.error('失败:', error);
    } else {
      console.error('失败:', error);
      alert('搜索请求失败！');
    }
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

const navigateToShopItemDetail = (item) => {
  setShopItemState(item)
  emit('navigate', 'ShopItemDetail');
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

  <div class="pt-8">
    <div class="flex justify-center mt-20 mb-3">
      <input v-model="searchQuery" class="shadow appearance-none border px-4 py-2 border rounded-l focus:outline-none text-gray-700 leading-tight focus:shadow-outline" id="searchQuery" type="text" placeholder="搜索商品名称">
      <button class="bg-blue-300 hover:bg-blue-400 text-white font-bold py-2 px-4 rounded focus:shadow-outline" type="button" @click="searchItem">
        搜索
      </button>
    </div>
    <div class="flex justify-center mt-6 ml-16">
      <div class="container h-[400px] overflow-auto scrollbar-thin scrollbar-thumb-gray-900 w-[1100px] max-w-full">
        <div class="grid grid-cols-2 gap-x-1 gap-y-6 p-4">
          <div v-for="(shopItem, index) in shopItems" :key="shopItem.shopItemID" class="flex items-center space-x-6 mb-2">
            <div class="relative h-32 w-auto rounded-xl overflow-hidden ml-12">
              <img src="../img/itemIcon.png" alt="商品图片" class="object-cover w-full h-full cursor-pointer" @click="navigateToShopItemDetail(shopItem)">
            </div>
            <div class="max-w-xs">
              <span class="text-black block mb-4 text-xl">{{ shopItem.shopItemName }}</span>
              <span class="text-gray-600 block mb-3">{{ shopItem.shopItemPrice }} 元</span>
              <span class="text-gray-600 block">{{ shopItem.shopItemDescription }}</span>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>

</template>

<style scoped>

</style>