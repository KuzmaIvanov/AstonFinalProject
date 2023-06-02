package com.example.astonfinalproject.presentation.pagination

import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView

class RecyclerViewGridLayoutScrollListener(
    val loadMore: () -> Unit
): RecyclerView.OnScrollListener() {

    private var endWithAuto = false

    override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
        super.onScrollStateChanged(recyclerView, newState)
        if(newState == RecyclerView.SCROLL_STATE_IDLE) {
            val layoutManager = recyclerView.layoutManager as GridLayoutManager
            val visibleItemCount = layoutManager.childCount
            val totalItemCount = layoutManager.itemCount
            val firstVisibleItemPosition = layoutManager.findLastVisibleItemPosition()
            if (visibleItemCount + firstVisibleItemPosition >= totalItemCount && !endWithAuto) {
                endWithAuto = true
                loadMore()
            } else {
                endWithAuto = false
            }
        }
    }
}