
package com.distantfuture.castvideos.app;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageInfo;
import android.graphics.Point;
import android.view.Display;
import android.view.WindowManager;
import android.widget.Toast;

import com.distantfuture.castcompanionlibrary.lib.cast.exceptions.CastException;
import com.distantfuture.castcompanionlibrary.lib.cast.exceptions.NoConnectionException;
import com.distantfuture.castcompanionlibrary.lib.cast.exceptions.TransientNetworkDisconnectionException;

import java.io.IOException;

/**
 * A collection of utility methods, all static.
 */
public class Utils {

  /*
   * Making sure public utility methods remain static
   */
  private Utils() {
  }

  /**
   * Returns the screen/display size
   */
  public static Point getDisplaySize(Context context) {
    WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
    Display display = wm.getDefaultDisplay();

    Point result = new Point();
    display.getSize(result);

    return result;
  }

  /**
   * Shows an error dialog with a given text message.
   */
  public static void showErrorDialog(Context context, String errorString) {
    new AlertDialog.Builder(context).setTitle(R.string.error)
        .setMessage(errorString)
        .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
          @Override
          public void onClick(DialogInterface dialog, int id) {
            dialog.cancel();
          }
        })
        .create()
        .show();
  }

  /**
   * Shows an error dialog with a text provided by a resource ID
   */
  public static void showErrorDialog(Context context, int resourceId) {
    new AlertDialog.Builder(context).setTitle(R.string.error)
        .setMessage(context.getString(resourceId))
        .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
          @Override
          public void onClick(DialogInterface dialog, int id) {
            dialog.cancel();
          }
        })
        .create()
        .show();
  }

  /**
   * Shows an "Oops" error dialog with a text provided by a resource ID
   */
  public static void showOopsDialog(Context context, int resourceId) {
    new AlertDialog.Builder(context).setTitle(R.string.oops)
        .setMessage(context.getString(resourceId))
        .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
          @Override
          public void onClick(DialogInterface dialog, int id) {
            dialog.cancel();
          }
        })
        .setIcon(R.drawable.ic_action_alerts_and_states_warning)
        .create()
        .show();
  }

  /**
   * A utility method to handle a few types of exceptions that are commonly thrown by the cast
   * APIs in this library. It has special treatments for
   * {@link TransientNetworkDisconnectionException}, {@link NoConnectionException} and shows an
   * "Oops" dialog conveying certain messages to the user. The following resource IDs can be used
   * to control the messages that are shown:
   * <p/>
   * <ul>
   * <li><code>R.string.connection_lost_retry</code></li>
   * <li><code>R.string.connection_lost</code></li>
   * <li><code>R.string.failed_to_perfrom_action</code></li>
   * </ul>
   */
  public static void handleException(Context context, Exception e) {
    int resourceId = 0;
    if (e instanceof TransientNetworkDisconnectionException) {
      // temporary loss of connectivity
      resourceId = R.string.connection_lost_retry;

    } else if (e instanceof NoConnectionException) {
      // connection gone
      resourceId = R.string.connection_lost;
    } else if (e instanceof RuntimeException ||
        e instanceof IOException ||
        e instanceof CastException) {
      // something more serious happened
      resourceId = R.string.failed_to_perfrom_action;
    } else {
      // well, who knows!
      resourceId = R.string.failed_to_perfrom_action;
    }
    if (resourceId > 0) {
      Utils.showOopsDialog(context, resourceId);
    }
  }

  /**
   * Gets the version of app.
   */
  public static String getAppVersionName(Context context) {
    String versionString = null;
    try {
      PackageInfo info = context.getPackageManager()
          .getPackageInfo(context.getPackageName(), 0 /* basic info */);
      versionString = info.versionName;
    } catch (Exception e) {
      // do nothing
    }
    return versionString;
  }

  /**
   * Shows a (long) toast
   */
  public static void showToast(Context context, String msg) {
    Toast.makeText(context, msg, Toast.LENGTH_LONG).show();
  }

  /**
   * Shows a (long) toast.
   */
  public static void showToast(Context context, int resourceId) {
    Toast.makeText(context, context.getString(resourceId), Toast.LENGTH_LONG).show();
  }

}
