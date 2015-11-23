package com.veontomo.fiestatime.api;

import com.veontomo.fiestatime.api.ITask;

/**
 * Task that retrieves all holidays from the storage
 */
public class AllHolidayTask implements ITask {

    private final IHolidayProvider provider;

    public AllHolidayTask(IHolidayProvider provider){
        this.provider = provider;
    }
    @Override
    public void run() {
            // TODO
    }
}
