/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

package org.apache.rocketmq.jms.msg;

import com.google.common.collect.Maps;
import com.google.common.io.BaseEncoding;
import java.io.Serializable;
import java.util.Enumeration;
import java.util.Map;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MessageNotWriteableException;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.rocketmq.jms.Constant;
import org.apache.rocketmq.jms.support.JmsHelper;
import org.apache.rocketmq.jms.support.TypeConverter;

//todo: add unit test after finishing JMS Properties
public class RocketMQMessage implements javax.jms.Message {

    protected Map<String, Object> properties = Maps.newHashMap();
    protected Map<String, Object> headers = Maps.newHashMap();
    protected Serializable body;

    protected boolean writeOnly;

    @Override
    public String getJMSMessageID() {
        return TypeConverter.convert2String(headers.get(Constant.JMS_MESSAGE_ID));
    }

    /**
     * Sets the message ID.
     * <p/>
     * <P>JMS providers set this field when a message is sent. Do not allow User to set the message ID by yourself.
     *
     * @param id the ID of the message
     * @see javax.jms.Message#getJMSMessageID()
     */

    @Override
    public void setJMSMessageID(String id) {
        JmsHelper.handleUnSupportedException();
    }

    @Override
    public long getJMSTimestamp() {
        if (headers.containsKey(Constant.JMS_TIMESTAMP)) {
            return TypeConverter.convert2Long(headers.get(Constant.JMS_TIMESTAMP));
        }
        return 0;
    }

    @Override
    public void setJMSTimestamp(long timestamp) {
        JmsHelper.handleUnSupportedException();
    }

    @Override
    public byte[] getJMSCorrelationIDAsBytes() {
        String jmsCorrelationID = getJMSCorrelationID();
        if (jmsCorrelationID != null) {
            try {
                return BaseEncoding.base64().decode(jmsCorrelationID);
            }
            catch (Exception e) {
                return jmsCorrelationID.getBytes();
            }
        }
        return null;
    }

    @Override
    public void setJMSCorrelationIDAsBytes(byte[] correlationID) {
        String encodedText = BaseEncoding.base64().encode(correlationID);
        setJMSCorrelationID(encodedText);
    }

    @Override
    public String getJMSCorrelationID() {
        if (headers.containsKey(Constant.JMS_CORRELATION_ID)) {
            return TypeConverter.convert2String(headers.get(Constant.JMS_CORRELATION_ID));
        }
        return null;
    }

    @Override
    public void setJMSCorrelationID(String correlationID) {
        JmsHelper.handleUnSupportedException();
    }

    @Override
    public Destination getJMSReplyTo() {
        if (headers.containsKey(Constant.JMS_REPLY_TO)) {
            return TypeConverter.convert2Object(headers.get(Constant.JMS_REPLY_TO), Destination.class);
        }
        return null;
    }

