package com.mskim.demo.base.service;

import com.mskim.demo.base.model.VuejsException;
import com.mskim.demo.base.model.VuejsExceptionType;
import org.springframework.stereotype.Service;

@Service
public class DemoService {

    public void abd() {
        throw new VuejsException(VuejsExceptionType.server_error);
    }
}
