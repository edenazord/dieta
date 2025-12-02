package com.filippo.dieta

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.filippo.dieta.data.DayPlan
import com.filippo.dieta.data.MealPlan
import com.filippo.dieta.data.ShoppingCategory
import com.filippo.dieta.ui.theme.DietaTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            DietaTheme {
                Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
                    DietaApp()
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DietaApp() {
    var selectedTab by remember { mutableStateOf(0) }
    val tabs = listOf("Ricette" to Icons.Default.Restaurant, "Lista" to Icons.Default.ShoppingCart)
    val scrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior()

    Scaffold(
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            Column {
                LargeTopAppBar(
                    title = { Text(text = stringResource(id = R.string.app_name)) },
                    scrollBehavior = scrollBehavior
                )
                TabRow(selectedTabIndex = selectedTab) {
                    tabs.forEachIndexed { index, (title, icon) ->
                        Tab(
                            selected = selectedTab == index,
                            onClick = { selectedTab = index },
                            text = { Text(title) },
                            icon = { Icon(icon, contentDescription = title) }
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
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        items(days) { day ->
            ElevatedCard(modifier = Modifier.fillMaxWidth()) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Row(verticalAlignment = androidx.compose.ui.Alignment.CenterVertically) {
                        Icon(Icons.Default.CalendarToday, contentDescription = null, modifier = Modifier.size(24.dp))
                        Spacer(Modifier.width(8.dp))
                        Text(text = day.day, style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold)
                    }
                    Spacer(Modifier.height(12.dp))

                    Row(verticalAlignment = androidx.compose.ui.Alignment.Top) {
                        Icon(Icons.Default.LunchDining, contentDescription = null, modifier = Modifier.size(20.dp), tint = MaterialTheme.colorScheme.primary)
                        Spacer(Modifier.width(8.dp))
                        Column {
                            Text(text = "Pranzo (13:00)", fontWeight = FontWeight.SemiBold, style = MaterialTheme.typography.titleSmall)
                            day.lunch.forEach { line -> Text("\u2022 $line", style = MaterialTheme.typography.bodyMedium) }
                        }
                    }
                    Spacer(Modifier.height(12.dp))

                    Row(verticalAlignment = androidx.compose.ui.Alignment.Top) {
                        Icon(Icons.Default.Icecream, contentDescription = null, modifier = Modifier.size(20.dp), tint = MaterialTheme.colorScheme.secondary)
                        Spacer(Modifier.width(8.dp))
                        Column {
                            Text(text = "Spuntino (16:30)", fontWeight = FontWeight.SemiBold, style = MaterialTheme.typography.titleSmall)
                            day.snack.forEach { line -> Text("\u2022 $line", style = MaterialTheme.typography.bodyMedium) }
                        }
                    }

                    if (day.dinner.isNotEmpty()) {
                        Spacer(Modifier.height(12.dp))
                        Row(verticalAlignment = androidx.compose.ui.Alignment.Top) {
                            Icon(Icons.Default.DinnerDining, contentDescription = null, modifier = Modifier.size(20.dp), tint = MaterialTheme.colorScheme.tertiary)
                            Spacer(Modifier.width(8.dp))
                            Column {
                                Text(text = "Cena (20:00)", fontWeight = FontWeight.SemiBold, style = MaterialTheme.typography.titleSmall)
                                day.dinner.forEach { line -> Text("\u2022 $line", style = MaterialTheme.typography.bodyMedium) }
                            }
                        }
                    } else {
                        Spacer(Modifier.height(12.dp))
                        Row(verticalAlignment = androidx.compose.ui.Alignment.CenterVertically) {
                            Icon(Icons.Default.Block, contentDescription = null, modifier = Modifier.size(20.dp), tint = MaterialTheme.colorScheme.error)
                            Spacer(Modifier.width(8.dp))
                            Text(text = "Cena (20:00): Saltata", style = MaterialTheme.typography.bodyMedium, color = MaterialTheme.colorScheme.error)
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun ShoppingListScreen(categories: List<ShoppingCategory>) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        items(categories) { cat ->
            ElevatedCard(modifier = Modifier.fillMaxWidth()) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Row(verticalAlignment = androidx.compose.ui.Alignment.CenterVertically) {
                        Icon(
                            imageVector = when {
                                cat.name.contains("Proteine", ignoreCase = true) -> Icons.Default.EggAlt
                                cat.name.contains("Cereali", ignoreCase = true) -> Icons.Default.Grain
                                cat.name.contains("Verdure", ignoreCase = true) -> Icons.Default.Yard
                                cat.name.contains("Patate", ignoreCase = true) -> Icons.Default.SetMeal
                                cat.name.contains("Frutta", ignoreCase = true) -> Icons.Default.LocalFlorist
                                cat.name.contains("Extra", ignoreCase = true) -> Icons.Default.Liquor
                                else -> Icons.Default.Category
                            },
                            contentDescription = null,
                            modifier = Modifier.size(24.dp),
                            tint = MaterialTheme.colorScheme.primary
                        )
                        Spacer(Modifier.width(8.dp))
                        Text(text = cat.name, style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
                    }
                    Spacer(Modifier.height(8.dp))
                    cat.items.forEach { item ->
                        if (item.name.isNotBlank()) {
                            Row(modifier = Modifier.padding(vertical = 2.dp)) {
                                Text("\u2022 ", style = MaterialTheme.typography.bodyMedium)
                                Text(
                                    text = if (item.quantity.isNotBlank()) "${item.name}: ${item.quantity}" else item.name,
                                    style = MaterialTheme.typography.bodyMedium
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun stringResource(id: Int): String = androidx.compose.ui.res.stringResource(id)
