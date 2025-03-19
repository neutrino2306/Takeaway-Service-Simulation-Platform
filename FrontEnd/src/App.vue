<script setup>
import { ref } from 'vue';
import { userState, setUserState, clearUserState } from "@/store/userState";
import Login from "@/Component_Pages/Login.vue";
import Register from "@/Component_Pages/Register.vue";
import CustomerOrder from "@/Component_Pages/CustomerOrder.vue";
import CustomerOrders from "@/Component_Pages/CustomerOrders.vue";
import CustomerOrderDetail from "@/Component_Pages/CustomerOrderDetail.vue";
import CustomerShop from "@/Component_Pages/CustomerShop.vue";
import CustomerShops from "@/Component_Pages/CustomerShops.vue";
import CustomerInfo from "@/Component_Pages/CustomerInfo.vue";
import MerchantOrder from "@/Component_Pages/MerchantOrder.vue";
import MerchantOrders from "@/Component_Pages/MerchantOrders.vue";
import MerchantOrderDetail from "@/Component_Pages/MerchantOrderDetail.vue";
import MerchantShop from "@/Component_Pages/MerchantShop.vue";
import MerchantShops from "@/Component_Pages/MerchantShops.vue";
import MerchantInfo from "@/Component_Pages/MerchantInfo.vue";
import AddShopItem from "@/Component_Pages/AddShopItem.vue";
import ShopItems from "@/Component_Pages/ShopItems.vue";
import ShopItemDetail from "@/Component_Pages/ShopItemDetail.vue";

const currentPage = ref('Login');

const navigateToPage = (pageName) => {
  currentPage.value = pageName;
};
</script>

<template>
  <Login v-if="currentPage === 'Login'" @navigate="navigateToPage"/>
  <Register v-if="currentPage === 'Register'" @navigate="navigateToPage"/>

  <CustomerShops v-if="currentPage === 'CustomerShops'" @navigate="navigateToPage" />
  <CustomerShop v-if="currentPage === 'CustomerShop'" @navigate="navigateToPage" />
  <CustomerOrderDetail v-if="currentPage === 'CustomerOrderDetail'" @navigate="navigateToPage" />
  <CustomerOrders v-if="currentPage === 'CustomerOrders'" @navigate="navigateToPage" />
  <CustomerOrder v-if="currentPage === 'CustomerOrder'" @navigate="navigateToPage" />
  <CustomerInfo v-if="currentPage === 'CustomerInfo'" @navigate="navigateToPage" />

  <MerchantShops v-if="currentPage === 'MerchantShops'" @navigate="navigateToPage" />
  <MerchantShop v-if="currentPage === 'MerchantShop'" @navigate="navigateToPage" />
  <MerchantOrderDetail v-if="currentPage === 'MerchantOrderDetail'" @navigate="navigateToPage" />
  <MerchantOrders v-if="currentPage === 'MerchantOrders'" @navigate="navigateToPage" />
  <MerchantOrder v-if="currentPage === 'MerchantOrder'" @navigate="navigateToPage" />
  <MerchantInfo v-if="currentPage === 'MerchantInfo'" @navigate="navigateToPage" />

  <AddShopItem v-if="currentPage === 'AddShopItem'" @navigate="navigateToPage" />
  <ShopItems v-if="currentPage === 'ShopItems'" @navigate="navigateToPage" />
  <ShopItemDetail v-if="currentPage === 'ShopItemDetail'" @navigate="navigateToPage" />

</template>

<style scoped>
.core::-webkit-scrollbar {
  display: none;
}
</style>
