package com.prgrms.wadiz.domain.funding.dto.response;

import com.prgrms.wadiz.domain.funding.FundingCategory;
import com.prgrms.wadiz.domain.funding.FundingStatus;
import com.prgrms.wadiz.domain.funding.entity.Funding;
import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record FundingResponseDTO(
        Long fundingId,
        Integer fundingTargetAmount,
        LocalDateTime fundingStartAt,
        LocalDateTime fundingEndAt,
        Integer fundingParticipants,
        Integer fundingAmount,
        Integer fundingSuccessRate,
        Boolean fundingSuccess,
        FundingCategory fundingCategory,
        FundingStatus fundingStatus
) {
    public static FundingResponseDTO from(Funding funding) {
        return FundingResponseDTO.builder()
                .fundingId(funding.getFundingId())
                .fundingTargetAmount(funding.getFundingTargetAmount())
                .fundingStartAt(funding.getFundingStartAt())
                .fundingEndAt(funding.getFundingEndAt())
                .fundingParticipants(funding.getFundingParticipants())
                .fundingAmount(funding.getFundingAmount())
                .fundingSuccessRate(funding.calculateSuccessRate())
                .fundingSuccess(funding.getFundingSuccess())
                .fundingCategory(funding.getFundingCategory())
                .fundingStatus(funding.getFundingStatus())
                .build();
    }
}
