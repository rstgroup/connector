package com.rstit.connector.di.date

import com.rstit.connector.date.ChatDateConverter
import com.rstit.connector.date.DateConverter
import com.rstit.connector.di.base.scope.ActivityScope
import com.rstit.connector.di.date.names.ChatConverter
import dagger.Module
import dagger.Provides

/**
 * @author Tomasz Trybala
 * @since 2017-07-25
 */
@Module
@ActivityScope
class DateModule {
    @Provides
    @ChatConverter
    fun provideChatConverter(): DateConverter = ChatDateConverter()
}