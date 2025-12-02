package com.filippo.dieta

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.filippo.dieta.data.DayPlan
import com.filippo.dieta.data.MealPlan
import com.filippo.dieta.data.ShoppingCategory
import com.filippo.dieta.ui.theme.DietaTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            DietaTheme {
                Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
                    DietaApp()
                }
            }
        }
    }
}

@Composable
fun DietaApp() {
    var selectedTab by remember { mutableStateOf(0) }
    val tabs = listOf("Ricette", "Lista")

    Scaffold(
        topBar = {
            Column {
                CenterAlignedTopAppBar(title = { Text(text = stringResource(id = R.string.app_name)) })
                TabRow(selectedTabIndex = selectedTab) {
                    tabs.forEachIndexed { index, title ->
                        Tab(
                            selected = selectedTab == index,
                            onClick = { selectedTab = index },
                            text = { Text(title) }
                        )
                    }
                }
            }
        }
    ) { padding ->
        Box(Modifier.padding(padding)) {
            when (selectedTab) {
                0 -> RecipesScreen(MealPlan.weekPlan)
                else -> ShoppingListScreen(MealPlan.shoppingCategories)
            }
        }
    }
}

@Composable
fun RecipesScreen(days: List<DayPlan>) {
    LazyColumn(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        items(days) { day ->
            Text(text = "🍴 ${day.day}", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
            Spacer(Modifier.height(8.dp))
            Text(text = "- Pranzo (13:00):", fontWeight = FontWeight.SemiBold)
            day.lunch.forEach { line -> Text("\u2022 $line") }
            Spacer(Modifier.height(6.dp))
            Text(text = "- Spuntino (16:30):", fontWeight = FontWeight.SemiBold)
            day.snack.forEach { line -> Text("\u2022 $line") }
            if (day.dinner.isNotEmpty()) {
                Spacer(Modifier.height(6.dp))
                Text(text = "- Cena (20:00):", fontWeight = FontWeight.SemiBold)
                day.dinner.forEach { line -> Text("\u2022 $line") }
            } else {
                Spacer(Modifier.height(6.dp))
                Text(text = "- Cena (20:00): ❌ Saltata")
            }
            Spacer(Modifier.height(16.dp))
            Divider()
            Spacer(Modifier.height(16.dp))
        }
    }
}

@Composable
fun ShoppingListScreen(categories: List<ShoppingCategory>) {
    LazyColumn(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        items(categories) { cat ->
            Text(text = cat.name, style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
            Spacer(Modifier.height(8.dp))
            cat.items.forEach { item ->
                Text(text = "\u2022 ${item.name}: ${item.quantity}")
            }
            Spacer(Modifier.height(16.dp))
            Divider()
            Spacer(Modifier.height(16.dp))
        }
    }
}

@Composable
private fun stringResource(id: Int): String = androidx.compose.ui.res.stringResource(id)
