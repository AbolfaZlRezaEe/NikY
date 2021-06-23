package com.abproject.niky.model.datasource.banner

import com.abproject.niky.model.apiservice.NikyApiService
import com.abproject.niky.model.model.Banner
import io.reactivex.Single
import javax.inject.Inject

class BannerRemoteDataSource @Inject constructor(
    private val apiService: NikyApiService,
) : BannerDataSource {

    override fun getBanners(): Single<List<Banner>> {
        return apiService.getBanners()
    }
}