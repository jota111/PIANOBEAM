package com.test.test;

public class Gunban {
    public static float black_vertical = 230; // 검은세로
    public static float white_vertical = 381; // 하얀세로
    public static float count_size_change = 15; //가로

    private int octave = 3;

    public int getOctave() {
        return octave;
    }

    public void setOctave(int octave) {
        this.octave = octave;
    }

    public float getBlackVertical() {
        return black_vertical;
    }

    public void plusBlackVertical() {
        black_vertical += 5;
    }

    public void minusBlackVertical() {
        black_vertical -= 5;
    }

    public float getWhiteVertical() {
        return white_vertical;
    }

    public void plusWhiteVertical(){
        white_vertical += 5;
    }

    public void minusWhiteVertical() {
        white_vertical -= 5;
    }

    public float getCountSizeChange() {
        return count_size_change;
    }

    public void plusCountSizeChange(){
        count_size_change += 0.1;
    }

    public void minusCountSizeChange(){
        count_size_change -= 0.1;
    }
}