package com.valentina.temperature

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import com.valentina.temperature.ui.theme.TemperatureTheme
import com.valentina.temperature.viewmodel.light.LightViewModel

class LightActivity : ComponentActivity(), SensorEventListener {

    private lateinit var sensorManager: SensorManager
    private var lightSensor: Sensor? = null
    private val viewModel: LightViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        sensorManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager
        lightSensor = sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT)

        setContent {
            TemperatureTheme {
                Content()
            }
        }
    }

    override fun onSensorChanged(event: SensorEvent) {
        viewModel.setLux(event.values.first())
    }

    override fun onAccuracyChanged(p0: Sensor?, p1: Int) {}

    override fun onResume() {
        super.onResume()
        sensorManager.registerListener(this, lightSensor, SensorManager.SENSOR_DELAY_NORMAL)
    }

    override fun onPause() {
        super.onPause()
        sensorManager.unregisterListener(this)
    }

    private fun colorForLux(lux: Float): Color = when {
        lux < 10f -> Color(0xFF0D1B2A)
        lux < 50f -> Color(0xFF1B263B)
        lux < 200f -> Color(0xFF415A77)
        lux < 500f -> Color(0xFF778DA9)
        lux < 1000f -> Color(0xFFE0E1DD)
        lux < 5000f -> Color(0xFFFFF3B0)
        else -> Color(0xFFFFD60A)
    }

    private fun textColorForLux(lux: Float): Color = if (lux < 500f) Color.White else Color.Black

    @Composable
    fun Content() {
        val lux by viewModel.luxState.collectAsState()
        val backgroundColor by animateColorAsState(
            colorForLux(lux),
            animationSpec = tween(durationMillis = 500, easing = FastOutSlowInEasing)
        )

        Surface(
            modifier = Modifier.fillMaxSize(),
            color = backgroundColor
        ) {
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    "$lux lx",
                    style = TextStyle(
                        fontSize = 36.sp,
                        fontWeight = FontWeight.Bold,
                        color = textColorForLux(lux)
                    )
                )
                Text(
                    "Nivel de luz ambiental",
                    style = TextStyle(
                        fontSize = 16.sp,
                        color = textColorForLux(lux)
                    )
                )
            }
        }
    }

    @Preview(showBackground = true)
    @Composable
    fun DefaultPreview() {
        TemperatureTheme {
            Surface(color = colorForLux(30f)) {
                Text("30.0 lx", color = Color.White)
            }
        }
    }
}
