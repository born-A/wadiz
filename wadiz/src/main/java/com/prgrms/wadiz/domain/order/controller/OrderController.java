package com.prgrms.wadiz.domain.order.controller;

import com.prgrms.wadiz.domain.order.dto.request.OrderCreateRequestDTO;
import com.prgrms.wadiz.domain.order.dto.response.OrderResponseDTO;
import com.prgrms.wadiz.domain.order.service.OrderService;
import com.prgrms.wadiz.global.util.resTemplate.ResponseFactory;
import com.prgrms.wadiz.global.util.resTemplate.ResponseTemplate;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/orders/")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    /**
     * Order create, cancel
     */
    @PostMapping("supporter/{supporterId}/new")
    public ResponseEntity<ResponseTemplate> createOrder(
            @PathVariable Long supporterId,
            @RequestBody @Valid OrderCreateRequestDTO orderCreateRequestDto
    ){
        OrderResponseDTO orderREsponseDTO = orderService.createOrder(supporterId, orderCreateRequestDto);

        return ResponseEntity.ok(ResponseFactory.getSingleResult(orderREsponseDTO));
    }

    @PutMapping("{orderId}/supporters/{supporterId}")
    public ResponseEntity<ResponseTemplate> cancelOrder(
            @PathVariable Long orderId,
            @PathVariable Long supporterId
    ){
        orderService.cancelOrder(supporterId, orderId);

        return ResponseEntity.ok(ResponseFactory.getSuccessResult());
    }

    /**
     * 서포터 주문 기록 조회(단건,다건)
     */

    @GetMapping("{orderId}/supporters/{supporterId}/purchase")
    public ResponseEntity<ResponseTemplate> getSupporterPurchase(
            @PathVariable Long orderId,
            @PathVariable Long supporterId
    ){
        OrderResponseDTO orderResponseDTO = orderService.getSupporterPurchase(orderId, supporterId);

        return ResponseEntity.ok(ResponseFactory.getSingleResult(orderResponseDTO));
    }

    @GetMapping("supporters/{supporterId}/history")
    public ResponseEntity<ResponseTemplate> getSupporterPurchaseHistory(@PathVariable Long supporterId){
        List<OrderResponseDTO> orderResponseDTOs = orderService.getSupporterPurchaseHistory(supporterId);

        return ResponseEntity.ok(ResponseFactory.getListResult(orderResponseDTOs));
    }

    /**
     * 메이커 주문 기록 조회
     */

    @GetMapping("projects/{projectId}/makers/{makerId}")
    public ResponseEntity<ResponseTemplate> getMakerProjectOrders(
            @PathVariable Long projectId,
            @PathVariable Long makerId
    ){
        List<OrderResponseDTO> orderResponseDTOs = orderService.getMakerProjectOrders(projectId,makerId);

        return ResponseEntity.ok(ResponseFactory.getListResult(orderResponseDTOs));
    }
}
