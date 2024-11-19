package org.example;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        System.out.println("== 명언 앱 ==");

        boolean status = true;

        while(status) {
            System.out.print("명령) ");
            String opt = br.readLine();
            if (opt.equals("종료")) {
                status = false;
                break;
            }
        }
    }
}