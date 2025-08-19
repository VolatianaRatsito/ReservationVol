<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Réservation de Vols - Menu Animé</title>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css">
    <style>
        :root {
            --primary-color: #4361ee;
            --secondary-color: #3f37c9;
            --accent-color: #4895ef;
            --text-color: #f8f9fa;
            --bg-color: #1a1a2e;
        }

        * {
            margin: 0;
            padding: 0;
            box-sizing: border-box;
            font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
        }

        body {
            background-color: var(--bg-color);
            color: var(--text-color);
            min-height: 100vh;
            display: flex;
            justify-content: center;
            align-items: center;
            background-image: 
                url('https://images.unsplash.com/photo-1436491865332-7a61a109cc05?ixlib=rb-1.2.1&auto=format&fit=crop&w=1950&q=80');
            background-size: cover;
            background-position: center;
            background-attachment: fixed;
            overflow-x: hidden;
            position: relative;
        }

        body::before {
            content: '';
            position: absolute;
            top: 0;
            left: 0;
            width: 100%;
            height: 100%;
            background: rgba(26, 26, 46, 0.85);
            z-index: -1;
        }

        .container {
            width: 90%;
            max-width: 1200px;
            padding: 2rem;
        }

        header {
            text-align: center;
            margin-bottom: 3rem;
            animation: fadeIn 1s ease-out;
            background: rgba(255, 255, 255, 0.1);
            backdrop-filter: blur(10px);
            padding: 2rem;
            border-radius: 15px;
            border: 1px solid rgba(255, 255, 255, 0.2);
        }

        h1 {
            font-size: 2.5rem;
            margin-bottom: 1rem;
            background: linear-gradient(90deg, var(--accent-color), var(--primary-color));
            -webkit-background-clip: text;
            background-clip: text;
            color: transparent;
            display: inline-block;
        }

        .subtitle {
            font-size: 1.1rem;
            opacity: 0.9;
        }

        .links-grid {
            display: grid;
            grid-template-columns: repeat(auto-fill, minmax(300px, 1fr));
            gap: 1.5rem;
        }

        .link-card {
            background: rgba(255, 255, 255, 0.05);
            backdrop-filter: blur(10px);
            border-radius: 15px;
            padding: 1.5rem;
            border: 1px solid rgba(255, 255, 255, 0.1);
            transition: all 0.4s ease;
            transform-style: preserve-3d;
            position: relative;
            overflow: hidden;
            box-shadow: 0 10px 30px rgba(0, 0, 0, 0.3);
            animation: slideUp 0.5s ease-out forwards;
            opacity: 0;
            min-height: 300px;
            display: flex;
            flex-direction: column;
        }

        /* Images thématiques aviation pour chaque carte */
        .link-card:nth-child(1) {
            background: 
                linear-gradient(rgba(67, 97, 238, 0.2), rgba(26, 26, 46, 0.7)),
                url('https://images.unsplash.com/photo-1556388158-158ea5ccacbd?ixlib=rb-1.2.1&auto=format&fit=crop&w=1350&q=80') center/cover;
        }

        .link-card:nth-child(2) {
            background: 
                linear-gradient(rgba(72, 149, 239, 0.2), rgba(26, 26, 46, 0.7)),
                url('https://images.unsplash.com/photo-1436491865332-7a61a109cc05?ixlib=rb-1.2.1&auto=format&fit=crop&w=1350&q=80') center/cover;
        }

        .link-card:nth-child(3) {
            background: 
                linear-gradient(rgba(63, 55, 201, 0.2), rgba(26, 26, 46, 0.7)),
                url('https://images.unsplash.com/photo-1499336315816-097655dcfbda?ixlib=rb-1.2.1&auto=format&fit=crop&w=1350&q=80') center/cover;
        }

        .link-card:nth-child(4) {
            background: 
                linear-gradient(rgba(67, 97, 238, 0.2), rgba(26, 26, 46, 0.7)),
                url('https://images.unsplash.com/photo-1529074963764-98f45c47344b?ixlib=rb-1.2.1&auto=format&fit=crop&w=1350&q=80') center/cover;
        }

        .link-card:nth-child(5) {
            background: 
                linear-gradient(rgba(72, 149, 239, 0.2), rgba(26, 26, 46, 0.7)),
                url('https://images.unsplash.com/photo-1529078155058-5d716f45d604?ixlib=rb-1.2.1&auto=format&fit=crop&w=1350&q=80') center/cover;
        }

        .link-card:nth-child(6) {
            background: 
                linear-gradient(rgba(63, 55, 201, 0.2), rgba(26, 26, 46, 0.7)),
                url('https://images.unsplash.com/photo-1500316124030-4cffa46f10f0?ixlib=rb-1.2.1&auto=format&fit=crop&w=1350&q=80') center/cover;
        }

        .link-card:nth-child(7) {
            background: 
                linear-gradient(rgba(63, 55, 201, 0.2), rgba(26, 26, 46, 0.7)),
                url('https://images.unsplash.com/photo-1500316124030-4cffa46f10f0?ixlib=rb-1.2.1&auto=format&fit=crop&w=1350&q=80') center/cover;
        }

        .link-card::before {
            content: '';
            position: absolute;
            top: 0;
            left: 0;
            width: 100%;
            height: 100%;
            background: linear-gradient(45deg, transparent, rgba(67, 97, 238, 0.2), transparent);
            transform: translateX(-100%);
            transition: transform 0.6s ease;
        }

        .link-card:hover {
            transform: translateY(-10px) scale(1.03);
            box-shadow: 0 15px 35px rgba(0, 0, 0, 0.4);
            border-color: rgba(72, 149, 239, 0.4);
        }

        .link-card:hover::before {
            transform: translateX(100%);
        }

        .link-content {
            position: relative;
            z-index: 2;
            flex-grow: 1;
            display: flex;
            flex-direction: column;
        }

        .link-icon {
            font-size: 2.5rem;
            margin-bottom: 1.5rem;
            color: white;
            text-shadow: 0 2px 10px rgba(0, 0, 0, 0.3);
            transition: all 0.3s ease;
            align-self: flex-start;
            background: rgba(67, 97, 238, 0.7);
            width: 60px;
            height: 60px;
            display: flex;
            align-items: center;
            justify-content: center;
            border-radius: 50%;
        }

        .link-card:hover .link-icon {
            transform: scale(1.2) rotate(10deg);
            background: var(--accent-color);
        }

        .link-title {
            font-size: 1.5rem;
            margin-bottom: 1rem;
            font-weight: 600;
            color: white;
            text-shadow: 0 1px 3px rgba(0, 0, 0, 0.5);
        }

        .link-description {
            font-size: 1rem;
            opacity: 0.9;
            margin-bottom: 2rem;
            line-height: 1.6;
            color: rgba(255, 255, 255, 0.9);
            flex-grow: 1;
        }

        .link-button {
            display: inline-block;
            padding: 0.8rem 1.8rem;
            background: linear-gradient(45deg, var(--primary-color), var(--secondary-color));
            color: white;
            border-radius: 50px;
            text-decoration: none;
            font-weight: 500;
            transition: all 0.3s ease;
            border: none;
            cursor: pointer;
            position: relative;
            overflow: hidden;
            align-self: flex-start;
            box-shadow: 0 4px 15px rgba(67, 97, 238, 0.3);
            border: 1px solid rgba(255, 255, 255, 0.2);
        }

        .link-button::before {
            content: '';
            position: absolute;
            top: 0;
            left: 0;
            width: 100%;
            height: 100%;
            background: linear-gradient(45deg, var(--accent-color), var(--primary-color));
            z-index: -1;
            opacity: 0;
            transition: opacity 0.3s ease;
        }

        .link-button:hover {
            transform: translateY(-3px);
            box-shadow: 0 8px 25px rgba(67, 97, 238, 0.5);
        }

        .link-button:hover::before {
            opacity: 1;
        }

        .link-button i {
            margin-left: 0.5rem;
            transition: transform 0.3s ease;
        }

        .link-button:hover i {
            transform: translateX(5px);
        }

        /* Animations */
        @keyframes fadeIn {
            from { opacity: 0; transform: translateY(-20px); }
            to { opacity: 1; transform: translateY(0); }
        }

        @keyframes slideUp {
            from { opacity: 0; transform: translateY(30px); }
            to { opacity: 1; transform: translateY(0); }
        }

        /* Animation delay for cards */
        .link-card:nth-child(1) { animation-delay: 0.1s; }
        .link-card:nth-child(2) { animation-delay: 0.2s; }
        .link-card:nth-child(3) { animation-delay: 0.3s; }
        .link-card:nth-child(4) { animation-delay: 0.4s; }
        .link-card:nth-child(5) { animation-delay: 0.5s; }
        .link-card:nth-child(6) { animation-delay: 0.6s; }
        .link-card:nth-child(7) { animation-delay: 0.6s; }
        /* Responsive */
        @media (max-width: 768px) {
            .links-grid {
                grid-template-columns: 1fr;
            }
            
            h1 {
                font-size: 2rem;
            }

            .link-card {
                min-height: 250px;
            }
        }
    </style>
