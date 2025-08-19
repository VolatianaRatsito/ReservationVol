<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="models.VolDetails" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Confirmation des Détails du Vol</title>
    <style>
        /* Styles généraux */
        body {
            font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
            margin: 0;
            background-color: #f5f5f5;
        }

        /* Conteneur principal */
        .confirmation-container {
            max-width: 800px;
            margin: 50px auto;
            padding: 30px;
            background: white;
            border-radius: 8px;
            box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);
        }

        /* Titre */
        h1 {
            color: #2c3e50;
            border-bottom: 2px solid #3498db;
            padding-bottom: 10px;
            margin-bottom: 25px;
        }

        /* Message de succès */
        .success-message {
            color: #27ae60;
            font-size: 1.2em;
            margin-bottom: 25px;
            padding: 15px;
            background-color: #e8f6ef;
            border-radius: 4px;
        }

        /* Boîte de détails */
        .details-box {
            border: 1px solid #ecf0f1;
            padding: 20px;
            margin: 20px 0;
            border-radius: 6px;
            background-color: #f9f9f9;
        }
        
        .details-box p {
            margin: 10px 0;
            color: #34495e;
        }

        /* Boutons */
        .btn {
            display: inline-block;
            padding: 10px 20px;
            margin: 10px 5px;
            border-radius: 4px;
            text-decoration: none;
            font-weight: 500;
            transition: all 0.3s ease;
        }

        .btn-primary {
            background-color: #3498db;
            color: white;
            border: 1px solid #2980b9;
        }

        .btn-primary:hover {
            background-color: #2980b9;
        }

        .btn-secondary {
            background-color: #95a5a6;
            color: white;
            border: 1px solid #7f8c8d;
        }

        .btn-secondary:hover {
            background-color: #7f8c8d;
        }
    </style>
</head>
<body>
    <div class="confirmation-container">
        <h1>✅ Confirmation d'Enregistrement</h1>
        
        <% if (request.getAttribute("success") != null) { %>
            <div class="success-message"><%= request.getAttribute("success") %></div>
            
            <% VolDetails volDetails = (VolDetails) request.getAttribute("volDetails"); 
               if (volDetails != null) { %>
                <div class="details-box">
                    <h3>Détails enregistrés :</h3>
                    <p><strong>ID :</strong> <%= volDetails.getId() %></p>
                    <p><strong>Vol :</strong> <%= volDetails.getVol().getNom() %></p>
                    <p><strong>Type de Siège :</strong> 
                        <%= volDetails.getSiege().getTypeSiege().getTypeNom() %>
                    </p>
                    <p><strong>Nombre Disponible :</strong> 
                        <%= volDetails.getNombreDisponible() %>
                    </p>
                    <p><strong>Catégorie Tarifaire :</strong> 
                        <%= volDetails.getTarif().getCategorie().getNom() %>
                    </p>
                </div>
            <% } %>
        <% } %>

        <div class="action-buttons">
            <a href="volDetails" class="btn btn-primary">Nouvel Enregistrement</a>
            <a href="accueil.jsp" class="btn btn-secondary">Retour à l'accueil</a>
        </div>
    </div>
</body>
</html>