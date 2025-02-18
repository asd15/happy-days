package com.example.happydates.firebase

import android.app.Activity
import android.util.Log
import android.widget.Toast
import com.example.happydates.activites.MainActivity
import com.example.happydates.activites.MyProfileActivity
import com.example.happydates.activites.SigninActivity
import com.example.happydates.activites.SignupActivity
import com.example.happydates.models.User
import com.example.happydates.utils.Constants
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions

class FirestoreClass {
    private val mFirestore = FirebaseFirestore.getInstance()

    fun registerUser(activity: SignupActivity, userInfo: User){
        mFirestore.collection(Constants.USERS)
            .document(getCurrentUserId())
            .set(userInfo, SetOptions.merge())
            .addOnSuccessListener {
                activity.userRegisteredSuccess()
            }.addOnFailureListener {
                e -> Log.e(activity.javaClass.simpleName, "Error")
            }
    }

    fun updateUserProfileData(activity: MyProfileActivity, userHashMap: HashMap<String, Any>){
        mFirestore.collection(Constants.USERS)
            .document(getCurrentUserId())
            .update(userHashMap)
            .addOnSuccessListener {
                Log.i(activity.javaClass.simpleName, "profile data updated")
                Toast.makeText(activity, "Profile updated success", Toast.LENGTH_SHORT).show()
                activity.profileUpdateSuccess()
            }.addOnFailureListener {
                e -> Log.e(
                activity.javaClass.simpleName,
                "Error", e
            )
                Toast.makeText(activity, "Error when updating", Toast.LENGTH_SHORT).show()
            }
    }

    fun loadUserData(activity: Activity){
        mFirestore.collection(Constants.USERS)
            .document(getCurrentUserId())
            .get()
            .addOnSuccessListener {document ->
                val loggedInUser = document.toObject(User::class.java)
                if (loggedInUser != null)
                    when(activity){
                        is SigninActivity ->{
                            activity.signInSuccess(loggedInUser)
                        }
                        is MainActivity ->{
                            activity.updateNavigationUserDetails(loggedInUser)
                        }
                        is MyProfileActivity ->{
                            activity.setUserDataUI(loggedInUser)
                        }
                    }
            }.addOnFailureListener {
                    e -> Log.e("signin", "Error")
            }
    }

    fun getCurrentUserId(): String{
        var currentUser = FirebaseAuth.getInstance().currentUser
        var currentUserId = ""
        if (currentUser != null){
            currentUserId = currentUser.uid
        }
        return currentUserId
    }
}