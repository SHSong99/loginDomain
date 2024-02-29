package hello.login.web;

import hello.login.domain.member.Member;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.SessionAttribute;

@Slf4j
@Controller
public class HomeController {

    @GetMapping("/")
    public String homeLoginV3Spring(
            //세션에서 SessionConst.LOGIN_MEMBER 라는 속성의 값(로그인된 멤버객체)을 Member loginMember 에 담음
            //required: 해당 속성의 이름인 세션이 없을때 ture 면 예외, false 면 null
            @SessionAttribute(name = SessionConst.LOGIN_MEMBER, required = false) Member loginMember, Model model) {

        //세션에 회원 데이터가 없으면 home 뷰
        if (loginMember == null) {
            return "home";
        }

        //세션이 있으면 모델에 담고
        model.addAttribute("member", loginMember);
        //loginHome 뷰로 이동
        return "loginHome";
    }
}