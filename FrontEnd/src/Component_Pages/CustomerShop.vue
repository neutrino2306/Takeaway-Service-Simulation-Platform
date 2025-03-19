<script setup>
import { ref, onMounted } from 'vue';
import { shopState } from "@/store/shopState";
import {
  findItemByShopID,
  updateOrAddLineItem,
  findLineItemByCustomerIDAndShopID,
  findItemByItemID,
  findItemByItemName,
  findLineItemByItemID,
  updateLineItemQuantityAndTotal,
  deleteLineItem,
  findShopByShopAddressContaining, findShopByShopNameContaining, findItemByItemNameContaining
} from "@/network/api";
import { userState } from "@/store/userState.js";

const items = ref([]);
const emit = defineEmits(['navigate']);
const lineItemCount = ref(0);
const lineItemsDetails = ref([]);
const showCart = ref(false);
const showUserOptions = ref(false);
const searchQuery = ref('');

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

const addLineItem = async (item) => {
  try {
    // 同样，确保 userState 和 shopState 已经被正确设置
    if (userState.value.data && shopState.value.currentShop) {
      const customerID = userState.value.data.customerID;
      const shopID = shopState.value.currentShop.shopID;
      const itemID = item.itemID;
      const quantity = 1;

      await updateOrAddLineItem(customerID, shopID, itemID, quantity);
      console.log("Item added to cart");
      // 重新获取购物车数量
      await getLineItems();
    }
  } catch (error) {
    console.error("Error adding item to cart:", error);
  }
};

const removeLineItem = async (item) => {
  try {
    let customerID = '';
    let shopID = '';
    if (userState.value.data && shopState.value.currentShop) {
      customerID = userState.value.data.customerID;
      shopID = shopState.value.currentShop.shopID;
    }

    const lineItemsResponse = await findLineItemByCustomerIDAndShopID(customerID, shopID);
    if (lineItemsResponse && lineItemsResponse.data) {
      const lineItemList = lineItemsResponse.data;
      const lineItem = lineItemList.find(li => li.itemID === item.itemID);

      if (lineItem) {
        if (lineItem.quantity > 1) {
          const newQuantity = lineItem.quantity - 1;
          await updateLineItemQuantityAndTotal(lineItem.lineItemID, item.itemID, newQuantity);
          console.log("Item quantity updated in cart");
        } else {
          // 当数量减至零时，删除 lineItem
          await deleteLineItem(lineItem.lineItemID);
          console.log("LineItem deleted as quantity reached zero");
        }
        // 重新获取购物车数量
        await getLineItems();
      } else {
        alert("商品数量已经为零，不能再减少！");
      }
    } else {
      alert("未找到对应的购物车项！");
    }
  } catch (error) {
    console.error("Error updating item quantity in cart:", error);
  }
};

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

const searchItem = async () => {
  let itemsData = [];
  try {
    const response2 = await findItemByItemNameContaining(searchQuery.value);
    if (response2.status === 200 && response2.data) {
      itemsData = itemsData.concat(response2.data);

      items.value = itemsData;
    }
  } catch (error) {
    console.error('搜索失败:', error);
    alert("未搜索到相关信息！");
  }
};

const navigateToCustomerOrder = () => {
  emit('navigate', 'CustomerOrder');
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
          <div v-for="(item, index) in items" :key="item.itemID" class="flex items-center space-x-6 mb-2">
            <div class="relative h-32 w-auto rounded-xl overflow-hidden ml-12">
              <img src="../img/itemIcon.png" alt="商品图片" class="object-cover w-full h-full">
            </div>
            <div class="max-w-xs">
              <span class="text-black block mb-4 text-xl">{{ item.itemName }}</span>
              <span class="text-gray-600 block mb-3">{{ item.itemPrice }} 元</span>
              <span class="text-gray-600 block">{{ item.itemDescription }}</span>
            </div>
<!--            <button class="flex items-center justify-center bg-orange-500 text-white rounded-full h-8 w-8" @click="addLineItem(item)">-->
<!--              <span class="text-2xl">+</span>-->
<!--            </button>-->
            <div class="flex items-center">
              <button class="flex items-center justify-center bg-orange-500 text-white rounded-full h-8 w-8 mr-2" @click="addLineItem(item)">
                <span class="text-2xl">+</span>
              </button>
              <button class="ml-4 flex items-center justify-center bg-orange-500 text-white rounded-full h-8 w-8" @click="removeLineItem(item)">
                <span class="text-2xl">−</span>
              </button>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>

<!--  <div class="bg-white bg-opacity-90 shadow-md fixed w-full flex bottom-0 left-0 z-10 h-16 py-1 px-6">-->
<!--    <span class="ml-32 text-xl mt-3">购物车：{{ cartItemCount }}</span>-->
<!--    <button class="ml-auto mr-32 bg-blue-300 hover:bg-blue-400 text-white font-bold h-8 px-8 rounded-2xl mt-3" @click="navigateToCustomerOrder">结算</button>-->
<!--  </div>-->

  <div class="bg-white bg-opacity-90 shadow-md fixed w-full flex bottom-0 left-0 z-10 h-16 py-1 px-6">
    <div class="relative ml-32 text-xl mt-3">
      <span class="cursor-pointer" @mouseover="showCart = lineItemsDetails.length > 0" @mouseleave="showCart = false">购物车：{{ lineItemCount }}</span>
      <div class="absolute bg-white border rounded shadow-md" v-if="showCart && lineItemsDetails.length > 0" style="bottom: 100%; left: 50%; transform: translateX(-50%) translateY(-30%); width: 270px">
        <ul class="flex flex-col items-center p-1">
          <li v-for="lineItem in lineItemsDetails" :key="lineItem.itemID" class="hover:bg-gray-100 cursor-pointer text-sm px-5 py-1 flex justify-between w-full">
            <span>{{ lineItem.itemName }}</span>
            <span>数量：{{ lineItem.quantity }}</span>
            <span>总金额：{{ lineItem.total }} 元</span>
          </li>
        </ul>
      </div>
    </div>
    <button class="ml-auto mr-32 bg-blue-300 hover:bg-blue-400 text-white font-bold h-8 px-8 rounded-2xl mt-3" @click="navigateToCustomerOrder">结算</button>
  </div>
</template>

<style scoped>

</style>