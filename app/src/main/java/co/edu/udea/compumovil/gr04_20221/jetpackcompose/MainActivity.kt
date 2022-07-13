package co.edu.udea.compumovil.gr04_20221.jetpackcompose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.Person
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.runtime.*
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import co.edu.udea.compumovil.gr04_20221.jetpackcompose.ui.theme.JetPackComposeTheme
import co.edu.udea.compumovil.gr04_20221.jetpackcompose.ui.theme.Shapes

class MainActivity : ComponentActivity() {

    data class Person(val name: String, val lastName: String, val city: String)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val navController = rememberNavController()
            val listNotes by rememberSaveable { mutableStateOf(mutableListOf<Person>()) }
            JetPackComposeTheme {
                Surface(color = MaterialTheme.colors.background) {
                    NavHost(navController = navController, startDestination = "Home") {
                        composable("Home") {
                            Layout(items = listNotes, nav = navController)
                        }
                        composable(route = "Form") {
                            Form(items = listNotes, nav = navController)
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun Item(name: String, lastName: String, city: String) {
    var count by rememberSaveable { mutableStateOf(0) }
    Card(
        shape = RoundedCornerShape(8.dp),
        elevation = 1.dp,
        modifier = Modifier
            .padding(horizontal = 8.dp, vertical = 4.dp)
            .fillMaxWidth(),
    ) {
        Row(
            modifier = Modifier.padding(8.dp),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                Modifier.weight(1f), verticalArrangement = Arrangement.Center
            ) {
                Text("$name $lastName")
                Text("$city")
            }
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                IconButton(onClick = {
                    count++
                }) {
                    Icon(Icons.Filled.Favorite, "Favorite")
                }
                Text(text = count.toString())
            }
        }
    }
}

@Composable
fun Layout(items: MutableList<MainActivity.Person>, nav: NavHostController) {
    Scaffold(floatingActionButton = {
        FloatingActionButton(onClick = { nav.navigate("Form") }) {
            Icon(imageVector = Icons.Default.Add, contentDescription = "Create")
        }
    }) {
        LazyColumn {
            items(items) { item ->
                Item(item.name, item.lastName, item.city)
            }
        }
    }
}

@Composable
fun Form(items: MutableList<MainActivity.Person>, nav: NavController) {
    var name by rememberSaveable { mutableStateOf("") }
    var lastName by rememberSaveable { mutableStateOf("") }
    var city by rememberSaveable { mutableStateOf("") }
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .padding(top = 10.dp, start = 5.dp, end = 5.dp, bottom = 5.dp)
            .fillMaxWidth()
    ) {
        TextFieldCompo(
            value = name,
            onValueChange = { name = it },
            imageVector = Icons.Filled.Person,
            contentDescription = "Person",
            placeholder = "Name"
        )
        Spacer(modifier = Modifier.height(10.dp))
        TextFieldCompo(
            value = lastName,
            onValueChange = { lastName = it },
            imageVector = Icons.Outlined.Person,
            contentDescription = "Person",
            placeholder = "Last Name"
        )
        Spacer(modifier = Modifier.height(10.dp))
        TextFieldCompo(
            value = city,
            onValueChange = { city = it },
            imageVector = Icons.Filled.Place,
            contentDescription = "Place",
            placeholder = "City"
        )
        Spacer(modifier = Modifier.height(20.dp))
        ButtonBasic(text = "Añadir", onClick = {
            items.add(MainActivity.Person(name, lastName, city))
            nav.navigate("Home")
        })
    }

}

@Composable
fun TextFieldCompo(
    value: String,
    onValueChange: (String) -> Unit,
    imageVector: ImageVector,
    contentDescription: String,
    placeholder: String
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        modifier = Modifier.fillMaxWidth(),
        leadingIcon = {
            Icon(
                imageVector = imageVector,
                contentDescription = contentDescription
            )
        },
        placeholder = {
            Text(text = placeholder)
        })
}

@Composable
fun ButtonBasic(text: String, onClick: () -> Unit) {
    Button(
        contentPadding = PaddingValues(horizontal = 1.dp, vertical = 10.dp),
        shape = Shapes.small,
        onClick = onClick
    ) {
        Text(
            text = text
        )
    }
}

