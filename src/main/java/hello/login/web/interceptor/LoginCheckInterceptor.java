package hello.login.web.interceptor;

import hello.login.web.SessionConst;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@Slf4j
public class LoginCheckInterceptor implements HandlerInterceptor {

    //요청마다 항상 디스패처서블릿 -> 프리핸들러 인터셉터 (URL 가로챔) -> 핸들러 실행 (컨트롤러 호출)
    //WebConfig 에 인터셉터 발동 조건을 걸어놓음
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        HttpSession session = request.getSession();

        //세션에 SessionConst.LOGIN_MEMBER 속성(attributeName)이 없으면 (get 이니까 조회)
        if (session.getAttribute(SessionConst.LOGIN_MEMBER) == null) {
            //login 뷰로 리다이렉트 시키고 로그인 후에 이동할 요청 쿼리스트링 넘김 (?key=value)
            response.sendRedirect("/login?redirectURL=" + request.getRequestURI());
            return false; //핸들러 실행 허용X
        }

        return true; //핸들러 실행 허용
    }
}
