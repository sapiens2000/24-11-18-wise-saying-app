package org.example;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.SQLOutput;

public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));


        System.out.println("== 명언 앱 ==");
        int wiseSayingNum = 1;
        boolean status = true;

        while(status) {
            System.out.print("명령) ");
            String opt = br.readLine();
            if (opt.equals("종료")) {
                status = false;
                break;
            }else if(opt.equals("등록")){
                System.out.print("명언 : ");
                String wiseSaying = br.readLine();
                System.out.print("작가 : ");
                String author = br.readLine();

                System.out.printf("%d번 명언이 등록되었습니다.\n", wiseSayingNum++);
            }
        }
    }
}