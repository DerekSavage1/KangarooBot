package me.ChewyN.Minecraft.Packets;

import io.netty.channel.*;
import org.bukkit.craftbukkit.v1_16_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;

public class packet {

    public static void injectPlayer(Player p) {
        ChannelDuplexHandler channelDuplexHandler = new ChannelDuplexHandler(){

            @Override
            public void channelRead(ChannelHandlerContext context, Object packet) throws Exception {
                //on packet read

                super.channelRead(context, packet);
            }

            @Override
            public void write(ChannelHandlerContext context, Object packet, ChannelPromise promise) throws Exception {
                //on packet write

                super.write(context, packet, promise);
            }
        };

        ChannelPipeline pipeline = ((CraftPlayer) p).getHandle().playerConnection.networkManager.channel.pipeline();
        pipeline.addBefore("packet_handler", p.getName(), channelDuplexHandler);
    }

    public static void removePlayer(Player p) {
        Channel channel = ((CraftPlayer) p).getHandle().playerConnection.networkManager.channel;
        channel.eventLoop().submit(() -> {
            channel.pipeline().remove(p.getName());
            return null;
        });
    }


}
