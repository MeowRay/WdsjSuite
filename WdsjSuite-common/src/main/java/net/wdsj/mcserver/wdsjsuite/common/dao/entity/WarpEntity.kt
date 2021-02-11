package net.wdsj.mcserver.wdsjsuite.common.dao.entity

import javax.persistence.*

/**
 * @author  Arthur
 * @date  2020/10/18 19:02
 * @version 1.0
 */


@Entity
@Table( uniqueConstraints = [UniqueConstraint(columnNames = [ "key"])])
data class WarpEntity(

        @Id
        var id: Long? = null,

        var key: String= "",

        var server: String= "" ,

        var location: String= ""
) {
    constructor(key: String, server: String ,  location: String) : this(null, key,  server , location)
}