package net.wdsj.mcserver.wdsjsuite.common.dao.entity

import meow.anno.NoArg
import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.Table
import javax.persistence.UniqueConstraint

/**
 * @author  Arthur
 * @date  2021/2/4 21:53
 * @version 1.0
 */
@Entity
@Table(uniqueConstraints = [UniqueConstraint(columnNames = ["res_name"])])
@NoArg
data class PlotEntity(

    @Id
    var id: Long? = null,

    var uid: Long = 0,

    var server: String = "",


    )