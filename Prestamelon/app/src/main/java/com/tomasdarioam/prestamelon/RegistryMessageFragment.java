package com.tomasdarioam.prestamelon;

import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class RegistryMessageFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_registry_message, container, false);
    }

    public RegistryMessageFragment(String mensaje, int segundosLectura) {
        this.mensaje = mensaje;
        this.segundosLectura = segundosLectura;
    }

    private TextView txvMensaje;
    private String mensaje;
    private long startTime = 0;
    private int segundosLectura = 0;


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        txvMensaje = getView().findViewById(R.id.txvMensaje);
        txvMensaje.setText(mensaje);

        startTime = System.currentTimeMillis();

        timerHandler.postDelayed(timerRunnable, 0);
    }

    Handler timerHandler = new Handler();
    Runnable timerRunnable = new Runnable() {
        @Override
        public void run() {
            long millis = System.currentTimeMillis() - startTime;

            int seconds = (int) (millis / 1000);

            seconds = seconds % 60;

            //txvMensaje.setText(String.valueOf(seconds));

            timerHandler.postDelayed(this, 0);

            if(seconds == segundosLectura) {
                nextFragment();
                timerHandler.removeCallbacks(timerRunnable);
            }
        }
    };

    private void nextFragment() {
        callback.timerExpired();
    }

    TimerExpiredListener callback;

    public void setTimerExpiredListener(TimerExpiredListener callback) {
        this.callback = callback;
    }

    public interface TimerExpiredListener {
        void timerExpired();
    }

}
