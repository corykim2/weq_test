package com.example.test;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.ArrayAdapter;
import android.widget.Toast;

public class SecondActivity extends AppCompatActivity {
    //카메라 관련
    private ActivityResultLauncher<Intent> cameraLauncher;
    private ImageView imageView;
    //드롭다운 메뉴 관련
    private Spinner spinnerStyle, spinnerType, spinnerCategory;
    private Button buttonSave;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        //카메라 관련 ********************************************************************************
        imageView = findViewById(R.id.imageView); // 레이아웃에 이미지뷰 있어야 함
        // 1. ActivityResultLauncher 등록 (카메라 결과 받기)
        cameraLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {
                        if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                            // 카메라에서 찍은 사진 비트맵 가져오기
                            Bitmap photo = (Bitmap) result.getData().getExtras().get("data");
                            imageView.setImageBitmap(photo);  // ImageView에 사진 보여주기
                        }
                    }
                }
        );
        //드롭다운 메뉴 관련 **************************************************************************

        // 스피너 참조erer
        spinnerStyle = findViewById(R.id.spinner_style);
        spinnerType = findViewById(R.id.spinner_type);
        buttonSave = findViewById(R.id.button_save);

        // 항목 리스트 생성
        String[] styleOptions = {"캐주얼", "스포티", "포멀"};
        String[] typeOptions = {"상의", "하의", "아우터"};

        // 어댑터 설정
        setUpSpinner(spinnerStyle, styleOptions);
        setUpSpinner(spinnerType, typeOptions);

        // 저장 버튼 클릭 이벤트
        buttonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String selectedStyle = spinnerStyle.getSelectedItem().toString();
                String selectedType = spinnerType.getSelectedItem().toString();

                String result = "스타일: " + selectedStyle +
                        "\n유형: " + selectedType;

                Toast.makeText(SecondActivity.this, result, Toast.LENGTH_LONG).show();
            }
        });

        //카메라실행 *********************************************************************************
        openCamera();
    }
    // 카메라 실행하는 메서드
    private void openCamera() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        cameraLauncher.launch(intent);
    }
    //드롭다운 관련 메소드
    private void setUpSpinner(Spinner spinner, String[] items) {
        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_spinner_dropdown_item,
                items
        );
        spinner.setAdapter(adapter);
    }
}