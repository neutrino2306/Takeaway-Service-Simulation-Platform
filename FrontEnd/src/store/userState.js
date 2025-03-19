import { ref } from 'vue';

export const userState = ref({
    type: null, // Customer或者Merchant
    data: null,
});

export const setUserState = (type, data) => {
    userState.value.type = type;
    userState.value.data = data;
};

export const clearUserState = () => {
    userState.value = { type: null, data: null };
};