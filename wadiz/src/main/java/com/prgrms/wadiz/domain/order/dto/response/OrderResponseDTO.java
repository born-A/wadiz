package com.prgrms.wadiz.domain.order.dto.response;

import com.prgrms.wadiz.domain.funding.FundingCategory;
import com.prgrms.wadiz.domain.order.OrderStatus;
import com.prgrms.wadiz.domain.orderReward.dto.response.OrderRewardResponseDTO;
import lombok.Builder;
import java.time.LocalDateTime;
import java.util.List;

@Builder
public record OrderResponseDTO(
        Long orderId,
        String postTitle,
        String makerBrand,
        List<OrderRewardResponseDTO> orderRewardResponseDTOs,
        FundingCategory fundingCategory,
        LocalDateTime createdAt,
        OrderStatus orderStatus
) {
    public static OrderResponseDTO of(Long orderId){
        return OrderResponseDTO.builder()
                .orderId(orderId)
                .build();
    }

    public static OrderResponseDTO of(
            Long orderId,
            String postTitle,
            String makerBrand,
            List<OrderRewardResponseDTO> orderRewardResponseDTOs,
            OrderStatus orderStatus
    ){
        return OrderResponseDTO.builder()
                .orderId(orderId)
                .postTitle(postTitle)
                .makerBrand(makerBrand)
                .orderRewardResponseDTOs(orderRewardResponseDTOs)
                .orderStatus(orderStatus)
                .build();
    }

    public static OrderResponseDTO of(
            Long orderId,
            List<OrderRewardResponseDTO> orderRewardResponseDTOs,
            OrderStatus orderStatus
    ){
        return OrderResponseDTO.builder()
                .orderId(orderId)
                .orderRewardResponseDTOs(orderRewardResponseDTOs)
                .orderStatus(orderStatus)
                .build();
    }

    public static OrderResponseDTO of(
            Long orderId,
            String postTitle,
            String makerBrand,
            FundingCategory fundingCategory,
            LocalDateTime createdAt
    ){
        return OrderResponseDTO.builder()
                .orderId(orderId)
                .postTitle(postTitle)
                .makerBrand(makerBrand)
                .fundingCategory(fundingCategory)
                .createdAt(createdAt)
                .build();
    }
}