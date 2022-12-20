package com.example.contacts

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
    import com.example.contacts.databinding.ListItemBinding

class ContactAdapter(
    private val contacts: List<Contact>,
    private val context: Context
) : RecyclerView.Adapter<ContactAdapter.ContactViewHolder>() {

    class ContactViewHolder(var viewBinding: ListItemBinding) : RecyclerView.ViewHolder(viewBinding.root) {
        fun bind(contact: Contact) {
            viewBinding.name.text = contact.name
            viewBinding.number.text = contact.number
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContactViewHolder {
        val holder = ContactViewHolder(
            ListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
        holder.viewBinding.name.setOnClickListener {
            context.startActivity(
                Intent(
                    Intent.ACTION_DIAL,
                    Uri.parse("tel:" + contacts[holder.absoluteAdapterPosition].number)
                )
            )
        }
        holder.viewBinding.message.setOnClickListener {
            context.startActivity(
                Intent(
                    Intent.ACTION_VIEW,
                    Uri.parse("smsto:" + contacts[holder.absoluteAdapterPosition].number)
                ).putExtra("sms_body", "Привет")
            )
        }
        return holder
    }

    override fun onBindViewHolder(holder: ContactViewHolder, position: Int) =
        holder.bind(contacts[position])

    override fun getItemCount() = contacts.size
}