package io.github.btpka3.first.jvm.sandbox;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author dangqian.zll
 * @date 2024/10/21
 */
@Data
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
public class MyClassLoaderStatInfo implements Serializable {
    private static final long serialVersionUID = 1L;
    private int classLoaderHash;
    private String classLoaderClassName;
    private String classLoaderName;
    //private String methodName;

    /**
     * 调用的次数
     */
    private int count;
    /**
     * 调用的总耗时（纳秒）
     *
     * @see System#nanoTime()
     */
    private long rt;


}
