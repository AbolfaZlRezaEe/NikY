package com.abproject.niky.model.datasource.banner

import com.abproject.niky.model.dataclass.Banner
import io.reactivex.Single

interface BannerDataSource {

    fun getBanners(): Single<List<Banner>>
}