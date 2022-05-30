package com.maxshop

import com.maxshop.model.TypeSort
import com.maxshop.utils.eventStream.RxEventStream
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SortStream @Inject constructor(rxEventStream: RxEventStream<TypeSort>) {
    val value = rxEventStream.also {
        it.post(TypeSort.Popular)
    }
}
