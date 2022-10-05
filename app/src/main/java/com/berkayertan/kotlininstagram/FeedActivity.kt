package com.berkayertan.kotlininstagram

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.berkayertan.kotlininstagram.adapter.FeedRecyclerAdapter
import com.berkayertan.kotlininstagram.databinding.ActivityFeedBinding
import com.berkayertan.kotlininstagram.model.Posts
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class FeedActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var binding: ActivityFeedBinding
    private lateinit var db: FirebaseFirestore
    private lateinit var postArrayList: ArrayList<Posts>
    private lateinit var feedAdapter : FeedRecyclerAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFeedBinding.inflate(LayoutInflater.from(this))
        val view = binding.root
        setContentView(view)

        auth = Firebase.auth
        db = Firebase.firestore
    postArrayList= ArrayList<Posts>()
    getData()
    binding.recyclerView.layoutManager = LinearLayoutManager(this)
        feedAdapter= FeedRecyclerAdapter(postArrayList)
        binding.recyclerView.adapter = feedAdapter

    }

    private fun getData() {
        db.collection("posts").orderBy("date",Query.Direction.DESCENDING).addSnapshotListener { value, error ->

            if (error != null) {
                Toast.makeText(this, error.localizedMessage, Toast.LENGTH_LONG).show()
            } else {
                if (value != null) {
                    if (!value.isEmpty) {

                        val documents = value.documents
                        postArrayList.clear()

                        for (document in documents) {
                            val comment = document.get("comment") as String
                            val userEmail = document.get("userEmail") as String
                            val downloadUrl = document.get("downloadUrl") as String

                            println(comment)

                            val posts = Posts(userEmail,comment,downloadUrl)
                            postArrayList.add(posts)
                        }
                        feedAdapter.notifyDataSetChanged()
                    }
                }

            }
        }
    }


    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
     val menuInflater = menuInflater
        menuInflater.inflate(R.menu.insta_menu,menu)
        //menü getirdiğimiz yer
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
     if(item.itemId==R.id.add_post){
       val intent = Intent(this,UploadActivity::class.java)
         startActivity(intent) }
     else if(item.itemId==R.id.sign_out) {
             auth.signOut()
         val intent = Intent(this,MainActivity::class.java)
         startActivity(intent)
         finish()
     }
        return super.onOptionsItemSelected(item)
    }

}


