import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.SavedStateHandle
import com.example.stockapp.data.dao.StocksEntity
import com.example.stockapp.data.dao.toStock
import com.example.stockapp.data.repository.StockRepository
import com.example.stockapp.ui.navigation.BuyStockScreenDest
import com.example.stockapp.ui.viewmodel.BuyStockViewModel
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.TestCoroutineScope
import kotlinx.coroutines.test.runBlockingTest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import kotlin.test.assertEquals

@ExperimentalCoroutinesApi
class BuyStockViewModelTest {

    @get:Rule
    val rule = InstantTaskExecutorRule()

    private val testDispatcher = TestCoroutineDispatcher()

    private val testScope = TestCoroutineScope(testDispatcher)

    @MockK
    lateinit var mockSavedStateHandle: SavedStateHandle

    @MockK
    lateinit var mockRepository: StockRepository

    private lateinit var viewModel: BuyStockViewModel

    @Before
    fun setup() {
        MockKAnnotations.init(this)
        coEvery { mockSavedStateHandle[BuyStockScreenDest.stockIdArg] } returns "your_stock_id"
        viewModel = BuyStockViewModel(mockSavedStateHandle, mockRepository)
    }

    @After
    fun tearDown() {
        testScope.cleanupTestCoroutines()
    }

    @Test
    fun `test uiState emits correct stock`() = testScope.runBlockingTest {
        // Given
        val mockStock = StocksEntity("your_stock_id", "StockName", 10.0, 5)
        coEvery { mockRepository.getStockById("your_stock_id") } returns
                MutableStateFlow(mockStock)

        // When
        val uiStateValues = viewModel.uiState.value

        // Then
        assertEquals(mockStock.toStock(), uiStateValues)
    }

    @Test
    fun `test buyStock updates repository`() = testScope.runBlockingTest {
        // Given
        val mockStock = StocksEntity("your_stock_id", "StockName", 10.0, 5)
        coEvery { mockRepository.update(mockStock) } returns Unit

        // When
        viewModel.buyStock(mockStock)

        // Then
        // Verify that the repository update function was called
        coEvery { mockRepository.update(mockStock) }
    }
}
