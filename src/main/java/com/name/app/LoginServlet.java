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
        out.println("<head>");
        out.println("  <meta charset='UTF-8'>");
        out.println("  <title>התחברות</title>");
        out.println("  <style>");
        out.println("    body { background-color: #f0f8ff; font-family: Arial, sans-serif; text-align: center; margin-top: 40px; }");
        out.println("    h2 { color: blue; font-size: 26px; }");
        out.println("    .calculator-img { width: 70px; height: auto; margin-bottom: 10px; }");
        out.println("    button, a { background-color: #007bff; color: white; padding: 8px 15px; text-decoration: none; border: none; border-radius: 4px; font-family: Arial; font-size: 14px; cursor: pointer; display: inline-block; margin: 5px; }");
        out.println("    button:hover, a:hover { background-color: #0056b3; }");
        out.println("    input[type='text'], input[type='password'] { padding: 6px; margin: 5px; border: 1px solid #ccc; border-radius: 4px; }");
        out.println("  </style>");
        out.println("</head>");
        out.println("<body>");

        out.println("    <img src='https://cdn.supercoloring.com/coloring/2078557/calculator-coloring-page-sm.webp' alt='מחשבון' class='calculator-img'>");
        out.println("  <h2>התחברות למערכת</h2>");
        out.println("  <form action='login' method='POST'>");
        out.println("    שם משתמש: <input type='text' name='username' required><br><br>");
        out.println("    סיסמה: <input type='password' name='password' required><br><br>");
        out.println("    <button type='submit'>התחבר</button>");
        out.println("  </form>");
        out.println("  <p>אין לך חשבון? <a href='register'>הירשם כאן</a></p>");
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
                response.setContentType("text/html; charset=UTF-8");
                PrintWriter out = response.getWriter();
                out.println("<!DOCTYPE html>");
                out.println("<html dir='rtl' lang='he'>");
                out.println("<head><meta charset='UTF-8'><title>שגיאת התחברות</title>");
                out.println("<style>");
                out.println("  body { background-color: #f0f8ff; font-family: Arial, sans-serif; text-align: center; margin-top: 50px; }");
                out.println("  a { background-color: #007bff; color: white; padding: 6px 12px; text-decoration: none; border-radius: 4px; display: inline-block; margin-top: 10px; }");
                out.println("</style>");
                out.println("</head>");
                out.println("<body>");
                out.println("  <h3 style='color:red;'>שם משתמש או סיסמה שגויים.</h3>");
                out.println("  <a href='login'>נסה שוב</a>");
                out.println("</body></html>");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}