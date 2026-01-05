package com.windtunnel.common;

/**
 * 系统常量类
 * 
 * 定义系统中使用的各种常量值
 * 
 * @author windtunnel team
 * @version 1.0.0
 * @since 2024-01-01
 */
public class Constants {

    /**
     * 用户状态
     */
    public static class UserStatus {
        public static final Integer ENABLED = 1;   // 启用
        public static final Integer DISABLED = 0;  // 禁用
    }

    /**
     * 实验室状态
     */
    public static class LaboratoryStatus {
        public static final Integer ENABLED = 1;   // 启用
        public static final Integer DISABLED = 0;  // 禁用
    }

    /**
     * 试验项目状态
     */
    public static class ExperimentStatus {
        public static final Integer DRAFT = 0;        // 草稿
        public static final Integer PENDING_APPROVAL = 1;  // 待审批
        public static final Integer APPROVED = 2;     // 已审批
        public static final Integer IN_PROGRESS = 3;  // 进行中
        public static final Integer COMPLETED = 4;    // 已完成
        public static final Integer CANCELLED = 5;    // 已取消
    }

    /**
     * 设备状态
     */
    public static class EquipmentStatus {
        public static final Integer INACTIVE = 0;     // 停用
        public static final Integer RUNNING = 1;      // 运行中
        public static final Integer MAINTENANCE = 2;  // 维护中
        public static final Integer FAULT = 3;        // 故障
    }

    /**
     * 数据状态
     */
    public static class DataStatus {
        public static final Integer NORMAL = 0;       // 正常
        public static final Integer ABNORMAL = 1;     // 异常
        public static final Integer FAULT = 2;        // 故障
    }

    /**
     * 风险等级
     */
    public static class RiskLevel {
        public static final Integer GENERAL = 1;      // 一般
        public static final Integer SERIOUS = 2;      // 较重
        public static final Integer SEVERE = 3;       // 严重
    }

    /**
     * 系统默认值
     */
    public static class DefaultValues {
        public static final String DEFAULT_PASSWORD = "123456";  // 默认密码
        public static final Integer DEFAULT_PAGE_SIZE = 10;      // 默认页面大小
        public static final Integer MAX_FILE_SIZE = 209715200;   // 最大文件大小 200MB
    }

    /**
     * Redis缓存前缀
     */
    public static class CachePrefix {
        public static final String USER_PREFIX = "user:";        // 用户缓存前缀
        public static final String EQUIPMENT_PREFIX = "equipment:"; // 设备缓存前缀
        public static final String EXPERIMENT_PREFIX = "experiment:"; // 试验缓存前缀
        public static final String REAL_TIME_DATA_PREFIX = "realtime:"; // 实时数据缓存前缀
    }

    /**
     * 响应码
     */
    public static class ResponseCode {
        public static final Integer SUCCESS = 200;    // 成功
        public static final Integer ERROR = 500;      // 错误
        public static final Integer UNAUTHORIZED = 401; // 未授权
        public static final Integer FORBIDDEN = 403;  // 禁止访问
        public static final Integer NOT_FOUND = 404;  // 未找到
    }

}