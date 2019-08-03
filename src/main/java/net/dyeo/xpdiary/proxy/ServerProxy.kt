package net.dyeo.xpdiary.proxy

import net.dyeo.xpdiary.XPDiary
import net.dyeo.xpdiary.network.EmptyMessage
import net.dyeo.xpdiary.network.EmptyMessageHandler
import net.minecraftforge.fml.common.event.FMLInitializationEvent
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent
import net.minecraftforge.fml.relauncher.Side

class ServerProxy : CommonProxy()
{
    override fun preInit(event: FMLPreInitializationEvent)
    {
        super.preInit(event)
    }

    override fun init(event: FMLInitializationEvent)
    {
        super.init(event)

        XPDiary.networkChannel.registerMessage(
                EmptyMessageHandler::class.java,
                EmptyMessage::class.java,
                XPDiary.NetworkDiscriminators.XPBalance.ordinal,
                Side.CLIENT
        )
    }

    override fun postInit(event: FMLPostInitializationEvent)
    {
        super.postInit(event)
    }
}