package net.dyeo.xpdiary.network

import net.dyeo.xpdiary.XPDiary
import net.dyeo.xpdiary.tileentity.TileEntityDiary
import net.minecraft.client.Minecraft
import net.minecraftforge.fml.common.network.simpleimpl.IMessage
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext
import net.minecraftforge.fml.relauncher.Side

class XPBalanceMessageHandler : IMessageHandler<XPBalanceMessage, IMessage>
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