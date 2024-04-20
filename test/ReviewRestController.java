package com.github.prgrms.orders;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/orders")
public class ReviewRestController {

    @Autowired
    private OrderService orderService;

    @PostMapping("/{id}/review")
    public ResponseEntity<?> review(@PathVariable Long id, @RequestBody ReviewRequest reviewRequest) {
        try {
            Order order = orderService.findOrderById(id);

            if (!order.getState().equals(OrderState.COMPLETED)) {
                return ResponseEntity.badRequest().body(new ErrorResponse("Could not write review for order " + id + " because state(" + order.getState() + ") is not allowed", 400));
            }

            if (order.getReview() != null) {
                return ResponseEntity.badRequest().body(new ErrorResponse("Could not write review for order " + id + " because have already written", 400));
            }

            Review review = new Review(reviewRequest.getContent());
            order.setReview(review);
            order.getProduct().increaseReviewCount();
            orderService.saveOrder(order);

            return ResponseEntity.ok(new ReviewResponse(true, new ReviewInfo(review.getSeq(), order.getProduct().getId(), review.getContent(), review.getCreatedAt())));
        } catch (OrderNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
