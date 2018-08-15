package com.test.test;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import static com.test.test.ScoreActivity.GREAT;
import static com.test.test.ScoreActivity.BAD;
import static com.test.test.SetPianoOctaveActivity.gunban;
import static com.test.test.DrawGunban.x_piano_upleft;
import static com.test.test.DrawGunban.y_piano_upleft;

class Ball {
    public static final int WHITE = 10; // 흰색: 10
    public static final int BLACK = 20; // 검은색: 20
    public static int sum_right_count_while = 0; // 오른손 누적 for문 도는 개수
    public static int sum_left_count_while = 0; // 왼손 누적 for문 도는 개수
    public static float velocity = 2f;
    public static int max = 0;

    private Paint paint;
    private boolean go_down; // 내려가는지 마는지
    private boolean same; // 동시에 치는지 아닌지
    private int same_num; // 동시에 치는 음이 몇개인지
    private int protocol;
    private int type; // 흰 건반인지 검은 건반인지
    private int count_while; // for문 몇번 도는지
    private float x; // 왼쪽위 x좌표
    private float y; // 왼쪽위 y좌표
    private float length; // 빛막대의 세로길이
    private float length_boundary; // 판정선에 닿았을 때 빛막대의 세로길이
    private Bitmap bitmap1, bitmap2, bitmap3, bitmap4, bitmap5, bitmap6, bitmap7, bitmap8, bitmap9;
    private int correct;
    private int effect_check = 0;

