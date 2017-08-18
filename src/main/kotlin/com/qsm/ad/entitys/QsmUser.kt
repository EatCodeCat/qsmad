package com.qsm.ad.entitys

import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id

/**
 * Created by TQ on 2017/8/18.
 */
@Entity
class QsmUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Int? = 0
    var username: String? = null
    var status: Int? = null
    var qsmUsername: String? = null
    var qsmPassword: String? = null
    var cookie: String? = null
    var loginTime: java.sql.Timestamp? = null
}