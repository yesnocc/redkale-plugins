/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.redkalex.pay;

import java.io.File;
import java.nio.charset.Charset;
import java.util.*;
import java.util.logging.*;
import java.util.stream.Collectors;
import org.redkale.convert.json.JsonConvert;
import org.redkale.service.*;
import org.redkale.util.Comment;

/**
 * 支付抽象类
 *
 * 详情见: https://redkale.org
 *
 * @author zhangjx
 */
@Comment("支付服务抽象类")
abstract class AbstractPayService implements Service {

    protected static final Charset UTF8 = Charset.forName("UTF-8");

    protected final Logger logger = Logger.getLogger(this.getClass().getSimpleName());

    protected final boolean fine = logger.isLoggable(Level.FINE);

    protected final boolean finer = logger.isLoggable(Level.FINER);

    protected final boolean finest = logger.isLoggable(Level.FINEST);

    @Comment("手机预支付")
    public abstract PayPreResponse prepay(PayPreRequest request);

    @Comment("手机支付回调")
    public abstract PayNotifyResponse notify(PayNotifyRequest request);

    @Comment("请求支付")
    public abstract PayCreatResponse create(PayCreatRequest request);

    @Comment("请求查询")
    public abstract PayQueryResponse query(PayRequest request);

    @Comment("请求关闭")
    public abstract PayResponse close(PayCloseRequest request);

    @Comment("请求退款")
    public abstract PayRefundResponse refund(PayRefundRequest request);

    @Comment("查询退款")
    public abstract PayRefundResponse queryRefund(PayRequest request);

    @Comment("计算签名")
    protected abstract String createSign(final PayElement element, Map<String, ?> map) throws Exception;

    @Comment("验证签名")
    protected abstract boolean checkSign(final PayElement element, Map<String, ?> map);

    @Comment("map对象转换成 key1=value1&key2=value2&key3=value3")
    protected final String joinMap(Map<String, ?> map) {
        if (!(map instanceof SortedMap)) map = new TreeMap<>(map);
        return map.entrySet().stream().map((e -> e.getKey() + "=" + e.getValue())).collect(Collectors.joining("&"));
    }

    @Comment("支付配置信息抽象类")
    protected static abstract class PayElement {

        public abstract boolean initElement(Logger logger, File home);

        @Override
        public String toString() {
            return JsonConvert.root().convertTo(this);
        }
    }
}
