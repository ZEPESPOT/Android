package com.buddman.zepespot

import android.graphics.Color
import android.graphics.drawable.Drawable
import android.support.v4.content.ContextCompat
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.SimpleItemAnimator
import com.buddman.zepespot.databinding.MainCourseCardBinding
import com.buddman.zepespot.utils.CredentialManager
import com.github.nitrico.lastadapter.LastAdapter
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.intentFor


class MainActivity : BaseActivity() {


    val trackList: ArrayList<DummyCourse> by lazy {
        arrayListOf(
                DummyCourse("BTS History", "Walk along the\nfootsteps of BTS!", 35724, ContextCompat.getDrawable(this, R.drawable.bts_history)!!, Color.parseColor("#5773ff")),
                DummyCourse("BTS Mukbang", "BTS’s Taste\nHouse Tour!", 12355, ContextCompat.getDrawable(this, R.drawable.bts)!!, Color.parseColor("#ffb900")),
                DummyCourse("Drama - Dokkaebi", "A filming location for \n‘Dokkaebi’ in Seoul.", 35724, ContextCompat.getDrawable(this, R.drawable.dokkaebi)!!, Color.parseColor("#EC5D99")),
                DummyCourse("Twice History", "Walk along the\nfootsteps of BTS!", 35724, ContextCompat.getDrawable(this, R.drawable.twice)!!, Color.parseColor("#F09563"))
        )
    }
    val trackAdatper: LastAdapter by lazy { LastAdapter(trackList, BR.content) }

    override fun setDefault() {
        initProfile()
        initView()
    }


    private fun initProfile() {
        mainProfile.setImageURI("http://47.74.149.35/api/photo/20H0qfm5DHKnIO96C8UEMp?hashCodes=${CredentialManager.instance.zepetoId}&width=300")
    }

    private fun initView() {
        mainRv.apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            (itemAnimator as SimpleItemAnimator).supportsChangeAnimations = false
        }
        trackAdatper
                .map<DummyCourse, MainCourseCardBinding>(R.layout.main_course_card) {
                    onBind {
                        val position = it.adapterPosition
                        it.binding.apply {
                            mainCardContainer.setOnClickListener {
                                trackList.forEachIndexed { index, item -> item.isSelected = (index == position) }
                                trackAdatper.notifyItemRangeChanged(0, trackList.size)
                            }
                            mainCardReview.setOnClickListener {
                                startActivity(intentFor<ReviewActivity>())
                            }
                            mainCardStart.setOnClickListener {
                                startActivity(intentFor<MainMapsActivity>())
                            }
                        }
                    }
                }
                .into(mainRv)
    }

    override val viewId: Int = R.layout.activity_main
    override val toolbarId: Int = 0
}

data class DummyCourse(
        var title: String,
        var subtitle: String,
        var attendCount: Int,
        var resources: Drawable,
        var bgcolor: Int,
        var isSelected: Boolean = false
)