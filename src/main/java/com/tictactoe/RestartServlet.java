package com.tictactoe;

import java.io.IOException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name = "RestartServlet",value = "/restart")
public class RestartServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request,HttpServletResponse response)
        throws IOException {
        request.getSession().invalidate();
        response.sendRedirect("/start");
    }


}
