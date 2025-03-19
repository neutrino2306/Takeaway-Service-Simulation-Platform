import http from './request';
import post from "axios";
import {ref} from "vue";
import request from "./request";

export function customerLogin(customerPhone, customerPassword) {
    let url = "/CustomerController/CustomerLogin";
    return request({
        url: url,
        method: 'post',
        params: {
            customerPhone,
            customerPassword
        }
    });
}

export function findCustomerByCustomerPhone(customerPhone) {
    let url = "/CustomerController/findByCustomerPhone";
    return request({
        url: url,
        method: 'post',
        params: {
            customerPhone
        }
    });
}

export function updateCustomer(customerID, customerName, customerAddress, customerPassword, customerPhone) {
    let url = "/CustomerController/updateCustomer";
    return request({
        url: url,
        method: 'put',
        params: {
            customerID,
            customerName,
            customerAddress,
            customerPassword,
            customerPhone
        }
    });
}

export function addCustomer(customerPhone, customerPassword, customerName, customerAddress) {
    let url = "/CustomerController/addCustomer";
    return request({
        url: url,
        method: 'post',
        params: {
            customerPhone,
            customerPassword,
            customerName,
            customerAddress
        }
    });
}

export function findItemByItemID(itemID) {
    return request({
        url: '/ItemController/findByItemID',
        method: 'post',
        params: {
            itemID
        }
    });
}

export function findItemByItemName(itemName) {
    return request({
        url: '/ItemController/findByItemName',
        method: 'post',
        params: {
            itemName
        }
    });
}

export function findItemByItemNameAndMerchantID(itemName, merchantID) {
    return request({
        url: '/ItemController/findByItemNameAndMerchantID',
        method: 'post',
        params: {
            itemName,
            merchantID
        }
    });
}

export function findItemByItemIDAndMerchantID(itemID, merchantID) {
    return request({
        url: '/ItemController/findByItemIDAndMerchantID',
        method: 'post',
        params: {
            itemID,
            merchantID
        }
    });
}

export function findItemByShopItemIDAndShopID(shopItemID, shopID) {
    return request({
        url: '/ItemController/findByShopItemIDAndShopID',
        method: 'post',
        params: {
            shopItemID,
            shopID
        }
    });
}

export function findItemByShopID(shopID) {
    return request({
        url: '/ItemController/findByShopID',
        method: 'post',
        params: {
            shopID
        }
    });
}

export function findItemByShopIDAndMerchantID(shopID, merchantID) {
    return request({
        url: '/ItemController/findByShopIDAndMerchantID',
        method: 'post',
        params: {
            shopID,
            merchantID
        }
    });
}

export function findItemByMerchantID(merchantID) {
    return request({
        url: '/ItemController/findByMerchantID',
        method: 'post',
        params: {
            merchantID
        }
    });
}

export function findItemByItemNameContaining(itemName) {
    return request({
        url: '/ItemController/findByItemNameContaining',
        method: 'post',
        params: {
            itemName
        }
    });
}

export function updateItemDetails(itemID, itemName, itemDescription, itemPrice) {
    return request({
        url: '/ItemController/updateItemDetails',
        method: 'put',
        params: {
            itemID,
            itemName,
            itemDescription,
            itemPrice
        }
    });
}

export function addItem(shopItemID, merchantID, shopID) {
    return request({
        url: '/ItemController/addItem',
        method: 'post',
        params: {
            shopItemID,
            merchantID,
            shopID
        }
    });
}

export function findLineItemByOrderID(orderID) {
    return request({
        url: '/LineItemController/findByOrderID',
        method: 'post',
        params: {
            orderID
        }
    });
}

export function findLineItemByItemID(itemID) {
    return request({
        url: '/LineItemController/findByItemID',
        method: 'post',
        params: {
            itemID
        }
    });
}

