package org.qtestapp.activities

import android.os.Bundle
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_cached_gifs.*
import org.qtestapp.R
import org.qtestapp.adapters.CachedGifsRecyclerViewAdapter
import org.qtestapp.cache.GifCache
import org.qtestapp.cache.GifCachePolicy
import org.qtestapp.extentions.getCacheDirectory

class CachedGifsActivity : BaseActivity() {

    private lateinit var gifsListAdapter: CachedGifsRecyclerViewAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cached_gifs)

        init()
    }

    override fun init() {

        val gifCache = GifCache.getInstance(getCacheDirectory(), GifCachePolicy())

        gifsListAdapter = CachedGifsRecyclerViewAdapter(this,
                                                        R.layout.cached_gif_list_item,
                                                        gifCache)

        with(gifsListRecyclerView) {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            itemAnimator = DefaultItemAnimator()
            adapter = gifsListAdapter
        }

        gifsListAdapter.resetData(gifCache.getAllFileNames())

    }
}
