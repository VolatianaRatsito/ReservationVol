<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="java.util.List" %>
<%@ page import="models.Avion" %>
<%@ page import="models.VilleDesservie" %>
<%@ page import="tool.MySession" %>
<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Insérer un Vol</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            background-color: #f4f4f4;
            margin: 0;
            padding: 0;
        }
        .container {
            width: 80%;
            margin: auto;
            overflow: hidden;
        }
        h1 {
            text-align: center;
            color: #333;
        }
        .alert {
            padding: 10px;
            margin: 15px 0;
            border-radius: 5px;
        }
        .alert-error {
            background-color: #f8d7da;
            color: #721c24;
        }
        .alert-success {
            background-color: #d4edda;
            color: #155724;
        }
        form {
            background: #fff;
            padding: 15px;
            border-radius: 5px;
            box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
        }
        .form-group {
            margin-bottom: 15px;
        }
        label {
            display: block;
            margin-bottom: 5px;
        }
        input[type="text"],
        input[type="number"],
        input[type="datetime-local"],
        select {
            width: 100%;
            padding: 8px;
            border: 1px solid #ccc;
            border-radius: 4px;
        }
        button {
            background-color: #5cb85c;
            color: white;
            border: none;
            padding: 10px 15px;
            border-radius: 5px;
            cursor: pointer;
        }
        button:hover {
            background-color: #4cae4c;
        }
    </style>
</head>
<body>
    <div class="container">
        <h1>Insérer un Nouveau Vol</h1>
        
        <form action="insertVol" method="post">
            <div class="form-group">
                <label for="nom">Nom du Vol :</label>
                <input type="text" id="nom" name="nom" required>
            </div>
            <div class="form-group">
                <label for="idAvion">Avion :</label>
                <select id="idAvion" name="idAvion" required>
                    <option value="">Sélectionnez un Avion</option>
                    <%
                        List<Avion> avions = (List<Avion>) request.getAttribute("avions");
                        if (avions != null) {
                            for (Avion avion : avions) {
                    %>
                        <option value="<%= avion.getId() %>"><%= avion.getNom() %></option>
                    <%
                            }
                        }
                    %>
                </select>
            </div>
            <div class="form-group">
                <label for="villeDepart">Ville de Départ :</label>
                <select id="villeDepart" name="villeDepart" required>
                    <option value="">Sélectionnez une Ville de Départ</option>
                    <%
                        List<VilleDesservie> villes = (List<VilleDesservie>) request.getAttribute("villes");
                        if (villes != null) {
                            for (VilleDesservie ville : villes) {
                    %>
                        <option value="<%= ville.getId() %>"><%= ville.getVille() %></option>
                    <%
                            }
                        }
                    %>
                </select>
            </div>
            <div class="form-group">
                <label for="villeArriver">Ville d'Arrivée :</label>
                <select id="villeArriver" name="villeArriver" required>
                    <option value="">Sélectionnez une Ville d'Arrivée</option>
                    <%
                        if (villes != null) {
                            for (VilleDesservie ville : villes) {
                    %>
                        <option value="<%= ville.getId() %>"><%= ville.getVille() %></option>
                    <%
                            }
                        }
                    %>
                </select>
            </div>
            <div class="form-group">
                <label for="dateDepart">Date de Départ :</label>
                <input type="datetime-local" id="dateDepart" name="dateDepart" required>
            </div>
            <div class="form-group">
                <label for="dateArriver">Date d'Arrivée :</label>
                <input type="datetime-local" id="dateArriver" name="dateArriver" required>
            </div>
            <button type="submit">Insérer le Vol</button>
        </form>
    </div>
</body>
</html>