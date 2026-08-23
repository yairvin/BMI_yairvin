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

@WebServlet("/bmi")
public class BmiCalculatorServlet extends HttpServlet {

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

        String weightParam = request.getParameter("weight");
        String heightParam = request.getParameter("height");

        out.println("<!DOCTYPE html>");
        out.println("<html dir='rtl' lang='he'>");
        out.println("  <head>");
        out.println("    <meta charset='UTF-8'>");
        out.println("    <title>מחשבון BMI</title>");
        out.println("    <style>");
        out.println("      body { font-family: Arial, sans-serif; margin: 40px; }");
        out.println("      .form-group { margin-bottom: 15px; }");
        out.println("      label { display: inline-block; width: 100px; }");
        out.println("      .result { margin-top: 20px; padding: 15px; border: 1px solid #ccc; width: 300px; }");
        out.println("    </style>");
        out.println("  </head>");
        out.println("  <body>");
        out.println("    <h1>מחשבון BMI</h1>");


        out.println("    <div style='margin-bottom: 20px;'>");
        out.println("      <a href='bmi' style='margin-left: 15px;'>מחשבון BMI</a>");
        out.println("      <a href='weight-average' style='margin-left: 15px;'>ממוצע משקל</a>");
        out.println("      <a href='bmi-average'>ממוצע BMI</a>");
        out.println("    </div>");

        out.println("    <form action='bmi' method='GET'>");
        out.println("      <div class='form-group'>");
        out.println("        <label for='weight'>משקל (ק\"ג):</label>");
        out.println("        <input type='number' step='0.1' id='weight' name='weight' required>");
        out.println("      </div>");
        out.println("      <div class='form-group'>");
        out.println("        <label for='height'>גובה (ס\"מ):</label>");
        out.println("        <input type='number' step='0.1' id='height' name='height' required>");
        out.println("      </div>");
        out.println("      <button type='submit'>חשב BMI</button>");
        out.println("    </form>");

        if (weightParam != null && heightParam != null) {
            try {
                double weight = Double.parseDouble(weightParam);
                double heightCm = Double.parseDouble(heightParam);
                double heightM = heightCm / 100.0;
                double bmi = weight / (heightM * heightM);
                String category;
                if (bmi < 18.5) {
                    category = "תת-משקל";
                } else if (bmi < 25.0) {
                    category = "משקל תקין";
                } else if (bmi < 30.0) {
                    category = "עודף משקל";
                } else {
                    category = "השמנה";
                }
                String sql = "INSERT INTO bmi_results (weight, height, bmi_value, category) VALUES (?, ?, ?, ?)";

                try (Connection conn = DatabaseManager.getConnection();
                     PreparedStatement pstmt = conn.prepareStatement(sql)) {

                    pstmt.setDouble(1, weight);
                    pstmt.setDouble(2, heightCm);
                    pstmt.setDouble(3, bmi);
                    pstmt.setString(4, category);

                    pstmt.executeUpdate();

                } catch (SQLException e) {
                    e.printStackTrace();
                }
                out.println("    <div class='result'>");
                out.println("      <h3>תוצאה:</h3>");
                out.println("      <p>תוצאת ה-BMI שלך: <strong>" + String.format("%.2f", bmi) + "</strong></p>");
                out.println("      <p>הגדרת קטגוריה: <strong>" + category + "</strong></p>");
                out.println("    </div>");
            } catch (NumberFormatException e) {
                out.println("    <p style='color:red;'>נא להזין מספרים תקינים.</p>");
            }
        }
        out.println("  </body>");
        out.println("</html>");
    }
}