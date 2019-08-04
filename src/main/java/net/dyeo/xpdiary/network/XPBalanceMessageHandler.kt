package net.dyeo.xpdiary.network

import net.dyeo.xpdiary.tileentity.TileEntityDiary
import net.minecraft.client.Minecraft
import net.minecraftforge.fml.common.network.simpleimpl.IMessage
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext

abstract class XPBalanceMessageHandler : IMessageHandler<XPBalanceMessage, IMessage>
{
    abstract override fun onMessage(message: XPBalanceMessage?, ctx: MessageContext?): IMessage?

    class Client : XPBalanceMessageHandler()
    {
        override fun onMessage(message: XPBalanceMessage?, ctx: MessageContext?): IMessage?
        {
            val minecraft = Minecraft.getMinecraft()
            val world = minecraft.world

            if (!world.isRemote) return null

            minecraft.addScheduledTask {
                if (message is XPBalanceMessage && world.isBlockLoaded(message.pos))
                {
                    (world.getTileEntity(message.pos) as? TileEntityDiary)
                            ?.let {
                                it.balance = message.balance
                            }
                }
            }

            return null
        }
    }

    class Server : XPBalanceMessageHandler()
    {
        override fun onMessage(message: XPBalanceMessage?, ctx: MessageContext?): IMessage?
        {
            return null
        }
    }
}