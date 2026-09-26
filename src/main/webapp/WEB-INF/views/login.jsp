<%@ page contentType="text/html;charset=UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>Login</title>
    <style>
        body { font-family: Arial, sans-serif; background: #f4f4f4; display: flex; justify-content: center; align-items: center; height: 100vh; margin: 0; }
        .login-box { background: #fff; padding: 30px 40px; border-radius: 8px; box-shadow: 0 2px 8px rgba(0,0,0,0.15); width: 280px; }
        h1 { font-size: 20px; margin-top: 0; }
        label { display: block; margin-top: 12px; margin-bottom: 4px; font-size: 14px; }
        input[type=text], input[type=password] { width: 100%; padding: 8px; box-sizing: border-box; }
        button { margin-top: 18px; width: 100%; padding: 10px; background: #2f6feb; color: #fff; border: none; border-radius: 4px; cursor: pointer; }
        button:hover { background: #2558c0; }
        .error { color: #c0392b; font-size: 13px; margin-top: 10px; }
    </style>
</head>
<body>

    <div class="login-box">
        <h1>Sign In</h1>
        <form method="post" action="${pageContext.request.contextPath}/login">
            <label for="username">Username</label>
            <input type="text" id="username" name="username" required autofocus/>

            <label for="password">Password</label>
            <input type="password" id="password" name="password" required/>

            <button type="submit">Log In</button>

            <% if (request.getAttribute("error") != null) { %>
                <div class="error"><%= request.getAttribute("error") %></div>
            <% } %>
        </form>
    </div>
</body>
</html>
