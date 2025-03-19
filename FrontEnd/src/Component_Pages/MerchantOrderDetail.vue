<script setup>
import { ref, onMounted } from 'vue';
import { shopState } from "@/store/shopState";
import { userState } from "@/store/userState.js";
import { orderState } from "@/store/orderState";
import { findItemByItemID, findLineItemByOrderID, updateOrderStatus } from '@/network/api';

const emit = defineEmits(['navigate']);
const showMerchantOptions = ref(false);
const lineItemsDetails = ref([]);

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

const order = orderState.value.currentOrder ? orderState.value.currentOrder : {
  orderID: '',
  merchantName: '',
  shopName: '',
  orderDate: '',
  status: '',
  totalPrice: ''
};

const getLineItemsDetails = async () => {
  if (!order.orderID) return;

  try {
    const response = await findLineItemByOrderID(order.orderID);
    if (response && Array.isArray(response.data)) { // 检查 response.data 是否是数组
      lineItemsDetails.value = await processLineItems(response.data);
    } else {
      console.error("Received non-iterable or unexpected response:", response);
    }
  } catch (error) {
    console.error("Error fetching line items:", error);
  }
};

const processLineItems = async (lineItems) => {
  const processedItems = []; // 定义一个新数组来存储处理过的line items

  for (let lineItem of lineItems) {
    try {
      const itemResponse = await findItemByItemID(lineItem.itemID);
      if (itemResponse && itemResponse.data) {
        const itemName = itemResponse.data.itemName;
        const itemPrice = itemResponse.data.itemPrice;
        const quantity = lineItem.quantity;
        const total = itemPrice * quantity;

        processedItems.push({
          itemName: itemName,
          quantity: quantity,
          total: total
        });
      }
    } catch (error) {
      console.error("Error fetching item details:", error);
    }
  }
  return processedItems;
};

onMounted(() => {
  getLineItemsDetails();
});

const handleRefund = async () => {
  try {
    const response = await updateOrderStatus(order.orderID, 2); // 2 表示退款
    if (response.status === 200) {
      alert('退款成功！');
      navigateToMerchantOrder();
    } else {
      alert('退款失败！');
    }
  } catch (error) {
    console.error('退款操作失败:', error);
    alert('退款请求失败！');
  }
};

const handleConfirm = async () => {
  try {
    const response = await updateOrderStatus(order.orderID, 4); // 4 表示确认
    if (response.status === 200) {
      alert('订单已确认！');
      navigateToMerchantOrder();
    } else {
      alert('确认操作失败！');
    }
  } catch (error) {
    console.error('确认操作失败:', error);
    alert('确认请求失败！');
  }
};

const navigateToMerchantShops = () => {
  emit('navigate', 'MerchantShops');
};

const navigateToMerchantInfo = () => {
  emit('navigate', 'MerchantInfo');
};

const navigateToMerchantOrders = () => {
  emit('navigate', 'MerchantOrders');
};

const navigateToMerchantOrder = () => {
  emit('navigate', 'MerchantOrder');
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

  <div class="pt-28 px-6 w-full">
    <!-- 第一行：订单ID、时间、状态 -->
    <div class="flex flex-nowrap justify-start items-center text-xl mb-4" style="gap: 150px;">
      <div style="flex-shrink: 0;" class="ml-20">时间：{{ order.orderDate }}</div>
      <div style="flex-shrink: 0;">订单ID：{{ order.orderID }}</div>
      <div style="flex-shrink: 0;">状态：{{ getStatusText(order.status) }}</div>
    </div>
    <!-- 第二行：商家名称和店铺名称 -->
    <div class="text-xl mb-4 ml-20">
      商家名称：{{ order.merchantName }} / 店铺名称：{{ order.shopName }}
    </div>
  </div>

  <div class="pt-48" style="margin-left: -770px;">
    <div class="flex justify-center">
      <div class="container h-[400px] overflow-auto scrollbar-thin scrollbar-thumb-gray-900 w-[1000px] max-w-full">
        <div class="grid grid-cols-2 gap-x-1 gap-y-6 p-4">
          <div v-for="(lineItemDetail, index) in lineItemsDetails" :key="index" class="flex items-center space-x-6 mb-8 ">
            <div class="relative h-32 w-auto rounded-xl overflow-hidden ml-12">
              <img src="../img/itemIcon.png" alt="商品图片" class="object-cover w-full h-full">
            </div>
            <div class="max-w-xs">
              <span class="text-black block mb-4 text-xl">{{ lineItemDetail.itemName }}</span>
              <span class="text-gray-600 block mb-3">数量：{{ lineItemDetail.quantity }}</span>
              <span class="text-gray-600 block">总金额：{{ lineItemDetail.total }} 元</span>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>

  <div class="bg-white bg-opacity-90 shadow-md fixed w-full flex bottom-0 left-0 z-10 h-16 py-1 px-6">
    <span class="ml-32 text-xl mt-3">总价：{{ order.totalPrice }} 元</span>
    <button class="ml-auto mr-16 bg-blue-300 hover:bg-blue-400 text-white font-bold h-8 px-8 rounded-2xl mt-3" @click="handleConfirm">确认</button>
    <button class="mr-24 bg-blue-300 hover:bg-blue-400 text-white font-bold h-8 px-8 rounded-2xl mt-3" @click="handleRefund">退款</button>
  </div>
</template>

<style scoped>

</style>