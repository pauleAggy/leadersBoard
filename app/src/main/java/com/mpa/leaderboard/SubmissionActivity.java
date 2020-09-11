/*
 *@Author MANDENGUE PAULE AGGY
 * this class will handle activity that displays list of leaders
 */
package com.mpa.leaderboard;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


import com.google.android.material.tabs.TabLayout;
import com.mpa.leaderboard.models.SubmissionForm;
import com.mpa.leaderboard.models.SubmissionFormReport;
import com.mpa.leaderboard.services.APIClient;
import com.mpa.leaderboard.services.APIInterface;

public class SubmissionActivity extends AppCompatActivity {
    TabLayout tabLayout;
    Button submissionButton;
    EditText email;
    EditText name;
    EditText lastName;
    EditText githubLink;
    APIInterface apiInterface;
    String emailContent;
    String nameContent;
    String lastNameContent;
    String githubLinkContent;
    static boolean submitForm=false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.submission_form);
        ImageView goBackToLeadersActivityButton = findViewById(R.id.go_back);
        goBackToLeadersActivityButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent loadLeadersActivityIntent = new Intent(getApplicationContext(), LeadersActivity.class);
                startActivity(loadLeadersActivityIntent);
                finish();
            }
        });

        apiInterface = APIClient.getClient().create(APIInterface.class);

        email =findViewById(R.id.editText_email);
        name = findViewById(R.id.editText_first_name);
        lastName = findViewById(R.id.editText_last_name);
        githubLink = findViewById(R.id.editText_github_link);
        submissionButton = findViewById(R.id.button_to_submit_form);
        submissionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkValidation()) {
                    if (CommonMethod.isNetworkAvailable(SubmissionActivity.this)) {
                        showConfirmAlert("Are you sure ?", SubmissionActivity.this);
                        if(submitForm){
                            submitRetrofit2Api(email.getText().toString(), name.getText().toString(), lastName.getText().toString(),  githubLink.getText().toString());
                        }

                    }else
                        CommonMethod.showAlert("Internet Connectivity Failure", SubmissionActivity.this);
                }
            }
        });


    }

    @Override
    protected void onPause() {
        super.onPause();
        Intent loadLeadersActivityIntent = new Intent(getApplicationContext(), LeadersActivity.class);
        startActivity(loadLeadersActivityIntent);
        finish();
    }
    /**
     * function to retrieve response after Api call
     *
     */

    private  void submitRetrofit2Api(String email, String name, String lastName, String githubLink) {
        final SubmissionForm submissionForm = new SubmissionForm(email, name,  lastName, githubLink);
        apiInterface.createSubmissionForm(
            submissionForm.getEmail(),
            submissionForm.getName(),
            submissionForm.getLastName(),
            submissionForm.getGithubLink(), new Callback<SubmissionFormReport>() {
            @Override
            public void onResponse(Call<SubmissionFormReport> call, Response<SubmissionFormReport> response) {
                SubmissionFormReport submitResponse = response.body();
                for(SubmissionForm submissionForm : submitResponse.getGetDetailOfFormsSubmitted()){
                    Log.e("keshav", "loginResponse 1 --> " + submissionForm);
                    if (submissionForm != null) {
                        Log.e("keshav", "get email         -->  " + submissionForm.getEmail());
                        Log.e("keshav", "get name       -->  " + submissionForm.getName());
                        Log.e("keshav", "get last name        -->  " + submissionForm.getLastName());
                        Log.e("keshav", "get github link -->  " + submissionForm.getGithubLink());
                        //alert succes


                    }
            }
            }

            @Override
            public void onFailure(Call<SubmissionFormReport> call, Throwable t) {
                //alert echec

                call.cancel();
            }
        });
    }

    /**
     * function to validate all fields before submission
     * @return boolean
     */
    public boolean checkValidation() {
        emailContent = email.getText().toString();
        nameContent = name.getText().toString();
        lastNameContent = lastName.getText().toString();
        githubLinkContent = githubLink.getText().toString();

        Log.e("emailContent", "email is -> " + emailContent);
        Log.e("nameContent", "name is -> " + nameContent);
        Log.e("lastNameContent", "lastName is -> " + lastNameContent);
        Log.e("githubLinkContent", "lastName is -> " + githubLinkContent);

        if (emailContent.trim().equals("") || emailContent.trim().equals("Email Address") ) {
            CommonMethod.showAlert("You must enter an Email the field can not be left blank", SubmissionActivity.this);
            return false;
        }
        if (nameContent.trim().equals("")  || emailContent.trim().equals("First Name")) {
            CommonMethod.showAlert("You must enter a first name the field can not be left blank", SubmissionActivity.this);
            return false;
        }
        if (lastNameContent.trim().equals("")   || emailContent.trim().equals("Last Name")) {
            CommonMethod.showAlert("You must enter a last name the field can not be left blank", SubmissionActivity.this);
            return false;
        }
        if (githubLinkContent.trim().equals("")  || emailContent.trim().equals("Project on GITHUB (link)")) {
            CommonMethod.showAlert("You must enter a github link the field can not be left blank", SubmissionActivity.this);
            return false;
        }
        return true;
    }


    public static void showConfirmAlert(String message, Activity context){

        final AlertDialog.Builder builder = new AlertDialog.Builder(context);
        final Activity mContext = context;

        builder.setMessage(message).setCancelable(true)
                .setPositiveButton("YES", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        submitForm=true;
                    }
                }).setNegativeButton("NO", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                Toast.makeText(mContext, "Submission cancelled!", Toast.LENGTH_SHORT).show();
            }
        });
        try {
            builder.show();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}