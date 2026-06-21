package com.drcmind.cleaapp.ui.auth.onboarding

data class OnboardingPage(
    val title: String,
    val description: String,
    val animationRes: Int // Placeholder for Lottie or drawable resource id
)

val onboardingPages = listOf(
    OnboardingPage(
        title = "Suivi intuitif de votre bien-être",
        description = "Apprenez à connaître votre corps en toute sérénité. Sans jugement, juste vous et votre santé au quotidien.",
        animationRes = 0 // Placeholder
    ),
    OnboardingPage(
        title = "Une communauté bienveillante",
        description = "Échangez, partagez et soutenez-vous dans un espace sûr, positif et 100% privé, conçu spécialement pour vous.",
        animationRes = 0
    ),
    OnboardingPage(
        title = "Vos données, votre intimité",
        description = "Votre vie privée est notre priorité absolue. Vos données de santé sont cryptées avec les standards les plus stricts.",
        animationRes = 0
    )
)
