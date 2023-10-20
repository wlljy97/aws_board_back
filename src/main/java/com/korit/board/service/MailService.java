package com.korit.board.service;

import com.korit.board.jwt.JwtProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import javax.mail.internet.MimeMessage;

@Service
@RequiredArgsConstructor
public class MailService {
    private final JavaMailSender javaMailSender;
    private final JwtProvider jwtProvider;

    public boolean sendMail() {
        String toEmail = SecurityContextHolder.getContext().getAuthentication().getName();

        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        try {
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, false, "utf-8"); // MimeMessageHelper <- 객체생성 할려면 예외처리가 필요
            helper.setSubject("스프링 부트 사용자 인증 메일 테스트");
            helper.setFrom("jw7812@gmail.com"); // 관리자 이메일
            helper.setTo(toEmail); // 해당 사용자 이메일
            String token = jwtProvider.generateAuthMailToken(toEmail); // token생성

            mimeMessage.setText( // html로 mail을 작성
                    "<div>" +
                        "<h1>사용자 인증 메일</h1>" +
                        "<p>사용자 인증을 완료하려면 아래의 버튼을 클릭하세요. </p>" +
                        "<a href=\"http://localhost:8080/auth/mail?token=" + token + "\">인증하기</a> " +
                    "</div>", "utf-8", "html"
                    // (첫번째, 두번째, 세번째) 매개변수
            );
            javaMailSender.send(mimeMessage);
        } catch (Exception e) {
            return false;
        }
        return true;
    }

}
