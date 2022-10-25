package com.emily.infrastructure.autoconfigure.exception.handler;

import com.emily.infrastructure.common.constant.AttributeInfo;
import com.emily.infrastructure.common.constant.HeaderInfo;
import com.emily.infrastructure.common.enums.DateFormat;
import com.emily.infrastructure.common.exception.BasicException;
import com.emily.infrastructure.common.exception.PrintExceptionInfo;
import com.emily.infrastructure.common.utils.RequestUtils;
import com.emily.infrastructure.common.utils.UUIDUtils;
import com.emily.infrastructure.common.utils.json.JSONUtils;
import com.emily.infrastructure.core.context.holder.ThreadContextHolder;
import com.emily.infrastructure.core.entity.BaseLogger;
import com.emily.infrastructure.core.helper.RequestHelper;
import com.emily.infrastructure.core.helper.SystemNumberHelper;
import com.emily.infrastructure.logger.LoggerFactory;
import org.slf4j.Logger;

import javax.servlet.http.HttpServletRequest;
import java.text.MessageFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

/**
 * @Description :  异常处理基础类
 * @Author : Emily
 * @CreateDate :  Created in 2022/7/8 1:43 下午
 */
public class GlobalExceptionCustomizer {

    private static final Logger logger = LoggerFactory.getLogger(DefaultGlobalExceptionHandler.class);

    /**
     * 获取异常堆栈信息并记录到error文件中
     */
    public static void recordErrorMsg(Throwable ex, HttpServletRequest request) {
        String errorMsg = PrintExceptionInfo.printErrorInfo(ex);
        if (ex instanceof BasicException) {
            BasicException systemException = (BasicException) ex;
            errorMsg = MessageFormat.format("业务异常，异常码是【{0}】，异常消息是【{1}】，异常详情{2}", systemException.getStatus(), systemException.getMessage(), errorMsg);
        }
        logger.error(errorMsg);
        //记录错误日志
        recordErrorLogger(request, errorMsg);
    }

    /**
     * 记录错误日志
     *
     * @param request
     * @param errorMsg
     */
    private static void recordErrorLogger(HttpServletRequest request, String errorMsg) {
        if (Objects.isNull(request)) {
            return;
        }
        if (Objects.nonNull(request.getAttribute(AttributeInfo.STAGE))) {
            return;
        }
        try {
            //事务流水号
            String traceId = request.getHeader(HeaderInfo.TRACE_ID) == null ? UUIDUtils.randomSimpleUUID() : request.getHeader(HeaderInfo.TRACE_ID);

            BaseLogger baseLogger = new BaseLogger();
            //系统编号
            baseLogger.setSystemNumber(SystemNumberHelper.getSystemNumber());
            //事务唯一编号
            baseLogger.setTraceId(traceId);
            //请求URL
            baseLogger.setUrl(request.getRequestURI());
            //客户端IP
            baseLogger.setClientIp(RequestUtils.getClientIp());
            //服务端IP
            baseLogger.setServerIp(RequestUtils.getServerIp());
            //版本类型
            baseLogger.setAppType(ThreadContextHolder.peek().getAppType());
            //版本号
            baseLogger.setAppVersion(ThreadContextHolder.peek().getAppVersion());
            //触发时间
            baseLogger.setTriggerTime(LocalDateTime.now().format(DateTimeFormatter.ofPattern(DateFormat.YYYY_MM_DD_HH_MM_SS_SSS.getFormat())));
            //请求参数
            baseLogger.setRequestParams(RequestHelper.getApiArgs(request));
            //响应体
            baseLogger.setBody(errorMsg);
            //耗时(未处理任何逻辑)
            baseLogger.setTime(0L);
            //记录日志到文件
            logger.info(JSONUtils.toJSONString(baseLogger));
        } catch (Exception exception) {
            logger.error(MessageFormat.format("记录错误日志异常：{0}", PrintExceptionInfo.printErrorInfo(exception)));
        } finally {
            //由于获取参数中会初始化上下文，清除防止OOM
            ThreadContextHolder.unbind(true);
        }
    }
}
