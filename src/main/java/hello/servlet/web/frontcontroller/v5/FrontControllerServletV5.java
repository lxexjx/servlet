package hello.servlet.web.frontcontroller.v5;

import hello.servlet.web.frontcontroller.ModelView;
import hello.servlet.web.frontcontroller.MyView;
import hello.servlet.web.frontcontroller.v3.ControllerV3;
import hello.servlet.web.frontcontroller.v3.controller.MemberFormControllerV3;
import hello.servlet.web.frontcontroller.v3.controller.MemberListControllerV3;
import hello.servlet.web.frontcontroller.v3.controller.MemberSaveControllerV3;
import hello.servlet.web.frontcontroller.v4.controller.MemberFormControllerV4;
import hello.servlet.web.frontcontroller.v4.controller.MemberListControllerV4;
import hello.servlet.web.frontcontroller.v4.controller.MemberSaveControllerV4;
import hello.servlet.web.frontcontroller.v5.adapter.ControllerV3HandlerAdapter;
import hello.servlet.web.frontcontroller.v5.adapter.ControllerV4HandlerAdapter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Handler;

@WebServlet(name = "frontControllerServletV5", urlPatterns = "/front-controller/v5/*")
public class FrontControllerServletV5 extends HttpServlet {
    private final Map<String,Object> handlerMappingMap=new HashMap<>(); //특정이 아닌 다 집어넣을 수 있기 위해 Object를 사용
    private final List<MyHandlerAdapter> handlerAdapters=new ArrayList<>();

    public FrontControllerServletV5() {
        initHandlerMappingMap();
        iniHandlerAdapters();
    }

    private void initHandlerMappingMap() {
        handlerMappingMap.put("front-controller/v5/v3/members/new-form",new MemberFormControllerV3());
        handlerMappingMap.put("front-controller/v5/v3/members/save",new MemberSaveControllerV3());
        handlerMappingMap.put("front-controller/v5/v3/members",new MemberListControllerV3());

        //V4추가
        handlerMappingMap.put("front-controller/v5/v4/members/new-form",new MemberFormControllerV4());
        handlerMappingMap.put("front-controller/v5/v4/members/save",new MemberSaveControllerV4());
        handlerMappingMap.put("front-controller/v5/v4/members",new MemberListControllerV4());

    }
    private void iniHandlerAdapters(){
        handlerAdapters.add(new ControllerV3HandlerAdapter());
        handlerAdapters.add(new ControllerV4HandlerAdapter());
    }

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //MemberFromControllerV3가 반환됨
        Object handler= getHandler(req);    //handler호출->v3처리 가능한 헨들러 어뎁터를 찾아와
        if (handler == null) {
            resp.setStatus(HttpServletResponse.SC_NOT_FOUND);   //not found  404error
            return;
        }

        //ControllerV3HandlerAdapter가 반환
        MyHandlerAdapter adapter= getHandlerAdapter(handler);   //handler adapter를 가져와
        ModelView mv=adapter.handle(req,resp,handler);  //handle호출_>v3로 바꾸고 adapter도 modelview를 바꿔

        String viewName = mv.getViewName(); //논리이름 new-form으로 반환
        MyView view = viewResolver(viewName);   //viewResolver호출

        view.render(mv.getModel(), req, resp);
        }

        private Object getHandler(HttpServletRequest request){
            String requestURI = request.getRequestURI();
            return handlerMappingMap.get(requestURI);
        }
        private MyHandlerAdapter getHandlerAdapter(Object handler) {
            for (MyHandlerAdapter adapter : handlerAdapters) {
                if (adapter.supports(handler)) {
                    return adapter;
                }
            }
            throw new IllegalArgumentException("handler adapter를 찾을 수 없습니다. handler="+handler);
        }
    private MyView viewResolver(String viewName) {
        return new MyView("/WEB-INF/views/" + viewName + ".jsp");
    }
}
