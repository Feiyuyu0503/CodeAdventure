package net.feiyuyu.game;

import com.bin.david.form.annotation.SmartColumn;
import com.bin.david.form.annotation.SmartTable;

@SmartTable
public class pointData {
    public pointData(String obj, int floor) {
        this.obj = obj;
        this.floor = floor;
    }

    @SmartColumn(id=0,name = "指针名")
    private String obj;
    @SmartColumn(id=1,name="存放地址")
    private int floor;
}
