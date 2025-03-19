<script setup>
import { ref, onMounted } from 'vue';
import { shopState } from "@/store/shopState";
import { userState } from "@/store/userState.js";
import {
  findByCustomerIDAndShopID,
  updateOrderStatus,
  findLineItemByCustomerIDAndShopID,
  findItemByItemID,
  findItemByShopID, cancelOrder
} from '@/network/api';

const items = ref([]);
const emit = defineEmits(['navigate']);
const unpaidOrder = ref(null);
const lineItemCount = ref(0);
const lineItemsDetails = ref([]);
const showUserOptions = ref(false);

const getUnpaidOrder = async () => {
  if (userState.value.data && shopState.value.currentShop) {
    const customerID = userState.value.data.customerID;
    const shopID = shopState.value.currentShop.shopID;

    try {
      const response = await findByCustomerIDAndShopID(customerID, shopID);
      if (response && response.data) {
        unpaidOrder.value = response.data; // 获取未支付的订单
        console.log(unpaidOrder.value);
      }
    } catch (error) {
      console.error("Error fetching unpaid order:", error);
    }
  }
};

onMounted(() => {
  getUnpaidOrder();
});

const handlePayment = async () => {
  if (unpaidOrder.value) {
    try {
      await updateOrderStatus(unpaidOrder.value.orderID, 1); // 设置订单状态为 1 (已支付)
      console.log("Order paid");
      alert("订单支付成功！");
      navigateToCustomerOrders();
    } catch (error) {
      console.error("Error updating order status:", error);
    }
  }
};

const handleCancel = async () => {
  if (unpaidOrder.value) {
    try {
      await cancelOrder(unpaidOrder.value.orderID);
      console.log("Order cancelled");
      alert("订单取消成功！");
      navigateToCustomerOrders();
    } catch (error) {
      console.error("Error updating order status:", error);
    }
  }
};

const getLineItems = async () => {
  try {
    // 确保 userState 和 shopState 已经被正确设置
    if (userState.value.data && shopState.value.currentShop) {
      const customerID = userState.value.data.customerID;
      const shopID = shopState.value.currentShop.shopID;

      const response = await findLineItemByCustomerIDAndShopID(customerID, shopID);
      if (response && response.data) {
        lineItemCount.value = response.data.length;
        await getLineItemsDetails(response.data); // 正确传递 response.data
      }
    }
  } catch (error) {
    console.error("Error fetching cart items:", error);
  }
};

onMounted(() => {
  getLineItems();
});

onMounted(async () => {
  if (shopState.value.currentShop && shopState.value.currentShop.shopID) {
    try {
      const response = await findItemByShopID(shopState.value.currentShop.shopID);
      if (response && response.data) {
        items.value = response.data;
      }
    } catch (error) {
      console.error("Error fetching items for shop:", error);
    }
  }
});

const getLineItemsDetails = async (lineItems) => {
  lineItemsDetails.value = [];
  for (let lineItem of lineItems) {
    try {
      const itemResponse = await findItemByItemID(lineItem.itemID);
      if (itemResponse && itemResponse.data) {
        const itemName = itemResponse.data.itemName;
        const itemPrice = itemResponse.data.itemPrice;
        const quantity = lineItem.quantity;
        const total = itemPrice * quantity;

        lineItemsDetails.value.push({
          itemName: itemName,
          quantity: quantity,
          total: total
        });
      }
    } catch (error) {
      console.error("Error fetching item details:", error);
    }
  }
};

const navigateToCustomerShops = () => {
  emit('navigate', 'CustomerShops');
};

const navigateToCustomerInfo = () => {
  emit('navigate', 'CustomerInfo');
};

const navigateToCustomerOrders = () => {
  emit('navigate', 'CustomerOrders');
};


</script>

<template>
<!--  <div class="bg-white bg-opacity-90 shadow-md fixed w-full flex top-0 left-0 z-10 h-17 py-1 px-6">-->
<!--    <img src="../img/userWord.png" alt="用户界面" class="w-20 h-20 mr-4 ml-32 cursor-pointer" @click="navigateToCustomerShops">-->
<!--    <img src="../img/userIcon.png" alt="用户头像" class="w-16 h-16 rounded-full object-cover ml-[750px] mt-2 cursor-pointer"/>-->
<!--  </div>-->
  <div class="bg-white bg-opacity-90 shadow-md fixed w-full flex top-0 left-0 z-10 h-17 py-1 px-6">
    <img src="../img/userWord.png" alt="用户界面" class="w-20 h-20 mr-4 ml-32 cursor-pointer" @click="navigateToCustomerShops">
    <!-- 用户头像和下拉菜单 -->
    <div class="relative flex items-center cursor-pointer ml-[750px] mt-2"
         @mouseenter="showUserOptions = true"
         @mouseleave="showUserOptions = false">
      <img src="../img/userIcon.png" alt="用户头像" class="w-16 h-16 rounded-full object-cover"/>
      <div class="absolute top-full mt-0 w-40 bg-white border rounded shadow-md"
           v-show="showUserOptions" style="left: 50%; transform: translateX(-50%);">
        <ul class="flex flex-col items-center p-1">
          <li class="hover:bg-gray-100 cursor-pointer text-sm px-5 py-1" @click="navigateToCustomerInfo">
            个人信息
          </li>
          <li class="hover:bg-gray-100 cursor-pointer text-sm px-5 py-1" @click="navigateToCustomerOrders">
            订单列表
          </li>
        </ul>
      </div>
    </div>
  </div>
  <div class="pt-12">
    <div class="flex justify-center mt-20 ml-8">
      <div class="container h-[400px] overflow-auto scrollbar-thin scrollbar-thumb-gray-900 w-[1130px] max-w-full">
        <div class="grid grid-cols-2 gap-x-1 gap-y-6 p-4">
          <div v-for="(lineItemDetail, index) in lineItemsDetails" :key="index" class="flex items-center space-x-6 mb-8">
            <div class="relative h-32 w-auto rounded-xl overflow-hidden ml-12">
              <img src="../img/itemIcon.png" alt="商品图片" class="object-cover w-full h-full">
            </div>
            <div class="max-w-xs">
              <span class="text-black block mb-4 text-xl">{{ lineItemDetail.itemName }}</span>
              <span class="text-gray-600 block mb-3">数量：{{ lineItemDetail.quantity }}</span>
              <span class="text-gray-600 block">总金额：{{ lineItemDetail.total }} 元</span>
            </div>
<!--            <button class="flex items-center justify-center bg-orange-500 text-white rounded-full h-8 w-8" @click="addToCart(item)">-->
<!--              <span class="text-2xl">+</span>-->
<!--            </button>-->
          </div>
        </div>
      </div>
    </div>
  </div>

  <div class="bg-white bg-opacity-90 shadow-md fixed w-full flex bottom-0 left-0 z-10 h-16 py-1 px-6">
    <span class="ml-32 text-xl mt-3">总价：{{  unpaidOrder ? unpaidOrder.totalPrice : '0' }} 元</span>
    <button class="ml-auto mr-16 bg-blue-300 hover:bg-blue-400 text-white font-bold h-8 px-8 rounded-2xl mt-3" @click="handlePayment">支付</button>
    <button class="mr-24 bg-blue-300 hover:bg-blue-400 text-white font-bold h-8 px-8 rounded-2xl mt-3" @click="handleCancel">取消</button>
  </div>
</template>

<style scoped>

</style>