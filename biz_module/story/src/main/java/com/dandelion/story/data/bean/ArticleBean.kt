package com.dandelion.story.data.bean

import com.alibaba.fastjson.annotation.JSONField
import com.dandelion.network.bean.IBean

/**
 * 文字列表Bean
 * Created by lin.wang on 2021/7/22
 */
data class ArticleBean(
        @JSONField(name = "title") var title: String?,
        @JSONField(name = "content") var content: String?,
        @JSONField(name = "time") var time: Int
)