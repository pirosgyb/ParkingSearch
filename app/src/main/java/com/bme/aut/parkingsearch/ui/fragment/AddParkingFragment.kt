package com.bme.aut.parkingsearch.ui.fragment

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.ViewModelProviders
import com.bme.aut.parkingsearch.enum.ToolbarType
import com.bme.aut.parkingsearch.model.ParkingSpot
import com.bme.aut.parkingsearch.model.ToolbarModel
import com.bme.aut.parkingsearch.repository.Repository
import com.bme.aut.parkingsearch.viewModel.AddParkingViewModel
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import kotlinx.android.synthetic.main.fragment_add_parking.*
import java.io.ByteArrayOutputStream
import java.net.URLEncoder
import java.util.*
//import sun.jvm.hotspot.utilities.IntArray
import android.R
import com.bme.aut.parkingsearch.ui.activity.MainActivity


class AddParkingFragment : BaseFragment() {

    companion object {
        const val ARG_KEY = "ADD_PARKING_ARG_KEY"
        private const val REQUEST_CODE = 101
    }

    private lateinit var viewModel: AddParkingViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(com.bme.aut.parkingsearch.R.layout.fragment_add_parking, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        init()
    }

    private fun init() {
        viewModel = ViewModelProviders.of(this).get(AddParkingViewModel::class.java)
        toolbarView?.bind(
            ToolbarModel(
                type = ToolbarType.NAVIGATE_BACK,
                title = "Add new parking spot"
            )
        )
        viewModel.address = arguments?.getParcelable(ARG_KEY)
        addressEditText?.setText(viewModel.address?.getAddressLine(0), TextView.BufferType.EDITABLE)

        cameraButton.setOnClickListener { attachClick() }
        btnSend.setOnClickListener { sendClick() }
    }

    fun sendClick() {
        if (imgAttach.visibility != View.VISIBLE) {
            uploadPlace()
        } else {
            try {
                uploadPlaceWithImg()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun uploadPlace(imageUrl: String? = null, address: String? = null) {
        val key = FirebaseDatabase.getInstance().reference.child("places").push().key ?: return
        val newPost = ParkingSpot(addressEditText.text.toString(), imageUrl)

        FirebaseDatabase.getInstance().reference
            .child("places")
            .child(key)
            .setValue(newPost)
            .addOnCompleteListener {
                (activity as MainActivity).hideProgressDialog()
                Toast.makeText(activity, "Parking Spot added", Toast.LENGTH_SHORT).show()
            }
    }

    fun uploadPlaceWithImg() {
        (activity as MainActivity).showProgressDialog()

        val bitmap: Bitmap = (imgAttach.drawable as BitmapDrawable).bitmap
        val baos = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos)
        val imageInBytes = baos.toByteArray()

        val storageReference = FirebaseStorage.getInstance().reference
        val newImageName = URLEncoder.encode(UUID.randomUUID().toString(), "UTF-8") + ".jpg"
        val newImageRef = storageReference.child("images/$newImageName")

        newImageRef.putBytes(imageInBytes)
            .addOnFailureListener { exception ->
                Toast.makeText(activity, exception.message, Toast.LENGTH_SHORT).show()
            }
            .continueWithTask { task ->
                if (!task.isSuccessful) {
                    task.exception?.let { throw it }
                }

                newImageRef.downloadUrl
            }
            .addOnSuccessListener { downloadUri ->
                uploadPlace(downloadUri.toString())
            }
    }

//    fun getImageView(): ImageView {
//        return imgAttach
//    }
//
    private fun attachClick() {
        val takePictureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        startActivityForResult(takePictureIntent, REQUEST_CODE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode != Activity.RESULT_OK) {
            return
        }

        if (requestCode == REQUEST_CODE) {
            val imageBitmap = data?.extras?.get("data") as? Bitmap ?: return
            imgAttach.setImageBitmap(imageBitmap)
            imgAttach.visibility = View.VISIBLE
        }
    }

}