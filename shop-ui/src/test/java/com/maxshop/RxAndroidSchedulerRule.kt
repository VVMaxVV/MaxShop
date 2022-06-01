package com.maxshop

import io.reactivex.Scheduler
import io.reactivex.android.plugins.RxAndroidPlugins
import io.reactivex.plugins.RxJavaPlugins
import io.reactivex.schedulers.Schedulers
import org.junit.rules.TestRule
import org.junit.runner.Description
import org.junit.runners.model.Statement

class RxAndroidSchedulerRule(
    private val schedulerInstance: Scheduler = Schedulers.trampoline()
) : TestRule {

    override fun apply(base: Statement, description: Description): Statement {
        return object : Statement() {
            @Throws(Throwable::class)
            override fun evaluate() {
                // prepare
                resetPlugins()
                setupJavaHandlers()
                setupAndroidHandlers()

                // execute
                base.evaluate()

                // reset
                resetPlugins()
            }
        }
    }

    private fun resetPlugins() {
        RxJavaPlugins.reset()
        RxAndroidPlugins.reset()
    }

    private fun setupJavaHandlers() {
        RxJavaPlugins.setComputationSchedulerHandler { schedulerInstance }
        RxJavaPlugins.setInitComputationSchedulerHandler { schedulerInstance }
        RxJavaPlugins.setIoSchedulerHandler { schedulerInstance }
        RxJavaPlugins.setInitIoSchedulerHandler { schedulerInstance }
    }

    private fun setupAndroidHandlers() {
        RxAndroidPlugins.setInitMainThreadSchedulerHandler { schedulerInstance }
        RxAndroidPlugins.setMainThreadSchedulerHandler { schedulerInstance }
    }
}