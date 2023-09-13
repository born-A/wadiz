package com.prgrms.wadiz.domain.reward.dto.request;

import com.prgrms.wadiz.domain.reward.RewardStatus.RewardStatus;
import com.prgrms.wadiz.domain.reward.RewardType.RewardType;
import com.prgrms.wadiz.global.annotation.ValidEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

@Builder
public record RewardCreateRequestDTO(
        @Schema(description = "리워드명")
        @NotBlank(message = "리워드명을 입력해주세요.")
        String rewardName,

        @Schema(description = "리워드 설명")
        @NotBlank(message = "리워드 설명을 입력해주세요.")
        String rewardDescription,

        @Schema(description = "리워드 재고")
        @Min(value = 1, message = "리워드 재고는 최소 1개 이상입니다.")
        Integer rewardQuantity,

        @Schema(description = "리워드 가격")
        @Min(value = 10, message = "리워드 가격을 입력해주세요.")
        Integer rewardPrice,

        @Schema(description = "리워드 타입")
        @ValidEnum(enumClass = RewardType.class, message = "리워드 타입을 입력해주세요.")
        RewardType rewardType,

        @Schema(description = "리워드 상태")
        @ValidEnum(enumClass = RewardStatus.class, message = "리워드 타입을 입력해주세요.")
        RewardStatus rewardStatus
) {
}
