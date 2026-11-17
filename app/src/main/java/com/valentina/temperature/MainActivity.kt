package com.valentina.temperature

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.valentina.temperature.ui.theme.TemperatureTheme

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            TemperatureTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    MenuContent()
                }
            }
        }
    }

    @Composable
    fun MenuContent() {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = "Sensores", style = MaterialTheme.typography.h5)
            Spacer(modifier = Modifier.height(32.dp))

            Button(
                onClick = { startActivity(Intent(this@MainActivity, TemperatureActivity::class.java)) },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Sensor de temperatura")
            }

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = { startActivity(Intent(this@MainActivity, LightActivity::class.java)) },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Sensor de luz")
            }
        }
    }
}
