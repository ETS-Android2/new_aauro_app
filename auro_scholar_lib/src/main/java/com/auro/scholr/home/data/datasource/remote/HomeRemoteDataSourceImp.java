package com.auro.scholr.home.data.datasource.remote;

import com.auro.scholr.core.common.AppConstant;
import com.auro.scholr.home.data.model.AssignmentReqModel;
import com.auro.scholr.home.data.model.AuroScholarDataModel;
import com.auro.scholr.home.data.model.DemographicResModel;
import com.auro.scholr.home.data.model.KYCDocumentDatamodel;
import com.auro.scholr.home.data.repository.HomeRepo;
import com.auro.scholr.util.ConversionUtil;
import com.google.gson.JsonObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.reactivex.Single;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Response;

public class HomeRemoteDataSourceImp implements HomeRepo.DashboardRemoteData {

    HomeRemoteApi homeRemoteApi;

    public HomeRemoteDataSourceImp(HomeRemoteApi homeRemoteApi) {
        this.homeRemoteApi = homeRemoteApi;
    }


    @Override
    public Single<Response<JsonObject>> getDashboardData(AuroScholarDataModel model) {
        Map<String, String> params = new HashMap<String, String>();
        params.put(AppConstant.MOBILE_NUMBER, model.getMobileNumber());
        params.put(AppConstant.DashBoardParams.SCHOLAR_ID, model.getScholrId());
        params.put(AppConstant.DashBoardParams.STUDENT_CLASS, model.getStudentClass());
        params.put(AppConstant.DashBoardParams.REGISTRATION_SOURCE, model.getRegitrationSource());
        params.put(AppConstant.DashBoardParams.SHARE_TYPE, model.getShareType());
        params.put(AppConstant.DashBoardParams.SHARE_IDENTITY, model.getShareIdentity());
        return homeRemoteApi.getDashboardData(params);
    }

    @Override
    public Single<Response<JsonObject>> getAzureData(AssignmentReqModel azureReqModel) {
        RequestBody registration_id = RequestBody.create(okhttp3.MultipartBody.FORM, azureReqModel.getRegistration_id());
        RequestBody exam_id = RequestBody.create(okhttp3.MultipartBody.FORM, azureReqModel.getEklavvya_exam_id());
        RequestBody exam_name = RequestBody.create(okhttp3.MultipartBody.FORM, azureReqModel.getExam_name());
        RequestBody quiz_attempt = RequestBody.create(okhttp3.MultipartBody.FORM, azureReqModel.getQuiz_attempt());
        RequestBody requestFile = RequestBody.create(MediaType.parse("image/jpeg"), azureReqModel.getImageBytes());
        MultipartBody.Part student_photo = MultipartBody.Part.createFormData("exam_face_img", "image.jpg", requestFile);
        return homeRemoteApi.getAzureApiData(registration_id, exam_id,exam_name,quiz_attempt,student_photo);
    }

    @Override
    public Single<Response<JsonObject>> uploadProfileImage(List<KYCDocumentDatamodel> list,String phone) {
        RequestBody phonenumber = RequestBody.create(okhttp3.MultipartBody.FORM, phone);
        MultipartBody.Part id_proof_front = ConversionUtil.INSTANCE.makeMultipartRequest(list.get(0));
        MultipartBody.Part id_proof_back = ConversionUtil.INSTANCE.makeMultipartRequest(list.get(1));
        MultipartBody.Part school_id_card = ConversionUtil.INSTANCE.makeMultipartRequest(list.get(2));
        MultipartBody.Part student_photo = ConversionUtil.INSTANCE.makeMultipartRequest(list.get(3));

        return homeRemoteApi.uploadImage(phonenumber, id_proof_front, id_proof_back, school_id_card, student_photo);
    }

    @Override
    public Single<Response<JsonObject>> postDemographicData(DemographicResModel demographicResModel) {
        Map<String, String> params = new HashMap<String, String>();
        params.put(AppConstant.MOBILE_NUMBER, demographicResModel.getPhonenumber());
        params.put(AppConstant.DemographicType.GENDER, demographicResModel.getGender());
        params.put(AppConstant.DemographicType.BOARD_TYPE, demographicResModel.getBoard_type());
        params.put(AppConstant.DemographicType.SCHOOL_TYPE, demographicResModel.getSchool_type());
        params.put(AppConstant.DemographicType.LANGUAGE, demographicResModel.getLanguage());
        return homeRemoteApi.postDemographicData(params);
    }

    @Override
    public Single<Response<JsonObject>> getAssignmentId(AssignmentReqModel assignmentReqModel) {
        Map<String, String> params = new HashMap<String, String>();
        params.put(AppConstant.AssignmentApiParams.REGISTRATION_ID, assignmentReqModel.getRegistration_id());
        params.put(AppConstant.AssignmentApiParams.EXAM_NAME, assignmentReqModel.getExam_name());
        params.put(AppConstant.AssignmentApiParams.QUIZ_ATTEMPT, assignmentReqModel.getQuiz_attempt());
        params.put(AppConstant.AssignmentApiParams.EXAMLANG, assignmentReqModel.getExamlang());
        return homeRemoteApi.getAssignmentId(params);
    }
}
