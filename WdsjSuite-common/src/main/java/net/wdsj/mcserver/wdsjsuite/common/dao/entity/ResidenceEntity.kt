package net.wdsj.mcserver.wdsjsuite.common.dao.entity

import meow.anno.NoArg
import net.wdsj.servercore.database.anntations.MiaoColumn
import javax.persistence.*

/**
 * @author  Arthur
 * @date  2021/2/4 21:53
 * @version 1.0
 */
@Entity
@Table( uniqueConstraints = [UniqueConstraint(columnNames = ["res_name"])])
@NoArg
data class ResidenceEntity (

    @Id
    var id: Long? = null,

    var resName: String = "",

    var server: String= "" ,


    )