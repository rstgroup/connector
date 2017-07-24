package com.rstit.connector.ui.chat

import android.content.Context
import android.content.Intent
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.view.MenuItem
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

    private fun setToolbar() {
        setSupportActionBar(binding.toolbar)
        supportActionBar?.apply {
            title = "Chat"
            setDisplayHomeAsUpEnabled(true)
        }
    }

    val scrollListener = object : PaginatedScrollListener() {
        override fun fetchData() {
            //todo implement
        }
    }

    override val adapter: MultiViewAdapter by lazy {
        MultiViewAdapter.Builder(model.models)
                .build()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        ConnectorApplication.get(this)
                .appComponent
                .plus(ChatModule(this))
                .inject(this)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_chat)
        binding.model = model
        binding.viewAccess = this

        setToolbar()
    }

    override fun clearScrollListener() = scrollListener.clear()

    override fun setScrollListenerEnabled(enabled: Boolean) {
        scrollListener.enabled = enabled
    }

    override fun closeKeyboard() = hideKeyboard()

    override fun notifyDataSetChanged() = adapter.notifyDataSetChanged()

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