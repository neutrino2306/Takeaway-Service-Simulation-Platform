import {ref} from 'vue';

export const shopState = ref({
    currentShop: null
});

export const setCurrentShop = (shop) => {
    shopState.value.currentShop = shop;
};

export const clearCurrentShop = () => {
    shopState.value.currentShop = null;
};