package hello.login.web.login;

import hello.login.domain.login.LoginService;
import hello.login.domain.member.Member;
import hello.login.web.SessionConst;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
@RequiredArgsConstructor
public class LoginController {

    private final LoginService loginService;

    @GetMapping("/login")
    public String loginForm(Model model) {
        model.addAttribute("loginForm", new LoginForm());
        return "login/loginForm";
    }

    //@RequestParam(defaultValue = "/") String redirectURL : 요청 쿼리스트링 있으면 담고 없으면 "/" 담음 (redirectURL 변수에)
    @PostMapping("/login")
    public String loginV1(@Validated @ModelAttribute("loginForm") LoginForm loginForm, BindingResult bindingResult,
                          HttpServletRequest request, @RequestParam(defaultValue = "/") String redirectURL) {
        if (bindingResult.hasErrors()) {
            return "login/loginForm";
        }

        //로그인 성공시 로그인한 회원의 객체를 반환받음, 실패시 null
        Member member = loginService.login(loginForm.getLoginId(), loginForm.getPassword());
        //로그인 실패시 다시 로그인폼으로
        if (member == null) {
            bindingResult.reject("loginFail", "아이디 또는 비밀번호가 맞지 않습니다!");
            return "login/loginForm";
        }

        //로그인 성공 처리

        //request.getSession() : 세션이 이미 존재하면 해당 세션을 담고, 세션이 없으면 새로운 세션을 생성 (true) 세션이 없으면 null (false)
        //HttpSession 이 생성되면 추청 불가한 쿠키(세션 id)가 생성됨
        HttpSession session = request.getSession();
        //세션에 로그인 회원 정보 보관 (attributeName, attributeValue) set 이니까 저장
        session.setAttribute(SessionConst.LOGIN_MEMBER, member);

        //redirectURL : 인터셉터가 로그인이 안되었어서 가로챈 url 또는 defaultValue = "/"
        return "redirect:" + redirectURL;
    }

    @PostMapping("/logout")
    public String logout(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();
        }

        return "redirect:/";
    }
}
