package com.bolesta.myapplication

import android.os.AsyncTask
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import java.io.File
import java.io.FileOutputStream
import java.net.URL

public val key_list = listOf<String>(
    "", "Esc", "1", "2", "3", "4", "5", "6", "7", "8", "9", "0", "-", "=", "", "Tab",
    "Q", "W", "E", "R", "T", "Y", "U", "I", "O", "P", "[", "]", "\n", "", "A", "S",
    "D", "F", "G", "H", "J", "K", "L", ";", "'", "`", "Shift Left", "\\", "Z", "X", "C",
    "V", "B", "N", "M", ",", ".", "/", "Shift Right", "KP *", "Alt Left (-> Command)", " ",
    "Caps Lock", "F1", "F2", "F3", "F4", "F5", "F6", "F7", "F8", "F9", "F10", "Num Lock",
    "Scroll Lock", "KP 7", "KP 8", "KP 9", "KP -", "KP 4", "KP 5", "KP 6", "KP +", "KP 1",
    "KP 2", "KP 3", "KP 0", "KP .", "", "", "International", "F11", "F12", "", "", "",
    "", "", "", "", "KP Enter", "Ctrl Right", "KP /", "PrintScrn", "Alt Right (-> Command)",
    "", "Home", "Cursor Up", "Page Up", "", "Cursor Right", "End", "Cursor Down",
    "Page Down", "Insert", "Delete", "", "", "", "", "", "", "", "Pause", "",
    "", "", "", "", "Logo Left (-> Option)", "Logo Right (-> Option)"
)

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        // get reference to button
        val btn_click_me = findViewById(R.id.btn) as Button
        // set on-click listener
        btn_click_me.setOnClickListener {
            Toast.makeText(this@MainActivity, "You clicked me.", Toast.LENGTH_SHORT).show()
            try {
                Pobierz().execute("http://192.168.1.106/klawiatura.txt", "/data/user/0/com.bolesta.myapplication/files/klawiatura.txt")
        }
            catch(e: Exception){
                Toast.makeText(this, e.toString(), Toast.LENGTH_LONG).show()
            }
        }
    }

    fun download(link: String, path: String) { //Funkcja pobierania pliku z serwera
        URL(link).openStream().use { input ->
            FileOutputStream(File(path)).use { output ->
                input.copyTo(output)
            }
        }
    }

    private inner class Pobierz : AsyncTask<String, Void, String>() {
        var url = mutableListOf<String>()
        var output = ""
        override fun doInBackground(vararg params: String): String? {
            try{
                println("test1")
              //  download(link=params[0],path=params[1])
                var url2 = URL("http://192.168.1.106/klawiatura.txt").readText()
                println("${url2::class.qualifiedName}")
                url2.toString()
                var url3 = url2.split("\n")
                url = url3.toMutableList()

                url.forEach {
                    output += key_list[it.toInt()]
                }
            }
            catch (e: Exception) {
                println(e.toString())
            }
            return "output"
        }

        override fun onPostExecute(result: String?) {
            println("test2")
            findViewById<TextView>(R.id.Output).text = output
        }
    }
}


