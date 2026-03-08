package com.tictactoe;

import java.io.IOException;
import java.util.List;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet(name = "LogicServlet", value = "/logic")
public class LogicServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
        throws ServletException, IOException {
        HttpSession session = req.getSession();
        Field field = extraFiled(session);

        int index = getSelectedIndex(req);
        Sign currentSign = field.getField().get(index);


        if (Sign.EMPTY != currentSign) {
            RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/index.jsp");
            dispatcher.forward(req, resp);
            return;
        }
        field.getField().put(index, Sign.CROSS);
        if (checkWin(resp, field, session)){
            return;
        }

        int emptyFieldIndex = field.getEmptyFieldIndex();
        if (emptyFieldIndex >= 0) {
            field.getField().put(emptyFieldIndex, Sign.NOUGHT);
            if (checkWin(resp, field, session)){
                return;
            }

        }else {
            session.setAttribute("draw",true);
            List<Sign> data = field.getFieldData();
            session.setAttribute("data",data);

            resp.sendRedirect("/index.jsp");
            return;
        }


        List<Sign> data = field.getFieldData();

        session.setAttribute("data", data);
        session.setAttribute("field", field);

        resp.sendRedirect("/index.jsp");
    }

    private int getSelectedIndex(HttpServletRequest request) {
        String click = request.getParameter("click");
        boolean isNumeric = click.chars().allMatch(Character::isDigit);
        return isNumeric ? Integer.parseInt(click) : 0;
    }

    private Field extraFiled(HttpSession ses) {
        Object fieldObject = ses.getAttribute("field");
        if (Field.class != fieldObject.getClass()) {
            ses.invalidate();
            throw new RuntimeException("Сессия прервана");
        }
        return (Field) fieldObject;
    }

    private boolean checkWin(HttpServletResponse response,Field field,HttpSession currentSession)
        throws IOException {
      Sign winner = field.checkWin();
      if (Sign.CROSS == winner || Sign.NOUGHT == winner){
          currentSession.setAttribute("winner",winner);

          List<Sign> data = field.getFieldData();
          currentSession.setAttribute("data",data);

          response.sendRedirect("/index.jsp");
          return  true;

      }
      return false;

    }

}
