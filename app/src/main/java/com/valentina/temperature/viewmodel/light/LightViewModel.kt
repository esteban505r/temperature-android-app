package com.valentina.temperature.viewmodel.light

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class LightViewModel : ViewModel() {
    private var _luxState = MutableStateFlow(0f)
    var luxState = _luxState.asStateFlow()

    fun setLux(lux: Float) {
        _luxState.value = lux
    }
}
