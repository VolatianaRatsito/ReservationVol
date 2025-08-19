<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="models.Vol" %>
<%@ page import="models.AvionDetailler" %>
<%@ page import="java.util.List" %>
<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Ajouter Promotion</title>
    <style>
        body {
            margin: 0;
            font-family: 'Arial', sans-serif;
            background: url('Vols/vols.jpeg') no-repeat center center fixed;
            background-size: cover;
            display: flex;
            justify-content: center;
            align-items: center;
            height: 100vh;
        }

        .container {
            background: rgba(0, 0, 0, 0.8);
            padding: 40px;
            border-radius: 15px;
            box-shadow: 0 0 20px rgba(0, 0, 0, 0.5);
            max-width: 500px;
            width: 90%;
            animation: fadeIn 0.5s;
        }

        @keyframes fadeIn {
            from { opacity: 0; }
            to { opacity: 1; }
        }

        h1 {
            color: #ffffff;
            margin-bottom: 20px;
            text-align: center;
        }

        label {
            color: #ffffff;
            margin-top: 10px;
        }

        input, select {
            width: 100%;
            padding: 10px;
            margin: 5px 0 20px;
            border: none;
            border-radius: 5px;
        }

        button {
            width: 100%;
            padding: 10px;
            background-color: #ff4757;
            color: white;
            border: none;
            border-radius: 5px;
            cursor: pointer;
            transition: background 0.3s;
        }

        button:hover {
            background-color: #ff6b81;
        }
    </style>
</head>
<body>
    <div class="container">
        <h1>Ajouter une Promotion</h1>
        <form action="add" method="POST">
            <label for="datePromotion">Date de Promotion</label>
            <input type="date" id="datePromotion" name="datePromotion" required>
        
            <label for="dateExpiration">Date d'Expiration</label>
            <input type="date" id="dateExpiration" name="dateExpiration" required>
        
            <label for="volId">Sélectionner le Vol</label>
            <select id="volId" name="volId" required>
                <%
                    List<Vol> vols = (List<Vol>) request.getAttribute("vols");
                    for (Vol vol : vols) {
                %>
                    <option value="<%= vol.getId() %>"><%= vol.getNom() %></option>
                <%
                    }
                %>
            </select>
        
            <!-- Modification ici : AvionDetailler au lieu de TypeSiege -->
            <label for="avionDetaillerId">Sélectionner la Configuration Siège</label>
            <select id="avionDetaillerId" name="avionDetaillerId" required>
                <%
                    List<AvionDetailler> avionDetaillers = (List<AvionDetailler>) request.getAttribute("avionDetaillers");
                    for (AvionDetailler ad : avionDetaillers) {
                        String optionText = ad.getAvion().getNom() + " - " + 
                                           ad.getTypeSiege().getTypeNom() + " (" + 
                                           ad.getNombre_siege() + " sièges)";
                %>
                    <option value="<%= ad.getId() %>"><%= optionText %></option>
                <%
                    }
                %>
            </select>
        
            <!-- Modification ici : quantite au lieu de nombre -->
            <label for="quantite">Nombre de Sièges</label>
            <input type="number" id="quantite" name="quantite" required min="1">
        
            <!-- Modification ici : pourcentage au lieu de promotion -->
            <label for="pourcentage">Pourcentage de Promotion</label>
            <input type="number" id="pourcentage" name="pourcentage" required min="0" max="100">
        
            <button type="submit">Ajouter Promotion</button>
        </form>
    </div>
</body>
</html>