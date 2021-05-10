package com.hss.cn

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder


/**
 *  宽带账密登录-宽带信息页-账号列表子项目适配器
 */
class MainFuncAdapter(layoutResId: Int, data: List<*>?) : BaseQuickAdapter<MainFuncBean, BaseViewHolder>(layoutResId, data as MutableList<MainFuncBean>?) {
    override fun convert(helper: BaseViewHolder, item: MainFuncBean) {
        helper.setText(R.id.tvTitle, item.topTitle)
    }
}