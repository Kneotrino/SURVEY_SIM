package com.survey.seed.survey

import android.content.Context
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ListView
import android.widget.TextView
import io.realm.Realm
import io.realm.RealmConfiguration


class TableActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_table)

        Log.d("TableActivity","mulai aja kan")
        Realm.init(this)
        val config = RealmConfiguration.Builder()
                .name("feedback.realm").build()
        val realm = Realm.getInstance(config)
        val findAllFeed = ArrayList(realm.where(feedback::class.java).findAll())
//        val list = ArrayList<String>()
        val lst = findViewById<ListView>(R.id.list_view)

//        var adp : ArrayAdapter<String>?=null

//        for (find in findAllFeed)
//            list.add(find.toString())
//        adp = ArrayAdapter(this, android.R.layout.simple_list_item_1,list)

        val myCustumAdapater = MyCustumAdapter(this, findAllFeed)
//        lst.adapter= adp
        lst.adapter= myCustumAdapater

    }

    private class MyCustumAdapter(context: Context, findAllFeed: ArrayList<feedback>): BaseAdapter() {
        private val mContext : Context
        private val mList :  ArrayList<feedback>
        init {
            mContext = context
            mList = findAllFeed
        }
        override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
            val layoutInflater = LayoutInflater.from(mContext)
            val rowMain = layoutInflater.inflate(R.layout.list_layout, parent , false)

            val textTanggal = rowMain.findViewById<TextView>(R.id.textViewTanggal)
            val textSTP = rowMain.findViewById<TextView>(R.id.textViewSTP)
            val textTP = rowMain.findViewById<TextView>(R.id.textViewTP)
            val textP = rowMain.findViewById<TextView>(R.id.textViewP)
            val textSP = rowMain.findViewById<TextView>(R.id.textViewSP)

            textTanggal.text = mList.get(position).tanggalString()
            textSTP.text = mList.get(position).sangatTidakPuas.toString()
            textTP.text = mList.get(position).tidakPuas .toString()
            textP.text = mList.get(position).puas.toString()
            textSP.text = mList.get(position).sangatPuas.toString()
            return rowMain
        }

        override fun getItem(position: Int): Any {
            return "Test String"
        }

        override fun getItemId(position: Int): Long {
            return position.toLong()
        }

        override fun getCount(): Int {
            return mList.size
        }
    }

}
