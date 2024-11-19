package org.example;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.LinkedHashMap;

public class Main {
    public static void main(String[] args) throws IOException {
        App app = new App();
        app.run();
    }
}

class App {
    public void run() throws IOException{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int wiseSayingNum = 1;
        boolean status = true;

        HashMap<Integer, WiseSaying> wiseSayingMap = new LinkedHashMap<>();

        System.out.println("== 명언 앱 ==");

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

                wiseSayingMap.put(wiseSayingNum, new WiseSaying(wiseSayingNum, author, wiseSaying));
                System.out.printf("%d번 명언이 등록되었습니다.\n", wiseSayingNum++);

            }else if(opt.equals("목록")){
                System.out.println("번호 / 작가 / 명언");

                // id 가 계속 증가만 한다고 가정했을때만 가능
                for(int i=wiseSayingNum;i>0;i--){
                    if(wiseSayingMap.get(i) == null){
                        continue;
                    }
                    WiseSaying cur = wiseSayingMap.get(i);
                    System.out.printf("%d / %s / %s\n", cur.getId(), cur.getAuthor(), cur.getWiseSaying());
                }
            }else if(opt.startsWith("삭제")){
                String[] tmp = opt.split("=");
                int targetId = Integer.parseInt(tmp[tmp.length-1]);

                if(wiseSayingMap.get(targetId) == null){
                    System.out.printf("%d번 명언은 존재하지 않습니다.\n", targetId);
                }else {
                    wiseSayingMap.remove(targetId);
                    System.out.printf("%d번 명언이 삭제되었습니다.\n", targetId);
                }
            }else if(opt.startsWith("수정")){
                String[] tmp = opt.split("=");
                WiseSaying target = wiseSayingMap.get(Integer.parseInt(tmp[tmp.length-1]));

                System.out.printf("명언(기존) : %s\n", target.getWiseSaying());
                System.out.print("명언 : ");
                String newWiseSaying = br.readLine();
                System.out.printf("작가(기존) : %s\n", target.getAuthor());
                System.out.print("작가 : ");
                String newAuthor = br.readLine();

                target.setWiseSaying(newWiseSaying);
                target.setAuthor(newAuthor);
            }
        }

        br.close();
    }
}