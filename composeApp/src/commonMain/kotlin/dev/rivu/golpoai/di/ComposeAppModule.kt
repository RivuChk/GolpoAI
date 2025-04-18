import dev.rivu.golpoai.presentation.HistoryScreenModel
import dev.rivu.golpoai.presentation.StoryScreenModel
import org.koin.dsl.module

val composeAppModule = module {
    factory { StoryScreenModel(get(), get()) }
    factory { HistoryScreenModel(get()) }
}
