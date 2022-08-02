package co.edu.udea.compumovil.gr04_20221.myapplication

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Person
import androidx.compose.runtime.Composable
import androidx.compose.runtime.*
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import co.edu.udea.compumovil.gr04_20221.myapplication.ui.theme.MyApplicationTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyApplicationTheme {
                // A surface container using the 'background' color from the theme
                Surface(color = MaterialTheme.colors.background) {
                    val navController = rememberNavController()
                    var persons by rememberSaveable { mutableStateOf(mutableListOf<Person>()) }
                   NavHost(navController = navController, startDestination = "LISTA" ){
                       composable(route="LISTA") {
                           MyList(navController = navController, persons= persons )
                       }
                       composable(route="FORM") {
                           Form(navController = navController, persons= persons)
                       }
                   }
                }
            }
        }
    }
}


@Composable
fun Greeting(name: String) {
    Text(text = "Hello $name!")
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    MyApplicationTheme {
        Greeting("Android")
    }
}

@Composable
fun Form(navController: NavHostController, persons: MutableList<Person>) {
    var name by rememberSaveable { mutableStateOf("") }
    var lastName by rememberSaveable { mutableStateOf("") }
    var city by rememberSaveable { mutableStateOf("") }

    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        MyField(
            value = name,
            onValueChange = { name = it},
            placeholder = "Name",
            imageVector = Icons.Filled.Person,
            contentDescription = "Person"
        )
        Spacer(modifier = Modifier.height(4.dp))
        MyField(
            value = lastName,
            onValueChange = {lastName=it},
            placeholder = "LastName",
            imageVector = Icons.Filled.Person,
            contentDescription = "Person"
        )
        Spacer(modifier = Modifier.height(4.dp))
        MyField(
            value = city,
            onValueChange = { city = it},
            placeholder = "City",
            imageVector = Icons.Filled.LocationOn,
            contentDescription = "Location"
        )
        Spacer(modifier = Modifier.height(8.dp))
        Button(onClick = {
            persons.add(Person(name, lastName, city))
            navController.navigate("LISTA")
        }) {
            Text(text = "Añadir")
        }
    }
}

@Composable
fun MyField(value: String, onValueChange: (String) -> Unit, placeholder: String, imageVector: ImageVector, contentDescription: String){
    OutlinedTextField(value = value, onValueChange = onValueChange, placeholder = {
        Text(text = placeholder)
    }, leadingIcon = {
        Icon(imageVector = imageVector, contentDescription = contentDescription)
    })
}

@Composable
fun MyItem(name: String, lastName: String, city: String){
    var count by rememberSaveable { mutableStateOf(0)}
    Row(verticalAlignment = Alignment.CenterVertically) {
        Column {
            Text("$name $lastName")
            Text(text = city)
        }
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            IconButton(onClick = { count++ }) {
                Icon(imageVector = Icons.Filled.Favorite , contentDescription = "")
            }
            Text(text = count.toString())
        }
    }
}

data class Person(val name: String, val lastName: String, val city: String)

@Composable
fun MyList(navController: NavController, persons: MutableList<Person>){

    LazyColumn(){
        items(persons){ i ->
            MyItem(i.name, i.lastName, i.city)
        }
        item {
            Button(onClick = { navController.navigate("FORM") }) {
                Text(text = "Nuevo")
            }
        }
    }
}

