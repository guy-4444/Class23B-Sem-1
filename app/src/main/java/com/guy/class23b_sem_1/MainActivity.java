package com.guy.class23b_sem_1;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.guy.simplegraph.GraphView;

public class MainActivity extends AppCompatActivity {

    private GraphView graphView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        graphView = findViewById(R.id.vm);

        //graphView.setNumOfPoints(22);
        graphView.setRange(0, 50);
        for (int i = 20; i < 50; i++) {
            graphView.newPoint(i);
        }
        graphView.invalidate();
    }
}