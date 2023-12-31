//package com.kosa.ShareTour.entity;
//
//import com.kosa.ShareTour.dto.MemberFormDto;
//import com.kosa.ShareTour.repository.CartRepository;
//import com.kosa.ShareTour.repository.MemberRepository;
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.test.context.TestPropertySource;
//import org.springframework.transaction.annotation.Transactional;
//
//import javax.persistence.EntityManager;
//import javax.persistence.EntityNotFoundException;
//import javax.persistence.PersistenceContext;
//
//import java.time.LocalDateTime;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//
//@SpringBootTest
//@Transactional
//@TestPropertySource(locations="classpath:application-test.properties")
//
//class CartTest {
//
//    @Autowired
//    CartRepository cartRepository;
//
//    @Autowired
//    MemberRepository memberRepository;
//
//    @Autowired
//    PasswordEncoder passwordEncoder;
//
//    @PersistenceContext
//    EntityManager em;
//
//    public Member createMember(){
//        MemberFormDto memberFormDto = new MemberFormDto();
//        memberFormDto.setName("홍길동");
//        memberFormDto.setEmail("test@email.com");
//        memberFormDto.setNickname("Hongs");
//        memberFormDto.setPassword("12345678");
//        memberFormDto.setGender("남성");
//        memberFormDto.setBirthday("2023-10-10");
//        memberFormDto.setPhone("010-1234-5678");
//        memberFormDto.setAddressMain("서울시 마포구 합정동");
//
//        return Member.createMember(memberFormDto, passwordEncoder);
//    }
//
//    @Test
//    @DisplayName("장바구니 회원 엔티티 매핑 조회 테스트")
//    public void findCartAndMemberTest(){
//        Member member = createMember();
//        memberRepository.save(member);
//        Cart cart = new Cart();
//        cart.setMember(member);
//        cartRepository.save(cart);
//
//        em.flush();
//        em.clear();
//
//        Cart savedCart = cartRepository.findById(cart.getId())
//                .orElseThrow(EntityNotFoundException::new);
//        assertEquals(savedCart.getMember().getId(), member.getId());
//    }
//
//}