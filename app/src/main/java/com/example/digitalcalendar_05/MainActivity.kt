package com.example.digitalcalendar_05

import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat

class MainActivity : AppCompatActivity() {

    // Mark -Properties/////////////////////////////////////////
    private val addPhotoBtn:Button by lazy { findViewById(R.id.addPhoto_Btn) }
    private val startPhotoFrameModeBtn: Button by lazy { findViewById(R.id.startPhotoFrameMode_Btn) }


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
    private fun initAddPhotoBtn(){
        addPhotoBtn.setOnClickListener {
            when {
                ContextCompat.checkSelfPermission( // checkSelfPermission 을 CMD 클릭 하면 볼 수 있음.
                    this, android.Manifest.permission.READ_EXTERNAL_STORAGE
                ) == PackageManager.PERMISSION_GRANTED -> {
                    // TODO: 권한이 잘 부여되었을 때 갤러리에서 사진을 선택하는 기능
                }
                shouldShowRequestPermissionRationale(android.Manifest.permission.READ_EXTERNAL_STORAGE) -> {
                    // TODO: 교육용 팝업 확인 후 권한 팝업 띄우는 기능
                    showPermissionContextPopup()
                }
                else -> {
                    requestPermissions(arrayOf(android.Manifest.permission.READ_EXTERNAL_STORAGE), 1000)
                }
            }
        }
    }
    private fun initStartPhotoFrameModeBtn(){

    }
    private fun showPermissionContextPopup(){
        AlertDialog.Builder(this)
            .setTitle("권한이 필요합니다")
            .setMessage("전자 액자 앱에서 사진을 불러오기 위해 권한이 필요합니다.")
            .setPositiveButton("동의하기", {dialog, which ->
                requestPermissions(arrayOf(android.Manifest.permission.READ_EXTERNAL_STORAGE), 1000)
            })
            .setNegativeButton("취소하기", { _, _ ->

            })
            .create().show()

    }

}
