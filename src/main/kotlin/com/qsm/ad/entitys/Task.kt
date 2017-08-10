package com.qsm.ad.entitys

import javax.persistence.Entity
import javax.persistence.Id


/**
 * Created by TQ on 2017/8/10.
 */
@Entity
class Task {
    @Id
    var id: Int? = null
    var taskName: String = ""
    var taskResult: String? = ""
    var status: Int = 0
    var exec_time: java.sql.Timestamp? = null
    var log: String = ""
    var hour: Int = 0
    var minute: Int = 0
    var second: Int = 0
    var userId: Int? = null
}