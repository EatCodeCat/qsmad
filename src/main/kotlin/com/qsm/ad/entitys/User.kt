package com.qsm.ad.entitys
import org.springframework.security.core.GrantedAuthority
import java.io.Serializable
import org.springframework.security.core.userdetails.UserDetails
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id


@Entity // 实体
class User : UserDetails, Serializable {

    @Id // 主键
    @GeneratedValue(strategy = GenerationType.IDENTITY) // 自增长策略
    var id: Long? = null // 用户的唯一标识

    var name: String? = null


    private var username: String? = null // 用户账号，用户登录时的唯一标识

    private var password: String? = null // 登录时密码


    protected constructor() { // JPA 的规范要求无参构造函数；设为 protected 防止直接使用
    }

    override fun getAuthorities(): Collection<GrantedAuthority>? {
        return this.authorities
    }


    override fun getUsername(): String? {
        return username
    }

    fun setUsername(username: String) {
        this.username = username
    }

    override fun getPassword(): String? {
        return password
    }

    fun setPassword(password: String) {
        this.password = password
    }

    override fun isAccountNonExpired(): Boolean {
        return true
    }

    override fun isAccountNonLocked(): Boolean {
        return true
    }

    override fun isCredentialsNonExpired(): Boolean {
        return true
    }

    override fun isEnabled(): Boolean {
        return true
    }
}