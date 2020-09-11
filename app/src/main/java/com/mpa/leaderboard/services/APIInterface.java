package com.mpa.leaderboard.services;


import com.mpa.leaderboard.models.SubmissionFormReport;


import retrofit2.Callback;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public  interface APIInterface {
    @FormUrlEncoded
    @POST("1FAIpQLSf9d1TcNU6zc6KR8bSEM41Z1g1zl35cwZr2xyjIhaMAz8WChQ/formResponse")

     void createSubmissionForm(
            @Field("entry.1824927963") String email,
            @Field("entry.1877115667") String name,
            @Field("entry.2006916086") String lastName,
            @Field("entry.284483984") String githubLink,
            Callback<SubmissionFormReport> cb);
}
