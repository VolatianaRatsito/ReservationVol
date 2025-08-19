<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="java.util.List" %>
<%@ page import="models.Passport" %>

<html>
<head>
    <title>Mes Passeports</title>
    <style>
        /* Reset & base */
        * {
            box-sizing: border-box;
            margin: 0; padding: 0;
            font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
        }
        body {
            background: linear-gradient(135deg, #1e3c72, #2a5298);
            color: #fff;
            min-height: 100vh;
            padding: 30px 20px;
            display: flex;
            flex-direction: column;
            align-items: center;
        }

        h1 {
            font-size: 2.8rem;
            font-weight: 900;
            margin-bottom: 40px;
            letter-spacing: 0.1em;
            text-shadow: 0 0 10px #ffd600aa;
            user-select: none;
        }

        a.btn-add {
            background-color: #ffd600;
            color: #1e3c72;
            font-weight: 700;
            font-size: 1.15rem;
            text-decoration: none;
            padding: 12px 28px;
            border-radius: 30px;
            box-shadow: 0 4px 15px #ffd600cc;
            margin-bottom: 30px;
            transition: background-color 0.3s ease, color 0.3s ease;
            user-select: none;
        }
        a.btn-add:hover {
            background-color: #fff200;
            color: #112d4e;
        }

        table {
            width: 90vw;
            max-width: 1100px;
            border-collapse: separate;
            border-spacing: 0 15px;
            box-shadow: 0 8px 30px rgba(0,0,0,0.4);
            background-color: rgba(255,255,255,0.12);
            border-radius: 12px;
            overflow: hidden;
        }

        thead tr {
            background: linear-gradient(90deg, #ffd600, #f0b429);
            color: #112d4e;
            text-transform: uppercase;
            font-weight: 900;
            letter-spacing: 0.08em;
            box-shadow: 0 4px 8px #ffdd0044;
        }

        th, td {
            padding: 16px 18px;
            text-align: center;
            vertical-align: middle;
        }

        tbody tr {
            background-color: rgba(255, 255, 255, 0.18);
            transition: background-color 0.3s ease;
            border-radius: 10px;
        }
        tbody tr:hover {
            background-color: rgba(255, 214, 0, 0.3);
            color: #112d4e;
            cursor: default;
        }

        tbody td img.photo-preview {
            width: 80px;
            height: 80px;
            object-fit: cover;
            border-radius: 10px;
            box-shadow: 0 2px 6px rgba(0,0,0,0.3);
            transition: transform 0.3s ease;
        }
        tbody td img.photo-preview:hover {
            transform: scale(1.1);
            box-shadow: 0 4px 12px rgba(0,0,0,0.5);
        }

        .empty-message {
            font-size: 1.5rem;
            margin-top: 60px;
            font-weight: 600;
            color: #ffd600cc;
            user-select: none;
        }

        /* Responsive */
        @media (max-width: 720px) {
            table {
                width: 100vw;
                font-size: 0.85rem;
            }
            th, td {
                padding: 12px 8px;
            }
            a.btn-add {
                width: 80%;
                text-align: center;
                padding: 10px 0;
                font-size: 1rem;
            }
        }
    </style>
</head>
<body>
    <h1>Mes Passeports</h1>

    <a class="btn-add" href="addPassport">➕ Ajouter un nouveau passeport</a>

    <%
        List<Passport> passports = (List<Passport>) request.getAttribute("passports");
        if (passports == null || passports.isEmpty()) {
    %>
        <div class="empty-message">Aucun passeport enregistré.</div>
    <%
        } else {
    %>
        <table>
            <thead>
                <tr>
                    <th>Numéro</th>
                    <th>Passager</th>
                    <th>Nationalité</th>
                    <th>Date d'émission</th>
                    <th>Date d'expiration</th>
                    <th>Photo</th>
                </tr>
            </thead>
            <tbody>
            <%
                for (int i = 0; i < passports.size(); i++) {
                    Passport passport = passports.get(i);
            %>
                <tr>
                    <td><%= passport.getNumero() %></td>
                    <td><%= passport.getPassager().getNom() %></td>
                    <td><%= passport.getNationalite().getNom() %></td>
                    <td><%= passport.getDateEmission() %></td>
                    <td><%= passport.getDateExpiration() %></td>
                    <td>
                        <img src="<%= request.getContextPath() %>/uploads//<%= passport.getPhoto() %>" alt="Photo Passeport" class="photo-preview" />
                    </td>
                </tr>
            <%
                }
            %>
            </tbody>
        </table>
    <%
        }
    %>
</body>
</html>
