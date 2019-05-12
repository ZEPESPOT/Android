package com.buddman.zepespot

import android.graphics.drawable.Drawable
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.SimpleItemAnimator
import com.buddman.zepespot.databinding.ReviewCardBinding
import com.github.nitrico.lastadapter.LastAdapter
import kotlinx.android.synthetic.main.activity_review.*


class ReviewActivity : BaseActivity() {


    val reviewList : ArrayList<DummyCourse> by lazy {
        arrayListOf<DummyCourse>()
    }
    val reviewAdapter : LastAdapter by lazy { LastAdapter(reviewList, BR.content)}

    override fun setDefault() {
        initView()
    }


    private fun initView() {
        reviewRv.apply {
            layoutManager = LinearLayoutManager(this@ReviewActivity)
            (itemAnimator as SimpleItemAnimator).supportsChangeAnimations = false
        }
        reviewAdapter
                .map<Review, ReviewCardBinding>(R.layout.review_card) {
                    onClick {
                        val position = it.adapterPosition
                        reviewList.forEachIndexed { index, item -> item.isSelected = (index == position) }
                        reviewAdapter.notifyItemRangeChanged(0, reviewList.size)
                    }
                }
                .into(reviewRv)
    }

    override val viewId: Int = R.layout.activity_review
    override val toolbarId: Int = 0
}

data class Review (
        var title : String,
        var subtitle : String,
        var attendCount : Int,
        var resources : Drawable,
        var bgcolor : Int,
        var isSelected : Boolean = false
)