package com.example.introductiontoroom

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.introductiontoroom.databinding.SingleItemBinding

class PersonDetailsAdapter(private val listener:PersonDetailsClickListener): ListAdapter<Person,PersonDetailsAdapter.PersonsDetailsViewHolder >(DiffUtilCallBack()) {

    inner class PersonsDetailsViewHolder(private val binding: SingleItemBinding)
        :RecyclerView.ViewHolder(binding.root){

            init {
                binding.editBtn.setOnClickListener {
                    listener.onEditPersonClick(getItem(adapterPosition))
                }
                binding.deleteBtn.setOnClickListener {
                    listener.onDeletePersonClick(getItem(adapterPosition))
                }
            }
            fun bind(person: Person){
                binding.personNameTv.text=person.name
                binding.personAgeTv.text=person.age.toString()
                binding.personCityTv.text=person.city
            }
        }

    class DiffUtilCallBack:DiffUtil.ItemCallback<Person>(){
        override fun areItemsTheSame(oldItem: Person, newItem: Person): Boolean {
            return oldItem.pId==newItem.pId
        }

        override fun areContentsTheSame(oldItem: Person, newItem: Person): Boolean {
            return oldItem==newItem
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PersonsDetailsViewHolder {
        return PersonsDetailsViewHolder(SingleItemBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun onBindViewHolder(holder: PersonsDetailsViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    interface PersonDetailsClickListener{
        fun onEditPersonClick(person: Person)
        fun onDeletePersonClick(person: Person)
    }
}