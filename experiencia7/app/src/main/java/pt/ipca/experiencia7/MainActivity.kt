package pt.ipca.experiencia7

import androidx.compose.material3.Text
import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.room.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Exercicio3()
        }
    }
}

@Entity(tableName = "car_table")
data class CarEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name = "description") val description: String
)

@Dao
interface CarDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(car: CarEntity): Long  // Use Long as return type

    @Query("SELECT * FROM car_table ORDER BY id ASC")
    fun getAllCars(): Flow<List<CarEntity>>  // Flow of List of CarEntity

    @Delete
    suspend fun delete(car: CarEntity): Int  // Return type is Int (number of rows deleted)
}

@Database(entities = [CarEntity::class], version = 1, exportSchema = false)
abstract class CarDatabase : RoomDatabase() {
    abstract fun carDao(): CarDao

    companion object {
        @Volatile
        private var INSTANCE: CarDatabase? = null

        fun getDatabase(context: Context): CarDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    CarDatabase::class.java,
                    "car_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}

class CarViewModel(private val carDao: CarDao) : ViewModel() {

    val allCars: StateFlow<List<CarEntity>> = carDao.getAllCars().stateIn(
        viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = emptyList()
    )

    fun addCar(name: String, description: String) {
        viewModelScope.launch {
            carDao.insert(CarEntity(name = name, description = description))
        }
    }

    fun deleteCar(car: CarEntity) {
        viewModelScope.launch {
            carDao.delete(car)
        }
    }
}

class CarViewModelFactory(private val carDao: CarDao) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CarViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return CarViewModel(carDao) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}

@Composable
fun CarListScreen(
    carViewModel: CarViewModel,
    onCarClick: (CarEntity) -> Unit,
    onAddCarClick: () -> Unit
) {
    val cars by carViewModel.allCars.collectAsState()

    Column(modifier = Modifier
        .background(color = Color.White)) {

        Button(onClick = onAddCarClick) {
            Text("+")
        }
        LazyColumn {
            items(cars) { car ->
                Text(
                    text = car.name,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                        .clickable { onCarClick(car)
                            }
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddCarScreen(carViewModel: CarViewModel, onCarAdded: () -> Unit) {
    var carName by remember { mutableStateOf("") }
    var carDescription by remember { mutableStateOf("") }

    Column {
        TextField(value = carName, onValueChange = { carName = it }, label = { Text("Car Name") })
        TextField(value = carDescription, onValueChange = { carDescription = it }, label = { Text("Car Description") })
        Button(onClick = {
            if (carName.isNotBlank() && carDescription.isNotBlank()) {
                carViewModel.addCar(carName, carDescription)
                onCarAdded()
            }
        }) {
            Text("Add Car")
        }
    }
}

@Composable
fun CarDetailScreen(carViewModel: CarViewModel, carId: Int, onBack: () -> Unit) {
    // Obter o carro pelo ID a partir do ViewModel
    val car = carViewModel.allCars.collectAsState().value.find { it.id == carId }

    car?.let {
        Column(modifier = Modifier
            .padding(16.dp)
            .background(color= Color.White)
        )
        {
            Text(text = "Nome: ${it.name}", modifier = Modifier.padding(bottom = 8.dp))
            Text(text = "Descrição: ${it.description}", modifier = Modifier.padding(bottom = 8.dp))
            Button(onClick = onBack) {
                Text("Voltar")
            }
        }
    } ?: Text("Carro não encontrado") // Caso o carro não seja encontrado
}


@Composable
fun Exercicio3() {
    val context = LocalContext.current
    val database = CarDatabase.getDatabase(context)
    val carDao = database.carDao()
    val carViewModel: CarViewModel = viewModel(factory = CarViewModelFactory(carDao))

    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = "car_list") {
        composable("car_list") {
            CarListScreen(
                carViewModel = carViewModel,
                onCarClick = { car -> navController.navigate("car_detail/${car.id}") },
                onAddCarClick = { navController.navigate("add_car") }
            )
        }

        composable("add_car") {
            AddCarScreen(carViewModel = carViewModel) {
                navController.popBackStack()
            }
        }

        composable("car_detail/{carId}") { backStackEntry ->
            val carId = backStackEntry.arguments?.getString("carId")?.toIntOrNull() ?: return@composable
            CarDetailScreen(
                carViewModel = carViewModel,
                carId = carId,
                onBack = { navController.popBackStack() }
            )
        }
    }
}

