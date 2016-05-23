package MoreResource.dao.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.text.DecimalFormat;

/** 
* @ClassName: ShardDsScript 
* @Description: 
* @author LUCKY
* @date 2016年5月19日 下午4:06:26 
*  
*/
public class ShardDsScript {

    public static void main(String[] args) throws IOException {

        File file = new File(
            "/Users/darbean/Documents/workspace/jdb/ordercore-branch-11-13-zk/ordercore-dao/src/main/resources/configs/Shard/Shard-ds.xml");
        if (!file.exists()) {
            file.createNewFile();
        }
        FileWriter fw = new FileWriter(file);

        String line1, line;

        for (int i = 0; i < 64; i++) {
            line1 = "<ref bean=\"physics_" + new DecimalFormat("00").format(i) + "\" />";
            fw.write(line1);
            fw.write("\n");
        }
        fw.write("\n");

        for (int i = 0; i < 64; i++) {
            InputStream tableIs = new FileInputStream(
                "/Users/darbean/Documents/workspace/jdb/ordercore-branch-11-13-zk/ordercore-dao/src/main/resources/configs/Shard/Shard-ds-demo.xml");
            InputStreamReader tableIsr = new InputStreamReader(tableIs, Charset.forName("utf8"));
            BufferedReader tableBr = new BufferedReader(tableIsr);
            while ((line = tableBr.readLine()) != null) {
                if (line.contains("com.alipay.zdal.client.config.bean.PhysicalDataSourceBean")) {
                    line = line.replace("physics", "physics_" + new DecimalFormat("00").format(i));
                }
                if (line.contains("<property name=\"name\"")) {
                    line = line.replace("master", "master_" + i);
                }
                if (line.contains("<property name=\"jdbcUrl\" value")) {
                    line = line.replace("30006/jdb", "30006/jdb_" + new DecimalFormat("00").format(i));
                }
                fw.write(line);
                fw.write("\n");
            }

            fw.write("\n");
        }

        fw.close();
    }
}
