package com.googlecode.easyec.modules.bpmn2.command.persistence.entity;

import java.nio.charset.Charset;

import static java.nio.charset.Charset.defaultCharset;
import static java.nio.charset.Charset.forName;

/**
 * 扩展ACTIVITI的备注实现
 *
 * @author JunJie
 */
public class CommentEntity extends org.activiti.engine.impl.persistence.entity.CommentEntity {

    private static final long serialVersionUID = -3783108359489621890L;
    private Charset charset = defaultCharset();

    @Override
    public byte[] getFullMessageBytes() {
        return (fullMessage != null ? fullMessage.getBytes(forName("utf-8")) : null);
    }

    @Override
    public void setFullMessageBytes(byte[] fullMessageBytes) {
        if (fullMessageBytes != null) {
            this.fullMessage = _transferFullMsg(fullMessageBytes,
                new Charset[] {
                    forName("utf-8"),
                    forName("gbk"),
                    forName("gb18030"),
                    forName("gb2312")
                });
        }
    }

    /**
     * 判断给定的字符串是否有乱码
     *
     * @param str 字符串对象
     */
    protected boolean hasMessyCode(String str) {
        float badCodeCount = 0;
        char[] array = str.toCharArray();
        for (char c : array) {
            if (!Character.isLetterOrDigit(c) && !_isChinese(c)) {
                badCodeCount++;
            }
        }

        return (badCodeCount / (float) array.length) > 0.4;
    }

    private String _transferFullMsg(byte[] fullMsgBytes, Charset[] charsets) {
        for (Charset charset : charsets) {
            String s = new String(fullMsgBytes, charset);
            if (!hasMessyCode(s)) {
                this.charset = charset;
                return s;
            }
        }

        return new String(fullMsgBytes, charset);
    }

    /* 判断是否是正常的中文字符 */
    private boolean _isChinese(char c) {
        Character.UnicodeBlock block = Character.UnicodeBlock.of(c);
        return (
            block == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS ||
                block == Character.UnicodeBlock.CJK_COMPATIBILITY_IDEOGRAPHS ||
                block == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_A ||
                block == Character.UnicodeBlock.GENERAL_PUNCTUATION ||
                block == Character.UnicodeBlock.CJK_SYMBOLS_AND_PUNCTUATION ||
                block == Character.UnicodeBlock.HALFWIDTH_AND_FULLWIDTH_FORMS
        );
    }
}
