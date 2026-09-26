<%@ page contentType="text/html;charset=UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>Home</title>
    <style>
        body { font-family: Arial, sans-serif; background: #f4f4f4; margin: 0; }
        .topbar { background: #2f6feb; color: #fff; padding: 14px 24px; display: flex; justify-content: space-between; align-items: center; }
        .topbar a { color: #fff; text-decoration: none; font-size: 14px; }
        .content { padding: 30px; }
        nav ul { list-style: none; padding: 0; }
        nav li { margin-bottom: 10px; }
        nav a { text-decoration: none; color: #2f6feb; font-size: 16px; }
        nav a:hover { text-decoration: underline; }
    </style>
</head>
<body>

    <div class="topbar">
        <span>Welcome, <%= session.getAttribute("username") %> (<%= session.getAttribute("role") %>)</span>
        <a href="${pageContext.request.contextPath}/logout">Log out</a>
    </div>
    <div class="content">
        <h1>Home</h1>
        <nav>
            <ul>
                <li><a href="${pageContext.request.contextPath}/messi">Messi</a></li>
                <li><a href="${pageContext.request.contextPath}/ronaldo">Ronaldo</a></li>
                <% if ("admin".equals(session.getAttribute("role"))) { %>
                <li><a href="${pageContext.request.contextPath}/history">Login History (admin)</a></li>
                <% } %>
            </ul>
        </nav>
    </div>
</body>
</html>
