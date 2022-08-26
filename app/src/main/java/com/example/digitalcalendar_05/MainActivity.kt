package com.example.digitalcalendar_05

import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat

class MainActivity : AppCompatActivity() {

    // Mark -Properties/////////////////////////////////////////
    private val addPhotoBtn: Button by lazy { findViewById(R.id.addPhoto_Btn) }
    private val startPhotoFrameModeBtn: Button by lazy { findViewById(R.id.startPhotoFrameMode_Btn) }
    private val imageViewList: List<ImageView> by lazy {
        mutableListOf<ImageView>().apply {
            add(findViewById(R.id.pic_iv_11))
            add(findViewById(R.id.pic_iv_12))
            add(findViewById(R.id.pic_iv_13))
            add(findViewById(R.id.pic_iv_21))
            add(findViewById(R.id.pic_iv_22))
            add(findViewById(R.id.pic_iv_23))
            add(findViewById(R.id.pic_iv_31))
            add(findViewById(R.id.pic_iv_32))
            add(findViewById(R.id.pic_iv_33))
        }
    }
    private val imageUriList: MutableList<Uri> = mutableListOf()


    // Mark -LifeCycle/////////////////////////////////////////
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        initAddPhotoBtn()
        initStartPhotoFrameModeBtn()
    }


    // Mark -HELP/////////////////////////////////////////
    // 메소드로 만들어주는 이유는 모든 코드가 메소드로 만들어짖 ㅣ않으면 찾기 힘들다.
    // 메소드로 같은 기능을 하는 것들을 모아서 이름을 주면 보기 쉽다. 한눈에 알 수 있다. (코드 추상화)
    private fun initAddPhotoBtn() {
        addPhotoBtn.setOnClickListener {
            when {
                ContextCompat.checkSelfPermission( // checkSelfPermission 을 CMD 클릭 하면 볼 수 있음.
                    this, android.Manifest.permission.READ_EXTERNAL_STORAGE
                ) == PackageManager.PERMISSION_GRANTED -> {
                    // TODO: 권한이 잘 부여되었을 때 갤러리에서 사진을 선택하는 기능
                    navigatePhotos()
                }
                shouldShowRequestPermissionRationale(android.Manifest.permission.READ_EXTERNAL_STORAGE) -> {
                    // TODO: 교육용 팝업 확인 후 권한 팝업 띄우는 기능
                    showPermissionContextPopup()
                }
                else -> {
                    requestPermissions(
                        arrayOf(android.Manifest.permission.READ_EXTERNAL_STORAGE),
                        1000
                    )
                }
            }
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        when (requestCode) {
            1000 -> {
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    navigatePhotos()

                } else {
                    Toast.makeText(this, "권한을 거부하였습니다.", Toast.LENGTH_SHORT).show()
                }
            }
            else -> { // NOTHING. }
            }
        }
    }

    private fun initStartPhotoFrameModeBtn() {

    }

    private fun showPermissionContextPopup() {
        AlertDialog.Builder(this)
            .setTitle("권한이 필요합니다")
            .setMessage("전자 액자 앱에서 사진을 불러오기 위해 권한이 필요합니다.")
            .setPositiveButton("동의하기", { dialog, which ->
                requestPermissions(
                    arrayOf(android.Manifest.permission.READ_EXTERNAL_STORAGE),
                    1000
                )
            })
            .setNegativeButton("취소하기", { _, _ ->

            })
            .create().show()

    }

    // 컨텐츠프로바이더에서 스토리지엑세스프레임워크 Storage Access Framework(SAF)
    // 코드가 간결하고 사용자 친화적임.
    // 다른 앱에서도 SAF을 사용하여 화면을 띄울 대 화면이 똑같이 떠서 유저에서 익숙할 것.
    // SAF 기능 이용한 것 말고도 직접 갤러리를 구현할 수는 있지만 이는 사용자에게 익숙하지 않을 것....
    private fun navigatePhotos() {
        val intent = Intent(Intent.ACTION_GET_CONTENT)
        intent.type = "image/*"
        startActivityForResult(intent, 2000)
        // startActivityForResult 로 실행을 하면 콜백으로 onActivityResult 가 떨어진다.
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode != Activity.RESULT_OK) {
            return
        }

        when(requestCode) {
            2000 -> {
                // onActivityResult 함수의 인수 data 는 NULLABLE 임.
                // 혹시라도 이전 액티비티에서 데이터를 null로 내려주었을 때 문제가 생기니까 nullable
                val selectedImageUri: Uri? = data?.data
                if (selectedImageUri != null) {
                    if(imageUriList.size >= 9) {
                        Toast.makeText(this, "이미 사진이 꽉 찼습니다.", Toast.LENGTH_SHORT).show()
                    }
                    imageUriList.add(selectedImageUri)
                    imageViewList[imageUriList.size -1].setImageURI(selectedImageUri)
                }else{
                    Toast.makeText(this, "사진을 가져오지 못했습니다.", Toast.LENGTH_SHORT).show()
                }
            }
            else -> {
                Toast.makeText(this, "사진을 가져오지 못했습니다.", Toast.LENGTH_SHORT).show()
            }
        }
    }

}

