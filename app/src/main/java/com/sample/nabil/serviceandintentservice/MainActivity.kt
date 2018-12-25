package com.sample.nabil.serviceandintentservice

import android.app.Service
import android.content.Intent
import android.os.*
import android.support.annotation.Nullable
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.Toast


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    fun startService(view: View) {

        // use this to start and trigger a service
        val i = Intent(this, ExampleService::class.java)
        // potentially add data to the intent
        i.putExtra("KEY1", "Value to be used by the service")
        startService(i)
     }

    fun stopService(view: View) {

        // use this to start and trigger a service
        val i = Intent(this, ExampleService::class.java)
        // potentially add data to the intent
        stopService(i)
    }






}


class ExampleService : Service() {


    private lateinit var mHandlerThread: HandlerThread
    private lateinit var mServiceHandler: ServiceHandler

    // Define how the handler will process messages
    private inner class ServiceHandler(looper: Looper) : Handler(looper) {

        // Define how to handle any incoming messages here
        override fun handleMessage(message: Message) {
            Toast.makeText(this@ExampleService, "handle Message", Toast.LENGTH_SHORT).show()
            // ...
            // When needed, stop the service with
            // stopSelf();
        }
    }


    // Fires when a service is first initialized
    override fun onCreate() {
        super.onCreate()

        // An Android handler thread internally operates on a looper.
        mHandlerThread = HandlerThread("ExampleService.HandlerThread")
        mHandlerThread.start()
        // An Android service handler is a handler running on a specific background thread.
        mServiceHandler = ServiceHandler(mHandlerThread.getLooper())
    }


    override fun onStartCommand(intent: Intent, flags: Int, startId: Int): Int {
        Toast.makeText(this, "Service Started", Toast.LENGTH_SHORT).show()

        // Send empty message to background thread
        mServiceHandler.sendEmptyMessageDelayed(0, 500)
        // or run code in background
        mServiceHandler.post {
            Toast.makeText(this, "handle (runnable)", Toast.LENGTH_SHORT).show()
            // Do something here in background!
            // ...
            // If desired, stop the service
//            stopSelf()
        }

        return START_NOT_STICKY
    }

    override fun onDestroy() {
        super.onDestroy()
        Toast.makeText(this, "Service Stopped", Toast.LENGTH_SHORT).show()
    }

    @Nullable
    override fun onBind(intent: Intent): IBinder? {
        return null
    }
}


