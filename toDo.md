## Conception MCD
- admin :
   - id
   - mail
   - mdp

- modele :
   - id
   - libelle

- type_siege :
   - id
   - libelle

- avion :
   - id
   - nom
   - id_modele
   - date_fabrication

- avion_detail :
   - id
   - id_avion
   - id_type_siege
   - nombre

- passager :
   - id
   - nom
   - prenom
   - mail

- ville :
   - id
   - nom
   - pays

- vol :
   - id
   - dateHeureDepart
   - dateHeureArrivee
   - id_avion
   - id_ville_depart
   - id_ville_arrivee

- vol_detail :
   - id
   - id_vol
   - id_type_siege
   - prix
   - nombre disponible

- etat_reservation :
   - id
   - libelle

- reservation :
   - id
   - dateHeure
   - id_passager
   - id_etat_reservation

- reservation_detail :
   - id_reservation_detail
   - id_reservation
   - id_vol
   - id_type_siege
   - nombre

- promotion :
   - id
   - dateHeure
   - date_expiration
   - id_vol
   - id_type_siege
   - nombre
   - pourcentage_promotion

- reservation_configuration:
   - id
   - heure_limite_reservation
   - heure_limite_annulation

## To do list :
   # préparation du projet 
   - créer un projet netbeans  : ok
   - ajouter les jar nécéssaires dans le projet : ok
   - recherche d'un template pour le projet : ok
   - intégrer le template dans le projet : ok
   - test si cela marche   : ok
   - créer le repertoire git vol : ok
   - creer et cloner le projet  : ok
   - configurer le web.xml

   # login :
   - Affichage : mail, mdp, valider
   - Metier :  Login, Connection
   - Controller : appel de la fonction login
      - si true : creation de la session
      - si false : renvoi du message d'erreur

   # CRUD vol :
      #### **1️⃣ CREATE (Ajout d’un vol)**
      - **Affichage** :
      - Formulaire contenant :
         - Date et heure de départ
         - Date et heure d’arrivée
         - Sélection d’un avion
         - Sélection de la ville de départ
         - Sélection de la ville d’arrivée
         - Bouton "Enregistrer"
      - **Métier (Service/DAO)** :
      - Vérifier si les informations sont valides  
      - Vérifier la disponibilité de l’avion  
      - Insérer les données dans la base  
      - **Controller** :  
      - Récupérer les données du formulaire  
      - Appeler la fonction d’insertion  
      - Afficher un message de succès ou d’échec  
      - **Base de données** :  
      - Ajouter une requête `INSERT INTO vol (...) VALUES (...)`  

      ---

      #### **2️⃣ READ (Liste des vols)**  
      - **Affichage** :  
      - Tableau listant :  
         - ID  
         - Date et heure de départ  
         - Date et heure d’arrivée  
         - Nom de l’avion  
         - Ville de départ  
         - Ville d’arrivée  
         - Boutons "Modifier" et "Supprimer"  
      - **Métier (Service/DAO)** :
      - Récupérer la liste des vols
      - **Controller** :
      - Appeler la fonction de récupération des vols
      - Envoyer la liste à la vue  
      - **Base de données** :  
      - Requête `SELECT * FROM vol` avec jointures pour récupérer les noms des villes et des avions  

      ---

      #### **3️⃣ UPDATE (Modification d’un vol)**  
      - **Affichage** :  
      - Formulaire pré-rempli avec les informations actuelles du vol  
      - Possibilité de modifier :  
         - Date et heure de départ  
         - Date et heure d’arrivée  
         - Avion  
         - Ville de départ  
         - Ville d’arrivée  
      - Bouton "Mettre à jour"  
      - **Métier (Service/DAO)** :  
      - Vérifier les nouvelles valeurs  
      - Vérifier la disponibilité de l’avion  
      - Mettre à jour les informations du vol  
      - **Controller** :  
      - Récupérer l’ID du vol à modifier  
      - Charger les informations actuelles dans le formulaire  
      - Récupérer les nouvelles données et appeler la fonction de mise à jour  
      - Afficher un message de confirmation  
      - **Base de données** :  
      - Requête `UPDATE vol SET ... WHERE id = ?`  

      ---

      #### **4️⃣ DELETE (Suppression d’un vol)**  
      - **Affichage** :  
      - Bouton "Supprimer" à côté de chaque vol dans la liste  
      - Popup de confirmation avant suppression  
      - **Métier (Service/DAO)** :  
      - Vérifier si le vol est encore utilisé dans une réservation  
      - Supprimer le vol si aucune contrainte  
      - **Controller** :  
      - Récupérer l’ID du vol à supprimer  
      - Appeler la fonction de suppression  
      - Rafraîchir la liste après suppression  
      - **Base de données** :  
      - Requête `DELETE FROM vol WHERE id = ?`  

      ### ✅ **Recherche Multi-Critères sur un Vol**  
         - Rechercher un vol en fonction de plusieurs critères :
         - Date de départ  
         - Ville de départ  
         - Ville d’arrivée  
         - Avion  
         - Période (Plage de dates)  

         #### **1️⃣ Affichage (Frontend)**  
         - Formulaire de recherche contenant :  
         - Champ "Date de départ" (sélection de date)  
         - Sélection de la ville de départ  
         - Sélection de la ville d’arrivée  
         - Sélection d’un avion (optionnel)  
         - Bouton "Rechercher"  
         - Affichage des résultats sous forme de tableau  

         #### **2️⃣ Métier (Service/DAO)**  
         - Vérifier les critères remplis  
         - Construire dynamiquement la requête SQL en fonction des critères fournis  
         - Retourner la liste des vols correspondants  

         #### **3️⃣ Controller**  
         - Récupérer les critères du formulaire  
         - Appeler la fonction de recherche  
         - Afficher les résultats dans la vue  

         #### **4️⃣ Base de données**  
         - Requête SQL dynamique (exemple) :  
         ```sql
         SELECT v.id, v.dateHeureDepart, v.dateHeureArrivee, a.nom AS avion, vd.nom AS ville_depart, va.nom AS ville_arrivee
         FROM vol v
         JOIN avion a ON v.id_avion = a.id
         JOIN ville vd ON v.id_ville_depart = vd.id
         JOIN ville va ON v.id_ville_arrivee = va.id
         WHERE (v.dateHeureDepart >= ? OR ? IS NULL)
         AND (v.id_ville_depart = ? OR ? IS NULL)
         AND (v.id_ville_arrivee = ? OR ? IS NULL)
         AND (v.id_avion = ? OR ? IS NULL)
         ```
         - Utilisation de `PreparedStatement` pour passer les paramètres dynamiquement.  

         ---

         ### ✅ **Réservation d’un Vol**  

         #### **🔍 Fonctionnalités**  
         - Sélection d’un vol  
         - Sélection du type de siège  
         - Nombre de places  
         - Vérification de la disponibilité  
         - Validation de la réservation  

         #### **1️⃣ Affichage (Frontend)**  
         - Formulaire contenant :  
         - Liste des vols disponibles  
         - Sélection du type de siège  
         - Champ "Nombre de places"  
         - Bouton "Réserver"  

         #### **2️⃣ Métier (Service/DAO)**  
         - Vérifier la disponibilité du vol et du nombre de places demandées  
         - Vérifier la date limite de réservation  
         - Insérer la réservation et ses détails dans la base  

         #### **3️⃣ Controller**  
         - Récupérer les informations du formulaire  
         - Vérifier les disponibilités  
         - Insérer la réservation  
         - Afficher un message de succès ou d’erreur  

         #### **4️⃣ Base de données**  
         - Vérification de la disponibilité :  
         ```sql
         SELECT nombre_disponible FROM vol_detail
         WHERE id_vol = ? AND id_type_siege = ?
         ```
         - Insertion de la réservation :  
         ```sql
         INSERT INTO reservation (dateHeure, id_passager, id_etat_reservation) VALUES (?, ?, ?)
         ```
         - Insertion des détails de réservation :  
         ```sql
         INSERT INTO reservation_detail (id_reservation, id_vol, id_type_siege, nombre) VALUES (?, ?, ?, ?)
         ```
         - Mise à jour du nombre de places disponibles :  
         ```sql
         UPDATE vol_detail SET nombre_disponible = nombre_disponible - ?
         WHERE id_vol = ? AND id_type_siege = ?
         ```

