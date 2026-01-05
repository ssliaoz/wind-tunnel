package com.windtunnel.config;

import com.windtunnel.tcp.TcpServerHandler;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.handler.timeout.IdleStateHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * TCP服务器配置类
 * 
 * 配置Netty TCP服务器，用于接收CWT1 PC、CWT2 PC、CWT3 PC、AAWT PC、公共动力系统PC的数据
 * 
 * @author windtunnel team
 * @version 1.0.0
 * @since 2024-01-01
 */
@Slf4j
@Configuration
public class TcpServerConfig {

    @Value("${wind-tunnel.data-collection.tcp.server-port:9090}")
    private int serverPort;

    @Value("${wind-tunnel.data-collection.tcp.read-timeout:30000}")
    private int readTimeout;

    @Value("${wind-tunnel.data-collection.tcp.write-timeout:30000}")
    private int writeTimeout;

    /**
     * 创建TCP服务器
     * 
     * @param tcpServerHandler TCP服务器处理器
     * @return ChannelFuture
     */
    @Bean
    public ChannelFuture tcpServer(TcpServerHandler tcpServerHandler) throws InterruptedException {
        // 配置服务端的NIO线程组
        EventLoopGroup bossGroup = new NioEventLoopGroup(1);
        EventLoopGroup workerGroup = new NioEventLoopGroup();

        try {
            ServerBootstrap b = new ServerBootstrap();
            b.group(bossGroup, workerGroup)
             .channel(NioServerSocketChannel.class)
             .option(ChannelOption.SO_BACKLOG, 100)
             .childOption(ChannelOption.SO_KEEPALIVE, true)
             .childOption(ChannelOption.TCP_NODELAY, true)
             .childHandler(new ChannelInitializer<SocketChannel>() {
                 @Override
                 public void initChannel(SocketChannel ch) throws Exception {
                     ChannelPipeline p = ch.pipeline();
                     // 添加心跳处理
                     p.addLast(new IdleStateHandler(readTimeout / 1000, writeTimeout / 1000, 0));
                     // 添加字符串解码器
                     p.addLast(new StringDecoder());
                     p.addLast(new StringEncoder());
                     // 添加自定义处理器
                     p.addLast(tcpServerHandler);
                 }
             });

            // 绑定端口，开始接收进来的连接
            ChannelFuture f = b.bind(serverPort).sync();
            log.info("TCP服务器启动成功，监听端口: {}", serverPort);
            
            // 等待服务器socket关闭
            f.channel().closeFuture();
            
            return f;
        } catch (Exception e) {
            log.error("TCP服务器启动失败", e);
            throw e;
        }
    }

}