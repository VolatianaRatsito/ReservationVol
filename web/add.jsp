<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="java.util.List" %>
<%@ page import="models.Passager" %>
<%@ page import="models.Nationalite" %>
<html>
<head>
    <title>Ajouter un Passeport</title>
    <style>
        /* Reset & base */
        body {
            font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
            background: linear-gradient(135deg, #0f2027, #203a43, #2c5364);
            color: #f0f0f0;
            margin: 0; padding: 0;
            display: flex;
            justify-content: center;
            align-items: flex-start;
            min-height: 100vh;
        }

        h1 {
            text-align: center;
            color: #ffd600; /* jaune vibrant */
            margin-bottom: 30px;
            font-weight: 700;
            letter-spacing: 2px;
            text-transform: uppercase;
            text-shadow: 0 0 5px #ffd600aa;
        }

        form {
            background-color: #1c2833cc;
            padding: 30px 40px;
            border-radius: 12px;
            box-shadow: 0 8px 20px rgba(255, 214, 0, 0.3);
            width: 400px;
        }

        .form-group {
            margin-bottom: 20px;
        }

        label {
            display: block;
            font-weight: 600;
            margin-bottom: 6px;
            letter-spacing: 0.05em;
        }

        select, input[type="date"], input[type="file"] {
            width: 100%;
            padding: 10px 14px;
            border-radius: 6px;
            border: none;
            font-size: 1em;
            background-color: #0b1620;
            color: #f0f0f0;
            box-shadow: inset 0 0 5px #0008;
            transition: background-color 0.3s ease;
        }
        select:hover, input[type="date"]:hover, input[type="file"]:hover,
        select:focus, input[type="date"]:focus, input[type="file"]:focus {
            background-color: #192a3b;
            outline: none;
            box-shadow: 0 0 10px #ffd600;
        }

        input[type="submit"] {
            background-color: #ffd600;
            border: none;
            color: #1c2833;
            font-weight: 700;
            padding: 12px 25px;
            cursor: pointer;
            border-radius: 8px;
            font-size: 1.1em;
            width: 100%;
            box-shadow: 0 0 15px #ffd600aa;
            transition: background-color 0.3s ease, color 0.3s ease;
        }

        input[type="submit"]:hover {
            background-color: #fff200;
            color: #000;
        }

        a {
            display: inline-block;
            margin-top: 15px;
            color: #ffd600;
            text-decoration: none;
            font-weight: 600;
            font-size: 0.95em;
            transition: color 0.3s ease;
        }

        a:hover {
            color: #fff200;
            text-decoration: underline;
        }

        /* Responsive */
        @media (max-width: 480px) {
            form {
                width: 90%;
                padding: 20px;
            }
        }
    </style>
</head>
<body>
    <form action="save" method="post" enctype="multipart/form-data">
        <h1>Ajouter un nouveau passeport</h1>
        
        <div class="form-group">
            <label for="idPassager">Passager :</label>
            <select id="idPassager" name="idPassager" required>
                <option value="">Sélectionnez un passager</option>
                <%
                    List<Passager> passagers = (List<Passager>) request.getAttribute("passagers");
                    if (passagers != null) {
                        for (Passager p : passagers) {
                %>
                    <option value="<%= p.getId() %>"><%= p.getNom() %> - <%= p.getCin() %></option>
                <%
                        }
                    }
                %>
            </select>
        </div>

        <div class="form-group">
            <label for="idNationalite">Nationalité :</label>
            <select id="idNationalite" name="idNationalite" required>
                <option value="">Sélectionnez une nationalité</option>
                <%
                    List<Nationalite> nationalites = (List<Nationalite>) request.getAttribute("nationalites");
                    if (nationalites != null) {
                        for (Nationalite nat : nationalites) {
                %>
                    <option value="<%= nat.getId() %>"><%= nat.getNom() %></option>
                <%
                        }
                    }
                %>
            </select>
        </div>

        <div class="form-group">
            <label for="date_emission">Date d'émission :</label>
            <input type="date" id="date_emission" name="date_emission" required>
        </div>

        <div class="form-group">
            <label for="date_expiration">Date d'expiration :</label>
            <input type="date" id="date_expiration" name="date_expiration" required>
        </div>

        <div class="form-group">
            <label for="photo">Photo :</label>
            <input type="file" id="photo" name="photo" accept="image/*" required>
        </div>

        <div class="form-group">
            <input type="submit" value="Enregistrer">
            <a href="listPassport">Annuler</a>
        </div>
    </form>
</body>
</html>
