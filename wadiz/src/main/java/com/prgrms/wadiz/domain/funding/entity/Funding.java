package com.prgrms.wadiz.domain.funding.entity;

import com.prgrms.wadiz.domain.funding.FundingCategory;
import com.prgrms.wadiz.domain.funding.FundingStatus;
import com.prgrms.wadiz.domain.project.entity.Project;
import com.prgrms.wadiz.domain.BaseEntity;
import com.prgrms.wadiz.global.annotation.ValidEnum;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import javax.persistence.*;
import javax.validation.constraints.Min;
import java.time.LocalDateTime;

@Entity
@Getter
@Table(name = "fundings")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Funding extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long fundingId;

    @OneToOne
    @JoinColumn(name = "project_id")
    private Project project;

    @Column(nullable = false)
    @Min(value = 0, message = "펀딩 모집 금액은 0이상의 정수만 허용됩니다.")
    private Integer fundingTargetAmount;

    @Column(nullable = false)
    private LocalDateTime fundingStartAt;

    @Column(nullable = false)
    private LocalDateTime fundingEndAt;

    @Column(nullable = false)
    private int fundingParticipants;

    @Column(nullable = false)
    private int fundingAmount;

    @Column(nullable = false) //수정 : booleantype은 tinyint로 0 1로 구분되어 저장된다.
    private int fundingSuccessRate;

    @Column(nullable = false)
    private boolean fundingSuccess = Boolean.FALSE;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    @ValidEnum(enumClass = FundingCategory.class, message = "존재하지 않는 카테고리 입니다.")
    private FundingCategory fundingCategory;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    @ValidEnum(enumClass = FundingStatus.class, message = "유효하지 않은 펀딩 상태입니다.")
    private FundingStatus fundingStatus;

    @Builder
    public Funding(
            Project project,
            Integer fundingTargetAmount,
            LocalDateTime fundingStartAt,
            LocalDateTime fundingEndAt,
            FundingCategory fundingCategory
    ) {
        this.project = project;
        this.fundingTargetAmount = fundingTargetAmount;
        this.fundingStartAt = fundingStartAt;
        this.fundingEndAt = fundingEndAt;
        this.fundingCategory = fundingCategory;
        this.fundingStatus = FundingStatus.OPEN;
    }

    public void updateFunding(
            Integer fundingTargetAmount,
            LocalDateTime fundingStartAt,
            LocalDateTime fundingEndAt,
            FundingCategory fundingCategory,
            FundingStatus fundingStatus
    ) {
        this.fundingTargetAmount = fundingTargetAmount;
        this.fundingStartAt = fundingStartAt;
        this.fundingEndAt = fundingEndAt;
        this.fundingCategory = fundingCategory;
        this.fundingStatus = fundingStatus;
    }

    public void addOrderInfo(int totalOrderPrice) {
        this.fundingAmount += totalOrderPrice;
        this.fundingParticipants += 1;
        this.fundingSuccessRate = (int) Math.round((this.fundingAmount /(double) this.fundingTargetAmount) * 100);
        validateFundingSuccess(this.fundingAmount , this.fundingTargetAmount);
    }

    public void removeOrderInfo(int totalOrderPrice) {
        this.fundingAmount -= totalOrderPrice;
        this.fundingParticipants -= 1;
        this.fundingSuccessRate = (int) Math.round((this.fundingAmount /(double) this.fundingTargetAmount) * 100);
        validateFundingSuccess(this.fundingAmount , this.fundingTargetAmount);
    }

    private void validateFundingSuccess(Integer fundingAmount, Integer fundingTargetAmount) {
        this.fundingSuccess = (fundingAmount >= fundingTargetAmount) ? Boolean.TRUE : Boolean.FALSE;
    }
}
