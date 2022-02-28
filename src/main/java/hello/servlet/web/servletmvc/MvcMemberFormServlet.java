package hello.servlet.web.servletmvc;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "mvcMemberFormServlet", urlPatterns = "/servelt-mvc/members/new-form")
public class MvcMemberFormServlet extends HttpServlet {
    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String viewPath="/WEB-INF/views/new-form.jsp";
        RequestDispatcher dispatcher=req.getRequestDispatcher(viewPath);  //controller에서 view로 이동
        dispatcher.forward(req,resp);   //=>서버끼리 내부에서 호출,클라이언트를 왔다갔다 하는게 아냐(redirect가 아님)
    }
    //WEB-INF:컨트롤러를 거쳐서 불러지길 바람. 외부에서 불러지는게 아닐 때 이 경로에 넣어줘
    //redirect:다시 서버에 요청
}
