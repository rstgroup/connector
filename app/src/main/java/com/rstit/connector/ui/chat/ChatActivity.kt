package com.rstit.connector.ui.chat

import android.content.Context
import android.content.Intent
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.view.MenuItem
import android.view.inputmethod.EditorInfo
import com.rstit.connector.ConnectorApplication
import com.rstit.connector.R
import com.rstit.connector.databinding.ActivityChatBinding
import com.rstit.connector.di.chat.ChatModule
import com.rstit.connector.model.user.User
import com.rstit.connector.ui.base.BaseActivity
import com.rstit.connector.ui.base.MultiViewAdapter
import com.rstit.connector.util.PaginatedScrollListener
import javax.inject.Inject

/**
 * @author Tomasz Trybala
 * @since 2017-07-24
 */
const val EXTRA_USER = "extra_user"

class ChatActivity : BaseActivity(), ChatViewAccess {
    @Inject
    lateinit var model: ChatViewModel

    lateinit var binding: ActivityChatBinding

    lateinit var user: User

    private fun bindViews() {
        setSupportActionBar(binding.toolbar)
        supportActionBar?.apply {
            title = "${user.name} ${user.lastName}"
            setDisplayHomeAsUpEnabled(true)
        }

        binding.recyclerView.addOnScrollListener(scrollListener)
        binding.edtSearch.setOnEditorActionListener({ _, id, _ -> onImeAction(id) })
    }

    private fun onImeAction(actionId: Int): Boolean {
        if (!model.content.get().isNullOrEmpty() && model.isConnected.get() && actionId == EditorInfo.IME_ACTION_SEND) {
            model.sendMessage()
            return true
        }

        return false
    }

    private fun loadFromIntent() {
        user = intent.getParcelableExtra(EXTRA_USER)
    }

    val scrollListener = object : PaginatedScrollListener() {
        override fun fetchData() {
            //todo implement
        }
    }

    override val adapter: MultiViewAdapter by lazy {
        MultiViewAdapter.Builder(model.models)
                .register(R.layout.row_chat_my_message, ChatMyMessageRowViewModel::class.java)
                .register(R.layout.row_chat_other_message, ChatOtherMessageRowViewModel::class.java)
                .build()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        loadFromIntent()

        ConnectorApplication.get(this)
                .appComponent
                .plus(ChatModule(this))
                .inject(this)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_chat)
        binding.model = model
        binding.viewAccess = this

        bindViews()
        model.otherUser = user
        model.refresh()
    }

    override fun clearScrollListener() = scrollListener.clear()

    override fun setScrollListenerEnabled(enabled: Boolean) {
        scrollListener.enabled = enabled
    }

    override fun closeKeyboard() = hideKeyboard()

    override fun notifyDataRangeChanged(start: Int, itemCount: Int) {
        adapter.notifyItemRangeInserted(start, itemCount)
        binding.recyclerView.scrollToPosition(start)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean =
            when (item?.itemId) {
                android.R.id.home -> {
                    onBackPressed()
                    true
                }
                else -> super.onOptionsItemSelected(item)
            }

    companion object {
        fun createIntent(context: Context, user: User): Intent =
                Intent(context, ChatActivity::class.java).apply { putExtra(EXTRA_USER, user) }
    }
}

