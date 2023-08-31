package com.prgrms.wadiz.domain.orderReward.entity;

import com.prgrms.wadiz.domain.reward.entity.Reward;
import com.prgrms.wadiz.domain.order.entity.Order;
import com.prgrms.wadiz.global.BaseEntity;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@Table(name = "order_rewards")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class OrderReward extends BaseEntity {
    private static final int POSITIVE_ORDER_QUANTITY = 1;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long orderRewardId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "reward_id")
    private Reward reward;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    private Order order;

    @Column(nullable = false)
    private Integer orderRewardPrice;

    @Column(nullable = false)
    private Integer orderRewardQuantity;

    @Builder
    public OrderReward(Reward reward, Integer orderRewardPrice, Integer orderRewardQuantity) {
        this.reward = reward;
        this.orderRewardPrice = orderRewardPrice;
        this.orderRewardQuantity = validatePositive(orderRewardQuantity);
    }

    public static OrderReward createOrderReward(Reward reward, Integer orderRewardPrice, Integer orderRewardQuantity){
        OrderReward orderReward = OrderReward.builder()
                .reward(reward)
                .orderRewardPrice(orderRewardPrice)
                .orderRewardQuantity(orderRewardQuantity)
                .build();

        reward.removeStock(orderRewardQuantity);

        return orderReward;
    }

    public void changeOrder(Order order) {
        this.order = order;
        order.getOrderRewards().add(this);
    }

    private Integer validatePositive(Integer orderRewardQuantity) {
        if(orderRewardQuantity < POSITIVE_ORDER_QUANTITY){
            throw new IllegalArgumentException("주문은 양수입니다.");
        }

        return orderRewardQuantity;
    }
}
