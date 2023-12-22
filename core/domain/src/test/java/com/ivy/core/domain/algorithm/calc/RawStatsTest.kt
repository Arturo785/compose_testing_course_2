package com.ivy.core.domain.algorithm.calc

import assertk.assertThat
import assertk.assertions.isEqualTo
import com.ivy.core.persistence.algorithm.calc.CalcTrn
import com.ivy.data.transaction.TransactionType
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import java.time.Instant

class RawStatsTest {

    private lateinit var transactions: List<CalcTrn>

    @BeforeEach
    fun setup(){
        transactions =
            listOf(
                CalcTrn(amount = 10.0, currency = "USD", type = TransactionType.Income, time = Instant.now()),
                CalcTrn(amount = 20.0, currency = "USD", type = TransactionType.Income, time = Instant.now()),
                CalcTrn(amount = 30.0, currency = "USD", type = TransactionType.Income, time = Instant.now()),
                CalcTrn(amount = 10.0, currency = "USD", type = TransactionType.Expense, time = Instant.now()),
                CalcTrn(amount = 20.0, currency = "USD", type = TransactionType.Expense, time = Instant.now()),
            )
    }


    @Test
    fun `get total income and expense summary`() {
        transactions =
            listOf(
                CalcTrn(amount = 10.0, currency = "USD", type = TransactionType.Income, time = Instant.now()),
                CalcTrn(amount = 20.0, currency = "USD", type = TransactionType.Income, time = Instant.now()),
                CalcTrn(amount = 30.0, currency = "USD", type = TransactionType.Income, time = Instant.now()),

                CalcTrn(amount = 10.0, currency = "USD", type = TransactionType.Expense, time = Instant.now()),
                CalcTrn(amount = 20.0, currency = "USD", type = TransactionType.Expense, time = Instant.now()),
            )

        val rawStats = rawStats(transactions)


        val totalIncomesAmount = rawStats.incomes.values.sum()
        val totalExpenseAmount = rawStats.expenses.values.sum()


        assertThat(totalIncomesAmount).isEqualTo(60.0)
        assertThat(totalExpenseAmount).isEqualTo(30.0)


        assertThat(rawStats.incomesCount).isEqualTo(3)
        assertThat(rawStats.expensesCount).isEqualTo(2)
    }
}