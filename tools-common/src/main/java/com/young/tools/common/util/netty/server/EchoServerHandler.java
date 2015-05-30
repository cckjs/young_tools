package com.young.tools.common.util.netty.server;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;

public class EchoServerHandler extends ChannelInboundHandlerAdapter{
	
	 private static final Logger logger = Logger.getLogger(
	            EchoServerHandler.class.getName());

	 //接收到消息以后调用
	    @Override
	    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
	    	 ByteBuf buf = (ByteBuf) msg;
	    	 byte[] bytes = new byte[buf.readableBytes()];
	    	 for(int i=0;i<buf.readableBytes();i++){
	    		 bytes[i] = buf.readByte();
	    	 }
	    	System.out.println("server read msg=["+new String(bytes)+"]");
	        ctx.write(msg);
	    }

	    @Override
	    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
	        ctx.flush();
	    }

	    @Override
	    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
	        // Close the connection when an exception is raised.
	        logger.log(Level.WARN, "Unexpected exception from downstream.", cause);
	        cause.printStackTrace();
	        ctx.close();
	    }
    
}
