package com.example.administrator.mybluetoothclient;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.util.UUID;

public class BluetoothAction implements View.OnClickListener {
    
    private Button switchBT = null;
    private Button searchDevices = null;    
    private Activity activity = null;
    
    private ListView unbondDevices = null;
    private ListView bondDevices = null;    
    private Context context = null;
    private BluetoothService bluetoothService = null;    
    
    public BluetoothAction(Context context, ListView unbondDevices,    
            ListView bondDevices, Button switchBT, Button searchDevices,    
            Activity activity) {    
        super();    
        this.context = context;    
        this.unbondDevices = unbondDevices;    
        this.bondDevices = bondDevices;    
        this.switchBT = switchBT;    
        this.searchDevices = searchDevices;    
        this.activity = activity;    
        this.bluetoothService = new BluetoothService(this.context,    
                this.unbondDevices, this.bondDevices, this.switchBT,    
                this.searchDevices);    
    }    
    
    public void setSwitchBT(Button switchBT) {    
        this.switchBT = switchBT;    
    }    
    
    public void setSearchDevices(Button searchDevices) {    
        this.searchDevices = searchDevices;    
    }    
    
    public void setUnbondDevices(ListView unbondDevices) {    
        this.unbondDevices = unbondDevices;    
    }    
    
    /**  
     * 初始化界面  
     */    
    public void initView() {    
    
        if (this.bluetoothService.isOpen()) {    
            System.out.println("蓝牙有开!");    
            switchBT.setText("关闭蓝牙");    
        }    
        if (!this.bluetoothService.isOpen()) {    
            System.out.println("蓝牙没开!");    
            this.searchDevices.setEnabled(false);    
        }    
    }

    
    private void searchDevices() {
        int checkCallPhonePermission = ContextCompat.checkSelfPermission(activity,
                Manifest.permission.ACCESS_COARSE_LOCATION);
        //判断这个权限是否已经授权过

        if(checkCallPhonePermission != PackageManager.PERMISSION_GRANTED){
            //判断是否需要 向用户解释，为什么要申请该权限
            if(ActivityCompat.shouldShowRequestPermissionRationale(activity,
                    Manifest.permission.ACCESS_COARSE_LOCATION))
                Toast.makeText(activity, "Need bluetooth permission.",
                        Toast.LENGTH_SHORT).show();
            ActivityCompat.requestPermissions(activity ,new String[]
                    {Manifest.permission.ACCESS_COARSE_LOCATION},1);
            return;
        }else{
            //bluetoothService.searchDevices();
        }
    }



    /**  
     * 各种按钮的监听  
     */    
    @Override    
    public void onClick(View v) {    
        if (v.getId() == R.id.searchDevices) {    
            this.searchDevices();    
        } else if (v.getId() == R.id.return_Bluetooth_btn) {    
            activity.finish();    
        } else if (v.getId() == R.id.openBluetooth_tb) {    
            if (!this.bluetoothService.isOpen()) {    
                // 蓝牙关闭的情况    
                System.out.println("蓝牙关闭的情况");    
                this.bluetoothService.openBluetooth(activity);    
            } else {    
                // 蓝牙打开的情况
                try{
                    System.out.println("蓝牙打开的情况");
                    this.bluetoothService.closeBluetooth();
                }catch (Exception e){
                    e.printStackTrace();
                }
    
            }    
    
        }    
    }    
    
}