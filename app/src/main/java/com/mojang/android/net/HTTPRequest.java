package com.mojang.android.net;

public class HTTPRequest {
    public HTTPRequest() {}

    public void abort() {}
    public HTTPResponse send(String s) { return new HTTPResponse(HTTPResponse.STATUS_SUCCESS,
            500, ""); }
    public void setContentType(String s) {}
    public void setCookieData(String s) {}
    public void setRequestBody(String s) {}
    public void setURL(String s) {}

}
