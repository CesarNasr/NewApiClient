package com.example.newapiclient.presentation.viewmodel

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.newapiclient.domain.usecase.GetNewsHeadlinesUseCase
import com.example.newapiclient.domain.usecase.GetSearchedNewUseCase

class NewsViewModelFactory(
    private val app: Application,
    private val getNewsHeadlinesUseCase: GetNewsHeadlinesUseCase,
    private val getSearchedNewUseCase: GetSearchedNewUseCase
) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return NewsViewModel(app, getNewsHeadlinesUseCase, getSearchedNewUseCase) as T
    }
}