package com.example.nwang.mms;

import java.util.ArrayList;
import java.util.List;


//Asynctask usually cannot be called on non activity class. A private inner class in the activity can be used but that is messy.
//I prefer the interface method where we set a delegate as a listener
public interface TaskListener {
        public void onFinished(List result);

    }

