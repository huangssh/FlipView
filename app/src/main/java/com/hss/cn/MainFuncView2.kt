package com.hss.cn

import android.animation.*
import android.annotation.SuppressLint
import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.GridLayoutManager
import kotlinx.android.synthetic.main.view_main_func.view.*
import java.util.*


/**
 * 说明：主功能区入口
 *
 */
class MainFuncView2 : LinearLayout {
    private var mIsShowBack = false // 是否牌面全部朝下
    private val bindAccounts: ArrayList<MainFuncBean> = ArrayList()
    private var currentNbr = 0
    private lateinit var mLeftInSet: AnimatorSet
    private lateinit var mRightOutSet: AnimatorSet

    constructor(context: Context?) : super(context) {
        init()
    }

    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs) {
        init()
    }

    private fun init() {
        LayoutInflater.from(context).inflate(R.layout.view_main_func, this)

        val bean1 = MainFuncBean()
        bean1.backUrl = "a"
        bean1.topUrl = "a"
        bean1.topTitle = "充话费1"
        bean1.backTitle = "流量套餐1"
        bindAccounts.add(bean1)
        val bean2 = MainFuncBean()
        bean2.backUrl = ""
        bean2.topUrl = "a"
        bean2.topTitle = "充话费2"
        bean2.backTitle = "流量套餐2"
        bindAccounts.add(bean2)
        val bean3 = MainFuncBean()
        bean3.backUrl = "a"
        bean3.topUrl = "a"
        bean3.topTitle = "充话费3"
        bean3.backTitle = "流量套餐3"
        bindAccounts.add(bean3)
        val bean4 = MainFuncBean()
        bean4.backUrl = ""
        bean4.topUrl = "a"
        bean4.topTitle = "充话费4"
        bean4.backTitle = "流量套餐4"
        bindAccounts.add(bean4)

        rvMainFunc.layoutManager = GridLayoutManager(context, 4)
        rvMainFunc.adapter = MainFuncAdapter(R.layout.item_main_func, bindAccounts)

        rvMainFunc.post {
            // 当有一面需要翻面则开启动画
            var isNeedFlip = false
            for(item in bindAccounts){
                if (item.backUrl != ""){
                    isNeedFlip = true
                    break
                }
            }
            if (isNeedFlip){
                flipCard()
            }
        }
    }

    // 翻转卡片
    @SuppressLint("ResourceType")
    fun flipCard() {
        // 跳过没有背面的卡片
        if (bindAccounts.get(currentNbr).backUrl == ""){
            if (!mIsShowBack) {
                if (currentNbr % bindAccounts.size == 3) {
                    mIsShowBack = true
                }
            } else {
                if (currentNbr % bindAccounts.size == 3) {
                    mIsShowBack = false
                }
            }
            currentNbr = (currentNbr + 1) % bindAccounts.size
            flipCard()
        }else{
            val layout = rvMainFunc.getChildAt(currentNbr % bindAccounts.size);
            val ivTop = layout.findViewById<ImageView>(R.id.ivTop)
            val ivBack = layout.findViewById<ImageView>(R.id.ivBack)
            val ivTopTag = layout.findViewById<ImageView>(R.id.ivTopTag)
            val ivBackTag = layout.findViewById<ImageView>(R.id.ivBackTag)
            val tvTitle = layout.findViewById<TextView>(R.id.tvTitle)

            mRightOutSet = AnimatorInflater.loadAnimator(context, R.anim.anim_out) as AnimatorSet
            mLeftInSet = AnimatorInflater.loadAnimator(context, R.anim.anim_in) as AnimatorSet

            mRightOutSet.addListener(object : Animator.AnimatorListener {
                override fun onAnimationStart(animation: Animator?) {
                    if (!mIsShowBack) {
                        ivTopTag.visibility = View.GONE
                    }else{
                        ivBackTag.visibility = View.GONE
                    }
                }
                override fun onAnimationEnd(animation: Animator?) {
                    if (!mIsShowBack) {
                        ivBackTag.visibility = View.VISIBLE
                    }else{
                        ivTopTag.visibility = View.VISIBLE
                    }

                    // 设置正反面置反
                    if (!mIsShowBack) {
                        if (currentNbr % bindAccounts.size == 3) {
                            mIsShowBack = true
                        }
                    } else {
                        if (currentNbr % bindAccounts.size == 3) {
                            mIsShowBack = false
                        }
                    }
                    currentNbr = (currentNbr + 1) % bindAccounts.size
                    flipCard()
                }
                override fun onAnimationCancel(animation: Animator?) {}
                override fun onAnimationRepeat(animation: Animator?) {}
            })

            mRightOutSet.childAnimations[1].addListener(object : AnimatorListenerAdapter() {
                override fun onAnimationEnd(animation: Animator) {
                    super.onAnimationEnd(animation)
                    // 标题在周期过半就开始变化
                    if (!mIsShowBack) {
                        tvTitle.text = bindAccounts.get(currentNbr).backTitle
                        ivTop.visibility = View.INVISIBLE
                        ivBack.visibility = View.VISIBLE
                    } else {
                        tvTitle.text = bindAccounts.get(currentNbr).topTitle
                        ivTop.visibility = View.VISIBLE
                        ivBack.visibility = View.INVISIBLE
                    }
                }
            })

            // 正面朝上
            if (!mIsShowBack) {
                mRightOutSet.setTarget(ivTop)
                mLeftInSet.setTarget(ivBack)
                mRightOutSet.start()
                mLeftInSet.start()

            } else { // 背面朝上
                mRightOutSet.setTarget(ivBack)
                mLeftInSet.setTarget(ivTop)
                mRightOutSet.start()
                mLeftInSet.start()
            }
        }

//        (mRightOutSet.childAnimations[0] as ObjectAnimator).addUpdateListener {
//            Log.i("hss", it.animatedValue.toString())
//        }
    }

    // 改变视角距离, 贴近屏幕
    private fun setCameraDistance() {
        val distance = 16000
        val scale = resources.displayMetrics.density * distance
//        mFlCardFront.cameraDistance = scale
//        mFlCardBack.cameraDistance = scale
    }

    fun pause() {
        mRightOutSet.pause()
        mLeftInSet.pause()
    }

    fun resume() {
        mRightOutSet.resume()
        mLeftInSet.resume()
    }

    override fun setRotationY(rotationY: Float) {
        super.setRotationY(rotationY)
        Log.i("hss", rotationY.toString())
    }

}