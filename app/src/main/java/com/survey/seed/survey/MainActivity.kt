package com.survey.seed.survey

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.LayoutInflater
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import io.realm.Realm
import io.realm.RealmConfiguration
import java.io.File
import java.io.FileWriter


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val btnKeluar = findViewById(R.id.buttonKeluar) as Button
        val btnSurvey = findViewById(R.id.buttonSurvey) as Button
        val btnHasil = findViewById(R.id.buttonHasil) as Button
        val btnPrint = findViewById(R.id.buttonPrint) as Button
        Realm.init(this)
        println(filesDir.absolutePath)
        btnKeluar.setOnClickListener({
            Toast.makeText(this, "Terimakasih" , Toast.LENGTH_LONG).show()
            finish();
            System.exit(0);
        })
        btnSurvey.setOnClickListener({
            val i = Intent(this, SurveyActivity::class.java)
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            startActivity(i)
        })
        btnHasil.setOnClickListener({
            adminLogin(this)
        })
        btnPrint.setOnClickListener({
            adminExportCSV(this)

            //endOfline

        })

    }

    fun adminLogin (context: Context) {

        val li = LayoutInflater.from(context)
        val promptsView = li.inflate(R.layout.form_login, null)
        val alertDialogBuilder = AlertDialog.Builder(context)

        alertDialogBuilder.setView(promptsView)

        val userInput = promptsView
                .findViewById(R.id.editTextDialogUserInput) as EditText
        // set dialog message
        val negativeButton = alertDialogBuilder
                .setCancelable(false)
                .setPositiveButton("MASUK",
                        DialogInterface.OnClickListener { dialog, id ->
                            println("userInput = ${userInput.text}")
                            val pass : String = "admin"
                            val input : String = userInput.text.toString()
                            if (input == pass) {
                                val i = Intent(this, TableActivity::class.java)
                                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                                startActivity(i)
                            }
                            else
                                Toast.makeText(applicationContext, " Password Salah" , Toast.LENGTH_SHORT).show()
                        })
                .setNegativeButton("BATAL",
                        DialogInterface.OnClickListener { dialog, id ->

                            dialog.cancel()
                        })
        val alertDialog = alertDialogBuilder.create()
        // show it
        alertDialog.show()

    }
    fun adminExportCSV (context: Context) {

        val li = LayoutInflater.from(context)
        val promptsView = li.inflate(R.layout.form_login, null)
        val alertDialogBuilder = AlertDialog.Builder(context)

        alertDialogBuilder.setView(promptsView)

        val userInput = promptsView
                .findViewById(R.id.editTextDialogUserInput) as EditText
        // set dialog message
        val negativeButton = alertDialogBuilder
                .setCancelable(false)
                .setPositiveButton("MASUK",
                        DialogInterface.OnClickListener { dialog, id ->
                            println("userInput = ${userInput.text}")
                            val pass : String = "admin"
                            val input : String = userInput.text.toString()
                            if (input == pass) {
                                val config = RealmConfiguration.Builder()
                                        .name("feedback.realm").build()
                                val realm = Realm.getInstance(config)
                                val findAll = realm.where(feedback::class.java).findAll()
                                val CSV_HEADER = "id,tanggal,SAP,SP,TP,STP"
                                var fileWriter: FileWriter? = null

                                try {
                                    val fileCSV = File(getExternalFilesDir(null), "feed.csv")
                                    println("fileCSV = ${fileCSV}")
                                    fileWriter = FileWriter(fileCSV)
                                    fileWriter.append(CSV_HEADER)
                                    fileWriter.append('\n')

                                    for (customer in findAll) {
                                        fileWriter.append(customer.id.toString())
                                        fileWriter.append(',')
                                        fileWriter.append(customer.tanggalString())
                                        fileWriter.append(',')
                                        fileWriter.append(customer.sangatPuas.toString())
                                        fileWriter.append(',')
                                        fileWriter.append(customer.puas.toString())
                                        fileWriter.append(',')
                                        fileWriter.append(customer.tidakPuas.toString())
                                        fileWriter.append(',')
                                        fileWriter.append(customer.sangatTidakPuas.toString())
                                        fileWriter.append('\n')
                                    }

                                    println("Write CSV successfully!")
                                    Toast.makeText(this,"${findAll.size} data tersimpan CSV di : {${fileCSV}}",Toast.LENGTH_LONG).show()

                                } catch (e: Exception) {
                                    println("Writing CSV error!")
                                    e.printStackTrace()
                                } finally {
                                    try {
                                        fileWriter!!.flush()
                                        fileWriter.close()
                                    } catch (e: Exception) {
                                        println("Flushing/closing error!")
                                        e.printStackTrace()
                                    }
                                }
                            }
                            else
                                Toast.makeText(applicationContext, " Password Salah" , Toast.LENGTH_SHORT).show()
                        })
                .setNegativeButton("BATAL",
                        DialogInterface.OnClickListener { dialog, id ->

                            dialog.cancel()
                        })
        val alertDialog = alertDialogBuilder.create()
        // show it
        alertDialog.show()

    }
}

