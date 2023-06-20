package com.example.chatapp

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.google.firebase.auth.FirebaseAuth

class MessageAdapter(val context: Context, val messageList: ArrayList<Message>): RecyclerView.Adapter<RecyclerView.ViewHolder>()
{
    val msgrecieved=1
    val msgsent=2
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder
    {
        if(viewType==1)
        {
            val view: View = LayoutInflater.from(context).inflate(R.layout.recieved, parent , false)
            return RecievedViewHolder(view)
        }
        else
        {
            val view: View = LayoutInflater.from(context).inflate(R.layout.sent, parent , false)
            return SentViewHolder(view)
        }
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int)
    {
        val currentMessage=messageList[position]
        if (holder.javaClass==SentViewHolder::class.java)
        {
           val viewHolder=holder as SentViewHolder
           holder.sentMessage.text=currentMessage.message
        }
        else
        {
            val viewHolder=holder as RecievedViewHolder
            holder.recievedMessage.text=currentMessage.message
        }
    }

    override fun getItemViewType(position: Int): Int
    {
      val currentMessage=messageList[position]
       if(FirebaseAuth.getInstance().currentUser?.uid.equals(currentMessage.senderId))
        {
          return msgsent
        }
       else
        {
          return msgrecieved
        }
    }

    override fun getItemCount(): Int
    {
        return messageList.size
    }
    class SentViewHolder(itemView:View):RecyclerView.ViewHolder(itemView)
    {
        val sentMessage= itemView.findViewById<TextView>(R.id.txt_sent_message)
    }
    class RecievedViewHolder(itemView:View):RecyclerView.ViewHolder(itemView)
    {
        val recievedMessage= itemView.findViewById<TextView>(R.id.txt_recieved_message)
    }

}