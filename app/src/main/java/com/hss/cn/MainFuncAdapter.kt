package com.hss.cn

import android.view.View
import android.widget.Toast
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder


/**
 *  宽带账密登录-宽带信息页-账号列表子项目适配器
 */
class MainFuncAdapter(layoutResId: Int, data: List<*>?) : BaseQuickAdapter<MainFuncBean, BaseViewHolder>(layoutResId, data as MutableList<MainFuncBean>?) {
    override fun convert(helper: BaseViewHolder, item: MainFuncBean) {
        helper.setText(R.id.tvTitle, item.topTitle)
        helper.setOnClickListener(R.id.ivBack, View.OnClickListener {
            Toast.makeText(mContext, "点击背面" + (helper.adapterPosition + 1), Toast.LENGTH_SHORT).show()
        })

        helper.setOnClickListener(R.id.ivTop, View.OnClickListener {
            Toast.makeText(mContext, "点击正面" + (helper.adapterPosition + 1), Toast.LENGTH_SHORT).show()
        })
    }
}