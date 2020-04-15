package com.example.aurosampleapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.auro.scholr.core.application.AuroApp;
import com.auro.scholr.core.common.AppConstant;
import com.auro.scholr.home.data.model.AuroScholarDataModel;
import com.auro.scholr.util.AuroScholar;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        AuroScholarDataModel auroScholarDataModel=new AuroScholarDataModel();
        auroScholarDataModel.setMobileNumber("7503600686");
        auroScholarDataModel.setStudentClass("6");
        auroScholarDataModel.setScholarID("577159");
        auroScholarDataModel.setRegistrationSource("auro-google");
        //6&scholr_id=577159&regitration_source=auro-google")
        auroScholarDataModel.setScreenType(AppConstant.ScreenType.QUIZ_DASHBOARD);
        AuroScholar.openAuroScholarActivity(auroScholarDataModel,this);
    }
}
