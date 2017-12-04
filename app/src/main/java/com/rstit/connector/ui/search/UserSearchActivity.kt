package com.rstit.connector.ui.search

import android.app.Activity
import android.content.ActivityNotFoundException
import android.content.Intent
import android.databinding.DataBindingUtil
import android.databinding.ViewDataBinding
import android.os.Bundle
import android.speech.RecognizerIntent
import android.speech.SpeechRecognizer
import android.text.Editable
import android.text.TextWatcher
import com.rstit.connector.R
import com.rstit.connector.databinding.ActivityUserSearchBinding
import com.rstit.connector.di.search.UserSearchModule
import com.rstit.connector.ui.base.BaseActivity
import com.rstit.connector.ui.base.MultiViewAdapter
import com.rstit.connector.ui.chat.ChatActivity
import com.rstit.connector.util.SimpleTextWatcher
import java.util.*
import javax.inject.Inject


/**
 * @author Tomasz Trybala
 * @since 2017-07-21
 */
const val SPEECH_RECOGNITION_CODE = 44

class UserSearchActivity : BaseActivity(), UserSearchViewAccess {
    @Inject
    lateinit var model: UserSearchViewModel

    lateinit var binding: ActivityUserSearchBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        appComponent.plus(UserSearchModule(this)).inject(this)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_user_search)
        binding.model = model
        binding.viewAccess = this
        binding.edtSearch.addTextChangedListener(textWatcher)

        model.isMicAvailable.set(SpeechRecognizer.isRecognitionAvailable(this))
        model.initSearching()
    }

    override fun onDestroy() {
        model.clearDisposables()
        super.onDestroy()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        when (requestCode) {
            SPEECH_RECOGNITION_CODE -> (setSpeechResult(resultCode, data))
        }
    }

    override fun notifyDataSetChanged() = adapter.notifyDataSetChanged()

    override fun startSpeaking() {
        try {
            val intent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH)
            intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault())
            intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM)

            startActivityForResult(intent, SPEECH_RECOGNITION_CODE)
        } catch (e: ActivityNotFoundException) {
            /* option won't be visible */
        }
    }

    override fun finishActivity() = finish()

    override val adapter: MultiViewAdapter by lazy {
        MultiViewAdapter.Builder(model.models)
                .register(R.layout.row_user, UserRowViewModel::class.java, this::setUserListener)
                .build()
    }

    override val textWatcher = SimpleTextWatcher()

    private fun setUserListener(model: UserRowViewModel, binding: ViewDataBinding) =
            binding.root.setOnClickListener({ navigateToChat(model) })

    private fun navigateToChat(model: UserRowViewModel) {
        startActivity(ChatActivity.createIntent(this, model.user))
    }

    private fun setSpeechResult(resultCode: Int, data: Intent?) {
        if (resultCode == Activity.RESULT_OK) {
            data?.let {
                it.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS).apply {
                    if (!isEmpty()) model.searchText.set(get(0))
                    updateCursorAfterSpeech()
                }
            }
        }
    }

    private fun updateCursorAfterSpeech() {
        binding.edtSearch.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {
                /*no-op*/
            }

            override fun onTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {
                /*no-op*/
            }

            override fun afterTextChanged(editable: Editable) {
                binding.edtSearch.setSelection(binding.edtSearch.length())
                binding.edtSearch.removeTextChangedListener(this)
            }
        })
    }
}