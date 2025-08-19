<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="models.Reservation" %>
<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <title>Liste des Réservations</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            background-color: #f4f4f4;
            margin: 0;
            padding: 20px;
        }
        .container {
            background-color: white;
            border-radius: 8px;
            box-shadow: 0 2px 10px rgba(0, 0, 0, 0.1);
            padding: 20px;
            max-width: 800px;
            margin: auto;
        }
        h1 {
            text-align: center;
            color: #0056b3;
        }
        table {
            width: 100%;
            border-collapse: collapse;
            margin-top: 20px;
        }
        th, td {
            padding: 12px;
            text-align: left;
            border-bottom: 1px solid #ddd;
        }
        th {
            background-color: #0056b3;
            color: white;
        }
        td img {
            width: 100px;
            height: auto;
            border-radius: 5px;
        }
        .delete-btn {
            background-color: #dc3545;
            color: white;
            border: none;
            padding: 5px 10px;
            border-radius: 4px;
            cursor: pointer;
        }
    </style>
</head>
<body>
    <div class="container">
        <h1>Liste des Réservations</h1>

        <table>
            <thead>
                <tr>
                    <th>ID Réservation</th>
                    <th>Date/Heure</th>
                    <th>ID Passager</th>
                    <th>État</th>
                    <th>Passeport</th>
                    <th>Actions</th>
                </tr>
            </thead>
            <tbody>
                <%
                    Reservation reservationModel = new Reservation(); // Instantiate the Reservation class
                    List<Reservation> reservations = reservationModel.readAll(); // Call readAll method
                    
                    for (Reservation reservation : reservations) {
                %>
                    <tr>
                        <td><%= reservation.getId() %></td>
                        <td><%= reservation.getDateHeure() %></td>
                        <td><%= reservation.getIdPassager() %></td>
                        <td><%= reservation.getIdEtatReservation() %></td>
                        <td>
                            <%
                                String passportImagePath = "D:\\Students\\S5\\Mr Naina\\Ticketing\\Vols\\passport\\" + reservation.getFileName().getName();
                            %>
                            <img src="<%= passportImagePath %>" alt="Passeport">
                        </td>
                        <td>
                            <form action="app/annuler_reservation" method="get" style="display:inline;">
                                <input type="hidden" name="id" value="<%= reservation.getId() %>">
                                <button type="submit" class="delete-btn">Annuler</button>
                            </form>
                        </td>
                    </tr>
                <%
                    }
                %>
            </tbody>
        </table>
    </div>
</body>
</html>