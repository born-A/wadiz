package com.prgrms.wadiz.domain.funding.dto.request;

import com.prgrms.wadiz.domain.funding.FundingCategory;
import com.prgrms.wadiz.domain.funding.FundingStatus;
import com.prgrms.wadiz.global.annotation.ValidEnum;

import javax.validation.constraints.FutureOrPresent;
import javax.validation.constraints.Min;
import java.time.LocalDateTime;

public record FundingUpdateRequestDTO(
        @Min(value = 0, message = "펀딩 모집 금액은 0이상의 정수만 허용됩니다.")
        Integer fundingTargetAmount,

        @FutureOrPresent(message = "펀딩 시작 시점을 과거 시각으로 설정할 수 없습니다.")
        LocalDateTime fundingStartAt,

        @FutureOrPresent(message = "펀딩 종료 시점을 과거 시각으로 설정할 수 없습니다.")
        LocalDateTime fundingEndAt,

        @ValidEnum(enumClass = FundingCategory.class, message = "존재하지 않는 카테고리 입니다.")
        FundingCategory fundingCategory,

        @ValidEnum(enumClass = FundingCategory.class, message = "유효하지 않은 펀딩 상태입니다.")
        FundingStatus fundingStatus
) {
}
