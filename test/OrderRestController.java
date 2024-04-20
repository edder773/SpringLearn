package com.github.prgrms.orders;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/orders")
public class OrderRestController {

    @Autowired
    private OrderService orderService;

    @GetMapping
    public ResponseEntity<?> findAll(@RequestParam(defaultValue = "0") int offset, @RequestParam(defaultValue = "5") int size) {
        try {
            Page<Order> orders = orderService.findOrders(offset, size);
            List<OrderInfo> orderInfos = orders.getContent().stream()
                    .map(this::mapToOrderInfo)
                    .collect(Collectors.toList());
            return ResponseEntity.ok(new OrderListResponse(true, orderInfos));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> findById(@PathVariable Long id) {
        try {
            Order order = orderService.findOrderById(id);
            OrderInfo orderInfo = mapToOrderInfo(order);
            return ResponseEntity.ok(new OrderResponse(true, orderInfo));
        } catch (OrderNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PatchMapping("/{id}/accept")
    public ResponseEntity<?> accept(@PathVariable Long id) {
        try {
            boolean result = orderService.acceptOrder(id);
            return ResponseEntity.ok(new OrderStatusResponse(true, result));
        } catch (OrderNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PatchMapping("/{id}/reject")
    public ResponseEntity<?> reject(@PathVariable Long id, @RequestBody RejectRequest rejectRequest) {
        try {
            boolean result = orderService.rejectOrder(id, rejectRequest.getMessage());
            return ResponseEntity.ok(new OrderStatusResponse(true, result));
        } catch (OrderNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PatchMapping("/{id}/shipping")
    public ResponseEntity<?> shipping(@PathVariable Long id) {
        try {
            boolean result = orderService.shippingOrder(id);
            return ResponseEntity.ok(new OrderStatusResponse(true, result));
        } catch (OrderNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PatchMapping("/{id}/complete")
    public ResponseEntity<?> complete(@PathVariable Long id) {
        try {
            boolean result = orderService.completeOrder(id);
            return ResponseEntity.ok(new OrderStatusResponse(true, result));
        } catch (OrderNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    private OrderInfo mapToOrderInfo(Order order) {
        ReviewInfo reviewInfo = null;
        if (order.getReview() != null) {
            reviewInfo = new ReviewInfo(order.getReview().getSeq(), order.getProduct().getId(), order.getReview().getContent(), order.getReview().getCreatedAt());
        }
        return new OrderInfo(order.getSeq(), order.getProduct().getId(), reviewInfo, order.getState(), order.getRequestMessage(), order.getRejectMessage(), order.getCompletedAt(), order.getRejectedAt(), order.getCreatedAt());
    }
}
