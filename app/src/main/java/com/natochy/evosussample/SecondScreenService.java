package com.natochy.evosussample;

import android.app.IntentService;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Handler;
import android.os.IBinder;
import android.os.RemoteException;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.RunnableFuture;

import co.poynt.api.model.OrderItem;
import co.poynt.api.model.OrderItemStatus;
import co.poynt.api.model.UnitOfMeasure;
import co.poynt.os.model.Intents;
import co.poynt.os.services.v1.IPoyntSecondScreenService;


/**
 * An {@link IntentService} subclass for handling asynchronous task requests in
 * a service on a separate handler thread.
 * <p>
 * TODO: Customize class - update intent actions and extra parameters.
 */
public class SecondScreenService extends IntentService {

    private IPoyntSecondScreenService secondScreenService;
    private ServiceConnection secondScreenConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            secondScreenService = IPoyntSecondScreenService.Stub.asInterface(iBinder);
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {
            secondScreenService = null;
        }
    };

    @Override
    public void onCreate() {
        super.onCreate();
        bindService(Intents.getComponentIntent(Intents.COMPONENT_POYNT_SECOND_SCREEN_SERVICE), secondScreenConnection,
                BIND_AUTO_CREATE);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unbindService(secondScreenConnection);
    }

    public SecondScreenService() {
        super("SecondScreenService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        String request = intent.getStringExtra("REQUEST");
        try {
            JSONObject jsonObject = new JSONObject(request);
            final long totalAmount = jsonObject.getLong("totalAmount");
            final String currency = jsonObject.getString("currency");
            JSONArray itemsArray = jsonObject.getJSONArray("items");
            final List<OrderItem> orderItemList = new ArrayList<>();
            for (int i = 0; i < itemsArray.length(); i++){
                JSONObject obj = itemsArray.getJSONObject(i);
                String name = obj.getString("name");
                double qty = obj.getDouble("quantity");
                String status = obj.getString("status");
                long tax = obj.getLong("tax");
                long unitPrice = obj.getLong("unitPrice");
                String unitOfMeasure = obj.getString("unitOfMeasure");

                OrderItem oi = new OrderItem();
                oi.setName(name);
                // unsafe parsing double to float
                oi.setQuantity((float) qty);
                oi.setStatus(OrderItemStatus.findByStatus(status));
                oi.setTax(tax);
                oi.setUnitOfMeasure(UnitOfMeasure.findByUnitOfMeasure(unitOfMeasure));
                oi.setUnitPrice(unitPrice);
                orderItemList.add(oi);
            }
            Runnable r = new Runnable() {
                @Override
                public void run() {
                    try {
                        // order, total amount, currency
                        secondScreenService.showItem(orderItemList, totalAmount, currency);
                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }
                }
            };
            Handler h = new Handler(getMainLooper());
            if (secondScreenService != null){
                h.post(r);
            }else{
                // wait half a second in case IPoyntSecondScreenService is not available yet
                h.postDelayed(r, 500l);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    /**
     * Handle action Foo in the provided background thread with the provided
     * parameters.
     */
    private void handleActionFoo(String param1, String param2) {
        // TODO: Handle action Foo
        throw new UnsupportedOperationException("Not yet implemented");
    }

    /**
     * Handle action Baz in the provided background thread with the provided
     * parameters.
     */
    private void handleActionBaz(String param1, String param2) {
        // TODO: Handle action Baz
        throw new UnsupportedOperationException("Not yet implemented");
    }
}
