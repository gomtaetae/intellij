package com.kosa.ShareTour.initial;

import com.kosa.ShareTour.constant.Role;
import com.kosa.ShareTour.entity.Member;
import com.kosa.ShareTour.service.MemberService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class DataInitializer implements CommandLineRunner {
    private final MemberService memberService;
    private final PasswordEncoder passwordEncoder;

    public DataInitializer (MemberService memberService, PasswordEncoder passwordEncoder) {
        this.memberService = memberService;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) throws Exception {
        Member alpha = createAlpha();

        Member existingAlpha = memberService.findByEmail(alpha.getEmail());

        if (existingAlpha == null) {
            memberService.saveMember(alpha);
        }
    }

    private Member createAlpha() {

        Member alpha = new Member();
        alpha.setName("Alpha");
        alpha.setEmail("alpha@email.com");
        alpha.setNickname("Alpha");

        String password = passwordEncoder.encode("1234qwer");
        alpha.setPassword(password);

        alpha.setImgUrl("N/A");
        alpha.setGender("N/A");
        alpha.setBirthday(LocalDate.now());
        alpha.setPhone("00000000000");
        alpha.setAddressMain("N/A");
        alpha.setAddressSub("N/A");
        alpha.setPoint(Integer.parseInt("999"));

        alpha.setRole(Role.ADMIN);

        return alpha;
    }
}