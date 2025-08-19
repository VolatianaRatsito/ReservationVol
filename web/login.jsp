<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="java.util.*" %>
<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Connexion</title>
    <style>
        * { margin: 0; padding: 0; box-sizing: border-box; font-family: Arial, sans-serif; }
        body {
            display: flex; justify-content: center; align-items: center;
            height: 100vh; background: url('img/Savony.png') center/cover no-repeat;
        }
        .login-container {
            background: rgba(255, 255, 255, 0.9); padding: 30px; border-radius: 8px;
            box-shadow: 0 4px 15px rgba(0, 0, 0, 0.2); width: 350px; text-align: center;
        }
        h2 { margin-bottom: 20px; color: #FBAE17; }
        label { display: block; text-align: left; margin: 5px 0; font-weight: bold; }
        input { width: 100%; padding: 10px; margin-bottom: 15px; border-radius: 4px; border: 1px solid #ccc; }
        button { width: 100%; padding: 10px; background: #FBAE17; border: none; border-radius: 4px; color: #fff; cursor: pointer; }
        button:hover { background: #e9a210; }
        .error { color: red; font-size: 14px; margin-bottom: 10px; }
    </style>
</head>
<body>
    <div class="login-container">
        <h2>Connexion</h2>
        <% String errorMessage = (String) request.getAttribute("error"); %>
        <% if (errorMessage != null) { %>
            <p class="error"><%= errorMessage %></p>
        <% } %>

        <form action="SubmitForm" method="Post">
            <label for="email">Email</label>
            <input type="email" id="email" name="email" required>
            
            <label for="password">Mot de passe</label>
            <input type="password" id="password" name="password" required>
            
            <button type="submit">Se connecter</button>
        </form>
    </div>
</body>
</html>
