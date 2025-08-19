<%@ page import="java.util.List" %>
<%@ page import="models.Vol" %>
<%@ page import="models.Avion" %>
<%@ page import="models.VilleDesservie" %>

<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Liste des Vols - Système de Réservation</title>
    <link href="https://fonts.googleapis.com/css2?family=Montserrat:wght@400;500;600&display=swap" rel="stylesheet">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0-beta3/css/all.min.css">
    <style>
        :root {
            --primary: #005f87;
            --secondary: #e51937;
            --accent: #ffc107;
            --light: #f8f9fa;
            --dark: #212529;
            --success: #28a745;
            --info: #17a2b8;
            --warning: #ffc107;
            --danger: #dc3545;
        }

        body {
            font-family: 'Montserrat', sans-serif;
            background-color: #f0f5f9;
            margin: 0;
            padding: 0;
            color: #333;
            background-image: linear-gradient(rgba(255,255,255,0.9), rgba(255,255,255,0.9)), 
                              url('https://images.unsplash.com/photo-1436491865332-7a61a109cc05?ixlib=rb-1.2.1&auto=format&fit=crop&w=1350&q=80');
            background-size: cover;
            background-attachment: fixed;
        }

	.container {
        width: 90%;
        max-width: 1200px;
        margin: 30px auto;
        padding: 20px;
        background-color: rgba(255, 255, 255, 0.8); /* Fond blanc avec transparence */
        animation: fadeIn 1s ease-in-out; /* Animation d'apparition */
        box-shadow: 0 0 20px rgba(0, 0, 0, 0.1);
        border-radius: 10px;
    }
       /* Animation */
    @keyframes fadeIn {
        from {
            opacity: 0;
        }
        to {
            opacity: 1; /* Correspond à l'opacité pleine */
        }
    }
        h1 {
            text-align: center;
            color: var(--primary);
            margin-bottom: 30px;
            font-weight: 600;
            text-transform: uppercase;
            letter-spacing: 1px;
            position: relative;
            padding-bottom: 15px;
        }

        h1:after {
            content: "";
            position: absolute;
            bottom: 0;
            left: 50%;
            transform: translateX(-50%);
            width: 100px;
            height: 3px;
            background-color: var(--accent);
        }

        .alert {
            padding: 15px;
            margin: 15px 0;
            border-radius: 5px;
            font-weight: 500;
        }

        .alert-error {
            background-color: #f8d7da;
            color: #721c24;
            border-left: 5px solid var(--danger);
        }

        .alert-success {
            background-color: #d4edda;
            color: #155724;
            border-left: 5px solid var(--success);
        }

        .flight-table {
            width: 100%;
            border-collapse: separate;
            border-spacing: 0;
            margin-top: 20px;
            overflow: hidden;
            box-shadow: 0 0 20px rgba(0, 0, 0, 0.1);
            border-radius: 10px;
        }

        .flight-table thead {
            background-color: var(--primary);
            color: white;
        }

        .flight-table th {
            padding: 15px;
            text-align: left;
            font-weight: 500;
            text-transform: uppercase;
            font-size: 0.9em;
            letter-spacing: 0.5px;
        }

        .flight-table tbody tr {
            transition: all 0.3s ease;
        }

        .flight-table tbody tr:nth-child(even) {
            background-color: #f8f9fa;
        }

        .flight-table tbody tr:hover {
            background-color: #e9f7fe;
            transform: translateY(-2px);
            box-shadow: 0 5px 15px rgba(0, 0, 0, 0.1);
        }

        .flight-table td {
            padding: 12px 15px;
            border-bottom: 1px solid #e0e0e0;
            vertical-align: middle;
        }

        .flight-table td:first-child {
            font-weight: 600;
            color: var(--primary);
        }

        .status {
            display: inline-block;
            padding: 5px 10px;
            border-radius: 20px;
            font-size: 0.8em;
            font-weight: 500;
        }

        .status.on-time {
            background-color: #d4edda;
            color: #155724;
        }

        .status.delayed {
            background-color: #fff3cd;
            color: #856404;
        }

        .status.cancelled {
            background-color: #f8d7da;
            color: #721c24;
        }

        .btn {
            display: inline-block;
            padding: 8px 15px;
            border-radius: 5px;
            font-weight: 500;
            text-decoration: none;
            transition: all 0.3s ease;
            border: none;
            cursor: pointer;
            font-size: 0.9em;
            margin-right: 5px;
        }

        .btn-primary {
            background-color: var(--primary);
            color: white;
        }

        .btn-primary:hover {
            background-color: #004b6b;
            transform: translateY(-2px);
        }

        .btn-danger {
            background-color: var(--danger);
            color: white;
        }

        .btn-danger:hover {
            background-color: #c82333;
            transform: translateY(-2px);
        }

        .btn-sm {
            padding: 5px 10px;
            font-size: 0.8em;
        }

        .no-flights {
            text-align: center;
            padding: 30px;
            color: #6c757d;
            font-size: 1.1em;
        }

        .flight-icon {
            color: var(--primary);
            margin-right: 8px;
        }

        .action-buttons {
            display: flex;
            gap: 10px;
        }

        @media (max-width: 768px) {
            .container {
                width: 95%;
                padding: 15px;
            }
            
            .flight-table {
                display: block;
                overflow-x: auto;
            }
            
            .action-buttons {
                flex-direction: column;
                gap: 5px;
            }
        }
    </style>
</head>
<body>
    <div class="container">
        <h1><i class="fas fa-plane-departure flight-icon"></i>Liste des Vols</h1>
        
        <%
            List<Vol> vols = (List<Vol>) request.getAttribute("vols");
            if (vols != null && !vols.isEmpty()) {
        %>
            <table class="flight-table">
                <thead>
                    <tr>
                        <th>ID Vol</th>
                        <th>Nom</th>
                        <th>Avion</th>
                        <th>Départ</th>
                        <th>Arrivée</th>
                        <th>Heure Départ</th>
                        <th>Heure Arrivée</th>
                        <th>Actions</th>
                    </tr>
                </thead>
                <tbody>
                    <% for (Vol vol : vols) { %>
                        <tr>
                            <td><%= vol.getId() %></td>
                            <td><strong><%= vol.getNom() %></strong></td>
                            <td><i class="fas fa-plane"></i> <%= vol.getAvion().getNom() %></td>
                            <td>
                                <i class="fas fa-map-marker-alt" style="color: var(--secondary);"></i> 
                                <%= vol.getVilleDepart().getVille() %>
                            </td>
                            <td>
                                <i class="fas fa-map-marker-alt" style="color: var(--success);"></i> 
                                <%= vol.getVilleArriver().getVille() %>
                            </td>
                            <td><%= vol.getDateDepart() %></td>
                            <td><%= vol.getDateArriver() %></td>
                            <td class="action-buttons">
                                <a href="update_redirect?id=<%= vol.getId() %>" class="btn btn-primary btn-sm">
                                    <i class="fas fa-edit"></i>
                                </a>
                                <form action="vols/supprimer/<%= vol.getId() %>" method="post" style="display:inline;">
                                    <button type="submit" class="btn btn-danger btn-sm">
                                        <i class="fas fa-trash-alt"></i>
                                    </button>
                                </form>
                            </td>
                        </tr>
                    <% } %>
                </tbody>
            </table>
        <%
            } else {
        %>
            <div class="no-flights">
                <i class="fas fa-plane-slash" style="font-size: 2em; color: #6c757d; margin-bottom: 15px;"></i>
                <p>Aucun vol disponible pour le moment.</p>
            </div>
        <%
            }
        %>
    </div>
</body>
</html>