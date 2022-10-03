package com.todoay.api.domain.auth.utility;

public interface MailTextCreator {

    static String createMailText(String domain, String path, String emailToken,String message) {
        StringBuilder sb = createCommonMailText();
        sb.append(domain)
                .append(path)
                .append(emailToken)
                .append("'>")
                .append(message)
                .append("</a>");
        return sb.toString();
    }
    static StringBuilder createCommonMailText() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("<a href='")
                .append("http://");
        return stringBuilder;
    }
}
