package com.jof.web.interceptor;

import java.util.Collections;
import java.util.Set;

import com.jof.common.JofConstant;
import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;
import com.opensymphony.xwork2.util.TextParseUtil;
import com.smrs.security.model.UserModel;

public class AuthorizationInterceptor extends AbstractInterceptor {
    private static final long serialVersionUID = -5140884040684756043L;
    
    protected Set<String> skipActions = Collections.emptySet();

    public void setSkipActions(String skipActions) {
        this.skipActions = TextParseUtil.commaDelimitedStringToSet(skipActions);
    }

    @Override
    public String intercept(ActionInvocation invocation) throws Exception {
        UserModel user = (UserModel)invocation.getInvocationContext().getSession().get(JofConstant.USER_SESSION_KEY);

        boolean isLogined = user != null;
        String action = invocation.getProxy().getActionName();
        if (isLogined || skipActions.contains(action)) {
            return invocation.invoke();
        } else {
            return Action.LOGIN;
        }
    }
}
