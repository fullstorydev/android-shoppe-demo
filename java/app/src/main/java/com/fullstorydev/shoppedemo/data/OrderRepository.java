package com.fullstorydev.shoppedemo.data;

import android.app.Application;
import android.util.Log;

import androidx.lifecycle.LiveData;

public class OrderRepository {
    private OrderDao orderDao;
    private LiveData<Order> currentOrder;

    public OrderRepository(Application application) {
        AppDatabase db = AppDatabase.getDatabase(application);
        orderDao = db.orderDao();
        currentOrder = orderDao.getCurrentOrder();
    }

    public LiveData<Order> getCurrentOrder() { return currentOrder; }

    public void createOrder() {
        Order order = new Order();
        insert(order);
    }

    public void completeOrder(Order order) {
        AppDatabase.databaseWriteExecutor.execute(() -> {
            orderDao.updateOrderStatus(order.id, true);
        });
    }

    public void insert(Order order) {
        AppDatabase.databaseWriteExecutor.execute(() -> {
            orderDao.insert(order);
        });
    }
}