package com.example.demoapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.demoapp.feature_auth.User

class OnlineAdapter: RecyclerView.Adapter<OnlineAdapter.MyViewHolder>() {

    private var onlineList: List<User> = emptyList()

    class MyViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val firstName = itemView.findViewById<TextView>(R.id.tvItemFirstName)
        val lastName = itemView.findViewById<TextView>(R.id.tvItemLastName)
        val cvItem = itemView.findViewById<CardView>(R.id.cv_user_item)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.user_item, parent,false)
        )
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentItem = onlineList[position]
        holder.firstName.text = currentItem.firstName
        holder.lastName.text = currentItem.lastName

        holder.cvItem.setOnClickListener {
            val action = HomeFragmentDirections.actionHomeFragmentToDetailFragment3(currentItem)
            holder.itemView.findNavController().navigate(action)
        }
    }

    override fun getItemCount(): Int {
        return onlineList.size
    }
    fun setData(onlineList: List<User>) {
        this.onlineList = onlineList
        notifyDataSetChanged()
    }
}