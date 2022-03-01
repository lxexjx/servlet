package hello.servlet.web.frontcontroller.v1;

import hello.servlet.web.frontcontroller.v1.controller.MemberSaveController;
import hello.servlet.web.frontcontroller.v1.controller.MemberformControllerV1;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@WebServlet(name = "frontControllerServletV1", urlPatterns = "/front-controller/v1/*")
public class FrontControllerServletV1 extends HttpServlet {

    private Map<String, ControllerV1> controllerMap = new HashMap<>();

    public FrontControllerServletV1() {
        controllerMap.put("front-controller/v1/members/new-form",new MemberformControllerV1());
        controllerMap.put("front-controller/v1/members/save",new MemberSaveController());
        controllerMap.put("front-controller/v1/members",new MemberListControllerV1());
    }

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("FrontControllerServletV1.service");

        //front-controller/v1/members->new MemberListControllerV1가 꺼내져
        String requestURI = req.getRequestURI();    //주소를 얻어낼 수 있음

        //ControllerV1 controller = MemberListControllerV1  다형성
        ControllerV1 controller = controllerMap.get(requestURI);        //호출되면 그 객체를 반환헤
        if (controller == null) {
            resp.setStatus(HttpServletResponse.SC_NOT_FOUND);   //not found  404error
            return;
        }
        controller.process(req, resp);  //인터페이스 호출
    }
}
