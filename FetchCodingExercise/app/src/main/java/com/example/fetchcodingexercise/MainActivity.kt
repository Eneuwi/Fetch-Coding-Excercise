package com.example.fetchcodingexercise

import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.GsonBuilder
import okhttp3.*
import java.io.IOException


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        findViewById<RecyclerView>(R.id.mainRecyclerView).layoutManager = LinearLayoutManager(this)

        run()
    }

    private fun run() {
        //okhttp connection
        val url = "https://fetch-hiring.s3.amazonaws.com/hiring.json"
        val request = Request.Builder().url(url).build()
        val client = OkHttpClient()
        client.newCall(request).enqueue(object: Callback {
            override fun onResponse(call: Call?, response: Response) {
                val body = response.body()?.string()
                val gson = GsonBuilder().create()
                val jsonItems: Array<JsonItem?> = gson.fromJson(
                    body,
                    Array<JsonItem?>::class.java
                )

                //filter out items where name is empty or null
                var jsonNonNull= ArrayList<JsonItem?>()
                for(item in jsonItems){
                    if( item?.name.toString() != "" && item?.name.toString() != " " && item?.name.toString() != "null" && item?.name != null)
                    {
                        jsonNonNull.add(item)
                    }
                }

                //sort by ListId
                var sortedJsonItems1 = ArrayList<JsonItem?>()
                var sortedJsonItems2 = ArrayList<JsonItem?>()
                var sortedJsonItems3 = ArrayList<JsonItem?>()
                var sortedJsonItems4 = ArrayList<JsonItem?>()
                var sortedJsonItems = ArrayList<JsonItem?>()
                for(i in jsonNonNull) {
                    if(i?.listId == 1) {
                        sortedJsonItems1.add(i)
                    }
                    else if(i?.listId == 2) {
                        sortedJsonItems2.add(i)
                    }
                    else if(i?.listId == 3) {
                        sortedJsonItems3.add(i)
                    }
                    else if(i?.listId == 4) {
                        sortedJsonItems4.add(i)
                    }
                }

                //sort by name
                sortlist(sortedJsonItems1)
                sortlist(sortedJsonItems2)
                sortlist(sortedJsonItems3)
                sortlist(sortedJsonItems4)

                //combine lists
                var count = 0
                for(item in sortedJsonItems1) {
                    sortedJsonItems.add(sortedJsonItems1.get(count))
                    count++
                    println(item?.listId)
                }
                count = 0
                for(item in sortedJsonItems2) {
                    sortedJsonItems.add(sortedJsonItems2.get(count))
                    count++
                }
                count = 0
                for(item in sortedJsonItems3) {
                    sortedJsonItems.add(sortedJsonItems3.get(count))
                    count++
                }
                count = 0
                for(item in sortedJsonItems4) {
                    sortedJsonItems.add(sortedJsonItems4.get(count))
                    count++
                }

                runOnUiThread {
                    findViewById<RecyclerView>(R.id.mainRecyclerView).adapter = MyAdapter(sortedJsonItems)
                }
            }

            override fun onFailure(call: Call, e: IOException) {
                Log.e(TAG, "failed to get request")
            }
        })
    }

    //bubblesort
    fun sortlist(unsorted: ArrayList<JsonItem?>) {
        val n: Int = unsorted.size
        var temp: JsonItem?

        var i = 0
        while(i < n) {
            var j = 1
            while (j < (n - i)) {
                if (unsorted[j - 1]!!.id > unsorted[j]!!.id) {
                    temp = unsorted[j - 1]
                    unsorted[j - 1] = unsorted[j]
                    unsorted[j] = temp
                }
                j++
            }
            i++
        }
    }
}

class JsonItem(val id: Int, val listId: Int, val name: String)
