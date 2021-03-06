package armored.g12matrickapp.Adapters;

/**
 * Created by Ohm on 17/06/15.
 */

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

public class ChooserArrayAdapter extends ArrayAdapter<String> {
    PackageManager mPm;
    int mTextViewResourceId;
    List<String> mPackages;
    Context ctx;

    public ChooserArrayAdapter(Context context, int resource, int textViewResourceId, List<String> packages) {
        super(context, resource, textViewResourceId, packages);
        ctx = context;
        mPm = context.getPackageManager();
        mTextViewResourceId = textViewResourceId;
        mPackages = packages;
    }

    private Drawable resize(Drawable drawa){
        try{ Bitmap b = ((BitmapDrawable)drawa).getBitmap();
            Bitmap bitmapRes = Bitmap.createScaledBitmap(b , 100 , 100 ,false);
            return new BitmapDrawable(ctx.getResources() , bitmapRes); }
        catch (Exception e){return drawa;}
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        String pkg = mPackages.get(position);
        View view = super.getView(position, convertView, parent);

        try {
            ApplicationInfo ai = mPm.getApplicationInfo(pkg, 0);

            CharSequence appName = mPm.getApplicationLabel(ai);
            Drawable appIcon = resize(mPm.getApplicationIcon(pkg));

            TextView textView = (TextView) view.findViewById(mTextViewResourceId);
            textView.setText(appName);
            textView.setCompoundDrawablesWithIntrinsicBounds(appIcon, null, null, null);
            textView.setCompoundDrawablePadding((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 12, getContext().getResources().getDisplayMetrics()));
        } catch (NameNotFoundException e) {
            e.printStackTrace();
        }

        return view;
    }

}