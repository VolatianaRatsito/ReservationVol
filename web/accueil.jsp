<!DOCTYPE html>
<html lang="fr">
<head>
<meta charset="UTF-8" />
<meta name="viewport" content="width=device-width, initial-scale=1" />
<title>Tableau de Bord Admin - Airport International</title>

<!-- Font Awesome -->
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css" />
<!-- Google Fonts Poppins -->
<link href="https://fonts.googleapis.com/css2?family=Poppins:wght@300;500;700&display=swap" rel="stylesheet" />
<!-- Chart.js -->
<script src="https://cdn.jsdelivr.net/npm/chart.js"></script>

<style>
/* ===== RESET & BASE ===== */
* {
  margin: 0;
  padding: 0;
  box-sizing: border-box;
}

body {
  font-family: 'Poppins', sans-serif;
  background: linear-gradient(135deg, #f0f4f8, #d9e4ec);
  display: flex;
  min-height: 100vh;
  color: #333;
}

/* ===== BACKGROUND IMAGE ===== */
.page-background {
  position: fixed;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  background: url('https://images.unsplash.com/photo-1536323760109-ca8c07450053') no-repeat center/cover;
  filter: blur(6px) brightness(0.7);
  z-index: -1;
}

/* ===== SIDEBAR ===== */
.sidebar {
  width: 260px;
  background: rgba(28, 37, 65, 0.95);
  color: white;
  padding: 2rem 1rem;
  display: flex;
  flex-direction: column;
  height: 100vh;
  position: fixed;
  left: 0;
  top: 0;
  overflow-y: auto;
  box-shadow: 2px 0 12px rgba(0, 0, 0, 0.3);
}

.sidebar h2 {
  text-align: center;
  font-size: 1.5rem;
  margin-bottom: 2rem;
  font-weight: 700;
  letter-spacing: 1px;
}

.sidebar ul {
  list-style: none;
}

.sidebar ul li {
  margin-bottom: 1rem;
}

.sidebar ul li a {
  color: white;
  text-decoration: none;
  padding: 0.7rem 1rem;
  display: flex;
  align-items: center;
  gap: 10px;
  border-radius: 8px;
  transition: background 0.3s ease, padding-left 0.3s ease;
}

.sidebar ul li a:hover {
  background: rgba(255, 255, 255, 0.15);
  padding-left: 1.3rem;
}

.sidebar ul li ul {
  margin-top: 0.5rem;
  margin-left: 1.5rem;
}

.sidebar ul li ul li a {
  font-size: 0.9rem;
  opacity: 0.85;
}

/* ===== MAIN CONTENT ===== */
.main-container {
  flex: 1;
  margin-left: 260px;
  padding: 2rem;
}

header {
  background: rgba(255, 255, 255, 0.85);
  backdrop-filter: blur(6px);
  padding: 1rem 2rem;
  border-radius: 12px;
  box-shadow: 0 4px 12px rgba(0, 0, 0, 0.1);
  margin-bottom: 2rem;
}

header h1 {
  font-size: 1.8rem;
  color: #1e293b;
}

/* ===== DASHBOARD CARDS ===== */
.cards {
  display: grid;
  grid-template-columns: repeat(auto-fit, minmax(220px, 1fr));
  gap: 1.5rem;
}

.card {
  background: white;
  padding: 1.5rem;
  border-radius: 12px;
  box-shadow: 0 4px 16px rgba(0, 0, 0, 0.05);
  text-align: center;
  transition: transform 0.3s ease;
}

.card:hover {
  transform: translateY(-6px);
}

.card i {
  font-size: 2rem;
  margin-bottom: 1rem;
  color: #2563eb;
}

.card h3 {
  font-size: 1.2rem;
  margin-bottom: 0.5rem;
}

.card p {
  color: #666;
}

/* ===== SCROLLBAR STYLE ===== */
.sidebar::-webkit-scrollbar {
  width: 6px;
}

.sidebar::-webkit-scrollbar-thumb {
  background-color: rgba(255, 255, 255, 0.3);
  border-radius: 3px;
}

</style>
</head>
<body>

  <div class="page-background"></div>

  <div class="main-container">
    <nav class="sidebar" aria-label="Menu principal">
      <h2>Airport Ticketing</h2>
      <ul>
        <li><a href="index" title="Accueil"><i class="fas fa-home"></i> Accueil</a></li> 
        <li><a href="demande" title="Réservations"><i class="fas fa-calendar-check"></i> Réservations</a></li>
        <li><a href="reservationList" title="Réservations"><i class="iconClass"></i>Liste des Réservations</a></li>
        <li>
          <a href="vols" title="Vols"><i class="fas fa-plane"></i> Vols</a>
          <ul>
            <li><a href="searchVols_redirect" title="Rechercher des vols">Rechercher des vols</a></li>
            <li><a href="insert_redirect" title="Insertion du vol">Insertion du Vol</a></li>
            <li><a href="volDetails" title="Détails du vol">Insertion du VolDetails</a></li>
          </ul>
        </li>
        <li>
          <a href="passagers" title="Passagers"><i class="fas fa-user-friends"></i> Passagers</a>
          <ul>
            <li><a href="passagers" title="Liste des passagers">Passagers</a></li>
            <li><a href="listPassport" title="Liste des passeports">Liste des Passeports</a></li>
            <li><a href="addPassport" title="Ajouter un passeport">Ajouter un Passeport</a></li>
          </ul>
        </li>
        <li><a href="add-form" title="Promotion"><i class="fas fa-chart-line"></i> Promotion</a></li>
      </ul>
    </nav>

  


</body>
</html>
