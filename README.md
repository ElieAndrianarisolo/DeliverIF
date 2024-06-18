# DeliverIF

## Introduction

DeliverIF est une application de gestion de livraisons conçue pour optimiser les tournées de livraison dans une ville. L'application permet de charger des cartes de ville, des demandes de livraison, de créer et assigner des demandes de livraison à des tournées, de déterminer des tournées optimisées et de générer des feuilles de route pour les coursiers.

## Table des matières

1. [Glossaire](#glossaire)
2. [Modèle du domaine](#modèle-du-domaine)
3. [Diagramme de cas d'utilisation](#diagramme-de-cas-dutilisation)
4. [Descriptions structurées des principaux cas d'utilisation](#descriptions-structurées-des-principaux-cas-dutilisation)
5. [Diagramme d'états-transitions](#diagramme-détats-transitions)
6. [Architecture MVC et motifs de conception](#architecture-mvc-et-motifs-de-conception)
7. [Diagrammes de classes et de packages](#diagrammes-de-classes-et-de-packages)
8. [Rapport de test](#rapport-de-test)
9. [Revue technique](#revue-technique)
10. [Problèmes environnementaux et sociaux](#problèmes-environnementaux-et-sociaux)
11. [Revue personnelle et commentaires](#revue-personnelle-et-commentaires)

## Glossaire

### Termes Clés

- **Carte de la ville**: Une vue d'ensemble détaillée de la disposition géographique d'une ville sous forme de plan 2D.
- **Coursier**: Individus responsables du transport et de la livraison de colis dans une ville à vélo.
- **Tour**: Itinéraire optimisé suivi par un coursier pour compléter une série de livraisons.
- **Fenêtre temporelle**: Intervalle de temps d'une heure pendant lequel une livraison spécifique doit avoir lieu.
- **Entrepôt**: Lieu central à partir duquel les coursiers commencent et terminent leurs tournées de livraison.
- **Fichier XML**: Fichiers utilisés pour charger les cartes de la ville, les demandes de livraison et les tournées précédemment sauvegardées.

## Descriptions structurées des principaux cas d'utilisation

### 1. Charger une carte de la ville

**Description**: Charger dans l'application un fichier XML de carte de la ville choisi par le planificateur de livraisons.

**Acteurs**: Planificateur de livraisons, Système de fichiers

**Préconditions**: L'application est lancée avec succès et prête à recevoir les entrées du planificateur de livraisons.

**Postconditions**: La carte de la ville est chargée dans l'application, fournissant les données géographiques nécessaires à la planification des tournées des coursiers.

**Scénario principal**:
1. Le planificateur de livraisons clique sur le bouton “Charger une carte de la ville”.
2. Le système ouvre une boîte de dialogue de fichiers.
3. Le planificateur de livraisons sélectionne un fichier XML de carte de la ville.
4. Le système charge le fichier XML choisi.
5. Le système dessine la carte correspondant aux données décrites dans le fichier XML.

### 2. Charger un fichier de demandes de livraison

**Description**: Charger dans l'application un fichier XML de demandes de livraison choisi par le planificateur de livraisons.

**Acteurs**: Planificateur de livraisons, Système de fichiers

**Préconditions**:
- L'application est lancée avec succès et prête à recevoir les entrées du planificateur de livraisons.
- Une carte de la ville a été chargée dans l'application.

**Postconditions**: Les lieux de livraison du fichier de demandes de livraison sélectionné sont affichés sur la carte de la ville.

**Scénario principal**:
1. Le planificateur de livraisons clique sur le bouton “Charger des demandes de livraison”.
2. Le système ouvre une fenêtre de sélection de fichier.
3. Le planificateur de livraisons sélectionne un fichier XML de livraison.
4. Le système charge le fichier XML choisi.
5. Le système ajoute les demandes de livraison chargées aux demandes de livraison disponibles.

### 3. Créer une demande de livraison

**Description**: Créer une demande de livraison depuis l'application en sélectionnant une intersection et une fenêtre temporelle.

**Acteurs**: Planificateur de livraisons

**Préconditions**:
- L'application est lancée avec succès et prête à recevoir les entrées du planificateur de livraisons.
- Une carte de la ville a été chargée dans l'application.
- Une demande de livraison a été assignée au coursier sélectionné.
- Au moins une tournée a été calculée avec succès.

**Postconditions**: Pour chaque tournée existante, une feuille de route est générée.

**Scénario principal**:
1. L'utilisateur clique sur le bouton “Créer une demande de livraison”.
2. Le planificateur de livraisons sélectionne une fenêtre temporelle.
3. Le planificateur de livraisons sélectionne une intersection sur la carte de la ville en cliquant dessus.
4. Le planificateur de livraisons spécifie si c'est un entrepôt ou non.
5. Le planificateur de livraisons clique sur le bouton “Valider la demande de livraison créée”.
6. Le système ajoute la demande de livraison créée aux demandes de livraison disponibles.

## Diagramme d'états-transitions

![Diagramme d'états-transitions](path/to/state-transition-diagram.png)

## Architecture MVC et motifs de conception

### MVC

L'approche Model-View-Controller (MVC) a été choisie pour garantir la maintenabilité, l'évolutivité et la flexibilité à long terme de l'application. Cette architecture permet de séparer l'application en trois préoccupations distinctes:

1. **Modèle**: Gestion et manipulation des entités nécessaires.
2. **Vue**: Interface utilisateur et présentation des données.
3. **Contrôleur**: Ajustement du modèle à la vue.

### Motifs de conception

- **Command**: Encapsule une requête en tant qu'objet, permettant aux utilisateurs de paramétrer les clients avec différentes requêtes, de mettre en file d'attente les requêtes, et de supporter les opérations annulables.
- **State**: Permet de changer le contexte de l'application, naviguant entre différents modes autorisés.
- **Observer**: Utilisé pour l'IHM avec une souris et pour montrer les composants sélectionnés, améliorant les performances lorsqu'il y a beaucoup d'objets.

## Diagrammes de classes et de packages

![Diagrammes de classes et de packages](path/to/class-and-package-diagrams.png)

## Rapport de test

Les tests sont effectués pour garantir le bon fonctionnement de l'application et prévenir toute régression.

## Revue technique

Une analyse technique approfondie a été réalisée pour vérifier l'efficacité et la performance de l'application.

## Problèmes environnementaux et sociaux

Les implications environnementales et sociales ont été examinées pour s'assurer que l'application contribue positivement à la société et minimise son impact environnemental.

## Revue personnelle et commentaires

Les membres de l'équipe ont partagé leurs retours personnels et leurs commentaires sur le projet, offrant des perspectives sur les défis rencontrés et les leçons apprises.

---

Pour plus de détails, veuillez consulter la documentation complète disponible dans le dossier `documentation`.
