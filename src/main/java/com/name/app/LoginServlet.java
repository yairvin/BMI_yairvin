package com.name.app;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import com.name.app.db.DatabaseManager;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();

        out.println("<!DOCTYPE html>");
        out.println("<html dir='rtl' lang='he'>");
        out.println("<head><meta charset='UTF-8'><title>התחברות</title></head>");
        out.println("<body>");
        out.println("<h2>התחברות למערכת</h2>");
        out.println("<form action='login' method='POST'>");
        out.println("שם משתמש: <input type='text' name='username' required><br><br>");
        out.println("סיסמה: <input type='password' name='password' required><br><br>");
        out.println("<button type='submit'>התחבר</button>");
        out.println("</form>");
        out.println("<p>אין לך חשבון? <a href='register'>הירשם כאן</a></p>");
        out.println("</body></html>");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        String sql = "SELECT * FROM users WHERE username = ? AND password = ?";

        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, username);
            pstmt.setString(2, password);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                HttpSession session = request.getSession();
                session.setAttribute("user", username);
                response.sendRedirect("bmi");
            } else {
                response.setContentType("text/html; dir='rtl' ;charset=UTF-8");
                PrintWriter out = response.getWriter();
                out.println("<html dir='rtl' lang='he'>");
                out.println("<h3 style='color:red;'>שם משתמש או סיסמה שגויים. <a href='login'>נסה שוב</a></h3>");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}