    public Ball(Context context, int protocol) {
        setProtocol(protocol); // 프로토콜 대입
        setColor(protocol); // 색깔 설정(빨간색인지 초록색인지)
        setGo_down(true);
        setSame_num(protocol);
        setSame();
        setType(protocol); // 흰 건반 or 검은 건반
        setCount_while(protocol); // for문을 몇번 도는지 설정
        setX(protocol); // 빛막대의 왼쪽위 x좌표 설정
        setY(protocol); // 빛막대의 왼쪽위 y좌표 설정
        setLength(protocol); // 빛막대의 세로 길이 설정
        setLength_boundary(length);
        setCorrect(BAD);

        bitmap1 = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(context.getResources(), R.drawable.effect_hit1), 100,100,false);
        bitmap2 = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(context.getResources(), R.drawable.effect_hit2), 100,100,false);
        bitmap3 = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(context.getResources(), R.drawable.effect_hit3), 100,100,false);
        bitmap4 = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(context.getResources(), R.drawable.effect_hit4), 100,100,false);
        bitmap5 = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(context.getResources(), R.drawable.effect_hit5), 100,100,false);
        bitmap6 = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(context.getResources(), R.drawable.effect_hit6), 100,100,false);
        bitmap7 = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(context.getResources(), R.drawable.effect_hit7), 100,100,false);
        bitmap8 = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(context.getResources(), R.drawable.effect_hit8), 100,100,false);
        bitmap9 = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(context.getResources(), R.drawable.effect_hit9), 100,100,false);
    }

    public int getProtocol() {
        return protocol;
    }

    public void setProtocol(int protocol) {
        this.protocol = protocol;
    }

    public void setColor(int protocol) {
        paint = new Paint();

        if(protocol / 10000000 == 1) {  // 오른손이라면, 쉼표도 이에 해당하지만, 어차피 안 보이니 상관 없다
            paint.setColor(Color.RED); // 오른손은 빨강색
        } else { // 왼손이라면
            paint.setColor(Color.GREEN); // 왼손은 초록색
        }
    }

    public boolean getGo_down() {
        return go_down;
    }

    public void setGo_down(boolean true_or_false) {
        go_down = true_or_false;
    }

    public boolean getSame() {
        return same;
    }

    public void setSame() {
        if(getSame_num() == 0) {
            same = false;
        } else {
            same = true;
        }
    }

    public int getSame_num() {
        return same_num;
    }

    public void setSame_num(int protocol) {
        same_num = protocol % 10;
    }

    public int getType() {
        return type;
    }

    public void setType(int protocol) {
        if((protocol / 10000) % 100 >= 10) { // 흰건반에 내려오는 빛이라면
            type = WHITE;
        } else { // 검은 건반에 내려오는 빛이라면, 쉼표도 이에 해당하지만, 어차피 안 보이니 상관 없다
            type = BLACK;
        }
    }

    public int getCount_while() {
        return count_while;
    }

    public void minusCount_while() {
        count_while--;
    }

    public void changeCount_while(int num) {
        count_while = num;
    }

    public void setCount_while(int protocol) {
        float percentage;

        if(velocity == 1) {
            percentage = 0.99f;
        } else if(velocity == 1.5) {
            percentage = 0.66f;
        } else if(velocity == 2) {
            percentage = 0.5f;
        } else if(velocity == 2.5) {
            percentage = 0.4f;
        } else if(velocity == 3) {
            percentage = 0.34f;
        } else if(velocity == 3.5) {
            percentage = 0.29f;
        } else if(velocity == 4) {
            percentage = 0.26f;
        } else {
            percentage = 0.22f;
        }

        if(protocol / 10000000 == 1) {
            if(getSame()) { // 동시에 치는 음이면
                if(getSame_num() == 1) {
                    count_while = sum_right_count_while; // 오른손 누적 for문 도는 개수에 추가
                } else {
                    sum_right_count_while += (protocol % 10000) * percentage;
                    count_while = sum_right_count_while; // 오른손 누적 for문 도는 개수에 추가
                }
            } else { // 동시에 치는 음이 아니면
                sum_right_count_while += (protocol % 10000) * percentage;
                count_while = sum_right_count_while; // 오른손 누적 for문 도는 개수에 추가
                sum_right_count_while = sum_right_count_while + 2;
            }

            //sum_right_count_for = sum_right_count_for + 2;
        } else {
            if(getSame()) { // 동시에 치는 음이면
                if(getSame_num() == 1) {
                    count_while = sum_left_count_while; // 오른손 누적 for문 도는 개수에 추가
                } else {
                    sum_left_count_while += (protocol % 10000) * percentage;
                    count_while = sum_left_count_while; // 오른손 누적 for문 도는 개수에 추가
                }
            } else { // 동시에 치는 음이 아니면
                sum_left_count_while += (protocol % 10000) * percentage;
                count_while = sum_left_count_while; // 오른손 누적 for문 도는 개수에 추가
            }

            sum_left_count_while = sum_left_count_while + 2;
        }
    }

    public void setX(int protocol) {
        float x_light = 0;
        int temp = protocol; // temp에 임시 저장

        if((temp > 10000000 && temp < 10010000) || temp < 10000) { // 쉼표라면
            x = 50000; // 화면에 안 보이도록

            return;
        }

        if(temp / 10000000 == 1) { // 오른손이라면
            temp -= 10000000; // 오른손일 경우에만 앞에 1이 추가로 붙기 때문에
        }

        if((temp / 10000) % 100 >= 10) { // 흰 건반이라면
            x_light += x_piano_upleft + (47 + gunban.getCountSizeChange()) * 7 * (temp / 1000000 - 1) + ((47 + gunban.getCountSizeChange()) * (((temp / 10000) % 100) / 10 - 1));
        } else { // 검은 건반이라면
            switch ((protocol / 10000) % 100) {
                case 1:
                    x_light += x_piano_upleft + (47 + gunban.getCountSizeChange()) * 7 * (temp / 1000000 - 1) + (((47 + gunban.getCountSizeChange()) * 3) - (23 + 23 / 47 * gunban.getCountSizeChange()) * 2) / 3;
                    break;
                case 2:
                    x_light += x_piano_upleft + (47 + gunban.getCountSizeChange()) * 7 * (temp / 1000000 - 1) + ((47 + gunban.getCountSizeChange()) * 3 - (23 + 23 / 47 * gunban.getCountSizeChange()) * 2) / 3 * 2 + 23 + 23 / 47 * gunban.getCountSizeChange();
                    break;
                case 3:
                    x_light += x_piano_upleft + (47 + gunban.getCountSizeChange()) * 7 * (temp / 1000000 - 1) + (47 + gunban.getCountSizeChange()) * 3 + ((47 + gunban.getCountSizeChange()) * 4 - (23 + 23 / 47 * gunban.getCountSizeChange()) * 3) / 4;
                    break;
                case 4:
                    x_light += x_piano_upleft + (47 + gunban.getCountSizeChange()) * 7 * (temp / 1000000 - 1) + (47 + gunban.getCountSizeChange()) * 3 + ((47 + gunban.getCountSizeChange()) * 4 - (23 + 23 / 47 * gunban.getCountSizeChange()) * 3) / 4 * 2 + 23 + 23 / 47 * gunban.getCountSizeChange();
                    break;
                case 5:
                    x_light += x_piano_upleft + (47 + gunban.getCountSizeChange()) * 7 * (temp / 1000000 - 1) + (47 + gunban.getCountSizeChange()) * 3 + ((47 + gunban.getCountSizeChange()) * 4 - (23 + 23 / 47 * gunban.getCountSizeChange()) * 3) / 4 * 3 + (23 + 23 / 47 * gunban.getCountSizeChange()) * 2;
                    break;
            }
        }

        x = x_light;
    }

    public float getY() {
        return y;
    }

    public void setY(int protocol) {

        if((protocol / 10000) % 100 >= 10) { // 흰건반이라면
            y = -700;
        } else { // 검은 건반이라면, 쉼표도 이에 해당
            y = -700 - (gunban.getWhiteVertical() - gunban.getBlackVertical()); // 흰 건반보다 조금 더 위에서 떨어진다
        }
    }

    public float getLength() {
        return length;
    }

    public void setLength(int protocol) {
        if(!getSame()) {
            if(protocol % 10000 > max) {
                max = protocol % 10000;
            }

            length = protocol % 10000;
        } else {
            if(protocol % 10000 - getSame_num() > max) {
                max = protocol % 10000 - getSame_num();
            }

            length = protocol % 10000 - getSame_num();
        }
    }

    public void changeLength_boundary(float num) {
        length_boundary = num;
    }

    public void setLength_boundary(float length) {
        length_boundary = length;
    }

    public void updatePosition(float velocity) {
        y += velocity; // 속력만큼 그려주는 y좌표가 증가
    }

    public void drawWhiteGubanLight(Canvas canvas) {
        if(y >= y_piano_upleft + gunban.getWhiteVertical() - length) { // 흰색 건반 아래쪽 경계선에 빛막대가 닿게 될 경우
            if(length_boundary >= 0) { // 빛 막대가 경계선을 아직 완전히 지나가지 않았을 경우
                canvas.drawRect(x, y, x + 47 + gunban.getCountSizeChange(), y + length_boundary, paint);
                length_boundary -= velocity; // 속력만큼 빛막대 세로길이 감소
                effect_check++;
            } else {
                go_down = false; // 더 이상 내려가지 마라
            }
        } else {
            canvas.drawRect(x, y, x + 47 + gunban.getCountSizeChange(), y + length, paint);
        }

        if(correct == GREAT && go_down) {
            switch (effect_check % 10) {
                case 1:
                    canvas.drawBitmap(bitmap1, x - 26, y_piano_upleft + gunban.getWhiteVertical() - 80, null);
                    break;
                case 2:
                    canvas.drawBitmap(bitmap2, x - 26, y_piano_upleft + gunban.getWhiteVertical() - 80, null);
                    break;
                case 3:
                    canvas.drawBitmap(bitmap3, x - 26, y_piano_upleft + gunban.getWhiteVertical() - 80, null);
                    break;
                case 4:
                    canvas.drawBitmap(bitmap4, x - 26, y_piano_upleft + gunban.getWhiteVertical() - 80, null);
                    break;
                case 5:
                    canvas.drawBitmap(bitmap5, x - 26, y_piano_upleft + gunban.getWhiteVertical() - 80, null);
                    break;
                case 6:
                    canvas.drawBitmap(bitmap6, x - 26, y_piano_upleft + gunban.getWhiteVertical() - 80, null);
                    break;
                case 7:
                    canvas.drawBitmap(bitmap7, x - 26, y_piano_upleft + gunban.getWhiteVertical() - 80, null);
                    break;
                case 8:
                    canvas.drawBitmap(bitmap8, x - 26, y_piano_upleft + gunban.getWhiteVertical() - 80, null);
                    break;
                case 9:
                    canvas.drawBitmap(bitmap9, x - 26, y_piano_upleft + gunban.getWhiteVertical() - 80, null);
                    break;
            }
        }

        updatePosition(velocity); // 한 단계 아래로
    }

    public void drawBlackGubanLight(Canvas canvas) {
        if(y >= y_piano_upleft + gunban.getBlackVertical() - length) { // 검은 건반 아래쪽 경계선에 빛막대가 닿게 될 경우
            if(length_boundary >= 0) { // 빛 막대가 경계선을 아직 완전히 지나가지 않았을 경우
                canvas.drawRect(x, y, x + 23 + 0.489361f * gunban.getCountSizeChange(), y + length_boundary, paint);
                length_boundary -= velocity; // 속력만큼 빛막대 세로길이 감소
                effect_check++;
            } else {
                go_down = false; // 더 이상 내려가지 마라
            }
        } else {
            canvas.drawRect(x, y, x + 23 + 0.489361f * gunban.getCountSizeChange(), y + length, paint);
        }

        if(correct == GREAT && go_down) {
            switch(effect_check % 10) {
                case 1:
                    canvas.drawBitmap(bitmap1, x - 26, y_piano_upleft + gunban.getBlackVertical() - 80, null);
                    break;
                case 2:
                    canvas.drawBitmap(bitmap2, x - 26, y_piano_upleft + gunban.getBlackVertical() - 80, null);
                    break;
                case 3:
                    canvas.drawBitmap(bitmap3, x - 26, y_piano_upleft + gunban.getBlackVertical() - 80, null);
                    break;
                case 4:
                    canvas.drawBitmap(bitmap4, x - 26, y_piano_upleft + gunban.getBlackVertical() - 80, null);
                    break;
                case 5:
                    canvas.drawBitmap(bitmap5, x - 26, y_piano_upleft + gunban.getBlackVertical() - 80, null);
                    break;
                case 6:
                    canvas.drawBitmap(bitmap6, x - 26, y_piano_upleft + gunban.getBlackVertical() - 80, null);
                    break;
                case 7:
                    canvas.drawBitmap(bitmap7, x - 26, y_piano_upleft + gunban.getBlackVertical() - 80, null);
                    break;
                case 8:
                    canvas.drawBitmap(bitmap8, x - 26, y_piano_upleft + gunban.getBlackVertical() - 80, null);
                    break;
                case 9:
                    canvas.drawBitmap(bitmap9, x - 26, y_piano_upleft + gunban.getBlackVertical() - 80, null);
                    break;
            }
        }

        updatePosition(velocity); // 한 단계 아래로
    }

    public int getCorrect() {
        return correct;
    }

    public void setCorrect(int c) {
        correct = c;
    }
}