export function findLineItemByCustomerIDAndShopID(customerID, shopID) {
    return request({
        url: '/LineItemController/findByCustomerIDAndShopID',
        method: 'post',
        params: {
            customerID,
            shopID
        }
    });
}

export function findLineItemByMerchantID(merchantID) {
    return request({
        url: '/LineItemController/findByMerchantID',
        method: 'post',
        params: {
            merchantID
        }
    });
}

export function findLineItemByShopID(shopID) {
    return request({
        url: '/LineItemController/findByShopID',
        method: 'post',
        params: {
            shopID
        }
    });
}

export function findByLineItemID(lineItemID) {
    return request({
        url: '/LineItemController/findByLineItemID',
        method: 'post',
        params: {
            lineItemID
        }
    });
}

export function deleteLineItem(lineItemID) {
    return request({
        url: '/LineItemController/deleteLineItem',
        method: 'post',
        params: {
            lineItemID
        }
    });
}

export function updateLineItemQuantityAndTotal(lineItemID, itemID, quantity) {
    return request({
        url: '/LineItemController/updateLineItem',
        method: 'put',
        params: {
            lineItemID,
            itemID,
            quantity
        }
    });
}

export function updateOrAddLineItem(customerID, shopID, itemID, quantity) {
    return request({
        url: '/LineItemController/updateOrAddLineItem',
        method: 'post',
        params: {
            customerID,
            shopID,
            itemID,
            quantity
        }
    });
}

export function merchantLogin(merchantPhone, merchantPassword) {
    return request({
        url: '/MerchantController/MerchantLogin',
        method: 'post',
        params: {
            merchantPhone,
            merchantPassword
        }
    });
}

export function findMerchantByMerchantID(merchantID) {
    return request({
        url: '/MerchantController/findByMerchantID',
        method: 'post',
        params: {
            merchantID
        }
    });
}

export function findMerchantByMerchantPhone(merchantPhone) {
    return request({
        url: '/MerchantController/findByMerchantPhone',
        method: 'post',
        params: {
            merchantPhone
        }
    });
}

export function updateMerchant(merchantID, merchantName, merchantDescription, merchantPhone, merchantPassword) {
    return request({
        url: '/MerchantController/updateMerchant',
        method: 'put',
        params: {
            merchantID,
            merchantName,
            merchantDescription,
            merchantPhone,
            merchantPassword
        }
    });
}

export function addMerchant(merchantPhone, merchantPassword, merchantName, merchantDescription) {
    return request({
        url: '/MerchantController/addMerchant',
        method: 'post',
        params: {
            merchantPhone,
            merchantPassword,
            merchantName,
            merchantDescription
        }
    });
}

export function findByCustomerID(customerID) {
    return request({
        url: '/OrderController/findByCustomerID',
        method: 'post',
        params: {
            customerID
        }
    });
}

export function findOrderByOrderID(orderID) {
    return request({
        url: '/OrderController/findByOrderID',
        method: 'post',
        params: {
            orderID
        }
    });
}

export function findOrderByMerchantID(merchantID) {
    return request({
        url: '/OrderController/findByMerchantID',
        method: 'post',
        params: {
            merchantID
        }
    });
}

export function findByCustomerIDAndShopID(customerID, shopID) {
    return request({
        url: '/OrderController/findByCustomerIDAndShopID',
        method: 'post',
        params: {
            customerID,
            shopID
        }
    });
}

export function findOrderByMerchantIDAndShopID(merchantID, shopID) {
    return request({
        url: '/OrderController/findByMerchantIDAndShopID',
        method: 'post',
        params: {
            merchantID,
            shopID
        }
    });
}

export function findOrderByShopID(shopID) {
    return request({
        url: '/OrderController/findByShopID',
        method: 'post',
        params: {
            shopID
        }
    });
}

export function findOrderByMerchantIDAndStatus(merchantID, status) {
    return request({
        url: '/OrderController/findByMerchantIDAndStatus',
        method: 'post',
        params: {
            merchantID,
            status
        }
    });
}

