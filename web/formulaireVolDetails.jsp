<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="models.Vol" %>
<%@ page import="models.TypeSiege" %>
<%@ page import="models.Categorie" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Ajouter Détails Vol</title>
    <style>
     body {
            font-family: Arial, sans-serif;
            background-color: #f4f4f4;
            margin: 0;
            padding: 20px;
        }
        h1 {
            text-align: center;
            color: #333;
        }
        .container {
            max-width: 600px;
            margin: auto;
            background: #fff;
            padding: 20px;
            border-radius: 8px;
            box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
        }
        .message {
            padding: 15px;
            margin: 20px 0;
            border-radius: 5px;
            text-align: center;
        }
        .error { background-color: #ffebee; color: #b71c1c; }
        .success { background-color: #e8f5e9; color: #2e7d32; }
        label {
            display: block;
            margin: 10px 0 5px;
            font-weight: bold;
        }
        select, input[type="number"], input[type="submit"] {
            width: 100%;
            padding: 10px;
            margin-bottom: 20px;
            border: 1px solid #ccc;
            border-radius: 4px;
        }
        input[type="submit"] {
            background-color: #28a745;
            color: white;
            border: none;
            cursor: pointer;
        }
        input[type="submit"]:hover {
            background-color: #218838;
        }
    </style>
    <script>
        function validateForm() {
            const siege = document.getElementsByName("siege")[0].value;
            const categorie = document.getElementsByName("categorie")[0].value;
            const nombre = document.getElementsByName("nombreDisponible")[0].value;
            
            if (!siege || !categorie || !nombre) {
                alert("Veuillez remplir tous les champs !");
                return false;
            }
            return true;
        }
    </script>
</head>
<body>
    <h1>Ajouter des détails pour un vol</h1>
    
    <%-- Affichage des messages --%>
    <% if (request.getAttribute("error") != null) { %>
        <div class="message error">${requestScope.error}</div>
    <% } %>
    <% if (request.getAttribute("success") != null) { %>
        <div class="message success">${requestScope.success}</div>
    <% } %>

    <form action="insertVolDetails" method="post" onsubmit="return validateForm()">
        <label for="vol">Vol :</label>
        <select id="vol" name="vol" required>
            <option value="">Sélectionnez un vol</option>
            <% List<Vol> vols = (List<Vol>) request.getAttribute("vols");
               if (vols != null) {
                   for (Vol vol : vols) { %>
                       <option value="<%= vol.getId() %>"><%= vol.getNom() %></option>
            <%     }
               } %>
        </select>

        <div>
            <label>Type de siège :</label>
            <select name="siegeType" required> <!-- Changement de nom -->
                <option value="">Sélectionnez un type</option>
                <% List<TypeSiege> sieges = (List<TypeSiege>) request.getAttribute("sieges");
                   for (TypeSiege type : sieges) { %>
                       <option value="<%= type.getId() %>"><%= type.getTypeNom() %></option>
                <% } %>
            </select>
            
            <label>Catégorie :</label>
            <select name="categorie" required>
                <option value="">Sélectionnez une catégorie</option>
                <% List<Categorie> categories = (List<Categorie>) request.getAttribute("categories");
                   if (categories != null) {
                       for (Categorie categorie : categories) { %>
                           <option value="<%= categorie.getId() %>"><%= categorie.getNom() %></option>
                <%     }
                   } %>
            </select>
            
            <label>Nombre disponible :</label>
            <input type="number" name="nombreDisponible" min="1" required>
        </div>

        <input type="submit" value="Enregistrer">
    </form>
</body>
</html>