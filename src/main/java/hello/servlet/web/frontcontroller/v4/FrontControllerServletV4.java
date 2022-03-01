package hello.servlet.web.frontcontroller.v4;

import hello.servlet.web.frontcontroller.ModelView;
import hello.servlet.web.frontcontroller.MyView;
import hello.servlet.web.frontcontroller.v3.ControllerV3;
import hello.servlet.web.frontcontroller.v3.controller.MemberFormControllerV3;
import hello.servlet.web.frontcontroller.v3.controller.MemberListControllerV3;
import hello.servlet.web.frontcontroller.v3.controller.MemberSaveControllerV3;
import hello.servlet.web.frontcontroller.v4.controller.MemberFormControllerV4;
import hello.servlet.web.frontcontroller.v4.controller.MemberListControllerV4;
import hello.servlet.web.frontcontroller.v4.controller.MemberSaveControllerV4;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@WebServlet(name = "frontControllerServletV4", urlPatterns = "/front-controller/v4/*")
public class FrontControllerServletV4 extends HttpServlet {

    private Map<String, ControllerV4> controllerMap = new HashMap<>();

    public FrontControllerServletV4() {
        controllerMap.put("front-controller/v4/members/new-form",new MemberFormControllerV4());
        controllerMap.put("front-controller/v4/members/save",new MemberSaveControllerV4());
        controllerMap.put("front-controller/v4/members",new MemberListControllerV4());
    }

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("FrontControllerServletV4.service");

        //front-controller/v2/members->new MemberListControllerV1가 꺼내져
        String requestURI = req.getRequestURI();    //주소를 얻어낼 수 있음

        //ControllerV1 controller = MemberListControllerV1  다형성
        ControllerV4 controller = controllerMap.get(requestURI);        //호출되면 그 객체를 반환헤
        if (controller == null) {
            resp.setStatus(HttpServletResponse.SC_NOT_FOUND);   //not found  404error
            return;
        }
        //paramap을 넘겨줘
        Map<String, String> paramMap = createParamMap(req);
        Map<String, Object> model = new HashMap<>();    //model추가됨

        String viewName= controller.process(paramMap,model);    //view의 논리이름 직접 반환
        MyView view = viewResolver(viewName);
        view.render(model, req, resp);
    }
    private Map<String, String> createParamMap(HttpServletRequest request) {
        Map<String, String> paramMap = new HashMap<>();
        request.getParameterNames().asIterator()
                .forEachRemaining(paramName -> paramMap.put(paramName,
                        request.getParameter(paramName)));
        return paramMap;
    }
    private MyView viewResolver(String viewName) {
        return new MyView("/WEB-INF/views/" + viewName + ".jsp");
    }
}