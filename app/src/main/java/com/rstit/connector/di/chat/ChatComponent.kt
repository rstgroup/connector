package com.rstit.connector.di.chat

import com.rstit.connector.di.base.scope.ActivityScope
import com.rstit.connector.ui.chat.ChatActivity
import dagger.Subcomponent

/**
 * @author Tomasz Trybala
 * @since 2017-07-24
 */
@ActivityScope
@Subcomponent(modules = arrayOf(ChatModule::class))
interface ChatComponent {
    fun inject(activity: ChatActivity): ChatActivity
}