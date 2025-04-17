import dev.rivu.golpoai.presentation.StoryScreenModel
import org.koin.dsl.module

val composeAppModule = module {
    factory { StoryScreenModel(get()) }
}
