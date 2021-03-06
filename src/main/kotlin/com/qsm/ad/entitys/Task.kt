package com.qsm.ad.entitys

import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id


/**
 * Created by TQ on 2017/8/10.
 */
@Entity
class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Int = 0
    var taskName: String? = null
    var taskResult: String? = null
    var goodsKey: String? = null
    var goodsList: String? = null
    var status: Int? = null
    var exec_time: java.sql.Timestamp? = null
    var log: String? = null
    var hour: Int? = null
    var minute: Int? = null
    var second: Int? = null
    var username: String? = null
    var createTime: java.sql.Timestamp? = null
    var qsmUsername: String? = null
    var qsmPassword: String? = null
    var cookie: String? = null
}