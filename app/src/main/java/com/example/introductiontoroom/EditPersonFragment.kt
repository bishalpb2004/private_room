package com.example.introductiontoroom

import android.content.Context
import android.location.GnssAntennaInfo.Listener
import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.introductiontoroom.databinding.FragmentEditPersonBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment


class EditPersonFragment : BottomSheetDialogFragment() {

    private lateinit var binding: FragmentEditPersonBinding
    private var listener: EditPersonListener?=null
    private var person:Person?=null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        listener = context as EditPersonListener
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding=FragmentEditPersonBinding.inflate(layoutInflater)
        // Inflate the layout for this fragment
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (arguments!=null){
            if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.TIRAMISU)
                person=arguments?.getParcelable("person",Person::class.java)
            else
                person=arguments?.getParcelable("person")
        }
        if(person!=null)
            setExistingDataOnUi(person!!)
        else
            attachUiListener()
    }

    private fun setExistingDataOnUi(person: Person) {
        binding.personNameEt.setText(person.name)
        binding.personAgeEt.setText(person.age.toString())
        binding.personCityEt.setText(person.city)
        binding.saveBtn.text="Update"

        binding.saveBtn.setOnClickListener {
            // Retrieve updated data from EditText fields
            val name = binding.personNameEt.text.toString()
            val age = binding.personAgeEt.text.toString()
            val city = binding.personCityEt.text.toString()

            if (name.isNotEmpty() && age.isNotEmpty() && city.isNotEmpty()) {
                val updatedPerson = Person(person.pId, name, age.toInt(), city)
                listener?.onSaveBtnClicked(true, updatedPerson)
            }

            dismiss()
        }

    }

    private fun attachUiListener() {
        binding.saveBtn.setOnClickListener {
            val name=binding.personNameEt.text.toString()
            val age=binding.personAgeEt.text.toString()
            val city=binding.personCityEt.text.toString()
            if(name.isNotEmpty() and age.isNotEmpty() and city.isNotEmpty()){
                val person1=Person(person?.pId ?: 0,name,age.toInt(),city)
                listener?.onSaveBtnClicked(person!=null,person1)
            }
            dismiss()
        }
    }

    companion object {
        const val TAG="EditPersonFragment"

        @JvmStatic
        fun newInstance(person: Person?)=EditPersonFragment().apply {
            arguments=Bundle().apply {
                putParcelable("person",person)
            }
        }
    }

    interface EditPersonListener{
        fun onSaveBtnClicked(isUpdate:Boolean, person: Person)
    }
}