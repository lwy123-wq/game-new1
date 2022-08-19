package com.task.service;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.task.entity.Card;
import com.task.entity.MyRank;
import com.task.entity.MyUser;

import org.springframework.stereotype.Service;

import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ClientService {
    String url="http://localhost:9999/";
    MyRank.Player.Builder player=MyRank.Player.newBuilder();
    Card.Character.Builder card=Card.Character.newBuilder();
    MyUser.User1.Builder user=MyUser.User1.newBuilder();
    MyRank.Player.Builder play= MyRank.Player.newBuilder();
    public String login(){
        //String body = "{useranme:bb,password:111}";

        user.setPassword("111");
        user.setUsername("cc");
        /*Gson gson = new Gson();
        final String json = gson.toJson(user);*/
        String result=sendHttp(user.build().toByteArray(),"login");
        return result;
    }
    public String register(){
        user.setPassword("222");
        user.setUsername("cc");
        user.setEmail("333");
        /*Gson gson = new Gson();
        final String json = gson.toJson(user);
        String result=sendUser(json,"registry");
        return result;*/

        String result=sendHttp(user.build().toByteArray(),"registry");
        return result;
    }
    //签到
    public String sigin(){
        play.setServerId(1);
        user.setUsername("bb");
        String result=sendHttp(user.build().toByteArray(),"signIn");
        return result;
    }

    /*public List<Fight.fight1.Builder> selectFight(){
        play.setServerId(1);
        String result=sendHttp(play.build().toByteArray(),"selectFight");
        Gson gson = new Gson();
        List<Fight.fight1.Builder> fight = gson.fromJson(result, new TypeToken<List<Fight.fight1.Builder>>(){}.getType());

        return fight;
    }*/
    public Map selectAll(){
        String result=sendHttp("selectAll");
        Gson gson = new Gson();
        Map map = gson.fromJson(result, new TypeToken<HashMap<Integer,String>>(){}.getType());
        return map;
    }


    private String sendHttp(byte[] data, String controller) {
        OutputStream out = null;
        BufferedReader in = null;
        StringBuilder result = new StringBuilder();
        try {
            URL realUrl = new URL(url + controller);
            HttpURLConnection connection = (HttpURLConnection) realUrl.openConnection();

            connection.setDoOutput(true);
            connection.setDoInput(true);
            connection.setUseCaches(false);
            connection.setInstanceFollowRedirects(true);
            connection.setRequestMethod("POST"); // 设置请求方式
            connection.setRequestProperty("Connection", "Keep-Alive");
            connection.setRequestProperty("Content-Type", "binary/octet-stream");
            connection.connect();

            out = connection.getOutputStream();

            // 发送请求参数，防止中文乱码
            out.write(data);
            // flush输出流的缓冲
            out.flush();

            // 定义BufferedReader输入流来读取URL的响应
            in = new BufferedReader(new InputStreamReader(connection.getInputStream()));

            String line;
            while ((line = in.readLine()) != null) {
                result.append(line);
            }

        } catch (Exception e) {
            System.out.println("发送 POST 请求出现异常！");
            e.printStackTrace();
        } finally {
            try {
                if (out != null) {
                    out.close();
                }
                if (in != null) {
                    in.close();
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }

        return result.toString();
    }
    //查找邮箱内容
    public ArrayList<String> messages() throws MessagingException, IOException {
        Folder folder=null;
        Message[] messages=null;
        MimeMessage msg=null;
        UserService userService=new UserService();
        ReceiveService service=new ReceiveService();
        ArrayList<String> list=new ArrayList<>();
        folder=userService.user();
        messages = folder.getMessages();
        if (messages == null || messages.length < 1)
            throw new MessagingException("未找到要解析的邮件!");
        for (int i = 0, count = messages.length; i < count; i++) {
            msg = (MimeMessage) messages[i];
            String subject=service.getSubject(msg); //主题
            String from=service.getFrom(msg);       //发件人
            String receive=service.getReceiveAddress(msg, null);   //收件人
            String time=service.getSentDate(msg, null);   //发送时间
            String size=msg.getSize() * 1024 + "kb";
            StringBuffer content = new StringBuffer(30);
            service.getMailTextContent(msg, content);
            String text=(content.length() > 100 ? content.substring(0,100) + "..." : String.valueOf(content)); //邮件正文
            list.add(subject);
            list.add(from);
            list.add(receive);
            list.add(time);
            list.add(size);
            list.add(text);
        }
        return list;
    }
    private String sendUser(String data, String controller) {
        BufferedWriter out = null;
        BufferedReader in = null;
        StringBuilder result = new StringBuilder();
        try {
            URL realUrl = new URL(url + controller);
            HttpURLConnection connection = (HttpURLConnection) realUrl.openConnection();

            connection.setDoOutput(true);
            connection.setDoInput(true);
            connection.setUseCaches(false);
            connection.setInstanceFollowRedirects(true);
            connection.setRequestMethod("POST"); // 设置请求方式
            connection.setRequestProperty("Connection", "Keep-Alive");
            connection.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
            connection.connect();

            out = new BufferedWriter(new OutputStreamWriter(connection.getOutputStream(), "UTF-8"));

            out.write(data);
            out.flush();
            in = new BufferedReader(new InputStreamReader(connection.getInputStream()));

            String line;
            while ((line = in.readLine()) != null) {
                result.append(line);
            }

        } catch (Exception e) {
            System.out.println("发送 POST 请求出现异常！");
            e.printStackTrace();
        } finally {
            try {
                if (out != null) {
                    out.close();
                }
                if (in != null) {
                    in.close();
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }

        return result.toString();
    }
    private String sendHttp(String controller) {
        OutputStream out = null;
        BufferedReader in = null;
        StringBuilder result = new StringBuilder();
        try {
            URL realUrl = new URL(url + controller);
            HttpURLConnection connection = (HttpURLConnection) realUrl.openConnection();

            connection.setDoOutput(true);
            connection.setDoInput(true);
            connection.setUseCaches(false);
            connection.setInstanceFollowRedirects(true);
            connection.setRequestMethod("POST"); // 设置请求方式
            connection.setRequestProperty("Connection", "Keep-Alive");
            connection.setRequestProperty("Content-Type", "binary/octet-stream");
            connection.connect();

            /*out = connection.getOutputStream();

            // 发送请求参数，防止中文乱码
            out.write(data);
            // flush输出流的缓冲
            out.flush();*/

            // 定义BufferedReader输入流来读取URL的响应
            in = new BufferedReader(new InputStreamReader(connection.getInputStream()));

            String line;
            while ((line = in.readLine()) != null) {
                result.append(line);
            }

        } catch (Exception e) {
            System.out.println("发送 POST 请求出现异常！");
            e.printStackTrace();
        } finally {
            try {
                if (out != null) {
                    out.close();
                }
                if (in != null) {
                    in.close();
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }

        return result.toString();
    }
    /*private List<Fight.fight1.Builder> sendList(byte[] data, String controller) {
        OutputStream out = null;

        ObjectInputStream in = null;
        StringBuilder result = new StringBuilder();
        List<Fight.fight1.Builder> list=null;
        try {
            URL realUrl = new URL(url + controller);
            HttpURLConnection connection = (HttpURLConnection) realUrl.openConnection();

            connection.setDoOutput(true);
            connection.setDoInput(true);
            connection.setUseCaches(false);
            connection.setInstanceFollowRedirects(true);
            connection.setRequestMethod("POST"); // 设置请求方式
            connection.setRequestProperty("Connection", "Keep-Alive");
            connection.setRequestProperty("Content-Type", "binary/octet-stream");
            connection.connect();
            out = connection.getOutputStream();

            out.write(data);
            out.flush();
            in = new ObjectInputStream(connection.getInputStream());
            list = (List<Fight.fight1.Builder>) in.readObject();

            *//*String line;
            while ((line = in.readLine()) != null) {
                result.append(line);
            }*//*

        } catch (Exception e) {
            System.out.println("发送 POST 请求出现异常！");
            e.printStackTrace();
        } finally {
            try {
                if (out != null) {
                    out.close();
                }
                if (in != null) {
                    in.close();
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }

        return list;
    }
*/
}
