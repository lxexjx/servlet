package hello.servlet.web.frontcontroller.v2;

import hello.servlet.web.frontcontroller.MyView;
import hello.servlet.web.frontcontroller.v2.controller.MemberFormControllerV2;
import hello.servlet.web.frontcontroller.v2.controller.MemberSaveControllerV2;
import hello.servlet.web.frontcontroller.v2.controller.MemberListControllerV2;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@WebServlet(name = "frontControllerServletV2", urlPatterns = "/front-controller/v2/*")
public class FrontControllerServletV2 extends HttpServlet {

    private Map<String, ControllerV2> controllerMap = new HashMap<>();

    public FrontControllerServletV2() {
        controllerMap.put("front-controller/v2/members/new-form",new MemberFormControllerV2());
        controllerMap.put("front-controller/v2/members/save",new MemberSaveControllerV2());
        controllerMap.put("front-controller/v2/members",new MemberListControllerV2());
    }

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("FrontControllerServletV2.service");

        //front-controller/v2/members->new MemberListControllerV1가 꺼내져
        String requestURI = req.getRequestURI();    //주소를 얻어낼 수 있음

        //ControllerV1 controller = MemberListControllerV1  다형성
        ControllerV2 controller = controllerMap.get(requestURI);        //호출되면 그 객체를 반환헤
        if (controller == null) {
            resp.setStatus(HttpServletResponse.SC_NOT_FOUND);   //not found  404error
            return;
        }
        MyView view = controller.process(req, resp);
        view.render(req, resp);
    }
}
