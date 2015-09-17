package cy.ucy.arm.example.MultiCare.MultiLocker;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.graphics.drawable.Drawable;

public class ApkInfo {
	private String appname;
	private String pname;
	private String versionName;
	private Drawable icon;
	private int versionCode;
	private PackageInfo p;
	
	
	
	
	public static ApkInfo getInfoFromPackageName(String pkgName,
            Context mContext) {
        ApkInfo newInfo = new ApkInfo();
        try {
            PackageInfo p = mContext.getPackageManager().getPackageInfo(
                    pkgName, PackageManager.GET_PERMISSIONS);

            newInfo.appname = p.applicationInfo.loadLabel(
                    mContext.getPackageManager()).toString();
            newInfo.pname = p.packageName;
            newInfo.versionName = p.versionName;
            newInfo.versionCode = p.versionCode;
            newInfo.icon = p.applicationInfo.loadIcon(mContext
                    .getPackageManager());
            newInfo.setP(p);
        } catch (NameNotFoundException e) {
            e.printStackTrace();
            return null;
        }
        return newInfo;
    }




	private void setP(PackageInfo p) {
		// TODO Auto-generated method stub
		this.p=p;
	}




	public PackageInfo getP() {
		// TODO Auto-generated method stub
		return this.p;
	}
	
}
