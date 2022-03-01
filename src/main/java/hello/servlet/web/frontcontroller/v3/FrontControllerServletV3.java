package hello.servlet.web.frontcontroller.v3;

import hello.servlet.web.frontcontroller.ModelView;
import hello.servlet.web.frontcontroller.MyView;
import hello.servlet.web.frontcontroller.v3.ControllerV3;
import hello.servlet.web.frontcontroller.v3.controller.MemberFormControllerV3;
import hello.servlet.web.frontcontroller.v3.controller.MemberListControllerV3;
import hello.servlet.web.frontcontroller.v3.controller.MemberSaveControllerV3;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@WebServlet(name = "frontControllerServletV3", urlPatterns = "/front-controller/v3/*")
public class FrontControllerServletV3 extends HttpServlet {

    private Map<String, ControllerV3> controllerMap = new HashMap<>();

    public FrontControllerServletV3() {
        controllerMap.put("front-controller/v2/members/new-form",new MemberFormControllerV3());
        controllerMap.put("front-controller/v2/members/save",new MemberSaveControllerV3());
        controllerMap.put("front-controller/v2/members",new MemberListControllerV3());
    }

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("FrontControllerServletV3.service");

        //front-controller/v2/members->new MemberListControllerV1가 꺼내져
        String requestURI = req.getRequestURI();    //주소를 얻어낼 수 있음

        //ControllerV1 controller = MemberListControllerV1  다형성
        ControllerV3 controller = controllerMap.get(requestURI);        //호출되면 그 객체를 반환헤
        if (controller == null) {
            resp.setStatus(HttpServletResponse.SC_NOT_FOUND);   //not found  404error
            return;
        }
        //paramap을 넘겨줘
        Map<String, String> paramMap = createParamMap(req);
        ModelView mv = controller.process(paramMap);

        String viewName = mv.getViewName(); //논리이름 new-form으로 반환
        MyView view = viewResolver(viewName);

        view.render(mv.getModel(), req, resp);
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