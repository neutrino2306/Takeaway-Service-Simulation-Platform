<script setup>
import {ref, onMounted, computed} from 'vue';
import { userState, setUserState, clearUserState } from "@/store/userState";
import {
  findItemByShopID,
  findMerchantByMerchantID,
  findShopByMerchantID, findShopByShopAddressContaining,
  findShopByShopNameContaining
} from "@/network/api";
import {setCurrentShop, shopState} from "@/store/shopState.js";

const shops = ref([]);
const emit = defineEmits(['navigate']);
const searchQuery = ref('');
const showMerchantOptions = ref(false);

const merchantName = computed(() => {
  return userState.value.data ? userState.value.data.merchantName : '未知商家';
});

const getShopsWithMerchantInfo = async () => {

  if (!userState.value.data || !userState.value.data.merchantID) {
    console.error("No merchant ID found in user state");
    return;
  }

  try {
    const merchantID = userState.value.data.merchantID;
    const response = await findShopByMerchantID(merchantID);
    if (response && response.data) {
      shops.value = response.data;
    }
  } catch (error) {
    console.error("Error fetching shops for the merchant:", error);
  }
};

onMounted(getShopsWithMerchantInfo);

const searchShops = async () => {
  let shopsData = [];

  // 三个搜索方法的函数数组
  const searchMethods = [
    findShopByShopNameContaining,
    findShopByShopAddressContaining
  ];

  // 依次执行每个搜索方法
  for (const searchMethod of searchMethods) {
    try {
      const response = await searchMethod(searchQuery.value);
      if (response.status === 200 && response.data) {
        // 合并数组，避免重复
        shopsData = [...shopsData, ...Array.isArray(response.data) ? response.data : [response.data]];
      }
    } catch (error) {
      // 仅记录非404的错误
      if (!error.response || error.response.status !== 404) {
        console.error('搜索失败:', error);
      }
    }
  }

  // 为每个店铺添加商家名称
  for (let shop of shopsData) {
    try {
      const merchantResponse = await findMerchantByMerchantID(shop.merchantID);
      if (merchantResponse.status === 200) {
        shop.merchantName = merchantResponse.data.merchantName;
      } else {
        shop.merchantName = '未知商家'; // 如果找不到商家信息，设置一个默认值
      }
    } catch (error) {
      // 找不到商家时设置默认商家名称
      shop.merchantName = '未知商家';
      console.error('获取商家信息失败:', error);
    }
  }

  // 如果没有找到商店，显示警告
  if (shopsData.length === 0) {
    alert("未搜索到相关店铺！");
  } else {
    // 将收集到的店铺数据赋值给响应式变量
    shops.value = shopsData;
  }
};

const navigateToMerchantShops = async () => {
  await getShopsWithMerchantInfo();
  emit('navigate', 'MerchantShops');
};

const navigateToMerchantShop = (shop) => {
  setCurrentShop(shop);
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

  <div class="pt-8">
    <div class="flex justify-center mt-20 mb-3">
      <input v-model="searchQuery" class="shadow appearance-none border px-4 py-2 border rounded-l focus:outline-none text-gray-700 leading-tight focus:shadow-outline" id="searchQuery" type="text" placeholder="搜索店铺名字或地址">
      <button class="bg-blue-300 hover:bg-blue-400 text-white font-bold py-2 px-4 rounded focus:shadow-outline" type="button" @click="searchShops">
        搜索
      </button>
    </div>
    <div class="flex justify-center mt-6 ml-16">
      <div class="container h-[400px] overflow-auto scrollbar-thin scrollbar-thumb-gray-900 w-[1100px] max-w-full">
        <div class="grid grid-cols-2 gap-x-1 gap-y-6 p-4">
          <div v-for="(shop, index) in shops" :key="shop.shopID" class="flex items-center space-x-6 mb-2">
            <div class="relative h-32 w-auto rounded-xl overflow-hidden ml-12">
              <img src="../img/shopIcon.png" alt="店铺图片" class="object-cover w-full h-full cursor-pointer" @click="navigateToMerchantShop(shop)">
            </div>
            <div class="max-w-xs">
              <span class="text-black block mb-4 text-xl">{{ merchantName }}：{{ shop.name }}</span>
              <span class="text-gray-600 block mb-3">{{ shop.address }}</span>
              <span class="text-gray-600 block">{{ shop.shopDescription }}</span>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<style scoped>

</style>