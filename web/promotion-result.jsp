<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="models.Promotion" %>
<%@ page import="models.AvionDetailler" %>
<%@ page import="models.Vol" %>
<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1" />
    <title>Liste des promotions</title>
    <link href="https://fonts.googleapis.com/css2?family=Montserrat:wght@400;600&display=swap" rel="stylesheet" />
    <style>
        /* Background image with overlay */
        body {
            font-family: 'Montserrat', sans-serif;
            margin: 0;
            padding: 0;
            min-height: 100vh;
            background-image: url('https://images.unsplash.com/photo-1506744038136-46273834b3fb?auto=format&fit=crop&w=1920&q=80');
            background-size: cover;
            background-position: center;
            position: relative;
            color: #f0f0f0;
        }
        /* Dark overlay for better text contrast */
        body::before {
            content: "";
            position: fixed;
            top: 0; left: 0; right: 0; bottom: 0;
            background-color: rgba(0, 0, 0, 0.6);
            z-index: 0;
        }
        /* Main container with some padding and centering */
        .container {
            position: relative;
            z-index: 1;
            max-width: 1000px;
            margin: 50px auto;
            background-color: rgba(255, 255, 255, 0.1);
            padding: 30px 40px;
            border-radius: 12px;
            box-shadow: 0 8px 25px rgba(0, 0, 0, 0.7);
        }
        h1 {
            font-weight: 600;
            font-size: 2.5rem;
            margin-bottom: 25px;
            text-shadow: 0 2px 6px rgba(0,0,0,0.7);
        }
        /* Success message styling */
        .success-message {
            background-color: #28a745dd;
            padding: 15px 20px;
            margin-bottom: 25px;
            border-radius: 8px;
            font-weight: 600;
            box-shadow: 0 3px 8px #1f6f39aa;
        }
        /* Table styling */
        table {
            width: 100%;
            border-collapse: separate;
            border-spacing: 0;
            border-radius: 10px;
            overflow: hidden;
            box-shadow: 0 6px 18px rgba(0, 0, 0, 0.5);
            background-color: rgba(255, 255, 255, 0.9);
            color: #333;
        }
        th, td {
            padding: 14px 18px;
            text-align: left;
        }
        th {
            background-color: #007bff;
            color: white;
            font-weight: 600;
            text-transform: uppercase;
            letter-spacing: 0.05em;
        }
        tr:nth-child(even) {
            background-color: #f7f9fc;
        }
        tr:hover {
            background-color: #e0f0ff;
            cursor: default;
        }
        /* Link button style */
        .btn-link {
            display: inline-block;
            margin-top: 30px;
            padding: 12px 28px;
            background-color: #007bffcc;
            color: white;
            text-decoration: none;
            border-radius: 8px;
            font-weight: 600;
            box-shadow: 0 4px 12px #004a99cc;
            transition: background-color 0.3s ease;
        }
        .btn-link:hover {
            background-color: #0056b3cc;
        }
        /* Responsive for mobile */
        @media (max-width: 768px) {
            .container {
                margin: 20px 10px;
                padding: 20px;
            }
            h1 {
                font-size: 1.8rem;
            }
            th, td {
                padding: 10px 8px;
                font-size: 0.9rem;
            }
        }
    </style>
</head>
<body>
    <div class="container">

        <% 
            String success = (String) request.getAttribute("success");
            if (success != null) { 
        %>
            <div class="success-message">
                <%= success %>
            </div>
        <% } %>

        <h1>Liste des promotions</h1>

        <%
            List<Promotion> promotions = (List<Promotion>) request.getAttribute("promotions");
            if (promotions != null && !promotions.isEmpty()) {
        %>
            <table>
                <tr>
                    <th>ID</th>
                    <th>Vol</th>
                    <th>Avion</th>
                    <th>Type Siège</th>
                    <th>Date de promotion</th>
                    <th>Date d'expiration</th>
                    <th>Quantité</th>
                    <th>Pourcentage</th>
                </tr>
                <%
                    for (int i = 0; i < promotions.size(); i++) {
                        Promotion promotion = promotions.get(i);
                        AvionDetailler avionSiege = promotion.getAvionSiege();
                %>
                    <tr>
                        <td><%= promotion.getId() %></td>
                        <td><%= promotion.getVol() != null ? promotion.getVol().getNom() : "" %></td>
                        <td><%= (avionSiege != null && avionSiege.getAvion() != null) ? avionSiege.getAvion().getNom() : "" %></td>
                        <td><%= (avionSiege != null && avionSiege.getTypeSiege() != null) ? avionSiege.getTypeSiege().getTypeNom() : "" %></td>
                        <td><%= promotion.getDatePromotion() %></td>
                        <td><%= promotion.getDateExpiration() %></td>
                        <td><%= promotion.getQuantite() %></td>
                        <td><%= promotion.getPourcentage() %>%</td>
                    </tr>
                <%
                    }
                %>
            </table>
        <%
            } else {
        %>
            <p>Aucune promotion disponible.</p>
        <%
            }
        %>

        <a href="add-form" class="btn-link">Ajouter une promotion</a>
    </div>
</body>
</html>
