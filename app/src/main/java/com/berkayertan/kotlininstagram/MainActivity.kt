package com.berkayertan.kotlininstagram

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.Toast
import com.berkayertan.kotlininstagram.databinding.ActivityMainBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var auth : FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(LayoutInflater.from(this))
        val view = binding.root
        setContentView(view)

        auth=Firebase.auth

        val currentuser = auth.currentUser
        if (currentuser != null) {
        val intent = Intent(this,FeedActivity::class.java)
           startActivity(intent)
           finish()    // bu kısımda kullanıcı önceden kayıtlı ise otomatik giriş yapılı şekilde uygulamaya giriyor

        }
    }

    fun SignIn (view : View) {

        val email = binding.emailText.text.toString()
        val password = binding.passwordText.text.toString()
        if (email.equals("")||password.equals("")) {
            Toast.makeText(this,"Type email and password",Toast.LENGTH_LONG).show()
        }
        else{
            auth.signInWithEmailAndPassword(email,password).addOnSuccessListener {
                val intent = Intent (this,FeedActivity::class.java)
                startActivity(intent)
                finish()

            }.addOnFailureListener {
                Toast.makeText(this,it.localizedMessage,Toast.LENGTH_LONG).show()

            }
        }

    }

    fun SignUp (view: View) {

        val email = binding.emailText.text.toString()
        val password = binding.passwordText.text.toString()
        if(email.equals("")||password.equals("")) {
            Toast.makeText(this, "Enter email and password", Toast.LENGTH_LONG).show()
        }
        else {
             auth.createUserWithEmailAndPassword(email,password).addOnSuccessListener {
                 val intent = Intent (this,FeedActivity::class.java)
                 startActivity(intent)
                 finish()    // doğru bilgiler olursa intent ile diğer activitye geçiş yapma kısmı

             }.addOnFailureListener {
                Toast.makeText(this,it.localizedMessage,Toast.LENGTH_LONG).show() // yanlış bilgide toast mesajı
             }


            }





    }
}