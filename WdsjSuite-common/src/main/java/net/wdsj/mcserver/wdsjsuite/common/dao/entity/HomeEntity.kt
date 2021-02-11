package net.wdsj.mcserver.wdsjsuite.common.dao.entity

import net.wdsj.servercore.database.anntations.MiaoColumn
import javax.persistence.*

/**
 * @author  Arthur
 * @date  2021/2/4 21:53
 * @version 1.0
 */
@Entity
@Table( uniqueConstraints = [UniqueConstraint(columnNames = ["uid", "home"])])
data class HomeEntity (

    @Id
    var id: Long? = null,

    var uid: Long = 0,

    @Column(name = "home_id")
    var homeId: Int = 0,

    var server: String= "" ,

    var location: String = ""


    )