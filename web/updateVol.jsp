<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="java.util.List" %>
<%@ page import="models.Avion" %>
<%@ page import="models.VilleDesservie" %>
<%@ page import="models.Vol" %> <!-- Importez la classe Vol -->
<html>
<head>
    <title>Update Flight</title>
   <style>
        body {
            font-family: Arial, sans-serif;
            background-color: #f4f4f4;
            margin: 0;
            padding: 20px;
        }
        h1 {
            color: #333;
            text-align: center;
        }
        form {
            max-width: 600px;
            margin: 0 auto;
            background: #fff;
            padding: 20px;
            border-radius: 5px;
            box-shadow: 0 2px 10px rgba(0, 0, 0, 0.1);
        }
        label {
            display: block;
            margin: 10px 0 5px;
        }
        input[type="text"],
        input[type="datetime-local"],
        select {
            width: 100%;
            padding: 8px;
            margin-bottom: 15px;
            border: 1px solid #ddd;
            border-radius: 4px;
        }
        button {
            background-color: #28a745;
            color: white;
            padding: 10px 15px;
            border: none;
            border-radius: 4px;
            cursor: pointer;
            width: 100%;
        }
        button:hover {
            background-color: #218838;
        }
    </style>
</head>
<body>
    <h1>Update Flight</h1>
    <form action="modifierVol" method="post">
        <input type="hidden" name="id" value="<%= ((Vol) request.getAttribute("vol")).getId() %>" />
        
        <input type="text" name="nom" value="<%= ((Vol) request.getAttribute("vol")).getNom() %>" required />
        
        <label for="idAvion">Sélectionner l'avion :</label>
        <select name="idAvion" required>
            <%
                List<Avion> avions = (List<Avion>) request.getAttribute("avions");
                for (Avion avion : avions) {
            %>
                <option value="<%= avion.getId() %>" 
                    <%= avion.getId() == ((Vol) request.getAttribute("vol")).getAvion().getId() ? "selected" : "" %>> 
                    <%= avion.getNom() %>
                </option>
            <%
                }
            %>
        </select>
        
        <label for="villeDepart">Ville de Départ :</label>
        <select name="villeDepart" required>
            <%
                List<VilleDesservie> villes = (List<VilleDesservie>) request.getAttribute("villes");
                for (VilleDesservie ville : villes) {
            %>
                <option value="<%= ville.getId() %>" 
                    <%= ville.getId() == ((Vol) request.getAttribute("vol")).getVilleDepart().getId() ? "selected" : "" %>> 
                    <%= ville.getVille() %>
                </option>
            <%
                }
            %>
        </select>

        <label for="villeArriver">Ville d'Arrivée :</label>
        <select name="villeArriver" required>
            <%
                for (VilleDesservie ville : villes) {
            %>
                <option value="<%= ville.getId() %>" 
                    <%= ville.getId() == ((Vol) request.getAttribute("vol")).getVilleArriver().getId() ? "selected" : "" %>> 
                    <%= ville.getVille() %>
                </option>
            <%
                }
            %>
        </select>

        <label for="dateDepart">Date de Départ :</label>
        <input type="datetime-local" name="dateDepart" value="<%= ((Vol) request.getAttribute("vol")).getDateDepart() %>" required />
        
        <label for="dateArriver">Date d'Arrivée :</label>
        <input type="datetime-local" name="dateArriver" value="<%= ((Vol) request.getAttribute("vol")).getDateArriver() %>" required />
        
        <button type="submit">Update Flight</button>
    </form>
</body>
</html>