export function updateOrderStatus(orderID, status) {
    return request({
        url: '/OrderController/updateOrderStatus',
        method: 'put',
        params: {
            orderID,
            status
        }
    });
}

export function cancelOrder(orderID) {
    return request({
        url: '/OrderController/cancelOrder',
        method: 'put',
        params: {
            orderID
        }
    });
}

export function addOrder(customerID, merchantID, shopID, totalPrice) {
    return request({
        url: '/OrderController/addOrder',
        method: 'post',
        params: {
            customerID,
            merchantID,
            shopID,
            totalPrice
        }
    });
}

export function listAllShops() {
    return request({
        url: '/ShopController/listAllShops',
        method: 'post'
    });
}

export function findShopByMerchantID(merchantID) {
    return request({
        url: '/ShopController/findByMerchantID',
        method: 'post',
        params: { merchantID }
    });
}

export function findShopByShopID(shopID) {
    return request({
        url: '/ShopController/findByShopID',
        method: 'post',
        params: { shopID }
    });
}

export function findShopByShopName(shopName) {
    return request({
        url: '/ShopController/findByShopName',
        method: 'post',
        params: { shopName }
    });
}

export function findShopByShopNameContaining(shopName) {
    return request({
        url: '/ShopController/findByShopNameContaining',
        method: 'post',
        params: { shopName }
    });
}

export function findShopByShopAddressContaining(shopAddress) {
    return request({
        url: '/ShopController/findByShopAddressContaining',
        method: 'post',
        params: { shopAddress }
    });
}

export function updateShopDetails(shopID, shopName, shopAddress, shopDescription) {
    return request({
        url: '/ShopController/updateShopDetails',
        method: 'put',
        params: {
            shopID,
            shopName,
            shopAddress,
            shopDescription
        }
    });
}

export function addShop(merchantID, shopName, shopAddress, shopDescription) {
    return request({
        url: '/ShopController/addShop',
        method: 'post',
        params: {
            merchantID,
            shopName,
            shopAddress,
            shopDescription
        }
    });
}

export function findShopItemByMerchantID(merchantID) {
    return request({
        url: '/ShopItemController/findByMerchantID',
        method: 'post',
        params: { MerchantID: merchantID }
    });
}

export function findShopItemByShopItemID(shopItemID) {
    return request({
        url: '/ShopItemController/findByShopItemID',
        method: 'post',
        params: { ShopItemID: shopItemID }
    });
}

export function findShopItemByMerchantIDAndShopItemName(merchantID, shopItemName) {
    return request({
        url: '/ShopItemController/findByMerchantIDAndShopItemName',
        method: 'post',
        params: {
            MerchantID: merchantID,
            ShopItemName: shopItemName
        }
    });
}

export function findShopItemByMerchantIDAndShopItemNameContaining(merchantID, shopItemName) {
    return request({
        url: '/ShopItemController/findByMerchantIDAndShopItemNameContaining',
        method: 'post',
        params: {
            MerchantID: merchantID,
            ShopItemName: shopItemName
        }
    });
}

export function findShopItemByMerchantIDAndStatus(merchantID, status) {
    return request({
        url: '/ShopItemController/findByMerchantIDAndStatus',
        method: 'post',
        params: {
            MerchantID: merchantID,
            Status: status
        }
    });
}

export function updateShopItem(shopItemID, shopItemName, shopItemPrice, shopItemDescription) {
    return request({
        url: '/ShopItemController/updateShopItem',
        method: 'put',
        params: {
            shopItemID,
            shopItemPrice,
            shopItemName,
            shopItemDescription
        }
    });
}

export function addShopItem(merchantID, shopItemName, shopItemPrice, shopItemDescription) {
    return request({
        url: '/ShopItemController/addShopItem',
        method: 'post',
        params: {
            merchantID,
            shopItemName,
            shopItemPrice,
            shopItemDescription
        }
    });
}


