**SUPMTI - RABAT** 

**Module : Programmation Java** Année universitaire 2025 — 2026 

## **E X A M E N  F I N A L** 

## **Mini-Projet Java** 

_Guide du étudiant — Conception, développement et soutenance_ 

**Enseignant responsable** 

**Pr. Soufiane HAMIDA** 

_Maître de Conférences — Informatique & Intelligence Artificielle_ 

## **1.  INSTRUCTIONS GÉNÉRALES** 

L'examen final consiste en la réalisation d'un mini-projet Java mettant en œuvre les compétences acquises durant le module. Le travail peut être réalisé individuellement, en binôme, en trinôme ou en groupe de quatre étudiants. 

Le projet doit obligatoirement inclure les éléments suivants : 

- Une interface graphique développée avec JavaFX 

- Une connexion à une base de données (MySQL, PostgreSQL ou SQLite) 

- Les opérations CRUD complètes : Create, Read, Update, Delete 

- Une architecture propre et bien organisée (modèle MVC recommandé) 

- Une documentation minimale dans le code (commentaires Javadoc) 

## **Modalités de travail** 

**Composition des équipes** 

**Le projet peut être réalisé selon l'une des configurations suivantes :** travail individuel, binôme (2 étudiants), trinôme (3 étudiants) ou groupe de quatre (4 étudiants). 

Les attendus pédagogiques et la qualité de livraison restent identiques quelle que soit la taille de l'équipe. Une équipe plus nombreuse devra démontrer une ambition fonctionnelle et technique proportionnelle. 

Lien pour inscrire les noms des étudiants de chaque groupe : 

https://forms.gle/LLrsfrynshTierJf9 

Chaque équipe ne remplit le formulaire QU'UNE SEULE FOIS (par le chef d'équipe). 

_Pr. Soufiane HAMIDA  •  Examen Java — Mini-Projet  •  Page_ _**1** /_ _**4**_ 

## **2.  CRITÈRES D'ÉVALUATION** 

La notation du mini-projet repose sur les cinq critères suivants, évalués lors de la lecture du code source, de l'exécution de l'application et de la soutenance orale. 

|||
|---|---|
|**Critère**|**Description**|
|||
|**Fonctionnalité**|Complétude des opérations CRUD et respect des exigences|
|**Interface**|Qualité et ergonomie de l'interface JavaFX|
|**Code**|Qualité, organisation et commentaires du code|
|**Base de données**|Conception et implémentation de la BD|
|**Originalité**|Créativité et valeur ajoutée du projet|



## **3.  LISTE DES SUJETS PROPOSÉS** 

Vingt sujets de gestion sont proposés ci-dessous. Chaque sujet implique la mise en œuvre complète des opérations CRUD ainsi qu'une interface JavaFX adaptée au contexte fonctionnel. 

|||
|---|---|
|**Colonne A**|**Colonne B**|
|||
|**01.**Gestion de bibliothèque (livres,<br>membres, emprunts)|**11.**Système de gestion de salle de sport<br>(membres, abonnements)|
|**02.**Système de réservation hôtelière|**12.**Application de gestion de contacts|
|**03.**Gestion de stock de magasin|**13.**Système de vente de billets de cinéma /<br>théâtre|
|**04.**Système de gestion scolaire (étudiants,<br>cours, notes)|**14.**Gestion de pharmacie (médicaments,<br>ventes)|
|**05.**Application de gestion de tâches (To-Do<br>List)|**15.**Plateforme de gestion de contenu (blog)|
|**06.**Système de gestion de restaurant<br>(commandes, menus)|**16.**Système de gestion de banque (comptes,<br>transactions)|
|**07.**Gestion de clinique (patients, rendez-<br>vous)|**17.**Application de recettes de cuisine|



_Pr. Soufiane HAMIDA  •  Examen Java — Mini-Projet  •  Page_ _**2** /_ _**4**_ 

|||
|---|---|
|**Colonne A**|**Colonne B**|
|||
|**08.**Application de gestion de budget<br>personnel|**18.**Gestion de flotte de véhicules|
|**09.**Système de location de voitures|**19.**Système de suivi de fitness (exercices,<br>progrès)|
|**10.**Plateforme de gestion de projets|**20.**Plateforme de gestion d'événements|



_Note : tout sujet hors liste doit faire l'objet d'une validation préalable par l'enseignant._ 

## **4.  STRUCTURE RECOMMANDÉE DU PROJET** 

L'arborescence ci-dessous illustre l'organisation attendue du code source. Elle reflète une séparation claire entre les couches contrôleur, modèle et vue. 

```
src/
├── main/
│   ├── java/
│   │   ├── controllers/    // Contrôleurs JavaFX
│   │   ├── models/         // Classes métier et DAO
│   │   ├── views/          // Fichiers FXML
│   │   └── Main.java       // Classe principale
│   └── resources/          // CSS, images, etc.
└── test/                   // Tests unitaires
```

## **5.  CALENDRIER ET LIVRABLES** 

**Dates clés Date de remise du projet : 21/06/2026 .** 

**Date de présentation orale :** _communiquée ultérieurement._ 

## **Format de remise** 

Le livrable consiste en une archive ZIP unique contenant les éléments suivants : 

- Le code source complet du projet 

- Le script SQL de création de la base de données 

- Un rapport détaillé sur l'application, illustré par des captures d'écran (screenshots) 

- Pour les projets volumineux : déposer le projet sur un Drive personnel et placer le lien dans un fichier **README.txt** inclus dans l'archive. 

_Pr. Soufiane HAMIDA  •  Examen Java — Mini-Projet  •  Page_ _**3** /_ _**4**_ 

## **6.  CONSEILS MÉTHODOLOGIQUES** 

Pour mener à bien votre projet dans les délais impartis, il est vivement recommandé de respecter la démarche progressive ci-dessous : 

**1.** Commencez par concevoir votre base de données (schéma relationnel, contraintes, jeux de tests). 

**2.** Utilisez le pattern DAO pour structurer l'accès aux données et isoler la logique persistance. 

**3.** Testez chaque fonctionnalité au fur et à mesure de son implémentation (approche incrémentale). 

**4.** Documentez votre code avec des commentaires clairs (Javadoc sur les classes et méthodes publiques). 

**5.** Sauvegardez régulièrement votre travail (Git recommandé, à défaut copies versionnées datées). 

_**Bon courage dans la réalisation de votre projet.**_ 

_Pr. Soufiane HAMIDA  •  Examen Java — Mini-Projet  •  Page_ _**4** /_ _**4**_ 

