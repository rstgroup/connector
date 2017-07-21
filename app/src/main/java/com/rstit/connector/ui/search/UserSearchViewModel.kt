package com.rstit.connector.ui.search

import android.databinding.ObservableBoolean
import com.rstit.binding.ObservableString
import com.rstit.ui.base.model.BaseViewModel
import javax.inject.Inject

/**
 * @author Tomasz Trybala
 * @since 2017-07-21
 */
class UserSearchViewModel@Inject constructor(): BaseViewModel() {
    val loading = ObservableBoolean()
    val isEmpty = ObservableBoolean()
    val isMicAvailable = ObservableBoolean()
    val searchText = ObservableString()

    fun clearText(){
        searchText.set("")
    }
}