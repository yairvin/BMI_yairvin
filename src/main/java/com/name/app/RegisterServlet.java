package com.name.app;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import com.name.app.db.DatabaseManager;

@WebServlet("/register")
public class RegisterServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();

        out.println("<!DOCTYPE html>");
        out.println("<html dir='rtl' lang='he'>");
        out.println("<head><meta charset='UTF-8'><title>הרשמה</title></head>");
        out.println("<body>");
        out.println("<h2>הרשמה למערכת</h2>");
        out.println("<form action='register' method='POST'>");
        out.println("שם משתמש: <input type='text' name='username' required><br><br>");
        out.println("סיסמה: <input type='password' name='password' required><br><br>");
        out.println("<button type='submit'>הירשם</button>");
        out.println("</form>");
        out.println("</body></html>");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();

        String sql = "INSERT INTO users (username, password) VALUES (?, ?)";

        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, username);
            pstmt.setString(2, password);
            pstmt.executeUpdate();

            out.println("<html dir='rtl' lang='he'>");
            out.println("<h3>נרשמת בהצלחה! <a href='login'>התחבר כאן</a></h3>");

        } catch (SQLException e) {
            e.printStackTrace();
            out.println("<html dir='rtl' lang='he'>");
            out.println("<h3 style='color:red;'>שגיאה: שם המשתמש כנראה כבר תפוס.</h3>");
        }
    }
}

