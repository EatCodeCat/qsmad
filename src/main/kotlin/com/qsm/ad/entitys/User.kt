package com.qsm.ad.entitys

import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id

/**
 * Created by TQ on 2017/8/10.
 */
@Entity
class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Int? = null
    var username = ""
    var nikname = ""
    var password = ""
    var amount: Float? = null
    var crateTime: java.sql.Timestamp? = null
    var email: String? = null
}