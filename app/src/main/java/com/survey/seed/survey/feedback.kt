package com.survey.seed.survey

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey
import io.realm.annotations.RealmClass
import java.text.SimpleDateFormat
import java.util.*

@RealmClass
open class feedback() : RealmObject() {
    @PrimaryKey
    var id: Long = 0
    var sangatTidakPuas: Int? = 0
    var tidakPuas: Int? = 0
    var puas: Int? = 0
    var sangatPuas: Int? = 0
    var tanggal: Date? = null

    fun tanggalString(): String {
        val sdf = SimpleDateFormat("dd-MM-yyyy")
        return sdf.format(tanggal).toString()
    }
    override fun toString(): String {

        val sdf = SimpleDateFormat("dd-MM-yyyy")
        return "tanggal=${sdf.format(tanggal)}\nSTP=$sangatTidakPuas\tTP=$tidakPuas\tSP=$puas\tSAP=$sangatPuas"
//        return "feedback(id=$id, ket=$ket, nilai=$nilai, tanggal=$tanggal)"
    }


}