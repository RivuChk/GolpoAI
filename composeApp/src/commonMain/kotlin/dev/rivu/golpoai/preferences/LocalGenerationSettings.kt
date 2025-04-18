package dev.rivu.golpoai.preferences

import com.russhwolf.settings.ExperimentalSettingsApi
import com.russhwolf.settings.ObservableSettings
import com.russhwolf.settings.Settings
import com.russhwolf.settings.coroutines.toFlowSettings
import kotlinx.coroutines.flow.Flow

class LocalGenerationSettings(private val settings: Settings) {
    companion object {
        private const val USE_LOCAL_GENERATION_KEY = "use_local_generation"
    }

    var useLocalGeneration: Boolean
        get() = settings.getBoolean(USE_LOCAL_GENERATION_KEY, false)
        set(value) = settings.putBoolean(USE_LOCAL_GENERATION_KEY, value)

    @OptIn(ExperimentalSettingsApi::class)
    val useLocalGenerationFlow: Flow<Boolean>
        get() = (settings as? ObservableSettings)
            ?.toFlowSettings()
            ?.getBooleanFlow(USE_LOCAL_GENERATION_KEY, false)
            ?: throw IllegalStateException("Settings instance is not ObservableSettings")
}
