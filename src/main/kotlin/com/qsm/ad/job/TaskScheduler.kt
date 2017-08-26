package com.qsm.ad.job

import org.quartz.*
import org.quartz.impl.StdSchedulerFactory

import java.text.ParseException
import java.util.Date

/**
 * Created by think on 2017/8/20.
 */
object TaskScheduler {
    private var jobDetail: JobDetail? = null
    private val scheduler: Scheduler? = null

    init {
        try {
            val schedulerFactory = StdSchedulerFactory()
            val scheduler = schedulerFactory.scheduler
            scheduler.start()
        } catch (e: SchedulerException) {
            e.printStackTrace()
        }

    }

    fun addTaskJob(name: String, cronExpression: String) {
        jobDetail = JobDetail(name, TaskJob::class.java)
        var trigger: Trigger? = null
        try {
            trigger = CronTrigger("cronTrigger", Scheduler.DEFAULT_GROUP, cronExpression)
            val date = scheduler!!.scheduleJob(jobDetail, trigger)
        } catch (e: ParseException) {
            e.printStackTrace()
        } catch (e: SchedulerException) {
            e.printStackTrace()
        }
    }

    fun pauseJob(name: String) {
        try {
            scheduler!!.pauseJob(name, Scheduler.DEFAULT_GROUP)
        } catch (e: SchedulerException) {
            e.printStackTrace()
        }

    }

    fun removeJob(name: String) {
        this.pauseJob(name)
        try {
            scheduler!!.unscheduleJob(name, Scheduler.DEFAULT_GROUP)
            scheduler.deleteJob(name, Scheduler.DEFAULT_GROUP)
        } catch (e: SchedulerException) {
            e.printStackTrace()
        }

    }

    fun modifyJob(name: String, cronExpression: String) {
        removeJob(name)
        addTaskJob(name, cronExpression)
    }
}
