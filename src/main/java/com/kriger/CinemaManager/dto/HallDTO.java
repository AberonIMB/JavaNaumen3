package com.kriger.CinemaManager.dto;

public class HallDTO {

    private String name;
    private int rows;
    private int seatsInRow;

    public HallDTO(String name, int rows, int seatsInRow) {
        this.name = name;
        this.rows = rows;
        this.seatsInRow = seatsInRow;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getRows() {
        return rows;
    }

    public void setRows(int rows) {
        this.rows = rows;
    }

    public int getSeatsInRow() {
        return seatsInRow;
    }

    public void setSeatsInRow(int seatsInRow) {
        this.seatsInRow = seatsInRow;
    }
}
