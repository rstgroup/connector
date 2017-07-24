package com.rstit.connector.di.chat

import com.rstit.connector.di.base.scope.ActivityScope
import com.rstit.connector.ui.chat.ChatActivity
import com.rstit.connector.ui.chat.ChatViewAccess
import dagger.Module
import dagger.Provides

/**
 * @author Tomasz Trybala
 * @since 2017-07-24
 */
@ActivityScope
@Module
class ChatModule(private val activity: ChatActivity) {
    @Provides
    @ActivityScope
    fun provideChatActivity(): ChatActivity = activity

    @Provides
    @ActivityScope
    fun provideChatActivityAccess(activity: ChatActivity): ChatViewAccess = activity
}