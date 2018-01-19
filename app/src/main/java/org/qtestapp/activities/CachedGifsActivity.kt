package org.qtestapp.activities

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_cached_gifs.*
import org.qtestapp.R
import org.qtestapp.adapters.DeleteActionItemConfiguration
import org.qtestapp.adapters.SimpleGifAdapter
import org.qtestapp.cache.GifCache
import org.qtestapp.extentions.getCacheDirectory
import org.qtestapp.loader.GifLoader

class CachedGifsActivity : BaseActivity() {

    private lateinit var gifsListAdapter: SimpleGifAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cached_gifs)

        init()
    }

    override fun init() {

        val gifCache = GifCache.getInstance(getCacheDirectory())

        gifsListAdapter = SimpleGifAdapter(R.layout.gif_list_item,
                                           gifCache,
                                           GifLoader(gifCache),
                                           DeleteActionItemConfiguration())

        with(gifsListRecyclerView) {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            adapter = gifsListAdapter
        }

        gifsListAdapter.resetData(gifCache.getAllCachedGifs())
    }
}
