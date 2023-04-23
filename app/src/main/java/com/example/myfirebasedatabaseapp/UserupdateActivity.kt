package com.example.myfirebasedatabaseapp

import android.annotation.SuppressLint
import android.app.ProgressDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.database.FirebaseDatabase

class UserupdateActivity : AppCompatActivity() {
    lateinit var edtName: EditText
    lateinit var edtEmail: EditText
    lateinit var edtIDNumber: EditText
    lateinit var progressDialog: ProgressDialog
    lateinit var btnUpdate:Button
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_userupdate)
        edtName = findViewById(R.id.mEdtName)
        edtEmail = findViewById(R.id.mEdtEmail)
        edtIDNumber = findViewById(R.id.mEdtNumber)
        btnUpdate = findViewById(R.id.mBtnUserUpdate)
        progressDialog = ProgressDialog (this)
        progressDialog.setTitle("Saving")
        progressDialog.setMessage("Please wait...")
        // recieve data sent via the intent
        var receivedName = intent.getStringExtra("name")
        var receivenEmail = intent.getStringExtra("email")
        var receivedIdNumber = intent.getStringExtra("idNumber")
        var receivedId = intent.getStringExtra("id")
        // Display the received data on the EditTexts
        edtName.setText(receivedName)
        edtEmail.setText(receivenEmail)
        edtIDNumber.setText(receivedIdNumber)
        // set onClick listener to button update
        btnUpdate.setOnClickListener {
            var name = edtName.text.toString().trim()
            var email = edtEmail.text.toString().trim()
            var idNumber = edtIDNumber.text.toString().trim()
            var id = receivedId
            // Check if the user is submitting empty fields
            if (name.isEmpty()){
                edtName.setError("Please fill this input")
                edtEmail.requestFocus()
            }else if (idNumber.isEmpty()){
                edtIDNumber.setError("Please fill this input")
                edtIDNumber.requestFocus()
            }else{
                // proceed to save
                var user = User(name,email,idNumber,id!!)
                // Create a reference to the FirebaseDatabase
                var ref = FirebaseDatabase.getInstance().getReference().child("Users/"+id)
                progressDialog.show()
                ref.setValue(user).addOnCompleteListener {
                    progressDialog.dismiss()
                    if (it.isSuccessful){
                        Toast.makeText(this, "User updated successful!", Toast.LENGTH_LONG).show()
                        startActivity(Intent(this@UserupdateActivity,UsersActivity::class.java))
                        finish()
                    }else{
                        Toast.makeText(this, "User updating failed", Toast.LENGTH_LONG).show()
                    }
                }
            }
        }


    }
}