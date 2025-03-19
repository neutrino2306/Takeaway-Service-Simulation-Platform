import { ref } from 'vue';

export const orderState = ref({
    currentOrder: null
});

export const setCurrentOrder = (order) => {
    orderState.value.currentOrder = order;
};

export const clearCurrentOrder = () => {
    orderState.value.currentOrder = null;
};