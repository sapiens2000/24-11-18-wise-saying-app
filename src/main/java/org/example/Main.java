package org.example;

import java.io.*;
import java.util.HashMap;
import java.util.LinkedHashMap;

public class Main {
    public static void main(String[] args) throws IOException {
        App app = new App();
        app.init();
        app.run();
    }
}

class App {
    private String basePath = System.getProperty("user.dir") + "\\db\\wiseSaying\\";
    private HashMap<Integer, WiseSaying> wiseSayingMap;
    private FileOutputStream fileOutputStream;
    private FileInputStream fileInputStream;
    private BufferedReader br;
    private BufferedWriter bw;
    // 초기화 전 = -1
    private int wiseSayingNum = 1;

    public void run() throws IOException {
        init();

        // 9단계 : 등록, 수정 시 파일 생성, 명언 번호 저장
        br = new BufferedReader(new InputStreamReader(System.in));
        boolean status = true;

        System.out.println("== 명언 앱 ==");

        while (status) {
            System.out.print("명령) ");
            String opt = br.readLine();
            if (opt.equals("종료")) {
                status = false;

                break;
            } else if (opt.equals("등록")) {
                System.out.print("명언 : ");
                String wiseSaying = br.readLine();
                System.out.print("작가 : ");
                String author = br.readLine();

                WiseSaying newWiseSaying = new WiseSaying(wiseSayingNum, author, wiseSaying);
                wiseSayingMap.put(wiseSayingNum, newWiseSaying);


                File outFile = new File(basePath + wiseSayingNum + ".json");

                if (!outFile.exists()) {
                    try {
                        outFile.createNewFile();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                bw = new BufferedWriter(new FileWriter(outFile));

                // 데이터 json 구조로 생성
                String jsonData = "{\n"
                        + "\t\"id\" : " + newWiseSaying.getId() + ",\n"
                        + "\t\"content\" : \"" + newWiseSaying.getWiseSaying() + "\",\n"
                        + "\t\"author\" : \"" + newWiseSaying.getAuthor() + "\"\n"
                        + "}";

                bw.write(jsonData);
                bw.close();

                String lastIdFilePath = basePath + "lastId.txt";
                File lastIdFile = new File(lastIdFilePath);

                if(!lastIdFile.exists()){
                    try {
                        lastIdFile.createNewFile();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

                jsonData = "" + wiseSayingNum;

                bw = new BufferedWriter(new FileWriter(lastIdFile));
                bw.write(jsonData);
                bw.close();

                System.out.printf("%d번 명언이 등록되었습니다.\n", wiseSayingNum++);
            } else if (opt.equals("목록")) {
                System.out.println("번호 / 작가 / 명언");

                // id 가 계속 증가만 한다고 가정했을때만 가능
                for (int i = wiseSayingNum; i > 0; i--) {
                    if (wiseSayingMap.get(i) == null) {
                        continue;
                    }
                    WiseSaying cur = wiseSayingMap.get(i);
                    System.out.printf("%d / %s / %s\n", cur.getId(), cur.getAuthor(), cur.getWiseSaying());
                }
            } else if (opt.startsWith("삭제")) {
                String[] tmp = opt.split("=");
                int targetId = Integer.parseInt(tmp[tmp.length - 1]);

                if (wiseSayingMap.get(targetId) == null) {
                    System.out.printf("%d번 명언은 존재하지 않습니다.\n", targetId);
                } else {
                    wiseSayingMap.remove(targetId);
                    System.out.printf("%d번 명언이 삭제되었습니다.\n", targetId);
                }
            } else if (opt.startsWith("수정")) {
                String[] tmp = opt.split("=");
                WiseSaying target = wiseSayingMap.get(Integer.parseInt(tmp[tmp.length - 1]));

                System.out.printf("명언(기존) : %s\n", target.getWiseSaying());
                System.out.print("명언 : ");
                String newWiseSaying = br.readLine();
                System.out.printf("작가(기존) : %s\n", target.getAuthor());
                System.out.print("작가 : ");
                String newAuthor = br.readLine();

                target.setWiseSaying(newWiseSaying);
                target.setAuthor(newAuthor);

                File targetFile = new File(basePath + target.getId() + ".json");

                if (!targetFile.exists()) {
                    try {
                        targetFile.createNewFile();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                bw = new BufferedWriter(new FileWriter(targetFile));

                // 데이터 json 구조로 생성
                String jsonData = "{\n"
                        + "\t\"id\" : " + target.getId() + ",\n"
                        + "\t\"content\" : \"" + target.getWiseSaying() + "\",\n"
                        + "\t\"author\" : \"" + target.getAuthor() + "\"\n"
                        + "}";

                bw.write(jsonData);
                bw.close();
                System.out.printf("%d번 명언이 수정되었습니다.\n", target.getId());
            }
        }

        br.close();
    }

    // 초기화 과정
    public void init() throws IOException {
        wiseSayingMap = new LinkedHashMap<>();
        File basePath = new File(this.basePath);


        for (String fileName : basePath.list()) {
            // null 아닐 경우 데이터 읽기
            if(fileName != null){
                File inputData = new File(basePath + "\\" + fileName);
                br = new BufferedReader(new FileReader(inputData));
                if(fileName.endsWith("json")){
                    StringBuilder sb;
                    // id 읽기
                    String data = br.readLine();
                    data = br.readLine();
                    sb = new StringBuilder(data.split(" : ")[1]);
                    sb.deleteCharAt(sb.length() - 1);
                    int id = Integer.parseInt(sb.toString());

                    // content 읽기
                    data = br.readLine();
                    sb = new StringBuilder(data.split(" : ")[1]);
                    sb.deleteCharAt(0);
                    sb.deleteCharAt(sb.length() - 1);
                    sb.deleteCharAt(sb.length() - 1);
                    String content = sb.toString();

                    // author 읽기
                    data = br.readLine();
                    sb = new StringBuilder(data.split(" : ")[1]);
                    sb.deleteCharAt(0);
                    sb.deleteCharAt(sb.length() - 1);
                    String author = sb.toString();

                    wiseSayingMap.put(id, new WiseSaying(id, author, content));
                }else if(fileName.endsWith("txt")){
                    wiseSayingNum = Integer.parseInt(br.readLine());

                }
            }
            br.close();
        }

    }
}