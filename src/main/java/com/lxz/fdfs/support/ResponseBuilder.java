package com.lxz.fdfs.support;

/**
 * Created by xiaolezheng on 16/5/20.
 */
public class ResponseBuilder{
    public interface Status{
        int SUCCESS = 0;
        int FAIL = 1;
    }

    private ResponseBuilder(){
    }

    public static Response createSuccResponse(){
        return new Response(Status.SUCCESS);
    }

    public static Response createFailResponse(){
        return new Response(Status.FAIL);
    }

    public static class Response<T>{
        private int status;
        private T data;
        private String message;

        private Response(int status){
            this.status = status;
        }

        public int getStatus() {
            return status;
        }

        private Response setStatus(int status) {
            this.status = status;
            return this;
        }

        public T getData() {
            return data;
        }

        public Response setData(T data) {
            this.data = data;
            return this;
        }

        public String getMessage() {
            return message;
        }

        public Response setMessage(String message) {
            this.message = message;
            return this;
        }
    }
}
