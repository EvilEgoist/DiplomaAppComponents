package ru.diploma.appcomponents.core.navigation

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import ru.diploma.appcomponents.core.di.MainImmediateScope
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class NavigationManager @Inject constructor(
    @MainImmediateScope private val externalMainImmediateScope: CoroutineScope
){
    private val navigationChannel = Channel<NavigationCommand>(Channel.BUFFERED)
    val navigationEvent = navigationChannel.receiveAsFlow()

    fun navigate(command: NavigationCommand){
        externalMainImmediateScope.launch {
            navigationChannel.send(command)
        }
    }

    fun navigateBack(){
        externalMainImmediateScope.launch {
            navigationChannel.send(object: NavigationCommand{
                override val destination: String = NavigationDestination.Back.route
            })
        }
    }
}