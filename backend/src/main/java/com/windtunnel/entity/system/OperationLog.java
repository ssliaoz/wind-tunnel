package com.windtunnel.entity.system;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.windtunnel.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

/**
 * 操作日志实体类
 * 
 * 存储系统操作日志信息
 * 
 * @author windtunnel team
 * @version 1.0.0
 * @since 2024-01-01
 */
@EqualsAndHashCode(callSuper = true)
@Data
@TableName("operation_log")
public class OperationLog extends BaseEntity {

    /**
     * 主键ID
     */
    private Long id;

    /**
     * 操作用户ID
     */
    @TableField("user_id")
    private Long userId;

    /**
     * 操作用户姓名
     */
    @TableField("user_name")
    private String userName;

    /**
     * 操作类型（1-新增，2-修改，3-删除，4-查询，5-登录，6-登出，7-其他）
     */
    @TableField("operation_type")
    private Integer operationType;

    /**
     * 操作模块
     */
    @TableField("module")
    private String module;

    /**
     * 操作方法
     */
    @TableField("method")
    private String method;

    /**
     * 操作描述
     */
    @TableField("description")
    private String description;

    /**
     * 操作IP地址
     */
    @TableField("ip_address")
    private String ipAddress;

    /**
     * 用户代理
     */
    @TableField("user_agent")
    private String userAgent;

    /**
     * 请求URL
     */
    @TableField("request_url")
    private String requestUrl;

    /**
     * 请求方法（GET, POST, PUT, DELETE等）
     */
    @TableField("request_method")
    private String requestMethod;

    /**
     * 请求参数
     */
    @TableField("request_params")
    private String requestParams;

    /**
     * 响应结果
     */
    @TableField("response_result")
    private String responseResult;

    /**
     * 响应状态码
     */
    @TableField("response_code")
    private Integer responseCode;

    /**
     * 操作耗时（毫秒）
     */
    @TableField("execution_time")
    private Long executionTime;

    /**
     * 操作状态（0-失败，1-成功）
     */
    @TableField("status")
    private Integer status;

    /**
     * 错误信息
     */
    @TableField("error_message")
    private String errorMessage;

    /**
     * 操作时间
     */
    @TableField("operation_time")
    private LocalDateTime operationTime;

    /**
     * 实验室ID（用于数据权限控制）
     */
    @TableField("laboratory_id")
    private Long laboratoryId;

    /**
     * 备注
     */
    @TableField("remark")
    private String remark;

}