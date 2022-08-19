package com.task.gzip;

import com.task.entity1.Player;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

public class Gzip {
    /**
     * 字节流gzip压缩
     * @param data
     * @return
     */
    public static byte[] gZip(byte[] data) {
        byte[] b = null;
        try {
            ByteArrayInputStream in = new ByteArrayInputStream(data);
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            GZIPOutputStream gzip = new GZIPOutputStream(out);
            byte[] buffer = new byte[4096];
            int n = 0;
            while((n = in.read(buffer, 0, buffer.length)) > 0){
                gzip.write(buffer, 0, n);
            }
            gzip.close();
            in.close();
            b = out.toByteArray();
            out.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return b;
    }
    /**
     * gzip解压
     * @param data
     * @return
     */
    public static byte[] unGZip(byte[] data){
        // 创建一个新的输出流
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        try {
            ByteArrayInputStream in = new ByteArrayInputStream(data);
            GZIPInputStream gzip = new GZIPInputStream(in);
            byte[] buffer = new byte[4096];
            int n = 0;
            // 将解压后的数据写入输出流
            while ((n = gzip.read(buffer)) >= 0) {
                out.write(buffer, 0, n);
            }
            in.close();
            gzip.close();
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return out.toByteArray();
    }

    public static byte[] getList(List list) throws IOException {
        ByteArrayOutputStream bos=new ByteArrayOutputStream();
        ObjectOutputStream oos=new ObjectOutputStream(bos);
        oos.writeObject(list);
        byte[] bytes = bos.toByteArray();
        oos.close();
        bos.close();
        return bytes;
    }
    public static List<Player> getByte(byte [] bytes) throws IOException, ClassNotFoundException {
        ByteArrayInputStream bi = new ByteArrayInputStream(bytes);
        ObjectInputStream oi = new ObjectInputStream(bi);
        List<Player> list = castList(oi.readObject(), Player.class);
        bi.close();
        oi.close();
        return list;
    }
    public static  <T> List<T> castList(Object obj, Class<T> c)
    {
        List<T> result = new ArrayList<T>();
        if(obj instanceof List<?>)
        {
            for (Object o : (List<?>) obj)
            {
                result.add(c.cast(o));
            }
            return result;
        }
        return null;
    }


}
