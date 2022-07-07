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
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.runtime.*
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import co.edu.udea.compumovil.gr04_20221.jetpackcompose.ui.theme.JetPackComposeTheme

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            JetPackComposeTheme {
                // A surface container using the 'background' color from the theme
                Surface(color = MaterialTheme.colors.background) {
                    Layout()
                }
            }
        }
    }
}

@Composable
fun Item(name: String, lastName: String, city: String) {
    var count by rememberSaveable { mutableStateOf(0 )}
    Card(
        shape = RoundedCornerShape(8.dp),
        elevation = 1.dp,
        modifier = Modifier
            .padding(horizontal = 8.dp, vertical = 4.dp)
            .fillMaxWidth(),
    ) {
        Row(modifier = Modifier.padding(8.dp)) {
            Spacer(modifier = Modifier)
            Column(
                Modifier.weight(1f),
            ) {
                Text("$name $lastName")
                Text("$city")
            }
            Spacer(modifier = Modifier.width(150.dp))
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                IconButton(onClick = {
                    count++
                }) {
                    Icon(Icons.Filled.Favorite, "Favorite")
                }
                Text(text = count.toString() )
                Component1()
            }
        }
    }
}

data class Person(val name: String, val lastName: String, val city: String)

var people = mutableListOf(
    Person("ALEJANDRO", "RUIZ", "Medelllin"),
    Person("CAMILO", "GOMEZ", "Cali"),
    Person("DENY", "RODRIGUEZ", "BARRANQUILLA"),
    Person("GLORIA", "LOPEZ", "BUENAVENTURA"),
    Person("JULY", "SUAREZ", "TURBO"),
    Person("MARÍA", "SANTOS", "CARTAGO"),
    )


@Preview(showBackground = true)
@Composable
fun Layout(){
        LazyColumn {
            items(people) { item ->
                Item(item.name, item.lastName, item.city)
            }
        }
}

@Composable
fun Component1() {
    Button(onClick = { /*TODO*/ }) {
        Text(text = "Holaaaa")
    }
}

