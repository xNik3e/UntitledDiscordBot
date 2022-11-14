package com.example.untitleddiscordbot.Utils;

import com.example.untitleddiscordbot.R;

public enum ServerStatus {
    ADDED(R.drawable.ic_check, R.color.check_green),
    NOT_ADDED(R.drawable.ic_warning, R.color.check_yellow),
    ERROR(R.drawable.ic_error, R.color.check_red);

    int iconResourceId;
    int colorResourceId;

    ServerStatus(int ic_check, int check_color) {
        this.iconResourceId = ic_check;
        this.colorResourceId = check_color;
    }
}
