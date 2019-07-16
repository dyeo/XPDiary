package net.dyeo.xpdiary.network

import io.netty.buffer.ByteBuf
import net.minecraft.util.math.BlockPos
import net.minecraftforge.fml.common.network.simpleimpl.IMessage

class XPBalanceMessage(var pos: BlockPos, var balance: Float) : IMessage
{
    constructor() : this(BlockPos.ORIGIN, 0.0f)
    {
    }

    override fun fromBytes(buf: ByteBuf)
    {
        pos = BlockPos(buf.readInt(), buf.readInt(), buf.readInt())
        balance = buf.readFloat()
    }

    override fun toBytes(buf: ByteBuf)
    {
        buf.writeInt(pos.x)
        buf.writeInt(pos.y)
        buf.writeInt(pos.z)
        buf.writeFloat(balance)
    }
}