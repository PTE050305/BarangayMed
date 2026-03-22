package com.stratex.barangaymed.ui.user;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.stratex.barangaymed.ui.MainActivity;

/**
 * @deprecated Use HomeFragment within MainActivity instead.
 * This activity now simply redirects to MainActivity.
 */
@Deprecated
public class HomeActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        startActivity(new Intent(this, MainActivity.class));
        finish();
    }
}