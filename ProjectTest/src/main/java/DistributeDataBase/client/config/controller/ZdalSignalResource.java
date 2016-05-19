/**
 * 
 */
package DistributeDataBase.client.config.controller;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import DistributeDataBase.client.config.ZdalConfigListener;

/** 
* @ClassName: ZdalSignalResource 
* @Description: 
* @author LUCKY
* @date 2016年5月16日 下午5:16:44 
*  
*/
public class ZdalSignalResource {

    private static final String DRM_ATT_KEY_WEIGHT = "keyWeight";
    private static final Logger log                = LoggerFactory.getLogger("zdal-client-config");

    private ZdalConfigListener  configListener;
    private Lock                lock               = new ReentrantLock();

    private void registerZk() {
    }

    public ZdalSignalResource(ZdalConfigListener configListener) {
        this.configListener = configListener;
        registerZk();
    }

    public void updateResource(String key, Object value) {
        this.lock.lock();
        try {
            if (key.equalsIgnoreCase(DRM_ATT_KEY_WEIGHT)) {
                if ((value == null) || (StringUtils.isBlank(value.toString()))) {
                    log.warn("WARN ## the keyWeight is null,will ignore this drm pull");
                    return;
                }

                Map groupInfos = convertKeyWeights(value.toString());
                if ((groupInfos == null) || (groupInfos.isEmpty())) {
                    log.warn("WARN ## the pull keyWeights = " + value + " is invalidate");
                    return;
                }
                this.configListener.resetWeight(groupInfos);
            }
        } catch (Exception e) {
            log.error("ERROR ## ", e);
        } finally {
            this.lock.unlock();
        }
    }

    public void close() {

    }

    private Map<String, String> convertKeyWeights(String keyWeight) {
        String[] splits = keyWeight.split(";");
        Map results = new HashMap();
        for (int i = 0; i < splits.length; i++) {
            String tmp = splits[i];
            String[] groupInfos = tmp.split("=");
            results.put(groupInfos[0], groupInfos[1]);
        }

        return results;
    }
}
