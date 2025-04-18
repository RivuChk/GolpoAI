import com.russhwolf.settings.Settings
import dev.rivu.golpoai.preferences.LocalGenerationSettings
import dev.rivu.golpoai.presentation.HistoryScreenModel
import dev.rivu.golpoai.presentation.StoryScreenModel
import org.koin.dsl.module

val composeAppModule = module {
    factory { StoryScreenModel(get(), get(), get()) }
    factory { HistoryScreenModel(get()) }
    single { Settings() }
    single { LocalGenerationSettings(get()) }
}
