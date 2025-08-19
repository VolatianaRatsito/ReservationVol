<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="models.Avion" %>
<%@ page import="models.VilleDesservie" %>
<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Recherche de Vols</title>
    <style>
        /* Styles globaux */
        body {
            font-family: 'Arial', sans-serif;
            background-color: #e0f7fa; /* Couleur ciel */
            margin: 0;
            padding: 0;
            color: #333;
        }

        .container {
            width: 85%;
            max-width: 1200px;
            margin: auto;
            padding: 20px;
            background: #ffffff;
            border-radius: 8px;
            box-shadow: 0 4px 8px rgba(0, 0, 0, 0.2);
        }

        /* Titres */
        h1, h2 {
            color: #00796b; /* Couleur vert aéroport */
            text-align: center;
        }

        /* Alertes */
        .alert {
            padding: 10px;
            margin: 15px 0;
            border-radius: 5px;
        }

        .alert-error {
            background-color: #f44336; /* Rouge */
            color: white;
        }

        /* Formulaire */
        form {
            background: #ffffff;
            padding: 20px;
            border-radius: 5px;
            box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
            margin-bottom: 20px;
        }

        .form-group {
            margin-bottom: 15px;
        }

        label {
            display: block;
            margin-bottom: 5px;
            font-weight: bold;
        }

        input[type="text"],
        input[type="datetime-local"],
        select {
            width: 100%;
            padding: 10px;
            border: 1px solid #ccc;
            border-radius: 4px;
            transition: border 0.3s;
        }

        input[type="text"]:focus,
        input[type="datetime-local"]:focus,
        select:focus {
            border-color: #00796b; /* Vert aéroport */
        }

        /* Boutons */
        button {
            background-color: #00796b; /* Vert aéroport */
            color: white;
            border: none;
            padding: 10px 15px;
            border-radius: 5px;
            cursor: pointer;
            transition: background 0.3s;
        }

        button:hover {
            background-color: #004d40; /* Vert plus foncé */
        }

        /* Responsive Design */
        @media (max-width: 768px) {
            .container {
                width: 95%;
            }

            h1, h2 {
                font-size: 1.5em;
            }

            button {
                width: 100%;
            }
        }
    </style>
</head>
<body>
    <div class="container">
        <h1>Recherche de Vols</h1>
        <%
            String errorMessage = (String) request.getAttribute("errorMessage");
            if (errorMessage != null) {
        %>
            <div class="alert alert-error"><%= errorMessage %></div>
        <%
            }
        %>

        <form action="searchVols" method="post">
            <div class="form-group">
                <label for="ville">Ville :</label>
                <select id="ville" name="ville" required>
                    <option value="">Sélectionnez une ville</option>
                    <%
                        List<VilleDesservie> villes = (List<VilleDesservie>) request.getAttribute("villes");
                        for (VilleDesservie ville : villes) {
                    %>
                        <option value="<%= ville.getId() %>"><%= ville.getVille() %></option>
                    <%
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
            <div class="form-group">
                <label for="idAvion">Avion :</label>
                <select id="idAvion" name="idAvion">
                    <option value="0">Tous</option>
                    <%
                        List<Avion> avions = (List<Avion>) request.getAttribute("avions");
                        for (Avion avion : avions) {
                    %>
                        <option value="<%= avion.getId() %>"><%= avion.getNom() %></option>
                    <%
                        }
                    %>
                </select>
            </div>
            <button type="submit">Rechercher</button>
        </form>
    </div>
</body>
</html>