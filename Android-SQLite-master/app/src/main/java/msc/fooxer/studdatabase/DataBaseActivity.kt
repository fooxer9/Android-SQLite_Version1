package msc.fooxer.studdatabase

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView

class DataBaseActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_data_base)
        val recycler: RecyclerView = findViewById(R.id.list)
        val adapter: RecyclerAdapter = RecyclerAdapter(this, MainActivity.NOTES)
        recycler.adapter = adapter
        recycler.layoutManager = LinearLayoutManager(this)
    }
}
