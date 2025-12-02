package com.filippo.dieta.data

data class IngredientItem(val name: String, val quantity: String)

data class ShoppingCategory(val name: String, val items: List<IngredientItem>)

data class DayPlan(
    val day: String,
    var lunch: List<String>,
    var snack: List<String>,
    var dinner: List<String>,
) {
    fun copy(
        day: String = this.day,
        lunch: List<String> = this.lunch,
        snack: List<String> = this.snack,
        dinner: List<String> = this.dinner
    ) = DayPlan(day, lunch, snack, dinner)
}

object MealPlan {
    fun getDefaultShoppingCategories(): List<ShoppingCategory> = listOf(
        ShoppingCategory(
            name = "Proteine",
            items = listOf(
                IngredientItem("Petto di pollo", "1 kg (200 g × 5 pranzi)"),
                IngredientItem("Pesce misto (orata, salmone, merluzzo, branzino, trota)", "1,2 kg (300 g × 4 cene, escluso giovedì)"),
                IngredientItem("Yogurt greco (0–2% grassi)", "1 kg (200 g × 5 spuntini)"),
            )
        ),
        ShoppingCategory(
            name = "Cereali integrali (pranzi)",
            items = listOf(
                IngredientItem("Quinoa", "80 g"),
                IngredientItem("Farro", "80 g"),
                IngredientItem("Cous cous integrale", "80 g"),
                IngredientItem("Orzo perlato", "80 g"),
                IngredientItem("Bulgur", "80 g"),
                IngredientItem("(circa 70–80 g crudi/giorno, variando)", ""),
            )
        ),
        ShoppingCategory(
            name = "Verdure (totale 5 giorni)",
            items = listOf(
                IngredientItem("Insalata mista (lattuga, rucola, spinaci, radicchio, valeriana)", "1 kg"),
                IngredientItem("Pomodorini", "500 g"),
                IngredientItem("Cetrioli", "250 g"),
                IngredientItem("Carote", "250 g"),
                IngredientItem("Peperoni", "250 g"),
                IngredientItem("Zucchine", "250 g"),
                IngredientItem("Broccoli", "250 g"),
                IngredientItem("Cavolfiore", "250 g"),
                IngredientItem("Spinaci", "250 g"),
                IngredientItem("Melanzane", "250 g"),
                IngredientItem("Finocchi", "250 g"),
                IngredientItem("Mais", "150 g"),
                IngredientItem("Pomodori secchi", "80 g"),
            )
        ),
        ShoppingCategory(
            name = "Patate",
            items = listOf(
                IngredientItem("Patate (bianche o dolci)", "1,2 kg (300 g × 4 cene, escluso giovedì)"),
            )
        ),
        ShoppingCategory(
            name = "Frutta per spuntini/frullati",
            items = listOf(
                IngredientItem("Banane", "2"),
                IngredientItem("Fragole", "200 g"),
                IngredientItem("Mango", "1 medio (200 g)"),
                IngredientItem("Frutta fresca di stagione", "400 g"),
            )
        ),
        ShoppingCategory(
            name = "Frutta secca",
            items = listOf(
                IngredientItem("Noci", "40 g"),
                IngredientItem("Mandorle", "40 g"),
                IngredientItem("Nocciole", "40 g"),
                IngredientItem("Pistacchi", "40 g"),
                IngredientItem("(20 g a spuntino, alternando)", ""),
            )
        ),
        ShoppingCategory(
            name = "Extra",
            items = listOf(
                IngredientItem("Olio extravergine d’oliva", "100 ml (1 cucchiaio a pasto)"),
                IngredientItem("Latte vegetale (mandorla/cocco)", "400 ml (per frullati)"),
                IngredientItem("Limone, erbe aromatiche, spezie", "q.b."),
            )
        ),
    )

    fun getDefaultWeekPlan(): List<DayPlan> = listOf(
        DayPlan(
            day = "Lunedì",
            lunch = listOf(
                "Insalata di pollo e quinoa",
                "- 200 g petto di pollo grigliato a striscioline",
                "- 80 g quinoa cotta",
                "- Rucola, pomodorini, cetriolo",
                "- Condisci con 1 cucchiaio olio EVO, limone, pepe",
            ),
            snack = listOf(
                "Yogurt greco (200 g) + 20 g noci",
            ),
            dinner = listOf(
                "Orata al forno con zucchine e patate",
                "- 300 g orata intera o filetti",
                "- 300 g patate a fette",
                "- 250 g zucchine",
                "- Aromi mediterranei (rosmarino, timo), 1 cucchiaio olio EVO",
            ),
        ),
        DayPlan(
            day = "Martedì",
            lunch = listOf(
                "Insalata di pollo e farro",
                "- 200 g pollo grigliato",
                "- 80 g farro cotto",
                "- Spinaci freschi, carote julienne",
                "- Condisci con olio EVO e aceto balsamico",
            ),
            snack = listOf(
                "Frullato: 1 banana + 200 ml latte di mandorla",
            ),
            dinner = listOf(
                "Salmone al forno con broccoli e patate dolci",
                "- 300 g salmone",
                "- 300 g patate dolci a cubetti",
                "- 250 g broccoli al vapore",
                "- Condisci con olio EVO e limone",
            ),
        ),
        DayPlan(
            day = "Mercoledì",
            lunch = listOf(
                "Insalata di pollo e cous cous",
                "- 200 g pollo grigliato",
                "- 80 g cous cous integrale",
                "- Lattuga, peperoni, pomodorini",
                "- Condisci con olio EVO e prezzemolo",
            ),
            snack = listOf(
                "Yogurt greco (200 g) + 20 g mandorle",
            ),
            dinner = listOf(
                "Merluzzo al forno con cavolfiore e patate",
                "- 300 g merluzzo",
                "- 300 g patate al forno",
                "- 250 g cavolfiore al vapore",
                "- Aromi: aglio, prezzemolo, olio EVO",
            ),
        ),
        DayPlan(
            day = "Giovedì",
            lunch = listOf(
                "Insalata di pollo e orzo perlato",
                "- 200 g pollo grigliato",
                "- 80 g orzo perlato cotto",
                "- Radicchio, pomodori secchi, finocchi",
                "- Condisci con olio EVO e limone",
            ),
            snack = listOf(
                "Frullato: 200 g fragole + 100 g yogurt greco",
            ),
            dinner = emptyList(),
        ),
        DayPlan(
            day = "Venerdì",
            lunch = listOf(
                "Insalata di pollo e bulgur",
                "- 200 g pollo grigliato",
                "- 80 g bulgur cotto",
                "- Misticanza, avocado, mais",
                "- Condisci con olio EVO e succo di limone",
            ),
            snack = listOf(
                "Yogurt greco (200 g) + 20 g pistacchi",
            ),
            dinner = listOf(
                "Trota al forno con carote e patate",
                "- 300 g trota",
                "- 300 g patate al cartoccio",
                "- 250 g carote al vapore",
                "- Aromi: rosmarino, olio EVO",
            ),
        ),
    )
}
