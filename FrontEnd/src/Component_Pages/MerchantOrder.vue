<script setup>
import { ref, onMounted } from 'vue';
import {orderState, setCurrentOrder} from "@/store/orderState.js";
import { setCurrentShop, shopState } from "@/store/shopState.js";
import { userState } from "@/store/userState.js";
import {findOrderByMerchantID, findOrderByMerchantIDAndShopID, findShopByShopID} from '@/network/api';

const orders = ref([]);
const ordersDetails = ref([]);
const emit = defineEmits(['navigate']);
const isLoading = ref(true);
const showMerchantOptions = ref(false);
const currentShop = shopState.value.currentShop ? shopState.value.currentShop : {
  shopID: '',
  merchantID: '',
  name: '',
  address: '',
  shopDescription: ''
};

const getStatusText = (status) => {
  const statusTexts = {
    0: '顾客未支付',
    1: '顾客已支付',
    2: '商家已退款',
    3: '顾客已取消',
    4: '商家已确认'
  };
  return statusTexts[status] || '未知状态';
};

const getOrders = async () => {
  if (userState.value.data && shopState.value.currentShop) {
    const merchantID = userState.value.data.merchantID;
    try {
      const response = await findOrderByMerchantIDAndShopID(merchantID, currentShop.shopID);

      if (response && response.data) {
        orders.value = response.data;
        await getOrdersDetails(orders.value);
      } else {
        console.error("No data returned from findByMerchantID");
      }
    } catch (error) {
      console.error("Error fetching orders:", error);
    } finally {
      isLoading.value = false;
    }
  }
};

const getOrdersDetails = async (ordersArray) => {
  ordersDetails.value = [];
  let tempOrdersDetails = []; // 临时数组
  if (ordersArray) {
    for (let order of ordersArray) {
      try {
        // const shopResponse = await findShopByShopID(order.shopID);

        const merchantName = userState.value.data? userState.value.data.merchantName: '未知商家';
        // const shopName = shopResponse.data ? shopResponse.data.name : '未知店铺';
        const shopName = currentShop.name;

        tempOrdersDetails.push({
          orderID: order.orderID,
          customerID: order.customerID,
          merchantID: order.merchantID,
          merchantName: merchantName,
          shopID: order.shopID,
          shopName: shopName,
          orderDate: order.orderDate,
          status: order.status,
          totalPrice: order.totalPrice
        });
      } catch (error) {
        console.error("Error fetching order details:", error);
      }
    }
    ordersDetails.value = tempOrdersDetails;
  }
};

onMounted(() => {
  getOrders();
});

const navigateToMerchantShops = async () => {
  emit('navigate', 'MerchantShops');
};

const navigateToMerchantOrderDetail = (orderDetail) => {
  setCurrentOrder(orderDetail);
  emit('navigate', 'MerchantOrderDetail');
};

const navigateToMerchantInfo = () => {
  emit('navigate', 'MerchantInfo');
};

const navigateToMerchantOrders = () => {
  emit('navigate', 'MerchantOrders');
};

const navigateToMerchantShop = async () => {
  emit('navigate', 'MerchantShop');
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
    <div class="flex justify-center mt-20 ml-8">
      <div class="container h-[400px] overflow-auto scrollbar-thin scrollbar-thumb-gray-900 w-[1130px] max-w-full">
        <div class="grid grid-cols-2 gap-x-1 gap-y-6 p-4">
          <div v-for="(orderDetail, index) in ordersDetails" :key="index" class="flex items-center space-x-6 mb-8">
            <div class="relative h-32 w-auto rounded-xl overflow-hidden ml-12">
              <img src="../img/itemIcon.png" alt="商品图片" class="object-cover w-full h-full cursor-pointer" @click="navigateToMerchantOrderDetail(orderDetail)">
            </div>
            <div class="max-w-xs">
              <span class="text-black block mb-4 text-xl">{{ orderDetail.merchantName }}：{{ orderDetail.shopName }}</span>
              <span class="text-gray-600 block mb-3">订单时间：{{ orderDetail.orderDate }}</span>
              <span class="text-gray-600 block">支付情况：{{ getStatusText(orderDetail.status) }}</span>
              <span class="text-gray-600 block">总价格：{{ orderDetail.totalPrice }} 元</span>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>

  <div class="bg-white bg-opacity-90 shadow-md fixed w-full flex bottom-0 left-0 z-10 h-16 py-1 px-6">
    <button class="ml-auto mr-32 bg-blue-300 hover:bg-blue-400 text-white font-bold h-8 px-8 rounded-2xl mt-3" @click="navigateToMerchantShop">返回</button>
  </div>
</template>

<style scoped>

</style>