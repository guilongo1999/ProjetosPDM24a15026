package pt.ipca.experiencia6



import android.os.Bundle
import android.widget.Button
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import pt.ipca.experiencia6.ui.theme.Experiencia6Theme



class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Experiencia6Theme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Exercicio3()
                }
            }
        }
    }
}

//--------------------------------------------ex1-------------------------------------


@Composable
fun Exercicio1() {

    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = "inicio") {
        composable("inicio") {
            EcraInicio(navController)

        }

        composable("destino") {

            EcraDestino()
        }

    }

}





@Composable
fun EcraInicio(navController: NavController) {


    Column(modifier = Modifier
        .fillMaxWidth()
        .fillMaxHeight(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {



        Button(
            onClick = { navController.navigate("destino") },
            modifier = Modifier
                .height(50.dp)
                .size(200.dp)

        ) {

        }

    }

}



@Composable
fun EcraDestino() {



}


//----------------------------------------------ex2-----------------------------------------


@Composable
fun Exercicio2() {

    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = "inicio") {
        composable("inicio") {
            EcraIni(navController)

        }

        composable("destino") {

            EcraDest()
        }

    }

}





@Composable
fun EcraIni(navController: NavController) {


    Column(modifier = Modifier
        .fillMaxWidth()
        .fillMaxHeight(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {



        Button(
            onClick = { navController.navigate("destino") },
            modifier = Modifier
                .height(50.dp)
                .size(200.dp)

        ) {
            Text("Botão")
        }

        Text("Este é um texto")

    }

}



@Composable
fun EcraDest() {

    Text("Este é um texto")

}


//---------------------------------------------------------------Ex3-----------------------------

@Composable
fun Exercicio3() {
    val navController = rememberNavController()
    val cars = remember {
            mutableStateListOf(
            Car("Ferrari Modelo F40", "Ferrari Modelo F40- Marca Italiana."),
            Car("Ferrari Modelo 512 TR", "Ferrari Modelo 512 TR- Marca Italiana."),
            Car("Ferrari Modelo 458", "Ferrari Modelo 458- Marca Italiana."),
            Car("Lamborghini Modelo Miura", "Lamborghini Modelo Miura -Marca Italiana."),
            Car("Lamborghini Modelo Murcielago", "Lamborghini Modelo Murcielago - Marca Italiana."),
            Car("Lamborghini Modelo Countach", "Lamborghini Modelo Countach - Marca Italiana."),
            Car("FIAT Modelo 500", "FIAT Modelo 500 - Marca Italiana."),
            Car("FIAT Modelo Panda", "FIAT Modelo Panda - Marca Italiana."),
            Car("FIAT Modelo Bravo", "FIAT Modelo Bravo - Marca Italiana.")
        )
    }

    NavHost(navController = navController, startDestination = "car_list") {
        composable("car_list") {
            CarListScreen(cars, onCarClick = { car ->
                navController.navigate("car_detail/${cars.indexOf(car)}")},
                onAddCarClick = {navController.navigate("add_car")})
            }


        composable("car_detail/{index}") { backStackEntry ->
            val index = backStackEntry.arguments?.getString("index")?.toInt() ?: 0
            CarDetailScreen(cars[index])
        }

        composable("add_car") {
            AddCarScreen(onCarAdded = {car -> cars.add(car)
            navController.popBackStack()})
        }
    }
}


@Composable
fun CarListScreen(cars: List<Car>, onCarClick: (Car) -> Unit, onAddCarClick: () -> Unit) {
    Column {

        Button(onClick = onAddCarClick,
            modifier = Modifier
                .height(50.dp)
                .width(200.dp)
        ) { Text("+")}

        LazyColumn {
        items(cars) { car ->
            Text(
                text = car.name,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
                    .clickable { onCarClick(car) }
            )

            }
        }
    }
}

@Composable
fun CarDetailScreen(car: Car) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(text = car.description)
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddCarScreen(onCarAdded:(Car) -> Unit) {

    var carName by  remember {mutableStateOf("")}
    var carDescription by remember {mutableStateOf("")}

    Column(
        modifier = Modifier
            .fillMaxSize()
            .fillMaxWidth(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        TextField(value = carName, onValueChange = {carName = it} ,
            label = {Text("Car Name")},
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        )

        TextField(value = carDescription, onValueChange = {carDescription = it},
            label = {Text("Car Description")},
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        )
        
        Button(onClick = {
            if (carName.isNotBlank() && carDescription.isNotBlank()) {

                onCarAdded(Car(carName,carDescription))
            }


        },

            modifier = Modifier
                .height(50.dp)
                .width(200.dp)

            ) {
                Text("Add Car")
        }
    }


}

@Composable
fun EcraInic(navController: NavController) {
    Column(
        modifier = Modifier
            .fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {


        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = { navController.navigate("car_list") },
            modifier = Modifier
                .height(50.dp)
                .width(200.dp)
        ) {
            Text("Lista de Carros")
        }
    }
}

