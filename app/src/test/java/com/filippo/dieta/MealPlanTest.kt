package com.filippo.dieta

import com.filippo.dieta.data.MealPlan
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class MealPlanTest {
    @Test
    fun `week plan has five days`() {
        assertEquals(5, MealPlan.weekPlan.size)
    }
}
