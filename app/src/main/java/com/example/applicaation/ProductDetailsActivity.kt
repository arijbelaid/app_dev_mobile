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
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.applicaation.ui.theme.ApplicaationTheme


class ProductDetailsActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ApplicaationTheme {
                val productName = intent.getStringExtra("productName")
                val productPrice = intent.getDoubleExtra("productPrice", 0.0)
                val productImage = intent.getIntExtra("productImage", 0)
                val productDescription = intent.getStringExtra("productDescription")


                ProductDetailsScreen(productName, productPrice, productDescription, productImage)
            }
        }
    }
}

@Composable
fun ProductDetailsScreen(productName: String?, productPrice: Double, productDescription: String?, productImage: Int) {
    Column(modifier = Modifier.padding(16.dp)) {

        Image(
            painter = painterResource(id = productImage),
            contentDescription = null,
            modifier = Modifier.fillMaxWidth(),
            contentScale = ContentScale.Crop
        )
        Spacer(modifier = Modifier.height(8.dp))

        Text(text = productName ?: "Unknown Product", fontSize = 24.sp)
        Spacer(modifier = Modifier.height(8.dp))
        Text(text = "Price: $$productPrice", fontSize = 20.sp)
        Spacer(modifier = Modifier.height(8.dp))
        Text(text = productDescription ?: "No description available", fontSize = 16.sp)
    }
}


