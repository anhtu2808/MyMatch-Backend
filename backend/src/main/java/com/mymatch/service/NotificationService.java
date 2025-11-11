package com.mymatch.service;

import java.util.Map;

import com.mymatch.dto.response.email.EmailResponse;
import com.mymatch.enums.EmailType;

public interface NotificationService {
    EmailResponse send(EmailType type, String toName, String toEmail, Map<String, Object> model);
}
