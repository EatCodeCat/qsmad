package com.wxanacms.util

import com.qsm.ad.annotation.JoinField
import com.qsm.ad.annotation.MyForeignKey
import java.util.ArrayList
import javax.persistence.Column
import javax.persistence.Id
import javax.persistence.Table
import javax.persistence.Transient
import kotlin.collections.HashMap
import kotlin.collections.component1
import kotlin.collections.component2

data class Result(val sql: String, val nameMap: Map<String, Any>)

/**
 * 构建查询SQL
 *
 * @param mainCls 主表
 * @param joinC 关联表
 * @param whereMap 查询条件
 * @param joinType 关联方式
 * @return sql, 查询条件Map
 */
fun buildNSQL(mainCls: Class<*>, joinC: Array<Class<*>> = arrayOf(), whereMap: Map<String, Pair<String, Any>> = hashMapOf(), joinType: String = " left join "): Result {
    var sql = "select "
    val fieldArr = ArrayList<String>()
    var nameMap = HashMap<String, Any>()
    var tableMap = mutableMapOf<String, Class<*>>()

    for (oc in joinC) {
        tableMap.put(oc.simpleName, oc)
    }

    val tableName = buildFieldStr(mainCls, tableMap, fieldArr)
    sql += fieldArr.joinToString(",")
    sql += " from $tableName as $tableName "

    sql += buildJoinStr(arrayOf(mainCls, *joinC), tableMap, joinType)
    if (whereMap.size > 0) {
        sql += " where "
        for ((k, v) in whereMap) {
            if (k?.indexOf(".") != -1) {
                var (a1, a2) = k.split('.')
                var col = getFieldsMap(tableMap[a1])[a2]
                var t =  getTableNameFormEntity(tableMap[a1])
                sql += " $t.$col ${v.first} :$col and"
                nameMap.put(col!!, v.second)
            } else {
                var col = getFieldsMap(mainCls)[k]
                sql += " $col ${v.first} :$col and"
                nameMap.put(col!!, v.second)
            }

        }
        sql += " 1=1 "
    }

    return Result(sql, nameMap)
}

fun buildFieldStr(c: Class<*>, tableMap: MutableMap<String, Class<*>>, fieldArr: ArrayList<String>): String {
    val tableName = getTableNameFormEntity(c)
    val fs = c.declaredFields
    for (f in fs) {
        val col = f.getAnnotation(Column::class.java)
        var joinCol = f.getAnnotation(JoinField::class.java)
        if (col != null) {
            val colName = col.name
            if (joinCol != null) {
                if (joinCol.entityName in tableMap) {
                    var t = getTableNameFormEntity(tableMap[(joinCol.entityName)])
                    fieldArr.add("$t.$colName as ${f.name}")
                }
            } else {
                fieldArr.add("$tableName.$colName as ${f.name}")
            }
        } else {
            val col = f.getAnnotation(Transient::class.java)
            if (col == null) {
                fieldArr.add(f.name)
            }
        }
    }
    return tableName
}


fun buildCountSQL(sql: String): String {
    var re = Regex("select (.*) from")
    var matches = re.find(sql)
    var countsql = ""
    if (matches != null && matches.groups.size > 0) {
        countsql = sql.replace(matches.groupValues[1], "count(*)")
    }
    return countsql
}

fun getFieldsMap(c: Class<*>?): Map<String, String> {
    var nameMap = mutableMapOf<String, String>()
    val fs = c!!.declaredFields
    for (f in fs) {
        val col = f.getAnnotation(Column::class.java)
        if (col != null) {
            nameMap.put(f.name, col.name)
        } else {
            nameMap.put(f.name, f.name)
        }
    }
    return nameMap
}

fun buildJoinStr(joinC: Array<Class<*>>, tableMap: Map<String, Class<*>>, joinType: String): String {
    var joinOnArr = ArrayList<String>()
    for (c in joinC) {
        val fs = c.declaredFields
        for (f in fs) {
            val col = f.getAnnotation(Column::class.java)
            var fkCol = f.getAnnotation(MyForeignKey::class.java)
            if (fkCol != null) {
                if (fkCol.entityName in tableMap) {
                    val colName = col.name ?: f.name
                    val tableName = getTableNameFormEntity(c)
                    val fkCls = tableMap[fkCol.entityName]
                    var t = getTableNameFormEntity(fkCls)
                    var id = getIdFieldfromClass(fkCls)
                    joinOnArr.add(" $joinType $t as $t on $tableName.$colName=$t.$id")
                }
            }
        }
    }
    return joinOnArr.joinToString(" ")


}

fun getIdFieldfromClass(c: Class<*>?): String? {
    if (c != null) {
        val fs = c.declaredFields
        for (f in fs) {
            var idCol = f.getAnnotation(Id::class.java)
            if (idCol != null) {
                return f.getAnnotation(Column::class.java).name
            }
        }
    }
    return null
}

fun getTableNameFormEntity(c: Class<*>?): String {

    if (c != null) {
        val t = c.getAnnotation(Table::class.java)
        if (t != null) {
            val tableName = c.getAnnotation(Table::class.java).name
            return tableName
        }
        return c.simpleName
    }
    return ""
}

