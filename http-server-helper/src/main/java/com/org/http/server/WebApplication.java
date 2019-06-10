package com.org.http.server;

import com.sise.itree.ITreeApplication;

/**
 * com.site.itree git Address  https://gitee.com/IdeaHome_admin/ITree.git
 */
public class WebApplication {
    public static void main(String[] args) throws InstantiationException, IllegalAccessException {
        ITreeApplication.start(WebApplication.class);
    }
}
