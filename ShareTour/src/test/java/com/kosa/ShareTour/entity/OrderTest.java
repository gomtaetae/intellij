//package com.kosa.ShareTour.entity;
//
//import com.kosa.ShareTour.constant.ItemSellStatus;
//import com.kosa.ShareTour.repository.ItemRepository;
//import com.kosa.ShareTour.repository.OrderRepository;
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.test.context.TestPropertySource;
//import org.springframework.transaction.annotation.Transactional;
//
//import javax.persistence.EntityManager;
//import javax.persistence.EntityNotFoundException;
//import javax.persistence.PersistenceContext;
//import java.time.LocalDate;
//import java.time.LocalDateTime;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//import com.kosa.ShareTour.repository.MemberRepository;
////import com.kosa.ShareTour.repository.OrderI
//
//@SpringBootTest
//@TestPropertySource(locations="classpath:application-test.properties")
//@Transactional
//class OrderTest {
//
//    @Autowired
//    OrderRepository orderRepository;
//
//    @Autowired
//    ItemRepository itemRepository;
//
//    @PersistenceContext
//    EntityManager em;
//
//    @Autowired
//    MemberRepository memberRepository;
//
//
////    @Autowired
////    OrderItemRepository orderItemRepository;
//
//    public Item createItem(){
//        Item item = new Item();
//        item.setTitle("부산여행");
//        item.setContent("부산");
//        item.setPrice(100000);
//        item.setInStock(20202020);
//        item.setItemSellStatus(ItemSellStatus.SELL);
//        return item;
//    }
//
//    @Test
//    @DisplayName("영속성 전이 테스트")
//    public void cascadeTest() {
//
//        Order order = new Order();
//
//        for(int i=0;i<3;i++){
//            Item item = this.createItem();
//            itemRepository.save(item);
//            OrderItem orderItem = new OrderItem();
//            orderItem.setItem(item);
//            orderItem.setCount(10);
//            orderItem.setOrderPrice(1000);
//            orderItem.setOrder(order);
//            order.getOrderItems().add(orderItem);
//        }
//
//        orderRepository.saveAndFlush(order);
//        em.clear();
//
//        Order savedOrder = orderRepository.findById(order.getId())
//                .orElseThrow(EntityNotFoundException::new);
//        assertEquals(3, savedOrder.getOrderItems().size());
//    }
//
//
//
//
//    public Order createOrder(){
//        Order order = new Order();
//        for(int i=0;i<3;i++){
//            Item item = createItem();
//            itemRepository.save(item);
//            OrderItem orderItem = new OrderItem();
//            orderItem.setItem(item);
//            orderItem.setCount(10);
//            orderItem.setOrderPrice(1000);
//            orderItem.setOrder(order);
//            order.getOrderItems().add(orderItem);
//        }
//        Member member = new Member();
//        memberRepository.save(member);
//
//        order.setMember(member);
//        orderRepository.save(order);
//        return order;
//    }
//
//    @Test
//    @DisplayName("고아객체 제거 테스트")
//    public void orphanRemovalTest(){
//        Order order = this.createOrder();
//        order.getOrderItems().remove(0);
//        em.flush();
//    }
//
////
////    @Test
////    @DisplayName("지연 로딩 테스트")
////    public void lazyLoadingTest(){
////        Order order = this.createOrder();
////        Long orderItemId = order.getOrderItems().get(0).getId();
////        em.flush();
////        em.clear();
////        OrderItem orderItem = orderItemRepository.findById(orderItemId)
////                .orElseThrow(EntityNotFoundException::new);
////        System.out.println("Order class : " + orderItem.getOrder().getClass());
////        System.out.println("===========================");
////        orderItem.getOrder().getOrderDate();
////        System.out.println("===========================");
////    }
//}