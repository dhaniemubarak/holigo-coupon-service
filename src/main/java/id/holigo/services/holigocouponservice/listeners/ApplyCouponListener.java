package id.holigo.services.holigocouponservice.listeners;

import id.holigo.services.common.model.ApplyCouponDto;
import id.holigo.services.holigocouponservice.config.JmsConfig;
import id.holigo.services.holigocouponservice.services.CouponService;
import id.holigo.services.holigocouponservice.web.exceptions.CouponNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import javax.jms.JMSException;
import javax.jms.Message;

@Component
public class ApplyCouponListener {

    private JmsTemplate jmsTemplate;

    private CouponService couponService;

    @Autowired
    public void setCouponService(CouponService couponService) {
        this.couponService = couponService;
    }

    @Autowired
    public void setJmsTemplate(JmsTemplate jmsTemplate) {
        this.jmsTemplate = jmsTemplate;
    }

    @JmsListener(destination = JmsConfig.CREATE_APPLY_COUPON)
    public void listenForCreateApplyCoupon(@Payload ApplyCouponDto applyCouponDto, @Headers MessageHeaders headers, Message message) throws JMSException {
        try {
            applyCouponDto = couponService.createApplyCoupon(applyCouponDto);
        } catch (CouponNotFoundException e) {
            applyCouponDto.setIsValid(false);
            applyCouponDto.setMessage(e.getMessage());
        }
        jmsTemplate.convertAndSend(message.getJMSReplyTo(), applyCouponDto);
    }
}
