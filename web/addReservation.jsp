<%@ page contentType="text/html;charset=UTF-8" language="java" import="java.util.List,models.Passager,models.Vol,models.TypeSiege,models.Categorie,models.ModePaiement, models.AvionDetailler" %>

<html lang="fr">
<head>
    <meta charset="UTF-8" />
    <title>Réservation de Vol</title>
    <style>
        /* Reset basique */
        * {
            box-sizing: border-box;
            margin: 0; padding: 0;
            font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
        }
        body {
            background: #f7f9fc;
            color: #333;
            padding: 2rem;
        }
        h1 {
            text-align: center;
            margin-bottom: 1.5rem;
            color: #2c3e50;
        }
        form {
            max-width: 600px;
            margin: 0 auto;
            background: white;
            padding: 2rem;
            border-radius: 10px;
            box-shadow: 0 0 15px rgba(0,0,0,0.1);
        }
        label {
            display: block;
            margin-bottom: 0.3rem;
            font-weight: 600;
            color: #34495e;
        }
        select, input[type="number"], input[type="submit"] {
            width: 100%;
            padding: 0.6rem 0.8rem;
            margin-bottom: 1.2rem;
            border: 1px solid #bdc3c7;
            border-radius: 6px;
            font-size: 1rem;
            transition: border-color 0.3s ease;
        }
        select:focus, input[type="number"]:focus {
            border-color: #2980b9;
            outline: none;
        }
        input[type="submit"] {
            background: #2980b9;
            border: none;
            color: white;
            font-weight: 700;
            cursor: pointer;
            transition: background 0.3s ease;
        }
        input[type="submit"]:hover {
            background: #3498db;
        }
        .error {
            color: #e74c3c;
            margin-bottom: 1rem;
            font-weight: 600;
            text-align: center;
        }
        .success {
            color: #27ae60;
            margin-bottom: 1rem;
            font-weight: 600;
            text-align: center;
        }
        @media(max-width: 640px) {
            body {
                padding: 1rem;
            }
            form {
                padding: 1rem;
            }
        }
    </style>
</head>
<body>
    <h1>Formulaire de Réservation</h1>

    <% if (request.getAttribute("error") != null) { %>
        <div class="error"><%= request.getAttribute("error") %></div>
    <% } %>
    <% if (request.getAttribute("success") != null) { %>
        <div class="success"><%= request.getAttribute("success") %></div>
    <% } %>

    <form method="post" action="reserver">
        <label for="passagerId">Passager</label>
        <select id="passagerId" name="passagerId" required>
            <option value="">-- Sélectionner un passager --</option>
            <%
                List<Passager> passagers = (List<Passager>) request.getAttribute("passagers");
                if(passagers != null) {
                    for (int i = 0; i < passagers.size(); i++) {
                        Passager p = passagers.get(i);
            %>
                <option value="<%= p.getId() %>"><%= p.getNom()%></option>
            <%
                    }
                }
            %>
        </select>

        <label for="volId">Vol</label>
        <select id="volId" name="volId" required>
            <option value="">-- Sélectionner un vol --</option>
            <%
                List<Vol> vols = (List<Vol>) request.getAttribute("vols");
                if(vols != null) {
                    for (int i = 0; i < vols.size(); i++) {
                        Vol v = vols.get(i);
            %>
                <option value="<%= v.getId() %>">
                    <%= v.getNom() + " - " 
                        + v.getVilleDepart().getVille() 
                        + " → " 
                        + v.getVilleArriver().getVille() 
                        + " (" + v.getDateDepart() + ")" %>
                </option>
            <%
                    }
                }
            %>
        </select>


       <label for="avionDetaillerId">Avion Détail Siège</label>
        <select id="avionDetaillerId" name="avionDetaillerId" required>
            <option value="">-- Sélectionner un siège d'avion --</option>
            <%
                List<models.AvionDetailler> avionDetaillers = (List<models.AvionDetailler>) request.getAttribute("avionDetaillers");
                if (avionDetaillers != null) {
                    for (models.AvionDetailler ad : avionDetaillers) {
            %>
                <option value="<%= ad.getId() %>">
                    <%= ad.getTypeSiege().getTypeNom() + " - " + ad.getAvion().getNom() %>
                </option>
            <%
                    }
                }
            %>
        </select>


        <label for="categorieId">Catégorie</label>
        <select id="categorieId" name="categorieId" required>
            <option value="">-- Sélectionner une catégorie --</option>
            <%
                List<Categorie> categories = (List<Categorie>) request.getAttribute("categories");
                java.util.Map<Integer, java.math.BigDecimal> tarifs = (java.util.Map<Integer, java.math.BigDecimal>) request.getAttribute("tarifs");
                if(categories != null) {
                    for (int i = 0; i < categories.size(); i++) {
                        Categorie c = categories.get(i);
                        java.math.BigDecimal tarif = tarifs != null ? tarifs.get(c.getId()) : null;
            %>
                <option value="<%= c.getId() %>">
                    <%= c.getNom() %> - Tarif : <%= tarif != null ? tarif.toString() : "0" %> €
                </option>
            <%
                    }
                }
            %>
        </select>

        <label for="quantite">Quantité</label>
        <input type="number" id="quantite" name="quantite" min="1" value="1" required />

        <label for="modePaiementId">Mode de Paiement</label>
        <select id="modePaiementId" name="modePaiementId" required>
            <option value="">-- Sélectionner un mode de paiement --</option>
            <%
                List<ModePaiement> modesPaiement = (List<ModePaiement>) request.getAttribute("modesPaiement");
                if(modesPaiement != null) {
                    for (int i = 0; i < modesPaiement.size(); i++) {
                        ModePaiement m = modesPaiement.get(i);
            %>
                <option value="<%= m.getId() %>"><%= m.getNom() %></option>
            <%
                    }
                }
            %>
        </select>

        <input type="submit" value="Réserver" />
    </form>
</body>
</html>
