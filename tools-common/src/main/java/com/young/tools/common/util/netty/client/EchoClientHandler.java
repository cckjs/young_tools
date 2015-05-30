package com.young.tools.common.util.netty.client;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.MessageToByteEncoder;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;

public class EchoClientHandler extends SimpleChannelInboundHandler<ByteBuf>{

	 private static final Logger logger = Logger.getLogger(
	            EchoClientHandler.class.getName());

	    /**
	     * Creates a client-side handler.
	     */

	    @Override
	    public void channelActive(ChannelHandlerContext ctx) {
	    	ctx.writeAndFlush(ByteBufAllocator.DEFAULT.buffer(256).writeBytes(("test_"+System.currentTimeMillis()).getBytes()));
	    }

	    @Override
	    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
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
	        ctx.close();
	    }

		@Override
		protected void channelRead0(ChannelHandlerContext ctx, ByteBuf msg)
				throws Exception {
			  ctx.write(msg);
			
		}
}
