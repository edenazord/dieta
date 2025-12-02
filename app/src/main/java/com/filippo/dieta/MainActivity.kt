package com.filippo.dieta

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardCapitalization
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
    
    val weekPlan = remember { mutableStateListOf(*MealPlan.getDefaultWeekPlan().toTypedArray()) }
    val shoppingList = remember { mutableStateListOf(*MealPlan.getDefaultShoppingCategories().toTypedArray()) }

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
                0 -> RecipesScreen(weekPlan) { dayIndex, updatedDay ->
                    weekPlan[dayIndex] = updatedDay
                }
                else -> ShoppingListScreen(shoppingList)
            }
        }
    }
}

@Composable
fun RecipesScreen(days: SnapshotStateList<DayPlan>, onDayUpdated: (Int, DayPlan) -> Unit) {
    var editingDayIndex by remember { mutableStateOf<Int?>(null) }

    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        items(days.size) { index ->
            val day = days[index]
            ElevatedCard(
                modifier = Modifier.fillMaxWidth(),
                onClick = { editingDayIndex = index }
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(Icons.Default.CalendarToday, contentDescription = null, modifier = Modifier.size(24.dp))
                        Spacer(Modifier.width(8.dp))
                        Text(text = day.day, style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold)
                        Spacer(Modifier.weight(1f))
                        Icon(Icons.Default.Edit, contentDescription = "Modifica", modifier = Modifier.size(20.dp), tint = MaterialTheme.colorScheme.primary)
                    }
                    Spacer(Modifier.height(12.dp))

                    Row(verticalAlignment = Alignment.Top) {
                        Icon(Icons.Default.LunchDining, contentDescription = null, modifier = Modifier.size(20.dp), tint = MaterialTheme.colorScheme.primary)
                        Spacer(Modifier.width(8.dp))
                        Column {
                            Text(text = "Pranzo (13:00)", fontWeight = FontWeight.SemiBold, style = MaterialTheme.typography.titleSmall)
                            day.lunch.forEach { line -> Text("• $line", style = MaterialTheme.typography.bodyMedium) }
                        }
                    }
                    Spacer(Modifier.height(12.dp))

                    Row(verticalAlignment = Alignment.Top) {
                        Icon(Icons.Default.Icecream, contentDescription = null, modifier = Modifier.size(20.dp), tint = MaterialTheme.colorScheme.secondary)
                        Spacer(Modifier.width(8.dp))
                        Column {
                            Text(text = "Spuntino (16:30)", fontWeight = FontWeight.SemiBold, style = MaterialTheme.typography.titleSmall)
                            day.snack.forEach { line -> Text("• $line", style = MaterialTheme.typography.bodyMedium) }
                        }
                    }

                    if (day.dinner.isNotEmpty()) {
                        Spacer(Modifier.height(12.dp))
                        Row(verticalAlignment = Alignment.Top) {
                            Icon(Icons.Default.DinnerDining, contentDescription = null, modifier = Modifier.size(20.dp), tint = MaterialTheme.colorScheme.tertiary)
                            Spacer(Modifier.width(8.dp))
                            Column {
                                Text(text = "Cena (20:00)", fontWeight = FontWeight.SemiBold, style = MaterialTheme.typography.titleSmall)
                                day.dinner.forEach { line -> Text("• $line", style = MaterialTheme.typography.bodyMedium) }
                            }
                        }
                    } else {
                        Spacer(Modifier.height(12.dp))
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(Icons.Default.Block, contentDescription = null, modifier = Modifier.size(20.dp), tint = MaterialTheme.colorScheme.error)
                            Spacer(Modifier.width(8.dp))
                            Text(text = "Cena (20:00): Saltata", style = MaterialTheme.typography.bodyMedium, color = MaterialTheme.colorScheme.error)
                        }
                    }
                }
            }
        }
    }

    editingDayIndex?.let { dayIndex ->
        EditDayDialog(
            day = days[dayIndex],
            onDismiss = { editingDayIndex = null },
            onSave = { updatedDay ->
                onDayUpdated(dayIndex, updatedDay)
                editingDayIndex = null
            }
        )
    }
}

@Composable
fun ShoppingListScreen(categories: SnapshotStateList<ShoppingCategory>) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        items(categories.size) { index ->
            val cat = categories[index]
            ElevatedCard(modifier = Modifier.fillMaxWidth()) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
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
                                Text("• ", style = MaterialTheme.typography.bodyMedium)
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditDayDialog(day: DayPlan, onDismiss: () -> Unit, onSave: (DayPlan) -> Unit) {
    var lunchText by remember { mutableStateOf(day.lunch.joinToString("\n")) }
    var snackText by remember { mutableStateOf(day.snack.joinToString("\n")) }
    var dinnerText by remember { mutableStateOf(day.dinner.joinToString("\n")) }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Modifica ${day.day}") },
        text = {
            LazyColumn(modifier = Modifier.fillMaxWidth(), verticalArrangement = Arrangement.spacedBy(12.dp)) {
                item {
                    Text("Pranzo (13:00)", fontWeight = FontWeight.SemiBold, style = MaterialTheme.typography.titleSmall)
                    OutlinedTextField(
                        value = lunchText,
                        onValueChange = { lunchText = it },
                        modifier = Modifier.fillMaxWidth(),
                        minLines = 3,
                        maxLines = 8,
                        keyboardOptions = KeyboardOptions(capitalization = KeyboardCapitalization.Sentences)
                    )
                }
                item {
                    Text("Spuntino (16:30)", fontWeight = FontWeight.SemiBold, style = MaterialTheme.typography.titleSmall)
                    OutlinedTextField(
                        value = snackText,
                        onValueChange = { snackText = it },
                        modifier = Modifier.fillMaxWidth(),
                        minLines = 2,
                        maxLines = 5,
                        keyboardOptions = KeyboardOptions(capitalization = KeyboardCapitalization.Sentences)
                    )
                }
                item {
                    Text("Cena (20:00)", fontWeight = FontWeight.SemiBold, style = MaterialTheme.typography.titleSmall)
                    OutlinedTextField(
                        value = dinnerText,
                        onValueChange = { dinnerText = it },
                        modifier = Modifier.fillMaxWidth(),
                        minLines = 3,
                        maxLines = 8,
                        keyboardOptions = KeyboardOptions(capitalization = KeyboardCapitalization.Sentences),
                        placeholder = { Text("Lascia vuoto per saltare la cena") }
                    )
                }
            }
        },
        confirmButton = {
            TextButton(onClick = {
                val updatedDay = day.copy(
                    lunch = lunchText.lines().filter { it.isNotBlank() },
                    snack = snackText.lines().filter { it.isNotBlank() },
                    dinner = dinnerText.lines().filter { it.isNotBlank() }
                )
                onSave(updatedDay)
            }) {
                Text("Salva")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Annulla")
            }
        }
    )
}

@Composable
private fun stringResource(id: Int): String = androidx.compose.ui.res.stringResource(id)
