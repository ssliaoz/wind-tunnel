package com.windtunnel.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Map;

/**
 * 实时数据实体类
 * 
 * 用于存储从CWT1 PC、CWT2 PC、CWT3 PC、AAWT PC、公共动力系统PC接口获取的实时数据
 * 
 * @author windtunnel team
 * @version 1.0.0
 * @since 2024-01-01
 */
@Data
@Document(collection = "real_time_data")
public class RealTimeData {

    /**
     * 主键ID
     */
    @Id
    private String id;

    /**
     * 数据来源（CWT1_PC、CWT2_PC、CWT3_PC、AAWT_PC、PUBLIC_POWER_SYSTEM_PC）
     */
    @Indexed
    private String source;

    /**
     * 设备ID
     */
    @Indexed
    private Long equipmentId;

    /**
     * 数据时间戳
     */
    @Indexed
    private LocalDateTime dataTime;

    /**
     * 数据内容（JSON格式存储具体参数值）
     */
    private Map<String, Object> dataContent;

    /**
     * 风速值
     */
    private BigDecimal windSpeed;

    /**
     * 温度值
     */
    private BigDecimal temperature;

    /**
     * 压力值
     */
    private BigDecimal pressure;

    /**
     * 流量值
     */
    private BigDecimal flow;

    /**
     * 功率值
     */
    private BigDecimal power;

    /**
     * 振动值
     */
    private BigDecimal vibration;

    /**
     * 电压值
     */
    private BigDecimal voltage;

    /**
     * 电流值
     */
    private BigDecimal current;

    /**
     * 其他参数（JSON格式）
     */
    private String otherParams;

    /**
     * 实验室ID（用于数据权限控制）
     */
    @Indexed
    private Long laboratoryId;

    /**
     * 数据状态（0-正常，1-异常，2-故障）
     */
    @Indexed
    private Integer status;

    /**
     * 风险等级（1-一般，2-较重，3-严重）
     */
    @Indexed
    private Integer riskLevel;

    /**
     * 异常描述
     */
    private String anomalyDescription;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 构造函数
     */
    public RealTimeData() {
        this.createTime = LocalDateTime.now();
        this.dataTime = LocalDateTime.now();
    }

}