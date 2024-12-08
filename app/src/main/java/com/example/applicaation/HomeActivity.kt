package com.example.applicaation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.applicaation.ui.theme.ApplicaationTheme
import android.content.Context
import android.content.Intent

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.TopAppBar
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.applicaation.data.Product
import com.example.applicaation.data.SQLiteHelper


class HomeActivity : ComponentActivity() {
    private lateinit var dbHelper: SQLiteHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        dbHelper = SQLiteHelper(this)


        dbHelper.insertProduct(
            "bougie",
            5199.0,
            R.drawable.produit1,
            "Laissez-vous envoûter par la lueur douce et le parfum enivrant de cette bougie, parfaite pour créer une atmosphère chaleureuse et relaxante"
        )

        dbHelper.insertProduct(
            "bougie",
            769.0,
            R.drawable.produit2,
            "Plongez dans une ambiance apaisante avec cette bougie parfumée, diffusant des notes subtiles de vanille et de bois de santal"
        )
        dbHelper.insertProduct(
            "bougie",
            2410.6,
            R.drawable.produit3,
            "Élégante et intemporelle, cette bougie artisanale crée une lumière douce et chaleureuse pour des moments de détente inoubliables"
        )


        val products = dbHelper.getAllProducts()

        setContent {
            HomeScreenScaffold(
                products = products,
                onDeleteAllProducts = {
                    dbHelper.deleteAllProducts()
                },
                onLogout = {
                    val sharedPref = getSharedPreferences("MyAppPreferences", Context.MODE_PRIVATE)
                    with(sharedPref.edit()) {
                        putBoolean("isLoggedIn", false)
                        apply()
                    }


                    val intent = Intent(this@HomeActivity, MainActivity::class.java)
                    startActivity(intent)
                    finish()
                }
            )
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreenScaffold(
    products: List<Product>,
    onDeleteAllProducts: () -> Unit,
    onLogout: () -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Produits Disponibles") },
                actions = {
                    Button(
                        onClick = onLogout,
                        modifier = Modifier
                            .padding(vertical = 16.dp),
                        colors = ButtonDefaults.buttonColors(Color.Black)
                    ) {
                        Text("Déconnexion", fontSize = 16.sp)
                    }
                }
            )
        },
        content = { paddingValues ->
            HomeScreen(
                products = products,
                onDeleteAllProducts = onDeleteAllProducts,
                modifier = Modifier.padding(paddingValues)
            )
        }
    )
}

@Composable
fun HomeScreen(
    products: List<Product>,
    onDeleteAllProducts: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        LazyColumn(
            modifier = Modifier.weight(1f)
        ) {
            items(products) { product ->
                ItemComposable(Product = product)
            }
        }


        Button(
            onClick = onDeleteAllProducts,
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 16.dp),
            colors = ButtonDefaults.buttonColors(Color.Red)
        ) {
            Text("Supprimer tous les produits", fontSize = 16.sp)
        }
    }
}



/*@Composable
fun ProductList(products: List<Product>) {
    LazyColumn {
        items(products) { product ->
            ItemComposable(Product = product)
        }
    }
} */


@Composable
fun ItemComposable(modifier: Modifier =Modifier, Product: Product){
    val context = LocalContext.current
    Card(
        modifier = Modifier
            .padding(16.dp)
            .clickable {
                val intent = Intent(context, ProductDetailsActivity::class.java)
                intent.putExtra("productName", Product.name)
                intent.putExtra("productPrice", Product.price)
                intent.putExtra("productImage", Product.imageUrl)
                intent.putExtra("productDescription", Product.description)
                context.startActivity(intent)
            },
    ){
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalAlignment = CenterHorizontally
        ){Text(text = Product.name,fontSize = 20.sp,fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.height(8.dp))
            Image(
                painter = painterResource(id = Product.imageUrl),
                contentDescription = null,
                modifier = Modifier.fillMaxWidth(),
                contentScale = ContentScale.Crop
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(text ="$${Product.price} $",fontSize = 20.sp,fontWeight = FontWeight.Bold)

        }
    }
}



@Composable
fun Greeting2(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview2() {
    ApplicaationTheme {
        Greeting2("Android")
    }
}