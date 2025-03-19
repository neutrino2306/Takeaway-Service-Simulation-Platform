package com.example.demo.controller;

import com.example.demo.entity.LineItem;
import com.example.demo.entity.Order;
import com.example.demo.service.LineItemService;
import com.example.demo.service.OrderService;
import jakarta.annotation.Resource;
import jakarta.transaction.Transactional;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/LineItemController")
public class LineItemController {

    @Resource
    private LineItemService lineItemService;

    @Resource
    private OrderService orderService;

    @PostMapping("/findByOrderID")
    @CrossOrigin
    public List<LineItem> findByOrderID(@RequestParam("orderID") Integer orderID) {
        List<LineItem> lineItemList = lineItemService.findByOrderID(orderID);
        if (lineItemList != null) {
            return new ResponseEntity<>(lineItemList, HttpStatus.OK).getBody();
        } else {
            return new ResponseEntity<>(lineItemList, HttpStatus.OK).getBody();
        }
    }

    @PostMapping("/findByItemID")
    @CrossOrigin
    public List<LineItem> findByItemID(@RequestParam("itemID") Integer itemID) {
        List<LineItem> lineItemList = lineItemService.findByItemID(itemID);
        if (lineItemList != null) {
            return new ResponseEntity<>(lineItemList, HttpStatus.OK).getBody();
        } else {
            return new ResponseEntity<>(lineItemList, HttpStatus.OK).getBody();
        }
    }

    @PostMapping("/findByCustomerIDAndShopID")
    @CrossOrigin
    public List<LineItem> findByCustomerIDAndShopID(@RequestParam("customerID") Integer customerID,
                                                    @RequestParam("shopID") Integer shopID) {
        List<LineItem> lineItemList = lineItemService.findByCustomerIDAndShopID(customerID, shopID);
        if (lineItemList != null) {
            return new ResponseEntity<>(lineItemList, HttpStatus.OK).getBody();
        } else {
            return new ResponseEntity<>(lineItemList, HttpStatus.OK).getBody();
        }
    }

    @PostMapping("/findByMerchantID")
    @CrossOrigin
    public List<LineItem> findByMerchantID(@RequestParam("merchantID") Integer merchantID) {
        List<LineItem> lineItemList = lineItemService.findByMerchantID(merchantID);
        if (lineItemList != null) {
            return new ResponseEntity<>(lineItemList, HttpStatus.OK).getBody();
        } else {
            return new ResponseEntity<>(lineItemList, HttpStatus.OK).getBody();
        }
    }

    @PostMapping("/findByLineItemID")
    @CrossOrigin
    public LineItem findByLineItemID(@RequestParam("lineItemID") Integer lineItemID) {
        LineItem lineItem = lineItemService.findByLineItemID(lineItemID);
        if (lineItem != null) {
            return new ResponseEntity<>(lineItem, HttpStatus.OK).getBody();
        } else {
            return new ResponseEntity<>(lineItem, HttpStatus.OK).getBody();
        }
    }

    @PostMapping("/deleteLineItem")
    @CrossOrigin
    @Transactional
    public ResponseEntity<?> deleteLineItem(@RequestParam Integer lineItemID) {
        try {
            lineItemService.deleteLineItem(lineItemID);
            return ResponseEntity.ok().build();
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/updateLineItem")
    @CrossOrigin
    @Transactional
    public ResponseEntity<?> updateLineItemQuantityAndTotal(@RequestParam("lineItemID") Integer lineItemID,
                                                            @RequestParam("itemID") Integer itemID,
                                                            @RequestParam("quantity") Integer quantity) {
        int updated = lineItemService.updateLineItemQuantityAndTotal(lineItemID, itemID, quantity);
        if (updated > 0) {
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
        }
    }

    @PostMapping("/updateOrAddLineItem")
    @CrossOrigin
    @Transactional
    public ResponseEntity<LineItem> updateOrAddLineItem(@RequestParam("customerID") Integer customerID,
                                                @RequestParam("shopID") Integer shopID,
                                                @RequestParam("itemID") Integer itemID,
                                                @RequestParam("quantity") Integer quantity) {
        try {
            Order existingOrder = orderService.getOrCreateUnpaidOrder(customerID, shopID);
            LineItem lineItem;

            // 检查订单中是否已存在相同商品的 LineItem
            LineItem existingLineItem = lineItemService.findByOrderIDAndItemID(existingOrder.getOrderID(), itemID);

            if (existingLineItem != null) {
                // 如果找到，更新数量和总价
                lineItemService.updateLineItemQuantityAndTotal(existingLineItem.getLineItemID(), itemID, existingLineItem.getQuantity() + quantity);
                // 重新获取 LineItem，反映最新的变更
                lineItem = lineItemService.findByLineItemID(existingLineItem.getLineItemID());

            } else {
                // 如果没有找到，创建新的 LineItem
                lineItem = lineItemService.addLineItem(existingOrder.getOrderID(), itemID, customerID, shopID, quantity);
            }

            // 已在数据库中设置触发器自动更新订单总价，无需手动更新

            // 返回更新后或新创建的 LineItem
            return new ResponseEntity<>(lineItem, HttpStatus.CREATED);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new LineItem());
        }
    }

}
