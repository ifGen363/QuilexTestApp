package org.qtestapp.activities

import android.os.Bundle
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.SearchView
import android.view.Menu
import android.view.MenuItem
import kotlinx.android.synthetic.main.activity_gifs_list.*
import org.qtestapp.R
import org.qtestapp.adapters.GifsRecyclerViewAdapter
import org.qtestapp.extentions.enqueue
import org.qtestapp.extentions.getClient
import org.qtestapp.rest.SwipeToRefreshNetworkResponse
import org.qtestapp.rest.model.response.GifsRootModel
import retrofit2.Call


class GifsListActivity : BaseActivity(), SearchView.OnQueryTextListener, SwipeRefreshLayout.OnRefreshListener {

    private val gifsListAdapter = GifsRecyclerViewAdapter(this, R.layout.gif_list_item)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_gifs_list)

        init()

        refreshData()
    }

    override fun init() {

        gifsSwipeToRefresh.setOnRefreshListener(this)

        with(gifsListRecyclerView) {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            itemAnimator = DefaultItemAnimator()
            adapter = gifsListAdapter
        }
    }

    override fun onQueryTextSubmit(query: String): Boolean {
        refreshData(query)
        return true
    }

    override fun onQueryTextChange(newText: String?): Boolean = false


    override fun onRefresh() {
        refreshData()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_gif_list, menu)
        val searchView = menu.findItem(R.id.action_search).actionView as SearchView
        searchView.queryHint = getString(R.string.search_hint)
        searchView.setOnQueryTextListener(this)

        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_refresh -> refreshData()
        }
        return super.onOptionsItemSelected(item)
    }


    private fun refreshData(searchQuery: String = "") {

        val call: Call<GifsRootModel> = if (searchQuery.isNotEmpty()) {
            getClient().getGifsList(query = searchQuery)
        } else {
            getClient().getGifsList()
        }

        enqueue(call, object : SwipeToRefreshNetworkResponse<GifsRootModel>(this, gifsSwipeToRefresh) {
            override fun onResult(data: GifsRootModel) {
                gifsListAdapter.resetData(data.gifUrls)
            }
        })
    }
}
