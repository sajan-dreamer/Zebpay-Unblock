package in.theapu.zebpayunblock;

/**
 * Created by APU V on 17-03-2017.
 */

import android.app.AndroidAppHelper;
import android.content.Context;
import android.view.Gravity;
import android.widget.Toast;

import de.robv.android.xposed.IXposedHookLoadPackage;
import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.callbacks.XC_LoadPackage.LoadPackageParam;

import static de.robv.android.xposed.XposedHelpers.findAndHookMethod;

public class ZebpayUnblocker implements IXposedHookLoadPackage {

    public void handleLoadPackage(LoadPackageParam lpparam) throws Throwable {
        if (!lpparam.packageName.equals("zebpay.Application"))
            return;
        findAndHookMethod("zebpay.Application.SplashActivity", lpparam.classLoader, "b", new XC_MethodHook() {
            @Override
            protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                boolean theReturnVal = (boolean) param.getResult();
                if (theReturnVal) {
                    String warning = "App has been hooked by third party app, but ignored by Zebpay Unblock.";
                    Context context = AndroidAppHelper.currentApplication();
                    Toast toast = Toast.makeText(context, warning, Toast.LENGTH_LONG);
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.show();
                }
                param.setResult(false);
            }
        });
    }
}

