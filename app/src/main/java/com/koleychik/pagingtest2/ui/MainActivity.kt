package com.koleychik.pagingtest2.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.koleychik.pagingtest2.R
import com.koleychik.pagingtest2.model.Model
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.collectLatest

class MainActivity : AppCompatActivity() {

    private lateinit var viewModel: MainViewModel

    private lateinit var rv: RecyclerView
    private lateinit var btn: Button
    private lateinit var edt: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        viewModel = ViewModelProvider(this)[MainViewModel::class.java]

        val adapter = MainAdapter()

        rv = findViewById(R.id.rv)
        btn = findViewById(R.id.btn)
        edt = findViewById(R.id.edt)

        rv.adapter = adapter

//       makeInsertOnFirst()

        lifecycleScope.launch {
            @OptIn(ExperimentalCoroutinesApi::class)
            viewModel.allName.collectLatest { adapter.submitData(it) }
        }

        makeOnClickForBtn()
        initSwipeToDelete()
    }

    private fun initSwipeToDelete() {
        ItemTouchHelper(object : ItemTouchHelper.Callback() {
            // enable the items to swipe to the left or right
            override fun getMovementFlags(recyclerView: RecyclerView,
                                          viewHolder: RecyclerView.ViewHolder): Int =
                makeMovementFlags(0, ItemTouchHelper.LEFT)

            override fun onMove(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder,
                                target: RecyclerView.ViewHolder): Boolean = false

            // When an item is swiped, remove the item via the view model. The list item will be
            // automatically removed in response, because the adapter is observing the live list.
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                (viewHolder as MainViewHolder).model?.let {
                    viewModel.delete(it)
                    Log.d("swipe", "swipe left")
                }
            }
        }).attachToRecyclerView(rv)

        ItemTouchHelper(object : ItemTouchHelper.Callback() {
            // enable the items to swipe to the left or right
            override fun getMovementFlags(recyclerView: RecyclerView,
                                          viewHolder: RecyclerView.ViewHolder): Int =
                makeMovementFlags(0, ItemTouchHelper.RIGHT)

            override fun onMove(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder,
                                target: RecyclerView.ViewHolder): Boolean = false

            // When an item is swiped, remove the item via the view model. The list item will be
            // automatically removed in response, because the adapter is observing the live list.
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                (viewHolder as MainViewHolder).model?.let {
                    viewModel.delete(it)
                    Log.d("swipe", "swipe right")
                }
            }
        }).attachToRecyclerView(rv)
    }

    private fun makeInsertOnFirst() = CoroutineScope(Dispatchers.Main).launch{
        for(i in (0..1000000000)){
            makeInsert(i.toString())
        }
        Log.d("textStyle", "finish")
    }

    private fun makeOnClickForBtn(){
        btn.setOnClickListener {
            makeInsert(edt.text.toString())
        }
    }

    fun makeInsert(text: String) = CoroutineScope(Dispatchers.IO).launch{
        viewModel.insert(Model(text))
    }

}