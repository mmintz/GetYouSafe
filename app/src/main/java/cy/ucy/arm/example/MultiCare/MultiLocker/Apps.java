package cy.ucy.arm.example.MultiCare.MultiLocker;

import android.graphics.drawable.Drawable;

public class Apps {
	private String name;
	private String locked;
	private Drawable icon;
	private int rec_image;
	private String package_name;
	private String id;
	//private Drawable icon2;
	
	
	public Apps(String name,String locked,Drawable icon,int res,String pack)
	{
		this.name=name;
		this.locked=locked;
		this.icon=icon;
		this.rec_image=res;
		this.package_name=pack;
		
		
	}
	public Apps(String name)
	{
		this.package_name=name;
		
		
	}

	public void setId(String id_new)
	{
		id=id_new;
	}
	
	public String getId()
	{
		return this.id;
	}

	public String getPackName()
	{
		return this.package_name;
	}
	
	public Drawable getIcon()
	{
		return this.icon;
	}
	public int getrec()
	{
		return rec_image;
	}
	
	public void setrec(int rec){
		rec_image=rec;
	}


	public String getName() {
		return name;
	}



	public void setName(String name) {
		this.name = name;
	}



	public String getLocked() {
		return locked;
	}



	public void setLocked(String locked) {
		this.locked = locked;
	}
	
	
	
	
	
	

}
