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
        out.println("<head>");
        out.println("  <meta charset='UTF-8'>");
        out.println("  <title>הרשמה</title>");
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
        out.println("  <h2>הרשמה למערכת</h2>");
        out.println("  <form action='register' method='POST'>");
        out.println("    שם משתמש: <input type='text' name='username' required><br><br>");
        out.println("    סיסמה: <input type='password' name='password' required><br><br>");
        out.println("    <button type='submit'>הירשם</button>");
        out.println("  </form>");
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

            out.println("<!DOCTYPE html>");
            out.println("<html dir='rtl' lang='he'>");
            out.println("<head><meta charset='UTF-8'><title>הצלחה בהרשמה</title>");
            out.println("<style>");
            out.println("  body { background-color: #f0f8ff; font-family: Arial, sans-serif; text-align: center; margin-top: 50px; }");
            out.println("  a { background-color: #007bff; color: white; padding: 6px 12px; text-decoration: none; border-radius: 4px; display: inline-block; margin-top: 10px; }");
            out.println("</style></head>");
            out.println("<body>");
            out.println("  <img src='https://cdn-icons-png.flaticon.com/512/564/564443.png' alt='מחשבון' style='width: 70px; height: auto; margin-bottom: 10px;'>");
            out.println("  <h3 style='color:green;'>נרשמת בהצלחה!</h3>");
            out.println("  <a href='login'>התחבר כאן</a>");
            out.println("</body></html>");

        } catch (SQLException e) {
            e.printStackTrace();
            out.println("<!DOCTYPE html>");
            out.println("<html dir='rtl' lang='he'>");
            out.println("<head><meta charset='UTF-8'><title>שגיאת הרשמה</title>");
            out.println("<style>");
            out.println("  body { background-color: #f0f8ff; font-family: Arial, sans-serif; text-align: center; margin-top: 50px; }");
            out.println("  a { background-color: #007bff; color: white; padding: 6px 12px; text-decoration: none; border-radius: 4px; display: inline-block; margin-top: 10px; }");
            out.println("</style></head>");
            out.println("<body>");
            out.println("  <img src='https://cdn-icons-png.flaticon.com/512/564/564443.png' alt='מחשבון' style='width: 70px; height: auto; margin-bottom: 10px;'>");
            out.println("  <h3 style='color:red;'>שגיאה: שם המשתמש כנראה כבר תפוס.</h3>");
            out.println("  <a href='register'>נסה שוב</a>");
            out.println("</body></html>");
        }
    }
}