package cy.ucy.arm.example.MultiCare.MultiLocker;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageButton;




public class ExtendedButton extends ImageButton {

	int position;
	Drawable image;
	boolean is_clicked=false;
	int resource=-1;
	
    public ExtendedButton(Context context) {
        super(context);
    }

    public ExtendedButton(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ExtendedButton(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }
    
    public void setPos(int num)
    {
    	position=num;
    }
    
    public void setRes(int num)
    {
    	resource=num;
    }

    
    public int getPos()
    {
    	return position;
    }
    
    public boolean isClicked()
    {
    	return is_clicked;
    }
    
    public int getRes()
    {
    	return resource;
    }
    
    public void setClicked(Boolean click,int rec)
    {
    	is_clicked=click;
    	resource=rec;
    }
    
    @SuppressLint("NewApi")
	public void setDrawable (Drawable dr)
    {
    
    	this.setBackgroundDrawable(dr);
    }
    
    
    
    @Override
    public void setPressed(boolean pressed) {
        if (pressed && getParent() instanceof View && ((View) getParent()).isPressed()) {
            return;
        }
        super.setPressed(pressed);
    }

}