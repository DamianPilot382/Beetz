package com.pilotcraftsystems.beetz;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.wearable.activity.WearableActivity;
import android.support.wearable.view.BoxInsetLayout;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.fitness.Fitness;
import com.google.android.gms.fitness.data.DataSource;
import com.google.android.gms.fitness.data.DataType;
import com.google.android.gms.fitness.request.DataSourcesRequest;
import com.google.android.gms.fitness.request.OnDataPointListener;
import com.google.android.gms.fitness.result.DataSourcesResult;

import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

public class MainActivity extends WearableActivity {

    private static final SimpleDateFormat AMBIENT_DATE_FORMAT =
            new SimpleDateFormat("HH:mm", Locale.US);

    private BoxInsetLayout mContainerView;
    private TextView mTextView;
    private TextView mClockView;
    private GoogleApiClient mgoogleApiClient;
    private String TAG;
    public OnDataPointListener mListerner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setAmbientEnabled();

        TAG= "SpideySenses";
        mContainerView = (BoxInsetLayout) findViewById(R.id.container);
        mTextView = (TextView) findViewById(R.id.text);
        mClockView = (TextView) findViewById(R.id.clock);

        mgoogleApiClient= new GoogleApiClient() {
            @Override
            public boolean hasConnectedApi(Api<?> api) {
                return false;
            }

            @Override
            public ConnectionResult getConnectionResult(Api<?> api) {
                return null;
            }

            @Override
            public void connect() {

            }

            @Override
            public ConnectionResult blockingConnect() {
                return null;
            }

            @Override
            public ConnectionResult blockingConnect(long l, TimeUnit timeUnit) {
                return null;
            }

            @Override
            public void disconnect() {

            }

            @Override
            public void reconnect() {

            }

            @Override
            public PendingResult<Status> clearDefaultAccountAndReconnect() {
                return null;
            }

            @Override
            public void stopAutoManage(FragmentActivity fragmentActivity) {

            }

            @Override
            public boolean isConnected() {
                return false;
            }

            @Override
            public boolean isConnecting() {
                return false;
            }

            @Override
            public void registerConnectionCallbacks(ConnectionCallbacks connectionCallbacks) {

            }

            @Override
            public boolean isConnectionCallbacksRegistered(ConnectionCallbacks connectionCallbacks) {
                return false;
            }

            @Override
            public void unregisterConnectionCallbacks(ConnectionCallbacks connectionCallbacks) {

            }

            @Override
            public void registerConnectionFailedListener(OnConnectionFailedListener onConnectionFailedListener) {

            }

            @Override
            public boolean isConnectionFailedListenerRegistered(OnConnectionFailedListener onConnectionFailedListener) {
                return false;
            }

            @Override
            public void unregisterConnectionFailedListener(OnConnectionFailedListener onConnectionFailedListener) {

            }

            @Override
            public void dump(String s, FileDescriptor fileDescriptor, PrintWriter printWriter, String[] strings) {

            }
        };


    }

    @Override
    public void onEnterAmbient(Bundle ambientDetails) {
        super.onEnterAmbient(ambientDetails);
        updateDisplay();
    }

    @Override
    public void onUpdateAmbient() {
        super.onUpdateAmbient();
        updateDisplay();
    }

    @Override
    public void onExitAmbient() {
        updateDisplay();
        super.onExitAmbient();
    }

    private void updateDisplay() {
        if (isAmbient()) {
            mContainerView.setBackgroundColor(getResources().getColor(android.R.color.black));
            mTextView.setTextColor(getResources().getColor(android.R.color.white));
            mClockView.setVisibility(View.VISIBLE);

            mClockView.setText(AMBIENT_DATE_FORMAT.format(new Date()));
        } else {
            mContainerView.setBackground(null);
            mTextView.setTextColor(getResources().getColor(android.R.color.black));
            mClockView.setVisibility(View.GONE);
        }
    }

    private void findHeartRateSensor(){
            Fitness.SensorsApi.findDataSources(mgoogleApiClient,new DataSourcesRequest.Builder()
                    //specify a data type
                    .setDataTypes(DataType.TYPE_HEART_RATE_BPM)
                    //set data as raw or derived
                    .setDataSourceTypes(DataSource.TYPE_RAW).build()).setResultCallback(new ResultCallback<DataSourcesResult>() {
                @Override
                public void onResult(DataSourcesResult dataSourcesResult) {
                    Log.i(TAG,"Result: "+ dataSourcesResult.getStatus().toString());
                    for(DataSource dataSource: dataSourcesResult.getDataSources()){
                        Log.i(TAG,"Result: "+dataSource.toString());
                        Log.i(TAG,"Reslut: "+ dataSource.getDataType().getName());

                        //register a  listener to receive activity data
                        if(dataSource.getDataType().equals(DataType.TYPE_HEART_RATE_BPM)&& mListerner==null){
                            Log.i(TAG,"Data source for LOCATION_SAMPLE found!  Registering.");
;
                        }

                    }
                }
            });




    }
}
