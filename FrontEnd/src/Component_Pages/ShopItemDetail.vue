<script setup>
import {ref, onMounted, reactive} from 'vue';
import { userState, setUserState, clearUserState } from "@/store/userState";
import { shopItemState, setShopItemState } from "@/store/shopItemState.js";
import {
  addItem,
  findItemByShopItemIDAndShopID,
  updateShopItem
} from "@/network/api";
import {shopState} from "@/store/shopState.js";

const emit = defineEmits(['navigate']);
const showMerchantOptions = ref(false);
const isEditing = ref(false);
const shopItemData = ref({ shopItemID: '', shopItemName: '', shopItemPrice: '', shopItemDescription: '' });
const editableShopItemData = reactive({ name: '', price: '', description: '' });

onMounted(() => {
  if (shopItemState.value && shopItemState.value.data) {
    shopItemData.value = shopItemState.value.data;
    editableShopItemData.name = shopItemState.value.data.shopItemName;
    editableShopItemData.price = shopItemState.value.data.shopItemPrice;
    editableShopItemData.description = shopItemState.value.data.shopItemDescription;
  }
});

const toggleEdit = () => {
  isEditing.value = !isEditing.value;
  // 如果正在关闭编辑模式，则重置 editableUserData
  if (!isEditing.value) {
    resetEditableShopItemData();
  }
};

const resetEditableShopItemData = () => {
  editableShopItemData.name = shopItemData.value.shopItemName;
  editableShopItemData.price = shopItemData.value.shopItemPrice;
  editableShopItemData.description = shopItemData.value.shopItemDescription;
};

const saveChanges = async () => {
  try {
    const response = await updateShopItem(
        shopItemData.value.shopItemID,
        editableShopItemData.name,
        editableShopItemData.price,
        editableShopItemData.description
    );

    console.log("更新成功:", response);

    // 更新 shopItemData 以反映更改
    shopItemData.value.shopItemName = editableShopItemData.name;
    shopItemData.value.shopItemPrice = editableShopItemData.price;
    shopItemData.value.shopItemDescription = editableShopItemData.description;

    // 关闭编辑模式
    isEditing.value = false;
  } catch (error) {
    console.error("更新失败:", error);
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
      alert("无法获取商家或商店信息，无法上架商品！");
    }

    let itemAlreadyExists = false;
    try {
      // 尝试查找商品
      const itemResponse = await findItemByShopItemIDAndShopID(shopItemData.value.shopItemID, shopID);
      if (itemResponse && itemResponse.data) {
        itemAlreadyExists = true;
      }
    } catch (error) {
      // 如果错误不是因为商品不存在（即不是 404），则重新抛出错误
      if (error.response && error.response.status !== 404) {
        throw error;
      }
    }

    if (itemAlreadyExists) {
      alert("商品已经上架！");
      return;
    }

    // 继续上架商品流程
    await addItem(shopItemData.value.shopItemID, merchantID, shopID);
    console.log("商品上架成功！");
    alert("商品上架成功！");
  } catch (error) {
    console.error("商品上架失败:", error);
  }
};

const navigateToMerchantShops = async () => {
  emit('navigate', 'MerchantShops');
};

const navigateToShopItems = async () => {
  emit('navigate', 'ShopItems');
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
    <div v-if="!isEditing" class="flex flex-col gap-8 ml-32">
      <div class="flex items-center gap-6" style="white-space: nowrap;">
        <label class="text-xl w-24">商品ID:</label>
        <span class="text-xl flex-1 overflow-hidden text-ellipsis">{{ shopItemData.shopItemID }}</span>
      </div>

      <div class="flex items-center gap-6" style="white-space: nowrap;">
        <label class="text-xl w-24">商品名称:</label>
        <span class="text-xl flex-1 overflow-hidden text-ellipsis">{{ shopItemData.shopItemName }}</span>
      </div>

      <div class="flex items-center gap-6" style="white-space: nowrap;">
        <label class="text-xl w-24">商品价格:</label>
        <span class="text-xl flex-1 overflow-hidden text-ellipsis">{{ shopItemData.shopItemPrice }}</span>
      </div>

      <div class="flex items-center gap-6" style="white-space: nowrap;">
        <label class="text-xl w-24">商品描述:</label>
        <span class="text-xl flex-1 overflow-hidden text-ellipsis">{{ shopItemData.shopItemDescription }}</span>
      </div>

<!--      <button @click="toggleEdit" class="w-24 h-12 text-lg bg-blue-200 hover:bg-blue-300 rounded-2xl ml-12 mt-3">修改</button>-->
      <div class="flex justify-start mt-2 ml-6">
        <button @click="toggleEdit" class="w-28 h-12 text-lg bg-blue-200 hover:bg-blue-300 rounded-2xl mr-12">修改</button>
        <button @click="publishShopItem" class="w-28 h-12 text-lg bg-blue-200 hover:bg-blue-300 rounded-2xl">上架商品</button>
      </div>
    </div>

    <!-- 编辑模式 -->
    <div v-else class="flex flex-col gap-6 ml-32">
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
        <button @click="saveChanges" class="w-24 h-12 text-lg bg-blue-200 hover:bg-blue-300 rounded-2xl">确定</button>
        <button @click="toggleEdit" class="w-24 h-12 text-lg bg-blue-200 hover:bg-blue-300 rounded-2xl">取消</button>
      </div>
    </div>
  </div>

  <div class="bg-white bg-opacity-90 shadow-md fixed w-full flex bottom-0 left-0 z-10 h-16 py-1 px-6 justify-end">
    <button class="mr-28 bg-blue-300 hover:bg-blue-400 text-white font-bold h-8 px-8 rounded-2xl mt-3" @click="navigateToShopItems">返回</button>
  </div>
</template>

<style scoped>

</style>