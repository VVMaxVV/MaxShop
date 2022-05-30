package com.maxshop.utils.eventStream

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import javax.inject.Inject

class LiveDataEventStream<T> @Inject constructor() {
    private val _liveData = MutableLiveData<T>()

    fun post(data: T) {
        _liveData.value = data!!
    }

    fun stream(): LiveData<T> = _liveData

    fun value(): T? = _liveData.value
}
