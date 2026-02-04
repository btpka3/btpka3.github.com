package me.test.split.batch.deploy;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.StringUtils;

/**
 * @author dangqian.zll
 * @date 2023/11/23
 */
@Data
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
public class Machine {
    /**
     * 集群
     */
    private String cluster;
    /**
     * 单元
     */
    private String unit;
    /**
     * 分组
     */
    private String group;
    /**
     * 机房
     */
    private String room;
    /**
     * IP
     */
    private String ip;

    public String toString() {
        return StringUtils.trim(cluster)
                + "-" + StringUtils.trim(unit)
                + "-" + StringUtils.trim(group)
                + "-" + StringUtils.trim(room)
                + "-" + StringUtils.trim(ip);
    }
}
