package com.survey.seed.survey

//import android.view.Window
import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.os.CountDownTimer
import android.support.v7.app.AppCompatActivity
import android.view.Window
import android.widget.Button
import io.realm.Realm
import io.realm.RealmConfiguration
import java.text.SimpleDateFormat
import java.util.*

class SurveyActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_survey)

        val btnKembali= findViewById(R.id.pilihKeluar) as Button
        val btn1= findViewById<Button>(R.id.pilih1)
        val btn2= findViewById<Button>(R.id.pilih2)
        val btn3= findViewById<Button>(R.id.pilih3)
        val btn4= findViewById<Button>(R.id.pilih4)

        Realm.init(this)


        btnKembali.setOnClickListener({
            val i = Intent(this, MainActivity::class.java)
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            startActivity(i)
        })
        btn1.setOnClickListener({
            insertNewFeedback(1)
        })
        btn2.setOnClickListener({
            insertNewFeedback(2)
        })
        btn3.setOnClickListener({
            insertNewFeedback(3)
        })
        btn4.setOnClickListener({
            insertNewFeedback(4)
        })
    }

    fun insertNewFeedback(x:Int) {
        val config = RealmConfiguration.Builder()
                .name("feedback.realm").build()
        val realm = Realm.getInstance(config)
        realm.beginTransaction()
        val sdf = SimpleDateFormat("ddMMyyyy")
        val st = sdf.format(Date()).toString()
        println("st = ${st}")
        var key: Long = st.toLong()

        val findFirst = realm.where(feedback::class.java).equalTo("id", key).findFirst()

        if (findFirst == null)
        {
            val feedback = realm.createObject(
                    feedback::class.java,
                    key
            )
            feedback.tanggal = Date()
            when (x)
            {
                1 -> feedback.sangatTidakPuas = 1
                2 -> feedback.tidakPuas = 1
                3 -> feedback.puas = 1
                4 -> feedback.sangatPuas = 1
            }
        }
        else {
            when (x)
            {
                1 -> findFirst.sangatTidakPuas = findFirst.sangatTidakPuas?.inc()
                2 -> findFirst.tidakPuas = findFirst.tidakPuas?.inc()
                3 -> findFirst.puas = findFirst.puas?.inc()
                4 -> findFirst.sangatPuas = findFirst.sangatPuas?.inc()
            }
            realm.copyToRealmOrUpdate(findFirst)
        }
        realm.commitTransaction()

        val dialog= Dialog(this)
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
            dialog.setContentView(R.layout.form_makasih)
//            dialog.setTitle("Terimakasih")
            dialog.show()
        object : CountDownTimer(4000, 1000) {

            override fun onTick(millisUntilFinished: Long) {
//                 val detik = Math.round((millisUntilFinished / 1000).toDouble())
//                 Toast.makeText(applicationContext, " $detik Detik" , Toast.LENGTH_SHORT).show()
            }

            override fun onFinish() {
                dialog.dismiss()
            }
        }.start()
//        var ket: String? = "Terimakasih atas partisipasi anda" +
//                "\nPertisipasi Anda membuat kami dapat berbenah untuk memberikan pelayanan yang terbaik\n"
//        Toast.makeText(this, ket , Toast.LENGTH_SHORT).show()


    }
}
