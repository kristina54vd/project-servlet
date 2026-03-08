package com.tictactoe;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet(name = "InitServlet", value = "/start")
public class InitServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
        throws ServletException, IOException {
        HttpSession session = req.getSession(true);
        Field field = new Field();

        Map <Integer,Sign> fileData = field.getField();
        List<Sign> data = field.getFieldData();

        session.setAttribute("field",field);
        session.setAttribute("data",data);

        getServletContext().getRequestDispatcher("/index.jsp").forward(req,resp);

    }

}
