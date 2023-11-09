package me.test.first.spring.boot.web.service;

public class TimingItem {

    /**
     * Spring Bean 的名称
     */
    private String beanName;

    /**
     * Spring Bean 的 Class
     */
    private Class beanClass;

    /**
     * Spring Bean 的 hashCode
     */
    private Integer beanHashCode;

    /**
     * Spring Bean 的 开始初始化的时间
     */
    private Long startTime;

    /**
     * Spring Bean 的 初始化完成的时间
     */
    private Long endTime;

    public String getBeanName() {
        return beanName;
    }

    public void setBeanName(String beanName) {
        this.beanName = beanName;
    }

    public Class getBeanClass() {
        return beanClass;
    }

    public void setBeanClass(Class beanClass) {
        this.beanClass = beanClass;
    }

    public Integer getBeanHashCode() {
        return beanHashCode;
    }

    public void setBeanHashCode(Integer beanHashCode) {
        this.beanHashCode = beanHashCode;
    }

    public Long getStartTime() {
        return startTime;
    }

    public void setStartTime(Long startTime) {
        this.startTime = startTime;
    }

    public Long getEndTime() {
        return endTime;
    }

    public void setEndTime(Long endTime) {
        this.endTime = endTime;
    }
}
