package com.rstit.connector.ui.login

import com.rstit.ui.auth.login.LoginViewModel
import javax.inject.Inject

/**
 * @author Tomasz Trybala
 * @since 2017-07-18
 */
class CustomLoginViewModel @Inject constructor() : LoginViewModel() {
    @Inject
    lateinit var viewAccess: CustomLoginViewAccess

    fun validate() {
        if(isInputDataValid){
            viewAccess.closeKeyboard()
        }
    }
}
