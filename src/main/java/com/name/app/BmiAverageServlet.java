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
import java.sql.ResultSet;
import java.sql.SQLException;

import com.name.app.db.DatabaseManager;

@WebServlet("/bmi-average")
public class BmiAverageServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        javax.servlet.http.HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("user") == null) {
            response.sendRedirect("login");
            return;
        }

        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();

        double avgBmi = 0.0;
        String sql = "SELECT AVG(bmi_value) AS average_bmi FROM bmi_results";

        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {

            if (rs.next()) {
                avgBmi = rs.getDouble("average_bmi");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        out.println("<!DOCTYPE html>");
        out.println("<html dir='rtl' lang='he'>");
        out.println("<head>");
        out.println("  <meta charset='UTF-8'>");
        out.println("  <title>ממוצע BMI</title>");
        out.println("  <style>");
        out.println("    body { background-color: #f0f8ff; font-family: Arial, sans-serif; text-align: center; margin-top: 40px; }");
        out.println("    h1 { color: blue; font-size: 32px; }");
        out.println("    .calculator-img { width: 70px; height: auto; margin-bottom: 10px; }");
        out.println("    .result-box { margin: 20px auto; padding: 15px; border: 1px solid #ccc; width: 300px; background-color: white; border-radius: 6px; }");
        out.println("    a { background-color: #007bff; color: white; padding: 8px 15px; text-decoration: none; border-radius: 4px; font-family: Arial; font-size: 14px; display: inline-block; margin: 5px; }");
        out.println("    a:hover { background-color: #0056b3; }");
        out.println("  </style>");
        out.println("</head>");
        out.println("<body>");

        out.println("    <img src='https://cdn.supercoloring.com/coloring/2078557/calculator-coloring-page-sm.webp' alt='מחשבון' class='calculator-img'>");
        out.println("    <div style='margin-bottom: 20px;'>");
        out.println("      <a href='bmi'>מחשבון BMI</a>");
        out.println("      <a href='weight-average'>ממוצע משקל</a>");
        out.println("      <a href='bmi-average'>ממוצע BMI</a>");
        out.println("    </div>");
        out.println("    <h1>ממוצע ה-BMI במערכת</h1>");
        out.println("    <div class='result-box'>");
        out.println("      <p style=\"white-space: nowrap;\">ממוצע ה-BMI של כלל המשתמשים הוא: <strong>" + String.format("%.2f", avgBmi) + "</strong></p>");
        out.println("    </div>");
        out.println("</body></html>");
    }
}