package net.dyeo.xpdiary.network

import io.netty.buffer.ByteBuf
import net.minecraftforge.fml.common.network.simpleimpl.IMessage

class EmptyMessage : IMessage
{
    override fun toBytes(buf: ByteBuf?)
    {
    }
    override fun fromBytes(buf: ByteBuf?)
    {
    }
}