    @Override
    public void setJMSReplyTo(Destination replyTo) {
        JmsHelper.handleUnSupportedException();
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

    @Override
    public Destination getJMSDestination() {
        if (headers.containsKey(Constant.JMS_DESTINATION)) {
            return TypeConverter.convert2Object(headers.get(Constant.JMS_DESTINATION), Destination.class);
        }
        return null;
    }

    @Override
    public void setJMSDestination(Destination destination) {
        JmsHelper.handleUnSupportedException();
    }

    @SuppressWarnings("unchecked")
    public <T> T getBody(Class<T> clazz) throws JMSException {
        if (clazz.isInstance(body)) {
            return TypeConverter.convert2Object(body, clazz);
        }
        else {
            throw new IllegalArgumentException("The class " + clazz
                + " is unknown to this implementation");
        }
    }

    @Override
    public int getJMSDeliveryMode() {
        if (headers.containsKey(Constant.JMS_DELIVERY_MODE)) {
            return TypeConverter.convert2Integer(headers.get(Constant.JMS_DELIVERY_MODE));
        }
        return 0;
    }

    /**
     * Sets the <CODE>DeliveryMode</CODE> value for this message.
     * <p/>
     * <P>JMS providers set this field when a message is sent. ONS only support DeliveryMode.PERSISTENT mode. So do not
     * allow User to set this by yourself, but you can get the default mode by <CODE>getJMSDeliveryMode</CODE> method.
     *
     * @param deliveryMode the delivery mode for this message
     * @see javax.jms.Message#getJMSDeliveryMode()
     * @see javax.jms.DeliveryMode
     */

    @Override
    public void setJMSDeliveryMode(int deliveryMode) {
        JmsHelper.handleUnSupportedException();
    }

    @Override
    public boolean getJMSRedelivered() {
        return headers.containsKey(Constant.JMS_REDELIVERED)
            && TypeConverter.convert2Boolean(headers.get(Constant.JMS_REDELIVERED));
    }

    @Override
    public void setJMSRedelivered(boolean redelivered) {
        JmsHelper.handleUnSupportedException();
    }

    @Override
    public String getJMSType() {
        return TypeConverter.convert2String(headers.get(Constant.JMS_TYPE));
    }

    @Override
    public void setJMSType(String type) {
        JmsHelper.handleUnSupportedException();
    }

    public Map<String, Object> getHeaders() {
        return this.headers;
    }

    @Override
    public long getJMSExpiration() {
        if (headers.containsKey(Constant.JMS_EXPIRATION)) {
            return TypeConverter.convert2Long(headers.get(Constant.JMS_EXPIRATION));
        }
        return 0;
    }

    @Override
    public void setJMSExpiration(long expiration) {
        JmsHelper.handleUnSupportedException();
    }

    public boolean headerExits(String name) {
        return this.headers.containsKey(name);
    }

    @Override
    public int getJMSPriority() {
        if (headers.containsKey(Constant.JMS_PRIORITY)) {
            return TypeConverter.convert2Integer(headers.get(Constant.JMS_PRIORITY));
        }
        return 5;
    }

    @Override
    public void setJMSPriority(int priority) {
        JmsHelper.handleUnSupportedException();
    }

    public void setHeader(String name, Object value) {
        this.headers.put(name, value);
    }

    public Map<String, Object> getProperties() {
        return this.properties;
    }

    public void setProperties(Map<String, Object> properties) {
        this.properties = properties;
    }

    @Override
    public void acknowledge() throws JMSException {
        throw new UnsupportedOperationException("Unsupported!");
    }

    @Override
    public void clearProperties() {
        this.properties.clear();
    }

    @Override
    public void clearBody() {
        this.body = null;
        this.writeOnly = true;
    }

    @Override
    public boolean propertyExists(String name) {
        return properties.containsKey(name);
    }

    @Override
    public boolean getBooleanProperty(String name) throws JMSException {
        if (propertyExists(name)) {
            Object value = getObjectProperty(name);
            return Boolean.valueOf(value.toString());
        }
        return false;
    }

    @Override
    public byte getByteProperty(String name) throws JMSException {
        if (propertyExists(name)) {
            Object value = getObjectProperty(name);
            return Byte.valueOf(value.toString());
        }
        return 0;
    }

    @Override
    public short getShortProperty(String name) throws JMSException {
        if (propertyExists(name)) {
            Object value = getObjectProperty(name);
            return Short.valueOf(value.toString());
        }
        return 0;
    }

    @Override
    public int getIntProperty(String name) throws JMSException {
        if (propertyExists(name)) {
            Object value = getObjectProperty(name);
            return Integer.valueOf(value.toString());
        }
        return 0;
    }

    @Override
    public long getLongProperty(String name) throws JMSException {
        if (propertyExists(name)) {
            Object value = getObjectProperty(name);
            return Long.valueOf(value.toString());
        }
        return 0L;
    }

    @Override
    public float getFloatProperty(String name) throws JMSException {
        if (propertyExists(name)) {
            Object value = getObjectProperty(name);
            return Float.valueOf(value.toString());
        }
        return 0f;
    }

    @Override
    public double getDoubleProperty(String name) throws JMSException {
        if (propertyExists(name)) {
            Object value = getObjectProperty(name);
            return Double.valueOf(value.toString());
        }
        return 0d;
    }

    @Override
    public String getStringProperty(String name) throws JMSException {
        if (propertyExists(name)) {
            return getObjectProperty(name).toString();
        }
        return null;
    }

    @Override
    public Object getObjectProperty(String name) throws JMSException {
        return this.properties.get(name);
    }

    @Override
    public Enumeration<?> getPropertyNames() throws JMSException {
        final Object[] keys = this.properties.keySet().toArray();
        return new Enumeration<Object>() {
            int i;

            @Override
            public boolean hasMoreElements() {
                return i < keys.length;
            }

            @Override
            public Object nextElement() {
                return keys[i++];
            }
        };
    }

    @Override
    public void setBooleanProperty(String name, boolean value) {
        setObjectProperty(name, value);
    }

    @Override
    public void setByteProperty(String name, byte value) {
        setObjectProperty(name, value);
    }

    @Override
    public void setShortProperty(String name, short value) {
        setObjectProperty(name, value);
    }

    @Override
    public void setIntProperty(String name, int value) {
        setObjectProperty(name, value);
    }

    @Override
    public void setLongProperty(String name, long value) {
        setObjectProperty(name, value);
    }

    public void setFloatProperty(String name, float value) {
        setObjectProperty(name, value);
    }

    @Override
    public void setDoubleProperty(String name, double value) {
        setObjectProperty(name, value);
    }

    @Override
    public void setStringProperty(String name, String value) {
        setObjectProperty(name, value);
    }

    @Override
    public long getJMSDeliveryTime() throws JMSException {
        // todo
        return 0;
    }

    @Override
    public void setJMSDeliveryTime(long deliveryTime) throws JMSException {
        // todo
    }

    @Override
    public boolean isBodyAssignableTo(Class c) throws JMSException {
        return c.isInstance(body);
    }

    @Override
    public void setObjectProperty(String name, Object value) {
        if (value instanceof Number || value instanceof String || value instanceof Boolean) {
            this.properties.put(name, value);
        }
        else {
            throw new IllegalArgumentException(
                "Value should be boolean, byte, short, int, long, float, double, and String.");
        }
    }

    protected boolean isWriteOnly() {
        return writeOnly;
    }

    protected void checkIsWriteOnly() throws MessageNotWriteableException {
        if (!writeOnly) {
            throw new MessageNotWriteableException("Not writable");
        }
    }

}
