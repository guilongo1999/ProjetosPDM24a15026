package pt.ipca.Car_characteristics

import android.app.FragmentManager.BackStackEntry
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
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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
import pt.ipca.experiencia5.ui.theme.Experiencia5Theme



class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Experiencia5Theme {
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
    val cars = listOf(
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

    NavHost(navController = navController, startDestination = "car_list") {
        composable("car_list") {
            CarListScreen(cars) { car ->
                navController.navigate("car_detail/${cars.indexOf(car)}")
            }
        }

        composable("car_detail/{index}") { backStackEntry ->
            val index = backStackEntry.arguments?.getString("index")?.toInt() ?: 0
            CarDetailScreen(cars[index])
        }
    }
}


@Composable
fun CarListScreen(cars: List<Car>, onCarClick: (Car) -> Unit) {
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