</head>
<body>
    <div class="container">
        <header>
            <h1>Réservation de Vols</h1>
            <p class="subtitle">Découvrez nos services aériens à travers ces liens animés</p>
        </header>

        <div class="links-grid">
            <!-- Carte Lien 1 -->
            <div class="link-card">
                <div class="link-content">
                    <div class="link-icon">
                        <i class="fas fa-plane"></i>
                    </div>
                    <h3 class="link-title">Réserver un Vol</h3>
                    <p class="link-description">Trouvez et réservez le vol parfait pour votre prochain voyage parmi nos nombreuses destinations.</p>
                    <a href="reserver.html" class="link-button">Réserver <i class="fas fa-arrow-right"></i></a>
                </div>
            </div>

            <!-- Carte Lien 2 -->
            <div class="link-card">
                <div class="link-content">
                    <div class="link-icon">
                        <i class="fas fa-search"></i>
                    </div>
                    <h3 class="link-title">Rechercher un Vol</h3>
                    <p class="link-description">Recherchez des vols selon vos critères : destination, date, compagnie aérienne, etc.</p>
                    <a href="searchVols" class="link-button">Rechercher <i class="fas fa-arrow-right"></i></a>
                </div>
            </div>

            <!-- Carte Lien 3 -->
            <div class="link-card">
                <div class="link-content">
                    <div class="link-icon">
                        <i class="fas fa-ticket-alt"></i>
                    </div>
                    <h3 class="link-title">Mes Réservations</h3>
                    <p class="link-description">Accédez à l'historique de vos réservations et gérez vos billets existants.</p>
                    <a href="reservations.html" class="link-button">Voir <i class="fas fa-arrow-right"></i></a>
                </div>
            </div>

            <!-- Carte Lien 4 -->
            <div class="link-card">
                <div class="link-content">
                    <div class="link-icon">
                        <i class="fas fa-percentage"></i>
                    </div>
                    <h3 class="link-title">Promotions</h3>
                    <p class="link-description">Découvrez nos offres spéciales et promotions exclusives sur les vols.</p>
                    <a href="promotions.html" class="link-button">Voir les offres <i class="fas fa-arrow-right"></i></a>
                </div>
            </div>

            <!-- Carte Lien 5 -->
            <div class="link-card">
                <div class="link-content">
                    <div class="link-icon">
                        <i class="fas fa-map-marked-alt"></i>
                    </div>
                    <h3 class="link-title">Destinations</h3>
                    <p class="link-description">Explorez nos destinations phares et trouvez l'inspiration pour votre prochain voyage.</p>
                    <a href="vols" class="link-button">Explorer <i class="fas fa-arrow-right"></i></a>
                </div>
            </div>

            <!-- Carte Lien 6 -->
            <div class="link-card">
                <div class="link-content">
                    <div class="link-icon">
                        <i class="fas fa-headset"></i>
                    </div>
                    <h3 class="link-title">Insertion Vol</h3>
                    <p class="link-description">"Besoin d'aide pour votre vol ? Notre équipe est disponible 24/7 pour vous accompagner.</p>
                    <a href="insertVol" class="link-button">Contacter <i class="fas fa-arrow-right"></i></a>
                </div>
            </div>
             <!-- Carte Lien 7 -->
            <div class="link-card">
                <div class="link-content">
                    <div class="link-icon">
                        <i class="fas fa-headset"></i>
                    </div>
                    <h3 class="link-title">Insertion du VolDetails</h3>
                    <p class="link-description">"Besoin d'aide pour votre vol Details? Notre équipe est disponible 24/7 pour vous accompagner.</p>
                    <a href="insertVolDetails" class="link-button">Contacter <i class="fas fa-arrow-right"></i></a>
                </div>
            </div>
        </div>
    </div>
</body>
</html>