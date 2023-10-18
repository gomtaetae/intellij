package com.kosa.ShareTour.entity;

import com.kosa.ShareTour.constant.AuctionStatus;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "action_orders")
@Getter @Setter
public class AuctionOrder extends BaseEntity{

    @Id
    @GeneratedValue
    @Column(name = "auction_order_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    private LocalDateTime auctionOrderDate; //주문일

    @Enumerated(EnumType.STRING)
    private AuctionStatus auctionStatus; //주문상태

    @OneToMany(mappedBy = "auctionOrder", cascade = CascadeType.ALL
            , orphanRemoval = true, fetch = FetchType.LAZY)
    private List<AuctionOrderItem> auctionOrderItems = new ArrayList<>();

    public void addAuctionOrderItem(AuctionOrderItem auctionOrderItem) {
        auctionOrderItems.add(auctionOrderItem);
        auctionOrderItem.setAuctionOrder(this);
    }

    public static AuctionOrder createAuctionOrder(Member member, List<AuctionOrderItem> auctionOrderItemList) {
        AuctionOrder auctionOrder = new AuctionOrder();
        auctionOrder.setMember(member);

        for(AuctionOrderItem auctionOrderItem : auctionOrderItemList) {
            auctionOrder.addAuctionOrderItem(auctionOrderItem);
        }

        auctionOrder.setAuctionStatus(AuctionStatus.ORDER);
        auctionOrder.setAuctionOrderDate(LocalDateTime.now());
        return auctionOrder;
    }

    public int getAuctionTotalPrice() {
        int totalPrice = 0;
        for(AuctionOrderItem auctionOrderItem : auctionOrderItems){
            totalPrice += auctionOrderItem.getTotalPrice();
        }
        return totalPrice;
    }

    public void cancelAuctionOrder() {
        this.auctionStatus = AuctionStatus.CANCEL;
        for (AuctionOrderItem auctionOrderItem : auctionOrderItems) {
            auctionOrderItem.cancel();
        }
    }
}
