<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Promotion des Vols - Offres Exceptionnelles</title>
    <style>
        body {
            font-family: 'Arial', sans-serif;
            margin: 0;
            padding: 0;
            background-color: #f0f8ff;
            color: #333;
        }
        header {
            background: linear-gradient(135deg, #1e90ff, #00bfff);
            color: white;
            padding: 20px 0;
            text-align: center;
            box-shadow: 0 2px 10px rgba(0,0,0,0.1);
        }
        .container {
            max-width: 1200px;
            margin: 0 auto;
            padding: 20px;
        }
        h1 {
            margin: 0;
            font-size: 2.5em;
        }
        .tagline {
            font-style: italic;
            margin-top: 10px;
        }
        .main-nav {
            background-color: #333;
            padding: 10px 0;
        }
        .main-nav ul {
            list-style: none;
            padding: 0;
            margin: 0;
            display: flex;
            justify-content: center;
            flex-wrap: wrap;
        }
        .main-nav li {
            margin: 0 15px;
        }
        .main-nav a {
            color: white;
            text-decoration: none;
            font-weight: bold;
            padding: 5px 10px;
            border-radius: 5px;
            transition: background-color 0.3s;
        }
        .main-nav a:hover {
            background-color: #1e90ff;
        }
        .promo-banner {
            background-color: #ff6b6b;
            color: white;
            text-align: center;
            padding: 15px;
            font-weight: bold;
            margin: 20px 0;
            border-radius: 5px;
            animation: pulse 2s infinite;
        }
        @keyframes pulse {
            0% { transform: scale(1); }
            50% { transform: scale(1.02); }
            100% { transform: scale(1); }
        }
        .page-links {
            display: grid;
            grid-template-columns: repeat(auto-fill, minmax(300px, 1fr));
            gap: 20px;
            margin-top: 30px;
        }
        .page-card {
            background-color: white;
            border-radius: 8px;
            padding: 20px;
            box-shadow: 0 4px 8px rgba(0,0,0,0.1);
            transition: transform 0.3s;
        }
        .page-card:hover {
            transform: translateY(-5px);
        }
        .page-card h3 {
            color: #1e90ff;
            margin-top: 0;
        }
        .page-card p {
            color: #666;
        }
        .page-card a {
            display: inline-block;
            background-color: #1e90ff;
            color: white;
            padding: 8px 15px;
            border-radius: 5px;
            text-decoration: none;
            margin-top: 10px;
            font-weight: bold;
        }
        footer {
            background-color: #333;
            color: white;
            text-align: center;
            padding: 20px 0;
            margin-top: 40px;
        }
    </style>
</head>
<body>
    <header>
        <div class="container">
            <h1>Promotion des Vols</h1>
            <p class="tagline">Découvrez nos offres exceptionnelles vers des destinations incroyables</p>
        </div>
    </header>

    <nav class="main-nav">
        <ul>
            <li><a href="accueil.jsp">Accueil</a></li>
            <li><a href="add">inserer le promotion</a></li>
            <li><a href="#promotions">Promotions</a></li>
            <li><a href="#contact">Contact</a></li>
            <li><a href="#a-propos">À propos</a></li>
        </ul>
    </nav>

    <div class="container">
        <div class="promo-banner">
            ⚡ Offres spéciales - Jusqu'à 50% de réduction sur les vols sélectionnés ! ⚡
        </div>

        <h2>Explorez nos pages</h2>
        <div class="page-links">
            <div class="page-card">
                <h3>Vols Nationaux</h3>
                <p>Découvrez nos meilleures offres pour des vols à l'intérieur du pays.</p>
                <a href="vols-nationaux.html">Voir les offres</a>
            </div>

            <div class="page-card">
                <h3>Vols Internationaux</h3>
                <p>Explorez le monde avec nos promotions sur les vols internationaux.</p>
                <a href="vols-internationaux.html">Voir les offres</a>
            </div>

            <div class="page-card">
                <h3>Dernière Minute</h3>
                <p>Des deals incroyables pour les voyageurs spontanés.</p>
                <a href="derniere-minute.html">Voir les offres</a>
            </div>

            <div class="page-card">
                <h3>Vols en Économique</h3>
                <p>Voyagez malin sans vous ruiner avec ces promotions.</p>
                <a href="vols-economique.html">Voir les offres</a>
            </div>

            <div class="page-card">
                <h3>Vols Affaires</h3>
                <p>Confort et luxe à des prix promotionnels.</p>
                <a href="vols-affaires.html">Voir les offres</a>
            </div>

            <div class="page-card">
                <h3>Destinations Estivales</h3>
                <p>Préparez vos vacances d'été avec nos offres spéciales.</p>
                <a href="destinations-ete.html">Voir les offres</a>
            </div>

            <div class="page-card">
                <h3>Destinations Hivernales</h3>
                <p>Ski, aurores boréales et vacances au chaud.</p>
                <a href="destinations-hiver.html">Voir les offres</a>
            </div>

            <div class="page-card">
                <h3>Circuits Organisés</h3>
                <p>Vols + hôtel + activités à prix réduits.</p>
                <a href="circuits.html">Voir les offres</a>
            </div>
        </div>
    </div>

    <footer>
        <div class="container">
            <p>© 2023 Promotion des Vols - Tous droits réservés</p>
            <p>Contact: info@promotionvols.com | Tel: +123 456 7890</p>
        </div>
    </footer>
</body>
</html>