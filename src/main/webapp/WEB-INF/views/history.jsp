<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="java.util.List" %>
<%@ page import="com.name.app.HistoryServlet.LoginRecord" %>
<!DOCTYPE html>
<html>
<head>
    <title>Login History</title>
    <style>
        body { font-family: Arial, sans-serif; background: #f4f4f4; margin: 0; }
        .topbar { background: #2f6feb; color: #fff; padding: 14px 24px; display: flex; justify-content: space-between; align-items: center; }
        .topbar a { color: #fff; text-decoration: none; font-size: 14px; margin-left: 16px; }
        .content { padding: 30px; }
        table { border-collapse: collapse; width: 100%; max-width: 600px; background: #fff; }
        th, td { text-align: left; padding: 8px 12px; border-bottom: 1px solid #ddd; }
        th { background: #eaeaea; }
        .role-admin { color: #c0392b; font-weight: bold; }
        .role-user { color: #2f6feb; }
    </style>
</head>
<body>

    <div class="topbar">
        <span>Welcome, <%= session.getAttribute("username") %> (admin)</span>
        <span>
            <a href="${pageContext.request.contextPath}/home">Home</a>
            <a href="${pageContext.request.contextPath}/logout">Log out</a>
        </span>
    </div>
    <div class="content">
        <h1>Login History</h1>
        <%
            List<LoginRecord> records = (List<LoginRecord>) request.getAttribute("records");
        %>
        <table>
            <tr>
                <th>Username</th>
                <th>Role</th>
                <th>Login Time</th>
            </tr>
            <% for (LoginRecord r : records) { %>
            <tr>
                <td><%= r.username %></td>
                <td class="role-<%= r.role %>"><%= r.role %></td>
                <td><%= r.loginTime %></td>
            </tr>
            <% } %>
        </table>
        <p><a href="${pageContext.request.contextPath}/home">&larr; Back to menu</a></p>
    </div>
</body>
</html>
