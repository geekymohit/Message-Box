package com.example.mohit.messagebox;

import android.widget.ArrayAdapter;

/**
 * Created by Mohit on 24-11-2017.
 */

public class UserEmail extends ArrayAdapter {

    String email ;

    public UserEmail(String email) {
        super(null, Integer.parseInt(null));
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
