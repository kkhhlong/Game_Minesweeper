package com.example.nhl71.myfirstgame;

/**
 * Created by nhl71 on 23/06/2017.
 */

public class Mine {
    boolean boom;
    boolean flag;


    boolean open;
    int rowIndex;
    int columnIndex;
    private Mine(boolean boom,boolean flag,int rowIndex,int columnIndex,boolean open){
        this.boom = boom;
        this.flag = flag;
        this.rowIndex = rowIndex;
        this.columnIndex = columnIndex;
        this.open = open;
    }
    public static Mine getMine(int rowIndex,int columnIndex){
        return new Mine(false,false,rowIndex,columnIndex,false);
    }
    public boolean isBoom() {
        return boom;
    }

    public void setBoom(boolean boom) {
        this.boom = boom;
    }

    public boolean isFlag() {
        return flag;
    }

    public void setFlag(boolean flag) {
        this.flag = flag;
    }

    public int getRowIndex() {
        return rowIndex;
    }

    public void setRowIndex(int rowIndex) {
        this.rowIndex = rowIndex;
    }

    public int getColumnIndex() {
        return columnIndex;
    }

    public void setColumnIndex(int columnIndex) {
        this.columnIndex = columnIndex;
    }
    public boolean isOpen() {
        return open;
    }

    public void setOpen(boolean open) {
        this.open = open;
    }



}
