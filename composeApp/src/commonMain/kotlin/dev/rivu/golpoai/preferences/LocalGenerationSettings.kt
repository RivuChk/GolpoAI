package dev.rivu.golpoai.preferences

import com.russhwolf.settings.ExperimentalSettingsApi
import com.russhwolf.settings.ObservableSettings
import com.russhwolf.settings.Settings
import com.russhwolf.settings.coroutines.toFlowSettings
import dev.rivu.golpoai.ai.GenerationType
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class LocalGenerationSettings(private val settings: Settings) {
    companion object {
        private const val USE_LOCAL_GENERATION_KEY = "use_local_generation"
        private const val GENERATION_TYPE_KEY = "generation_type"
        private const val OLLAMA_BASE_URL_KEY = "ollama_base_url"
    }

    // Legacy support for existing useLocalGeneration
    var useLocalGeneration: Boolean
        get() = settings.getBoolean(USE_LOCAL_GENERATION_KEY, false)
        set(value) = settings.putBoolean(USE_LOCAL_GENERATION_KEY, value)

    var generationType: GenerationType
        get() {
            val typeString = settings.getString(GENERATION_TYPE_KEY, GenerationType.ONLINE_GEMINI.name)
            return GenerationType.valueOf(typeString)
        }
        set(value) = settings.putString(GENERATION_TYPE_KEY, value.name)

    var ollamaBaseUrl: String
        get() = settings.getString(OLLAMA_BASE_URL_KEY, "http://localhost:11434")
        set(value) = settings.putString(OLLAMA_BASE_URL_KEY, value)

    @OptIn(ExperimentalSettingsApi::class)
    val useLocalGenerationFlow: Flow<Boolean>
        get() = (settings as? ObservableSettings)
            ?.toFlowSettings()
            ?.getBooleanFlow(USE_LOCAL_GENERATION_KEY, false)
            ?: throw IllegalStateException("Settings instance is not ObservableSettings")

    @OptIn(ExperimentalSettingsApi::class)
    val generationTypeFlow: Flow<GenerationType>
        get() = (settings as? ObservableSettings)
            ?.toFlowSettings()
            ?.getStringFlow(GENERATION_TYPE_KEY, GenerationType.ONLINE_GEMINI.name)
            ?.map { GenerationType.valueOf(it) }
            ?: throw IllegalStateException("Settings instance is not ObservableSettings")

    @OptIn(ExperimentalSettingsApi::class)
    val ollamaBaseUrlFlow: Flow<String>
        get() = (settings as? ObservableSettings)
            ?.toFlowSettings()
            ?.getStringFlow(OLLAMA_BASE_URL_KEY, "http://localhost:11434")
            ?: throw IllegalStateException("Settings instance is not ObservableSettings")
}
