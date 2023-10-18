package com.kosa.ShareTour.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter @Setter
public class AuctionOrderItem extends BaseEntity{

    @Id
    @GeneratedValue
    @Column(name = "action_order_item_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "auction_id")
    private Auction auction;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "action_order_id")
    private AuctionOrder auctionOrder;

    private int orderPrice; //주문가격

    private int count; //수량

    public static AuctionOrderItem createAuctionOrderItem(Auction auction, int count){
        AuctionOrderItem auctionOrderItem = new AuctionOrderItem();
        auctionOrderItem.setAuction(auction);
        auctionOrderItem.setCount(count);
        auctionOrderItem.setOrderPrice(auction.getPrice());
        return auctionOrderItem;
    }

    public int getTotalPrice(){
        return orderPrice*count;
    }

    public void cancel() {
        this.getAuction();
    }
}
