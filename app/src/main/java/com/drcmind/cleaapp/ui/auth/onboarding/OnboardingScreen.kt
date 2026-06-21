package com.drcmind.cleaapp.ui.auth.onboarding

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.drcmind.cleaapp.data.local.datastore.AuthDataStore
import kotlinx.coroutines.launch
import org.koin.compose.koinInject

@Composable
fun OnboardingScreen(
    onFinish: () -> Unit,
    dataStore: AuthDataStore = koinInject()
) {
    val pagerState = rememberPagerState(pageCount = { onboardingPages.size })
    val scope = rememberCoroutineScope()

    // Soft white background
    Column(modifier = Modifier
        .fillMaxSize()
        .background(MaterialTheme.colorScheme.surface)) {
        
        // Header with Skip Button
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 40.dp, end = 24.dp),
            horizontalArrangement = Arrangement.End
        ) {
            TextButton(
                onClick = {
                    scope.launch {
                        dataStore.setOnboardingCompleted()
                        onFinish()
                    }
                }
            ) {
                Text(
                    text = "Passer", 
                    color = MaterialTheme.colorScheme.secondary,
                    style = MaterialTheme.typography.labelMedium
                )
            }
        }

        // Pager Content
        HorizontalPager(
            state = pagerState,
            modifier = Modifier.weight(1f),
            contentPadding = PaddingValues(horizontal = 24.dp),
            pageSpacing = 16.dp
        ) { position ->
            PagerScreen(onboardingPage = onboardingPages[position])
        }

        // Footer
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp, vertical = 40.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Pager Indicator (Lime Green for active, Light Grey for inactive)
            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier.padding(bottom = 32.dp)
            ) {
                repeat(onboardingPages.size) { iteration ->
                    val isCurrentPage = pagerState.currentPage == iteration
                    val color = if (isCurrentPage) MaterialTheme.colorScheme.tertiary else MaterialTheme.colorScheme.surfaceVariant
                    val width by animateDpAsState(if (isCurrentPage) 32.dp else 10.dp, label = "indicator_width")
                    Box(
                        modifier = Modifier
                            .height(10.dp)
                            .width(width)
                            .clip(CircleShape)
                            .background(color)
                    )
                }
            }

            // Next / Start Button
            Button(
                onClick = {
                    scope.launch {
                        if (pagerState.currentPage < onboardingPages.size - 1) {
                            pagerState.animateScrollToPage(pagerState.currentPage + 1)
                        } else {
                            dataStore.setOnboardingCompleted()
                            onFinish()
                        }
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp), // 48px minimum as per guidelines, 56 is more tactile
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    contentColor = MaterialTheme.colorScheme.onPrimary
                ),
                shape = RoundedCornerShape(12.dp)
            ) {
                Text(
                    text = if (pagerState.currentPage == onboardingPages.size - 1) "Commencer" else "Suivant",
                    style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.Bold)
                )
            }
        }
    }
}

@Composable
fun PagerScreen(onboardingPage: OnboardingPage) {
    // Card with soft ambient shadow
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(0.85f)
            .shadow(
                elevation = 12.dp, 
                shape = RoundedCornerShape(24.dp), 
                ambientColor = MaterialTheme.colorScheme.secondary.copy(alpha = 0.1f),
                spotColor = MaterialTheme.colorScheme.secondary.copy(alpha = 0.1f)
            )
            .background(Color(0xFFFFFFFF), RoundedCornerShape(24.dp))
            .padding(32.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            // Placeholder for an illustration/icon
            Box(
                modifier = Modifier
                    .size(200.dp)
                    .clip(CircleShape)
                    .background(MaterialTheme.colorScheme.primaryContainer),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "Illustration", 
                    color = MaterialTheme.colorScheme.onPrimaryContainer,
                    style = MaterialTheme.typography.labelMedium
                )
            }

            Spacer(modifier = Modifier.height(48.dp))

            Text(
                text = onboardingPage.title,
                style = MaterialTheme.typography.headlineMedium,
                textAlign = TextAlign.Center,
                color = MaterialTheme.colorScheme.onSurface
            )

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = onboardingPage.description,
                style = MaterialTheme.typography.bodyMedium,
                textAlign = TextAlign.Center,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}
