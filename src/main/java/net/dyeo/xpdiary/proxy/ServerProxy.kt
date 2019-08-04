package net.dyeo.xpdiary.proxy

import net.dyeo.xpdiary.XPDiary
import net.dyeo.xpdiary.network.XPBalanceMessage
import net.dyeo.xpdiary.network.XPBalanceMessageHandler
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
                XPBalanceMessageHandler.Server::class.java,
                XPBalanceMessage::class.java,
                XPDiary.NetworkDiscriminators.XPBalance.ordinal,
                Side.CLIENT
        )
    }

    override fun postInit(event: FMLPostInitializationEvent)
    {
        super.postInit(event)
    }
}