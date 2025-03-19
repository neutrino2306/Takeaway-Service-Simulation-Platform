import { ref } from 'vue';

export const shopItemState = ref({
    data: null,
});

export const setShopItemState = (data) => {
    shopItemState.value.data = data;
};

export const clearShopItemState = () => {
    shopItemState.value = { type: null, data: null };
};