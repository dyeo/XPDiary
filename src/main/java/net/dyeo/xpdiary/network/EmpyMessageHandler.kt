package net.dyeo.xpdiary.network

import net.minecraftforge.fml.common.network.simpleimpl.IMessage
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext

class EmptyMessageHandler : IMessageHandler<EmptyMessage, IMessage>
{
    override fun onMessage(message: EmptyMessage?, ctx: MessageContext?): IMessage?
    {
        return null
    }

}