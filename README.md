<p align="center">
  <h1 align="center">🌸 CleaApp</h1>
  <p align="center">
    <strong>Application Android de suivi du cycle menstruel</strong>
  </p>
  <p align="center">
    <em>Compagnon de santé féminine intelligent, intuitif et sécurisé</em>
  </p>
  <p align="center">
    <img src="https://img.shields.io/badge/Kotlin-2.1.0-purple?logo=kotlin" alt="Kotlin">
    <img src="https://img.shields.io/badge/Jetpack_Compose-Material3-blue?logo=jetpackcompose" alt="Compose">
    <img src="https://img.shields.io/badge/Min_SDK-24-green" alt="Min SDK">
    <img src="https://img.shields.io/badge/Target_SDK-35-green" alt="Target SDK">
    <img src="https://img.shields.io/badge/License-Proprietary-red" alt="License">
  </p>
</p>

---

## 📖 À propos

**CleaApp** est une application Android moderne de suivi du cycle menstruel développée par [DRCMind](https://github.com/drcmind). Elle permet aux utilisatrices de suivre leur cycle, enregistrer des symptômes quotidiens, consulter des prédictions de fertilité et gérer leur profil de santé — le tout connecté à un backend sécurisé via l'API [Clea by HerciaLabs](https://clea.hercialabs.com/).

---

## ✨ Fonctionnalités

### 🔐 Authentification
- **Connexion / Inscription** avec e-mail et mot de passe
- Gestion sécurisée des tokens (JWT + CSRF) via DataStore
- **Écran Splash** avec vérification automatique de la session
- **Onboarding** pour les nouveaux utilisateurs
- Déconnexion sécurisée

### 📅 Suivi du cycle menstruel
- **Dashboard interactif** avec vue d'ensemble du cycle actif
- Identification automatique de la **phase du cycle** (Menstruelle, Folliculaire, Ovulation, Lutéale)
- **Calendrier menstruel** visuel avec marqueurs de jours
- Création, modification et complétion de cycles
- Enregistrement quotidien : flux, douleur, humeur, température, poids, médicaments

### 📊 Prédictions & Statistiques
- **Prédictions de fertilité** : ovulation, fenêtre fertile, début/fin de règles
- Indice de **confiance** des prédictions
- Statistiques : durée moyenne du cycle/règles, nombre de cycles complétés, variation

### 🩺 Suivi des symptômes
- Bibliothèque de symptômes avec icônes et descriptions
- Association de symptômes aux jours du cycle
- Suivi de l'humeur (Heureux, Neutre, Triste, Anxieux, Irritable, Énergique)
- Niveaux de flux (Aucun, Léger, Modéré, Abondant, Très abondant)

### 👤 Profil utilisateur
- Modification du nom et de l'e-mail
- Changement de mot de passe sécurisé
- Visualisation des informations du compte

### 📱 Interface moderne
- **Material Design 3** avec thème dynamique
- **Navigation adaptative** (Navigation Suite Scaffold)
- Edge-to-edge display
- Composants réutilisables personnalisés (CleaButton, CleaTextField, CleaLogo)
- Bottom sheets, dialogues et animations fluides

---

## 🏗️ Architecture

CleaApp suit les principes de la **Clean Architecture** combinée au pattern **MVVM** (Model-View-ViewModel) :

```
📦 com.drcmind.cleaapp
├── 🎯 domain/                    # Couche Domaine (logique métier pure)
│   ├── model/                    # Modèles de domaine
│   │   ├── AuthResult.kt         # Sealed interface pour les résultats d'auth
│   │   ├── MenstrualModels.kt    # Cycle, CycleDay, Symptom, Prediction, Dashboard
│   │   └── User.kt               # Modèle utilisateur
│   └── repository/               # Interfaces (contrats) des repositories
│       ├── AuthRepository.kt     
│       └── MenstrualRepository.kt
│
├── 💾 data/                      # Couche Data (implémentation)
│   ├── local/                    # Sources de données locales
│   │   ├── TokenManager.kt       # Gestion des tokens JWT/CSRF (DataStore)
│   │   ├── datastore/
│   │   │   └── AuthDataStore.kt  # Persistance session & onboarding
│   │   └── room/                 # Base de données locale
│   │       ├── CleaDatabase.kt   # Configuration Room
│   │       ├── dao/
│   │       │   └── MenstrualDao.kt
│   │       └── entity/           # Entités Room
│   │           ├── CycleEntity.kt
│   │           ├── DayEntity.kt
│   │           ├── SymptomEntity.kt
│   │           └── DaySymptomCrossRef.kt
│   ├── remote/                   # Sources de données distantes
│   │   ├── api/
│   │   │   ├── AuthApi.kt        # Service API authentification
│   │   │   └── MenstrualApiService.kt  # Service API cycle menstruel
│   │   └── dto/                  # Data Transfer Objects
│   │       ├── AuthDtos.kt
│   │       ├── CycleDto.kt
│   │       ├── CycleDayDto.kt
│   │       ├── DashboardDto.kt
│   │       ├── PredictionDto.kt
│   │       ├── StatisticsDto.kt
│   │       └── SymptomDto.kt
│   ├── mapper/
│   │   └── MenstrualMapper.kt    # Mapping DTO ↔ Domain ↔ Entity
│   ├── model/                    # Enums et constantes de données
│   │   ├── CycleStatus.kt
│   │   ├── FlowLevel.kt
│   │   └── Mood.kt
│   └── repository/               # Implémentations des repositories
│       ├── AuthRepositoryImpl.kt
│       └── MenstrualRepositoryImpl.kt
│
├── 💉 di/                        # Injection de dépendances (Koin)
│   ├── AppModule.kt              # Module principal (DB, API, Repos, ViewModels)
│   └── NetworkModule.kt          # Configuration HttpClient Ktor
│
└── 🎨 ui/                        # Couche Présentation
    ├── auth/
    │   ├── splash/               # Écran de démarrage
    │   │   ├── SplashScreen.kt
    │   │   └── SplashViewModel.kt
    │   ├── onboarding/           # Écran d'introduction
    │   │   ├── OnboardingPage.kt
    │   │   └── OnboardingScreen.kt
    │   └── login/                # Connexion & Inscription
    │       ├── LoginScreen.kt
    │       ├── LoginViewModel.kt
    │       ├── LoginState.kt
    │       ├── SignInScreen.kt
    │       ├── SignInViewModel.kt
    │       └── SignInState.kt
    ├── home/
    │   ├── MainScreen.kt         # Écran principal (Navigation Suite)
    │   └── HomeScreen.kt         # Contenu de l'accueil
    ├── menstrual/
    │   ├── MenstrualDashboardScreen.kt
    │   ├── MenstrualViewModel.kt
    │   └── components/           # Composants spécifiques au cycle
    │       ├── AddLogBottomSheet.kt
    │       ├── CompleteCycleDialog.kt
    │       ├── MenstrualCalendar.kt
    │       └── MenstrualComponents.kt
    ├── profile/
    │   ├── ProfileScreen.kt
    │   └── ProfileViewModel.kt
    ├── components/               # Composants réutilisables
    │   ├── CleaButton.kt
    │   ├── CleaLogo.kt
    │   ├── CleaTextField.kt
    │   └── SocialLoginButton.kt
    ├── navigation/
    │   ├── AppDestination.kt     # Sealed interface des destinations
    │   └── AppNavigation.kt      # Configuration de la navigation (Nav3)
    └── theme/                    # Thème Material 3
```

---

## 🛠️ Stack technique

| Catégorie | Technologie | Version |
|---|---|---|
| **Langage** | Kotlin | 2.1.0 |
| **UI** | Jetpack Compose + Material 3 | BOM 2024.09.00 |
| **Navigation** | Navigation 3 (Nav3) | 1.1.4 |
| **Layouts adaptatifs** | Material 3 Adaptive | 1.0.0 |
| **Réseau** | Ktor Client (Android) | 3.5.2 |
| **Sérialisation** | Kotlinx Serialization | 1.9.0 |
| **Base de données** | Room | 2.8.4 |
| **Injection de dépendances** | Koin | 4.2.2 |
| **Stockage de préférences** | DataStore Preferences | 1.2.1 |
| **Images** | Coil Compose | 2.6.0 |
| **Traitement d'annotations** | KSP | 2.1.0-1.0.29 |
| **Splash Screen** | Core Splashscreen | 1.2.0 |
| **Build** | AGP | 9.1.1 |

---

## 🚀 Démarrage rapide

### Prérequis

- **Android Studio** Ladybug ou plus récent
- **JDK 11** ou supérieur
- **SDK Android** avec API 35
- Connexion Internet (l'app communique avec `https://clea.hercialabs.com/`)

### Installation

```bash
# 1. Cloner le dépôt
git clone https://github.com/drcmind/CleaApp.git
cd CleaApp

# 2. Ouvrir dans Android Studio
# File → Open → Sélectionner le dossier CleaApp

# 3. Synchroniser Gradle
# Android Studio synchronisera automatiquement les dépendances

# 4. Exécuter l'application
# Sélectionner un émulateur ou un appareil physique → Run ▶️
```

### Configuration

L'application est pré-configurée pour utiliser l'API **Clea by HerciaLabs**. Aucune configuration supplémentaire n'est requise pour le développement.

Le backend est accessible à : `https://clea.hercialabs.com/`

---

## 📐 Patterns & bonnes pratiques

- **Clean Architecture** : séparation stricte entre les couches domain, data et UI
- **MVVM** : ViewModels avec StateFlow pour la gestion d'état réactive
- **Repository Pattern** : abstraction des sources de données via des interfaces
- **DTO ↔ Domain Mapping** : isolation des modèles API des modèles métier
- **Sealed interfaces** : gestion typée des résultats (Success/Error/Loading) et des destinations de navigation
- **Dependency Injection** : Koin pour un DI léger et lisible
- **Offline-first** : Room pour le cache local des données menstruelles
- **Sécurité** : gestion des tokens JWT/CSRF avec DataStore chiffré

---

## 📱 Captures d'écran

> _À venir — Les captures d'écran seront ajoutées prochainement._

---

## 🤝 Contribution

Les contributions sont les bienvenues ! Pour contribuer :

1. **Fork** le projet
2. Créez une branche feature (`git checkout -b feature/ma-fonctionnalite`)
3. Commitez vos changements (`git commit -m "feat: ajout de ma fonctionnalité"`)
4. Poussez vers la branche (`git push origin feature/ma-fonctionnalite`)
5. Ouvrez une **Pull Request**

### Convention de commits

Ce projet suit la convention [Conventional Commits](https://www.conventionalcommits.org/) :
- `feat:` nouvelle fonctionnalité
- `fix:` correction de bug
- `docs:` documentation
- `refactor:` refactoring
- `style:` formatage
- `test:` tests

---

## 📄 Licence

Ce projet est un logiciel propriétaire développé par **DRCMind** pour **HerciaLabs**.

---

## 👥 Auteur

Développé avec ❤️ par **[DRCMind](https://github.com/drcmind)**

---

<p align="center">
  <strong>CleaApp</strong> — Votre compagnon de santé féminine 🌸
</p>
