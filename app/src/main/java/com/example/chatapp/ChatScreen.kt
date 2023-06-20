package com.example.chatapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*

class ChatScreen : AppCompatActivity()
{
    private lateinit var chatRecyclerView: RecyclerView
    private lateinit var messagebox:EditText
    private lateinit var sendButton: ImageView
    private lateinit var messageAdapter: MessageAdapter
    private lateinit var messageList: ArrayList<Message>
    private lateinit var mDbRef: DatabaseReference

    var recieverRoom:String?=null
    var senderRoom:String?=null

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat_screen)

        val name=intent.getStringExtra("name")
        val recieveruid=intent.getStringExtra("uid")
        val senderuid=FirebaseAuth.getInstance().currentUser?.uid
        mDbRef = FirebaseDatabase.getInstance().getReference()

        senderRoom = recieveruid + senderuid
        recieverRoom = senderuid + recieveruid

        supportActionBar?.title = name

        chatRecyclerView=findViewById(R.id.ChatRecyclerView)
        messagebox=findViewById(R.id.messagebox)
        sendButton=findViewById(R.id.sendButton)
        messageList= ArrayList()
        messageAdapter= MessageAdapter(this,messageList)

        chatRecyclerView.layoutManager=LinearLayoutManager(this)
        chatRecyclerView.adapter=messageAdapter

        mDbRef.child("chats").child(senderRoom!!).child("messages")
            .addValueEventListener(object: ValueEventListener
            {
                override fun onDataChange(snapshot: DataSnapshot)
                {

                    messageList.clear()

                    for (postSnapshot in snapshot.children)
                    {
                        val message = postSnapshot.getValue(Message::class.java)
                        messageList.add(message!!)
                    }
                    messageAdapter.notifyDataSetChanged()
                }

                override fun onCancelled(error: DatabaseError)
                {

                }
            } )

        sendButton.setOnClickListener {
            val message = messagebox.text.toString()
            val messageObject = Message(message,senderuid)
            mDbRef.child("chats").child(senderRoom!!).child("messages").push().setValue(messageObject).addOnSuccessListener {
                mDbRef.child("chats").child(recieverRoom!!).child("messages").push().setValue(messageObject)
                }
            messagebox.setText("")
        }

    }
}