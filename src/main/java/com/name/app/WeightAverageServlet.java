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

@WebServlet("/weight-average")
public class WeightAverageServlet extends HttpServlet {

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

        double avgWeight = 0.0;
        String sql = "SELECT AVG(weight) AS average_weight FROM bmi_results";

        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {

            if (rs.next()) {
                avgWeight = rs.getDouble("average_weight");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        out.println("<!DOCTYPE html>");
        out.println("<html dir='rtl' lang='he'>");
        out.println("<head><meta charset='UTF-8'><title>ממוצע משקל</title></head>");
        out.println("<body style='font-family: Arial; margin: 40px;'>");
        out.println("    <div style='margin-bottom: 20px;'>");
        out.println("      <a href='bmi' style='margin-left: 15px;'>מחשבון BMI</a>");
        out.println("      <a href='weight-average' style='margin-left: 15px;'>ממוצע משקל</a>");
        out.println("      <a href='bmi-average'>ממוצע BMI</a>");
        out.println("    </div>");

        out.println("    <h1>ממוצע המשקל במערכת</h1>");
        out.println("    <div style='padding: 15px; border: 1px solid #ccc; width: 300px;'>");
        out.println("      <p>ממוצע המשקל של כלל המשתמשים הוא: <strong>" + String.format("%.2f", avgWeight) + " ק\"ג</strong></p>");
        out.println("    </div>");
        out.println("</body></html>");
    }
}