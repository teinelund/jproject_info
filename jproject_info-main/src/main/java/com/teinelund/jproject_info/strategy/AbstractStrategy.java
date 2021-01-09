package com.teinelund.jproject_info.strategy;

import com.teinelund.jproject_info.context.Context;

public abstract class AbstractStrategy implements Strategy {

    protected Context context;

    public AbstractStrategy(Context context) {
        this.context = context;
    }
}