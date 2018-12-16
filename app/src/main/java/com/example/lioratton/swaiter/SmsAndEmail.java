package com.example.lioratton.swaiter;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

public class SmsAndEmail extends AppCompatActivity {
    EditText massage, phone, emailA;
    String smsNum, smsText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sms_and_email);
        massage = (EditText) findViewById(R.id.massage);
        phone = (EditText) findViewById(R.id.phone);
        emailA = (EditText) findViewById(R.id.emailA);

    }

    public void sms(View view) {
        smsNum = phone.getText().toString();
        smsText = massage.getText().toString();
        SmsManager smsManager = SmsManager.getDefault();
        smsManager.sendTextMessage(smsNum, null, smsText, null, null);

    }

    public void em(View view) {
        String fromEmail = "smrtwaiter@gmail.com";
        String fromPassword = "la12062001";
        String toEmails = emailA.getText().toString();
        //String adminEmail = "admin@gmail.com";
        String emailSubject = massage.getText().toString();
        //String adminSubject = "App Registration Mail";
        String emailBody = massage.getText().toString();
        //String adminBody = "Your message";
        new SendMailTask(SmsAndEmail.this).execute(fromEmail,
                fromPassword, toEmails, emailSubject, emailBody);
    }
}

