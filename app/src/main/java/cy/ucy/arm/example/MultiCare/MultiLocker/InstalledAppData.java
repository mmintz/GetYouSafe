package cy.ucy.arm.example.MultiCare.MultiLocker;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import android.graphics.drawable.Drawable;



public class InstalledAppData{
    private String deviceId;
    private String packageName;
    private String appName;
    private String installTime;
    private String status;
    private Drawable icon;
 
    public InstalledAppData(String appName,String status,Drawable icon,String pack) {
        super();
        
        this.appName = appName;
        this.packageName=pack;
        //long time = installTime;
        //Date date = new Date(time);
        //SimpleDateFormat sdf = new SimpleDateFormat(
          //      "yyyy-MM-dd HH:mm:ss");
       // String datetime = sdf.format(date);
       // this.installTime = datetime;
        this.status = status;
        this.icon=icon;
    }
    public String getDeviceId() {
        return deviceId;
    }
    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }
    public String getPackageName() {
        return packageName;
    }
    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }
    public String getAppName() {
        return appName;
    }
    public void setAppName(String appName) {
        this.appName = appName;
    }
    public long getInstallTime() {
        try {
            DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date date = formatter.parse(this.installTime);
            return date.getTime();
 
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return 0;
    }
    public void setInstallTime(long installTime) {
        long time = installTime;
        Date date = new Date(time);
        SimpleDateFormat sdf = new SimpleDateFormat(
                "yyyy-MM-dd HH:mm:ss");
        String datetime = sdf.format(date);
        this.installTime = datetime;
    }
    public String getStatus() {
        return status;
    }
    public void setStatus(String status) {
        this.status = status;
    }
    
    public Drawable getIcon()
    {
    	return this.icon;
    }
    
    
    
   
}