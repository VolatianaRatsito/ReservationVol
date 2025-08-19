<%@ page contentType="text/html;charset=UTF-8" language="java" import="java.util.List,models.DemandeReservation" %>

<html lang="fr">
<head>
    <title>Liste des Réservations</title>
    <style>
    /* Reset et typographie */
    * {
        box-sizing: border-box;
        margin: 0;
        padding: 0;
        font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
    }

    body {
        background: #eef2f7;
        color: #333;
        padding: 2rem;
    }

    h1 {
        text-align: center;
        margin-bottom: 2rem;
        font-weight: 700;
        color: #2c3e50;
        letter-spacing: 1px;
        text-transform: uppercase;
    }

    .container {
        max-width: 1200px;
        margin: auto;
        background: #fff;
        padding: 2rem;
        border-radius: 12px;
        box-shadow: 0 10px 25px rgba(0,0,0,0.1);
        animation: fadeIn 0.8s ease forwards;
    }

    table {
        width: 100%;
        border-collapse: collapse;
        table-layout: fixed;
        font-size: 0.95rem;
    }

    thead {
        background: linear-gradient(90deg, #2980b9, #3498db);
        color: #fff;
        user-select: none;
    }

    thead tr {
        transition: background-color 0.3s ease;
    }

    thead tr:hover {
        background-color: #1f618d;
    }

    th, td {
        padding: 0.9rem 1rem;
        text-align: center;
        overflow-wrap: break-word;
    }

    tbody tr {
        border-bottom: 1px solid #ddd;
        transition: all 0.3s ease;
    }

    tbody tr:hover {
        background-color: #dff0fb;
        transform: scale(1.01);
        box-shadow: 0 2px 10px rgba(41,128,185,0.2);
    }

    /* Badges pour quantités */
    .badge {
        display: inline-block;
        padding: 0.35em 0.8em;
        border-radius: 15px;
        font-weight: 600;
        font-size: 0.85rem;
        color: #fff;
        box-shadow: 0 2px 6px rgba(0,0,0,0.15);
        transition: all 0.3s ease;
    }

    .badge.low-quantity { background: #f39c12; }   /* orange */
    .badge.high-quantity { background: #e74c3c; }  /* rouge */
    .badge.normal-quantity { background: #27ae60; } /* vert */

    /* Formulaire d'annulation */
    form input[type="text"] {
        padding: 0.4rem 0.6rem;
        border-radius: 6px;
        border: 1px solid #ccc;
        font-size: 0.85rem;
        width: 120px;
        margin-right: 0.5rem;
        transition: all 0.3s ease;
    }

    form input[type="text"]:focus {
        outline: none;
        border-color: #2980b9;
        box-shadow: 0 0 5px rgba(41,128,185,0.3);
    }

    form button {
        padding: 0.45rem 0.9rem;
        border: none;
        border-radius: 6px;
        background: #c0392b;
        color: #fff;
        font-weight: 600;
        cursor: pointer;
        transition: all 0.3s ease;
    }

    form button:hover {
        background: #e74c3c;
        transform: scale(1.05);
    }

    /* Messages succès / erreur */
    .message {
        max-width: 1200px;
        margin: 1rem auto;
        padding: 1rem 1.5rem;
        border-radius: 8px;
        font-weight: 600;
        text-align: center;
        letter-spacing: 0.05em;
        user-select: none;
    }

    .success {
        background-color: #d4edda;
        color: #155724;
        border: 1px solid #c3e6cb;
        animation: fadeIn 1s ease forwards;
    }

    .error {
        background-color: #f8d7da;
        color: #721c24;
        border: 1px solid #f5c6cb;
        animation: fadeIn 1s ease forwards;
    }

    /* Animation fadeIn */
    @keyframes fadeIn {
        0% {opacity: 0; transform: translateY(15px);}
        100% {opacity: 1; transform: translateY(0);}
    }

    /* Responsive amélioré */
    @media (max-width: 900px) {
        table, thead, tbody, th, td, tr {
            display: block;
        }

        thead tr {
            position: absolute;
            top: -9999px;
            left: -9999px;
        }

        tr {
            margin-bottom: 1.5rem;
            border: 1px solid #ddd;
            border-radius: 10px;
            padding: 1rem;
            background: #fff;
            box-shadow: 0 4px 12px rgba(0,0,0,0.05);
        }

        td {
            border: none;
            position: relative;
            padding-left: 50%;
            text-align: left;
        }

        td:before {
            position: absolute;
            top: 1rem;
            left: 1rem;
            width: 45%;
            white-space: nowrap;
            font-weight: 600;
            color: #2980b9;
            content: attr(data-label);
        }

        form input[type="text"] { width: 100%; margin-bottom: 0.5rem; }
        form button { width: 100%; }
    }
</style>

</head>
<body>
<div class="container">
    <h1>Liste des Réservations</h1>

    <c:if test="${not empty success}">
        <div class="message success">${success}</div>
    </c:if>
    <c:if test="${not empty error}">
        <div class="message error">${error}</div>
    </c:if>

    <%
        // Récupérer la liste des réservations passée en attribut "reservations"
        List<DemandeReservation> reservations = (List<DemandeReservation>) request.getAttribute("reservations");
        if (reservations == null) {
            reservations = new java.util.ArrayList<>();
        }
    %>

    <table>
        <thead>
        <tr>
            <th>ID</th>
            <th>Passager</th>
            <th>Vol</th>
            <th>Type Siège</th>
            <th>Catégorie</th>
            <th>Quantité</th>
            <th>Mode Paiement</th>
            <th>Date Demande</th>
            <th>Montant Total</th>
            <th>Statut</th>
            <th>Action</th>
        </tr>
        </thead>
        <tbody>
        <%
            for (int i = 0; i < reservations.size(); i++) {
                DemandeReservation reservation = reservations.get(i);

                // Formatage de la date
                java.time.format.DateTimeFormatter formatter = java.time.format.DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
                String dateFormatted = reservation.getDateDemande().format(formatter);

                // Classes CSS badge selon quantité
                String badgeClass = "";
                if (reservation.getQuantite() <= 2) badgeClass = "low-quantity";
                else if (reservation.getQuantite() >= 5) badgeClass = "high-quantity";
        %>
        <tr>
            <td data-label="ID"><%= reservation.getId() %></td>
            <td data-label="Passager"><%= reservation.getPassager().getNom() %></td>
            <td data-label="Vol">
                <%= reservation.getVol().getNom() + " - " 
                    + reservation.getVol().getVilleDepart().getVille() 
                    + " → " 
                + reservation.getVol().getVilleArriver().getVille() %>
            </td>
            <td data-label="Type Siège"><%= reservation.getAvionDetailler().getTypeSiege().getTypeNom() %></td>
            <td data-label="Catégorie"><%= reservation.getTarif().getCategorie().getNom() %></td>
            <td data-label="Quantité">
                <span class="badge <%= badgeClass %>">
                    <%= reservation.getQuantite() %>
                </span>
            </td>
            <td data-label="Mode Paiement"><%= reservation.getModePaiement().getNom() %></td>
            <td data-label="Date Demande"><%= dateFormatted %></td>
            <td data-label="Montant Total"><%= reservation.getMontantTotal() %> €</td>
            <td data-label="Statut"><%= reservation.getStatut() %></td>
            <td data-label="Action">
                <% if (!"ANNULEE".equals(reservation.getStatut())) { %>
                <form action="annulerReservation" method="post" onsubmit="return confirm('Confirmer l\'annulation ?');">
                    <input type="hidden" name="demandeId" value="<%= reservation.getId() %>" />
                    <input type="text" name="motif" placeholder="Motif" required />
                    <button type="submit">Annuler</button>
                </form>
                <% } else { %>
                    <span style="color:red;font-weight:bold;">Annulée</span>
                <% } %>
            </td>
        </tr>
        <%
            } // fin boucle for
        %>
        </tbody>
    </table>
</div>
</body>
</html>
